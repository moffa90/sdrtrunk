/*
 * *****************************************************************************
 * Copyright (C) 2014-2022 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * ****************************************************************************
 */

package io.github.dsheirer.source.tuner.sdrplay;

import com.github.dsheirer.sdrplay.SDRplayException;
import com.github.dsheirer.sdrplay.callback.IDeviceEventListener;
import com.github.dsheirer.sdrplay.callback.IStreamListener;
import com.github.dsheirer.sdrplay.callback.StreamCallbackParameters;
import com.github.dsheirer.sdrplay.device.Device;
import com.github.dsheirer.sdrplay.device.TunerSelect;
import com.github.dsheirer.sdrplay.parameter.control.AgcMode;
import com.github.dsheirer.sdrplay.parameter.event.EventType;
import com.github.dsheirer.sdrplay.parameter.event.GainCallbackParameters;
import com.github.dsheirer.sdrplay.parameter.event.PowerOverloadCallbackParameters;
import com.github.dsheirer.sdrplay.parameter.event.RspDuoModeCallbackParameters;
import com.github.dsheirer.sdrplay.parameter.tuner.GainReduction;
import io.github.dsheirer.buffer.INativeBuffer;
import io.github.dsheirer.sample.Listener;
import io.github.dsheirer.source.SourceException;
import io.github.dsheirer.source.tuner.ITunerErrorListener;
import io.github.dsheirer.source.tuner.TunerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract RSP tuner controller
 */
public abstract class RspTunerController<T extends Device> extends TunerController
{
    private static final Logger mLog = LoggerFactory.getLogger(RspTunerController.class);
    private T mDevice;
    private StreamListener mStreamListener = new StreamListener();
    private DeviceEventListener mDeviceEventListener = new DeviceEventListener();
    private int mGain = 0;

    /**
     * Abstract tuner controller class.  The tuner controller manages frequency bandwidth and currently tuned channels
     * that are being fed samples from the tuner.
     *
     * @param device that is the RSP tuner
     * @param tunerErrorListener to monitor errors produced from this tuner controller
     */
    public RspTunerController(T device, ITunerErrorListener tunerErrorListener)
    {
        super(tunerErrorListener);
        mDevice = device;
    }

    /**
     * The RSP tuner device for this controller
     */
    protected T getDevice()
    {
        return mDevice;
    }

    /**
     * Starts this RSP tuner and attempts to claim the device via the API
     * @throws SourceException
     */
    @Override
    public void start() throws SourceException
    {
        try
        {
            getDevice().select();
        }
        catch(SDRplayException se)
        {
            throw new SourceException("Unable to select tuner - in use");
        }
    }

    @Override
    public void stop()
    {
        try
        {
            getDevice().release();
        }
        catch(SDRplayException se)
        {
            mLog.error("Error releasing RSP tuner device", se);
        }
    }

    /**
     * Adds the listener to receive native buffers and starts the sample stream when this is the first listener.
     * @param listener to add
     */
    @Override
    public void addBufferListener(Listener<INativeBuffer> listener)
    {
        if(!mNativeBufferBroadcaster.hasListeners())
        {
            init();
        }

        super.addBufferListener(listener);
    }

    /**
     * Removes the listener from receiving native buffers and stops the sample stream when this is the last listener.
     * @param listener to remove
     */
    @Override
    public void removeBufferListener(Listener<INativeBuffer> listener)
    {
        super.removeBufferListener(listener);

        if(!mNativeBufferBroadcaster.hasListeners())
        {
            uninit();
        }
    }

    /**
     * Starts the sample stream
     */
    private void init()
    {
        mLog.info("Initializing RSP tuner sample stream");
        try
        {
            getDevice().init(mDeviceEventListener, mStreamListener);
        }
        catch(SDRplayException se)
        {
            setErrorMessage("Error starting sample stream");
            mLog.error("Error starting RSP sample stream for " + getDevice().getDeviceType(), se);
        }
    }

