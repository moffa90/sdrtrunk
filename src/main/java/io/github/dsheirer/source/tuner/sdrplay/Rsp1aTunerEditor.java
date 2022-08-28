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
import com.github.dsheirer.sdrplay.parameter.tuner.SampleRate;
import io.github.dsheirer.preference.UserPreferences;
import io.github.dsheirer.source.tuner.manager.TunerManager;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SpinnerNumberModel;

/**
 * RSP1A Tuner Editor
 */
public class Rsp1aTunerEditor extends RspTunerEditor<Rsp1aTunerConfiguration>
{
    private Logger mLog = LoggerFactory.getLogger(Rsp1aTunerEditor.class);

    private JComboBox<SampleRate> mSampleRateCombo;
    private JCheckBox mBiasTCheckBox;
    private JCheckBox mRfDabNotchCheckBox;
    private JCheckBox mRfNotchCheckBox;

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

        add(new JLabel("Sample Rate:"));
        add(getSampleRateCombo(), "wrap");

        add(new JSeparator(), "span,growx,push");

        add(new JLabel());
        add(getBiasTCheckBox(), "wrap");
        add(new JLabel());
        add(getRfNotchCheckBox(), "wrap");
        add(new JLabel());
        add(getRfDabNotchCheckBox(), "wrap");
    }

    private DiscoveredRspTuner getDiscoveredRspTuner()
    {
        return (DiscoveredRspTuner)getDiscoveredTuner();
    }

    /**
     * Access tuner controller
     */
    private Rsp1aTunerController getTunerController()
    {
        if(hasTuner())
        {
            return (Rsp1aTunerController)getTuner().getTunerController();
        }

        return null;
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

        getSampleRateCombo().setEnabled(hasTuner() && !getTuner().getTunerController().isLocked());
        getSampleRateCombo().setSelectedItem(getTunerController().getSampleRateEnumeration());

        getBiasTCheckBox().setEnabled(hasTuner());
        try
        {
            getBiasTCheckBox().setSelected(getTunerController().isBiasT());
        }
        catch(SDRplayException se)
        {
            mLog.error("Error setting Bias-T enabled state in editor");
        }

        getRfDabNotchCheckBox().setEnabled(hasTuner());
        try
        {
            getRfDabNotchCheckBox().setSelected(getTunerController().isRfDabNotch());
        }
        catch(SDRplayException se)
        {
            mLog.error("Error setting RF DAB Notch enabled state in editor");
        }

        getRfNotchCheckBox().setEnabled(hasTuner());
        try
        {
            getRfNotchCheckBox().setSelected(getTunerController().isRfNotch());
        }
        catch(SDRplayException se)
        {
            mLog.error("Error setting RF Notch enabled state in editor");
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
            getConfiguration().setSampleRate((SampleRate)getSampleRateCombo().getSelectedItem());
            getConfiguration().setBiasT(getBiasTCheckBox().isSelected());
            getConfiguration().setRfNotch(getRfNotchCheckBox().isSelected());
            getConfiguration().setRfDabNotch(getRfDabNotchCheckBox().isSelected());

            saveConfiguration();
        }
    }

    /**
     * Sample rate selection combobox control
     */
    private JComboBox<SampleRate> getSampleRateCombo()
    {
        if(mSampleRateCombo == null)
        {
            SampleRate[] sampleRates = SampleRate.VALID_VALUES.toArray(new SampleRate[SampleRate.VALID_VALUES.size()]);
            mSampleRateCombo = new JComboBox<>(sampleRates);
            mSampleRateCombo.setEnabled(false);
            mSampleRateCombo.addActionListener(e -> {
                if(hasTuner() && !isLoading())
                {
                    SampleRate selected = (SampleRate)mSampleRateCombo.getSelectedItem();

                    try
                    {
                        getTunerController().setSampleRate(selected);
                    }
                    catch(SDRplayException se)
                    {
                        mLog.error("Error setting sample rate for RSP1A tuner", se);
                    }
                }
            });
        }

        return mSampleRateCombo;
    }

    /**
     * Checkbox control for Bias-T
     */
    private JCheckBox getBiasTCheckBox()
    {
        if(mBiasTCheckBox == null)
        {
            mBiasTCheckBox = new JCheckBox("Bias-T");
            mBiasTCheckBox.setEnabled(false);
            mBiasTCheckBox.addActionListener(e -> {
                if(hasTuner() && !isLoading())
                {
                    try
                    {
                        getTunerController().setBiasT(mBiasTCheckBox.isSelected());
                        save();
                    }
                    catch(SDRplayException se)
                    {
                        mLog.error("Unable to set RSP1A Bias-T enabled to " + mBiasTCheckBox.isSelected(), se);
                    }
                }
            });
        }

        return mBiasTCheckBox;
    }

    /**
     * Checkbox control for RF DAB notch
     */
    private JCheckBox getRfDabNotchCheckBox()
    {
        if(mRfDabNotchCheckBox == null)
        {
            mRfDabNotchCheckBox = new JCheckBox("RF DAB Notch");
            mRfDabNotchCheckBox.setEnabled(false);
            mRfDabNotchCheckBox.addActionListener(e -> {
                if(hasTuner() && !isLoading())
                {
                    try
                    {
                        getTunerController().setRfDabNotch(mRfDabNotchCheckBox.isSelected());
                        save();
                    }
                    catch(SDRplayException se)
                    {
                        mLog.error("Unable to set RSP1A RF DAB notch enabled to " + mRfDabNotchCheckBox.isSelected(), se);
                    }
                }
            });
        }

        return mRfDabNotchCheckBox;
    }

    /**
     * Checkbox control for RF notch
     */
    private JCheckBox getRfNotchCheckBox()
    {
        if(mRfNotchCheckBox == null)
        {
            mRfNotchCheckBox = new JCheckBox("RF Notch");
            mRfNotchCheckBox.setEnabled(false);
            mRfNotchCheckBox.addActionListener(e -> {
                if(hasTuner() && !isLoading())
                {
                    try
                    {
                        getTunerController().setRfNotch(mRfNotchCheckBox.isSelected());
                        save();
                    }
                    catch(SDRplayException se)
                    {
                        mLog.error("Unable to set RSP1A RF notch enabled to " + mRfNotchCheckBox.isSelected(), se);
                    }
                }
            });
        }

        return mRfNotchCheckBox;
    }

}
