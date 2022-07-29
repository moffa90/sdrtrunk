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

import io.github.dsheirer.preference.UserPreferences;
import io.github.dsheirer.source.tuner.manager.TunerManager;
import io.github.dsheirer.source.tuner.ui.TunerEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * Abstract RSP tuner editor
 */
public abstract class RspTunerEditor extends TunerEditor<RspTuner,RspTunerConfiguration>
{
    private JComboBox<Decimation> mDecimationCombo;

    /**
     * Constructs an instance
     * @param userPreferences for preference settings
     * @param tunerManager to notify for state changes
     * @param discoveredTuner to be edited.
     */
    public RspTunerEditor(UserPreferences userPreferences, TunerManager tunerManager, DiscoveredRspTuner discoveredTuner)
    {
        super(userPreferences, tunerManager, discoveredTuner);
    }

    /**
     * Decimation rates combo box
     */
    protected JComboBox<Decimation> getDecimationCombo()
    {
        if(mDecimationCombo == null)
        {
            mDecimationCombo = new JComboBox<>(Decimation.values());
            mDecimationCombo.setToolTipText("<html>Sample Rate Decimation.</html>");
            mDecimationCombo.setEnabled(false);
            mDecimationCombo.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {

                }
            });
        }

        return mDecimationCombo;
    }

    /**
     * Convenience method to always get the decimation rate from the combobox.
     */
    protected Decimation getDecimationRate()
    {
        if(getDecimationCombo().getSelectedItem() != null)
        {
            return (Decimation)getDecimationCombo().getSelectedItem();
        }

        return Decimation.D1;
    }
}
