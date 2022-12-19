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
package io.github.cellgain.source.tuner.test;

import io.github.cellgain.source.tuner.ITunerErrorListener;
import io.github.cellgain.source.tuner.Tuner;
import io.github.cellgain.source.tuner.TunerClass;
import io.github.cellgain.source.tuner.TunerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Testing tuner that implements an internal oscillator to output a unity gain tone at a specified frequency offset
 * from a configurable center tune frequency and sample rate
 */
public class TestTuner extends Tuner
{
    private final static Logger mLog = LoggerFactory.getLogger(TestTuner.class);
    private static int mInstanceCounter = 1;
    private final int mInstanceID = mInstanceCounter++;

    public TestTuner(ITunerErrorListener tunerErrorListener)
    {
        super(new TestTunerController(), tunerErrorListener);
    }

    /**
     * Returns the tuner controller cast as a test tuner controller.
     */
    public TestTunerController getTunerController()
    {
        return (TestTunerController)super.getTunerController();
    }

    @Override
    public String getPreferredName()
    {
        return "Test Tuner-" + mInstanceID;
    }

    @Override
    public String getUniqueID()
    {
        return getPreferredName();
    }

    @Override
    public TunerClass getTunerClass()
    {
        return TunerClass.TEST_TUNER;
    }

    @Override
    public TunerType getTunerType()
    {
        return TunerType.TEST;
    }

    @Override
    public double getSampleSize()
    {
        return 16.0;
    }

    @Override
    public int getMaximumUSBBitsPerSecond()
    {
        return 0;
    }
}