    /**
     * Stops the sample stream
     */
    private void uninit()
    {
        mLog.info("Uninitializing RSP tuner sample stream");
        try
        {
            getDevice().uninitialize();
        }
        catch(SDRplayException se)
        {
            setErrorMessage("Error stopping sample stream");
            mLog.error("Error stopping RSP sample stream for " + getDevice().getDeviceType(), se);
        }
    }

    /**
     * Current gain index
     * @return current index, 0 - 28
     */
    public int getGain()
    {
        return mGain;
    }

    /**
     * Sets the gain index to use.
     * @param gain index, 0 - 28
     * @throws SDRplayException if there is an issue setting the value on the tuner
     */
    public void setGain(int gain) throws SDRplayException
    {
        if(gain < GainReduction.MIN_GAIN_INDEX || gain > GainReduction.MAX_GAIN_INDEX)
        {
            mLog.error("Invalid gain index value [" + gain + "].  Valid range is " +
                    GainReduction.MIN_GAIN_INDEX + " - " + GainReduction.MAX_GAIN_INDEX);
            return;
        }

        if(getDevice().isSelected())
        {
            getDevice().getTuner().setGain(gain);
            mGain = gain;
        }
        else
        {
            mLog.error("Can't set gain to [" + gain + "] - device is not selected");
        }
    }

    /**
     * Sets the Automatic Gain Control (AGC) mode
     * @param mode to set
     */
    public void setAgcMode(AgcMode mode)
    {
        if(getDevice().isSelected())
        {
            try
            {
                getDevice().getTuner().setAGC(mode);
            }
            catch(SDRplayException se)
            {
                mLog.error("Unable to set AGC mode to " + mode, se);
            }
        }
        else
        {
            mLog.error("Unable to set AGC mode to " + mode + " - device not selected");
        }
    }

    /**
     * Current AGC mode
     * @return AGC mode
     */
    public AgcMode getAgcMode() throws SDRplayException
    {
        return getDevice().getTuner().getAGC();
    }

    /**
     * Sample stream listener
     */
    private class StreamListener implements IStreamListener
    {
        @Override
        public void processStream(short[] inphase, short[] quadrature,
                                  StreamCallbackParameters parameters, boolean reset)
        {
            mNativeBufferBroadcaster.broadcast(new RspNativeBuffer(inphase, quadrature, System.currentTimeMillis()));

            if(reset)
            {
                mLog.info("Reset received");
            }
            if(parameters.isGainReductionChanged())
            {
                mLog.info("Gain reduction has changed");
            }
            if(parameters.isRfFrequencyChanged())
            {
                mLog.info("RF Frequency has changed");
            }
            if(parameters.isSampleRateChanged())
            {
                mLog.info("Sample rate has changed");
            }
        }
    }

    /**
     * Device event listener implementation
     */
    private class DeviceEventListener implements IDeviceEventListener
    {
        @Override
        public void processEvent(EventType eventType, TunerSelect tunerSelect)
        {
            mLog.info("Event: " + eventType + " Tuner: " + tunerSelect);
        }

        @Override
        public void processGainChange(TunerSelect tunerSelect, GainCallbackParameters parameters)
        {
            mLog.info("Tuner: " + tunerSelect + " Gain: " + parameters.toString());
        }

        @Override
        public void processPowerOverload(TunerSelect tunerSelect, PowerOverloadCallbackParameters parameters)
        {
            mLog.info("Tuner: " + tunerSelect + " Power Overload: " + parameters.toString());
        }

        @Override
        public void processRspDuoModeChange(TunerSelect tunerSelect, RspDuoModeCallbackParameters parameters)
        {
            mLog.info("Tuner: " + tunerSelect + " RSP Duo Mode Callback: " + parameters.toString());
        }

        @Override
        public void processDeviceRemoval(TunerSelect tunerSelect)
        {
            mLog.info("RSP device stop notification received for tuner: " + tunerSelect);
            stop();
        }
    }
}
