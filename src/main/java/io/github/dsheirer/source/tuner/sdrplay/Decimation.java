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

/**
 * Decimation rates supported by the RSP tuner series.
 */
public enum Decimation
{
    D1(1),
    D2(2),
    D4(4),
    D8(8);

    private int mRate;

    Decimation(int rate)
    {
        mRate = rate;
    }

    /**
     * Decimation rate
     */
    public int getRate()
    {
        return mRate;
    }

    /**
     * Overrides the default string representation
     */
    @Override
    public String toString()
    {
        return getRate() + "x";
    }
}
