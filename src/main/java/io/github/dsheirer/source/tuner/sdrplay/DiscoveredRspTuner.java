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

import com.github.dsheirer.sdrplay.DeviceDescriptor;
import com.github.dsheirer.sdrplay.DeviceSelectionMode;
import com.github.dsheirer.sdrplay.SDRplay;
import com.github.dsheirer.sdrplay.SDRplayException;
import com.github.dsheirer.sdrplay.device.Device;
import com.github.dsheirer.sdrplay.device.DeviceType;
import com.github.dsheirer.sdrplay.device.Rsp1aDevice;
import io.github.dsheirer.preference.source.ChannelizerType;
import io.github.dsheirer.source.tuner.TunerClass;
import io.github.dsheirer.source.tuner.manager.DiscoveredTuner;

/**
 * Discovered SDRplay RSP tuner
 */
public class DiscoveredRspTuner extends DiscoveredTuner
{
    private SDRplay mSDRplayApi;
    private DeviceDescriptor mDeviceDescriptor;
    private ChannelizerType mChannelizerType;

    /**
     * Constructs an instance
     * @param sdrplayApi for controlling the RSP device
     * @param deviceDescriptor of the RSP device
     * @param channelizerType to use with this tuner
     */
    public DiscoveredRspTuner(SDRplay sdrplayApi, DeviceDescriptor deviceDescriptor, ChannelizerType channelizerType)
    {
        mSDRplayApi = sdrplayApi;
        mDeviceDescriptor = deviceDescriptor;
        mChannelizerType = channelizerType;
    }

    @Override
    public TunerClass getTunerClass()
    {
        return TunerClass.RSP;
    }

    /**
     * Type of RSP tuner device
     */
    public DeviceType getDeviceType()
    {
        return mDeviceDescriptor.getDeviceType();
    }

    @Override
    public String getId()
    {
        return mDeviceDescriptor.getSerialNumber();
    }

    @Override
    public void start()
    {
        try
        {
            Device device = mSDRplayApi.select(mDeviceDescriptor, DeviceSelectionMode.SINGLE_TUNER_1);

            switch(getDeviceType())
            {
                case RSP1:
                    break;
                case RSP1A:
                    mTuner = new RspTuner(new Rsp1aTunerController((Rsp1aDevice)device, this),
                            this, mChannelizerType);
                    break;
                case RSP2:
                    break;
                case RSPdx:
                    break;
                case UNKNOWN:
                    setErrorMessage("Unrecognized RSP Device");
                    break;
            }
        }
        catch(SDRplayException se)
        {
            setErrorMessage("Unable to select RSP/" + getDeviceType() + " - may be in use by other application");
        }
    }

    @Override
    public String toString()
    {
        return getDeviceType() + " ID:" + getId();
    }
}
