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
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SpinnerNumberModel;

/**
 * RSP1A Tuner Editor
 */
public class Rsp1aTunerEditor extends RspTunerEditor
{
    /**
     * Constructs an instance
     * @param userPreferences for settings
     * @param tunerManager for state updates
     * @param discoveredTuner to edit or control.
     */
    public Rsp1aTunerEditor(UserPreferences userPreferences, TunerManager tunerManager, DiscoveredRspTuner discoveredTuner)
    {
        super(userPreferences, tunerManager, discoveredTuner);
        init();
        tunerStatusUpdated();
    }

    private void init()
    {
        setLayout(new MigLayout("fill,wrap 3", "[right][grow,fill][fill]",
                "[][][][][][][][][][][grow]"));

        add(new JLabel("Tuner:"));
        add(getTunerIdLabel(), "wrap");
//        add(getTunerInfoButton());

        add(new JLabel("Status:"));
        add(getTunerStatusLabel(), "wrap");

        add(getButtonPanel(), "span,align left");

        add(new JSeparator(), "span,growx,push");

        add(new JLabel("Frequency (MHz):"));
        add(getFrequencyPanel(), "wrap");

//        add(new JLabel("Sample Rate:"));
//        add(getSampleRateCombo(), "wrap");

        add(new JSeparator(), "span,growx,push");

        add(new JLabel("Decimation"));
        add(getDecimationCombo(), "wrap");
//        add(new JLabel("Gain Control"));
//        add(getAmplifierToggle(), "wrap");
//
//        add(new JLabel("LNA:"));
//        add(getLnaGainCombo(), "wrap");
//
//        add(new JLabel("VGA:"));
//        add(getVgaGainCombo(), "wrap");
    }

    private DiscoveredRspTuner getDiscoveredRspTuner()
    {
        return (DiscoveredRspTuner)getDiscoveredTuner();
    }

    @Override
    protected void tunerStatusUpdated()
    {
        setLoading(true);

        getTunerIdLabel().setText(getDiscoveredRspTuner().getDeviceType() + " SER #" + getDiscoveredTuner().getId());

        String status = getDiscoveredTuner().getTunerStatus().toString();
        if(getDiscoveredTuner().hasErrorMessage())
        {
            status += " - " + getDiscoveredTuner().getErrorMessage();
        }
        getTunerStatusLabel().setText(status);
        getButtonPanel().updateControls();
        getFrequencyPanel().updateControls();

        getDecimationCombo().setEnabled(hasTuner());

//        getSampleRateCombo().setEnabled(hasTuner() && !getTuner().getTunerController().isLocked());
//        updateSampleRateToolTip();
//        getTunerInfoButton().setEnabled(hasTuner());

//        getAmplifierToggle().setEnabled(hasTuner());
//        getLnaGainCombo().setEnabled(hasTuner());
//        getVgaGainCombo().setEnabled(hasTuner());

        if(hasConfiguration())
        {
//            getSampleRateCombo().setSelectedItem(getConfiguration().getSampleRate());
//            getAmplifierToggle().setSelected(getConfiguration().getAmplifierEnabled());
//            getLnaGainCombo().setSelectedItem(getConfiguration().getLNAGain());
//            getVgaGainCombo().setSelectedItem(getConfiguration().getVGAGain());
        }

        setLoading(false);
    }

    @Override
    public void save()
    {
        if(hasConfiguration() && !isLoading())
        {
            getConfiguration().setFrequency(getFrequencyControl().getFrequency());
            double value = ((SpinnerNumberModel) getFrequencyCorrectionSpinner().getModel()).getNumber().doubleValue();
            getConfiguration().setFrequencyCorrection(value);
            getConfiguration().setAutoPPMCorrectionEnabled(getAutoPPMCheckBox().isSelected());

            getConfiguration().setDecimation(getDecimationRate().getRate());


//            getConfiguration().setSampleRate((HackRFTunerController.HackRFSampleRate)getSampleRateCombo().getSelectedItem());
//            getConfiguration().setAmplifierEnabled(getAmplifierToggle().isSelected());
//            getConfiguration().setLNAGain((HackRFTunerController.HackRFLNAGain)getLnaGainCombo().getSelectedItem());
//            getConfiguration().setVGAGain((HackRFTunerController.HackRFVGAGain)getVgaGainCombo().getSelectedItem());
            saveConfiguration();
        }
    }
}
