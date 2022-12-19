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

package io.github.cellgain.spectrum.menu;

import io.github.cellgain.dsp.window.WindowType;
import io.github.cellgain.spectrum.ComplexDftProcessor;

import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FFTWindowTypeItem extends JCheckBoxMenuItem
{
    private static final long serialVersionUID = 1L;

    private ComplexDftProcessor mComplexDftProcessor;
    private WindowType mWindowType;

    public FFTWindowTypeItem(ComplexDftProcessor processor, WindowType windowType)
    {
        super(windowType.toString());

        mComplexDftProcessor = processor;
        mWindowType = windowType;

        if(processor.getWindowType() == mWindowType)
        {
            setSelected(true);
        }

        addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent arg0)
            {
                mComplexDftProcessor.setWindowType(mWindowType);
            }
        });
    }
}
