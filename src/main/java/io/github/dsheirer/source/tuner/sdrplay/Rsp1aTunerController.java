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
import com.github.dsheirer.sdrplay.device.Rsp1aDevice;
import com.github.dsheirer.sdrplay.parameter.control.AgcMode;
import com.github.dsheirer.sdrplay.parameter.tuner.SampleRate;
import io.github.dsheirer.source.SourceException;
import io.github.dsheirer.source.tuner.ITunerErrorListener;
import io.github.dsheirer.source.tuner.TunerType;
import io.github.dsheirer.source.tuner.configuration.TunerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tuner controller for RSP1a
 */
public class Rsp1aTunerController extends RspTunerController<Rsp1aDevice>
{
    private Logger mLog = LoggerFactory.getLogger(Rsp1aTunerController.class);
    private static final long MINIMUM_FREQUENCY = 100_000;
    private static final long MAXIMUM_FREQUENCY = 2_000_000_000;
    private static final double USABLE_BANDWIDTH = 8.0d / 10.0d; //Initial value
    private static final int MIDDLE_UNUSABLE_BANDWIDTH = 0;

    private SampleRate mSampleRate = SampleRate.RATE_10_000;

    /**
     * Constructs an instance
     *
     * @param device that is the RSP tuner
     * @param tunerErrorListener to monitor errors produced from this tuner controller
     */
    public Rsp1aTunerController(Rsp1aDevice device, ITunerErrorListener tunerErrorListener)
    {
        super(device, tunerErrorListener);

        setMinimumFrequency(MINIMUM_FREQUENCY);
        setMaximumFrequency(MAXIMUM_FREQUENCY);
        setMiddleUnusableHalfBandwidth(MIDDLE_UNUSABLE_BANDWIDTH);
        setUsableBandwidthPercentage(USABLE_BANDWIDTH);
    }

    @Override
    public TunerType getTunerType()
    {
        return TunerType.RSP_1A;
    }

    @Override
    public int getBufferSampleCount()
    {
        return 0;
    }

    @Override
    public void apply(TunerConfiguration config) throws SourceException
    {
        if(config instanceof Rsp1aTunerConfiguration rtc)
        {
            super.apply(config);

            try
            {
                setSampleRate(rtc.getSampleRate());
            }
            catch(SDRplayException se)
            {
                mLog.error("Error setting RSP1A sample rate to " + rtc.getSampleRate());
            }

            try
            {
                setAgcMode(AgcMode.ENABLE);
                setGain(rtc.getGain());
            }
            catch(SDRplayException se)
            {
                mLog.error("Error setting RSP1A gain index to " + rtc.getGain());
            }

            try
            {
                setBiasT(rtc.isBiasT());
            }
            catch(SDRplayException se)
            {
                mLog.error("Error setting RSP1A bias-T enabled to " + rtc.isBiasT());
            }

            try
            {
                setRfNotch(rtc.isRfNotch());
            }
            catch(SDRplayException se)
            {
                mLog.error("Error setting RSP1A RF Notch enabled to " + rtc.isRfNotch());
            }

            try
            {
                setRfDabNotch(rtc.isRfDabNotch());
            }
            catch(SDRplayException se)
            {
                mLog.error("Error setting RSP1A RF DAB Notch enabled to " + rtc.isRfDabNotch());
            }
        }
        else
        {
            mLog.error("Invalid RSP1A tuner configuration type: " + config.getClass());
        }
    }

    @Override
    public long getTunedFrequency() throws SourceException
    {
        try
        {
            return getDevice().getTuner().getFrequency();
        }
        catch(SDRplayException se)
        {
            throw new SourceException("Unable to access center tuned frequency", se);
        }
    }

    @Override
    public void setTunedFrequency(long frequency) throws SourceException
    {
        try
        {
            getDevice().getTuner().setFrequency(frequency);
        }
        catch(SDRplayException se)
        {
            throw new SourceException("Unable to set center frequency [" + frequency + "]", se);
        }
    }

    /**
     * Sets the sample rate
     * @param sampleRate to apply
     * @throws SDRplayException if there is an error
     */
    public void setSampleRate(SampleRate sampleRate) throws SDRplayException
    {
        getDevice().setSampleRate(sampleRate);
        mSampleRate = sampleRate;
        try
        {
            mFrequencyController.setSampleRate((int)sampleRate.getEffectiveSampleRate());
        }
        catch(SourceException se)
        {
            mLog.error("Error setting sample rate in frequency controller");
        }

        mLog.info("Updating for sample rate: " + sampleRate);
        mLog.info("Sample Rate Bandwidth: " + sampleRate.getBandwidth().getBandwidth());
        mLog.info("Sample Rate: " + sampleRate.getSampleRate());
        mLog.info("Usable: " + ((double)sampleRate.getBandwidth().getBandwidth() / (double)sampleRate.getEffectiveSampleRate()));

        //Update the usable bandwidth based on the sample rate and filtered bandwidth
        setUsableBandwidthPercentage((double)sampleRate.getBandwidth().getBandwidth() / (double)sampleRate.getEffectiveSampleRate());
    }

    public SampleRate getSampleRateEnumeration()
    {
        return mSampleRate;
    }

    @Override
    public double getCurrentSampleRate() throws SourceException
    {
        return mSampleRate.getSampleRate();
    }

    /**
     * Sets the Bias-T enabled state
     * @param enabled true to enable the bias-T
     * @throws SDRplayException if the tuner is not selected
     */
    public void setBiasT(boolean enabled) throws SDRplayException
    {
        getDevice().getTuner().setBiasT(enabled);
    }

    /**
     * Indicates if the bias-T is currently enabled
     * @return bias-T enabled
     * @throws SDRplayException if the tuner is not selected
     */
    public boolean isBiasT() throws SDRplayException
    {
        return getDevice().getTuner().isBiasT();
    }

    /**
     * Sets the enabled state of the RF DAB notch
     * @param enabled true to enable
     * @throws SDRplayException if the tuner is not selected
     */
    public void setRfDabNotch(boolean enabled) throws SDRplayException
    {
        getDevice().getTuner().setRfDabNotch(enabled);
    }

    /**
     * Indicates if the RF DAB notch is enabled
     * @return true if enabled
     * @throws SDRplayException if the tuner is not selected
     */
    public boolean isRfDabNotch() throws SDRplayException
    {
        return getDevice().getTuner().isRfDabNotch();
    }

    /**
     * Sets the enabled state of the RF notch
     * @param enabled true to enable
     * @throws SDRplayException if the tuner is not selected
     */
    public void setRfNotch(boolean enabled) throws SDRplayException
    {
        getDevice().getTuner().setRfNotch(enabled);
    }

    /**
     * Indicates if the RF notch is enabled
     * @return true if enabled
     * @throws SDRplayException if the tuner is not selected
     */
    public boolean isRfNotch() throws SDRplayException
    {
        return getDevice().getTuner().isRfNotch();
    }
}
