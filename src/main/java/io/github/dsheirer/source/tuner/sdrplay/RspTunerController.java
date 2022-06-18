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

import com.github.dsheirer.sdrplay.device.Device;
import io.github.dsheirer.source.tuner.ITunerErrorListener;
import io.github.dsheirer.source.tuner.TunerController;

/**
 * Abstract RSP tuner controller
 */
public abstract class RspTunerController extends TunerController
{
    /**
     * Abstract tuner controller class.  The tuner controller manages frequency bandwidth and currently tuned channels
     * that are being fed samples from the tuner.
     *
     * @param tunerErrorListener to monitor errors produced from this tuner controller
     */
    public RspTunerController(Device device, ITunerErrorListener tunerErrorListener)
    {
        super(tunerErrorListener);
    }
}
