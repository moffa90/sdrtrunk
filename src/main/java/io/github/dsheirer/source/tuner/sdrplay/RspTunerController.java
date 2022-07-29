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
import com.github.dsheirer.sdrplay.device.Device;
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
}
