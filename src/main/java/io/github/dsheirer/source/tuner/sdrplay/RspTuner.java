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

import io.github.dsheirer.preference.source.ChannelizerType;
import io.github.dsheirer.source.tuner.ITunerErrorListener;
import io.github.dsheirer.source.tuner.Tuner;
import io.github.dsheirer.source.tuner.TunerClass;

/**
 * Generic RSP tuner with a device specific tuner controller implementation.
 */
public class RspTuner extends Tuner
{
    /**
     * Constructs an instance
     * @param tunerController for controlling the tuner
     * @param tunerErrorListener to process errors from this tuner
     * @param channelizerType to use with this tuner
     */
    public RspTuner(RspTunerController tunerController, ITunerErrorListener tunerErrorListener, ChannelizerType channelizerType)
    {
        super(tunerController, tunerErrorListener, channelizerType);
    }

    @Override
    public int getMaximumUSBBitsPerSecond()
    {
        return 0;
    }

    @Override
    public String getUniqueID()
    {
        return null;
    }

    @Override
    public TunerClass getTunerClass()
    {
        return TunerClass.RSP;
    }

    @Override
    public String getPreferredName()
    {
        return null;
    }

    @Override
    public double getSampleSize()
    {
        return 0;
    }
}
