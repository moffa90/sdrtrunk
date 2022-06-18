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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.github.dsheirer.sdrplay.parameter.tuner.Rsp2AmPort;
import com.github.dsheirer.sdrplay.parameter.tuner.Rsp2Antenna;
import io.github.dsheirer.source.tuner.TunerType;

/**
 * RSP2 tuner configuration
 */
public class Rsp2TunerConfiguration extends RspTunerConfiguration
{
    private boolean mExternalReferenceOutput;
    private boolean mBiasT;
    private boolean mRfNotch;
    private Rsp2AmPort mAmPort;
    private Rsp2Antenna mAntenna;

    @Override
    public TunerType getTunerType()
    {
        return TunerType.RSP_2;
    }

    /**
     * Indicates if the external reference output is enabled.
     */
    @JacksonXmlProperty(isAttribute = true, localName = "external_reference_output")
    public boolean isExternalReferenceOutput()
    {
        return mExternalReferenceOutput;
    }

    /**
     * Sets the enabled state of the external reference output.
     */
    public void setExternalReferenceOutput(boolean enabled)
    {
        mExternalReferenceOutput = enabled;
    }

    /**
     * Indicates if the Bias-T is enabled
     */
    @JacksonXmlProperty(isAttribute = true, localName = "bias_t")
    public boolean isBiasT()
    {
        return mBiasT;
    }

    /**
     * Sets the enabled state of the Bias-T
     */
    public void setBiasT(boolean enabled)
    {
        mBiasT = enabled;
    }

    /**
     * Indicates if the RF Notch is enabled
     */
    @JacksonXmlProperty(isAttribute = true, localName = "rf_notch")
    public boolean isRfNotch()
    {
        return mRfNotch;
    }

    /**
     * Sets the enabled state of the RF Notch.
     */
    public void setRfNotch(boolean rfNotch)
    {
        mRfNotch = rfNotch;
    }

    /**
     * AM Port setting
     */
    @JacksonXmlProperty(isAttribute = true, localName = "am_port")
    public Rsp2AmPort getAmPort()
    {
        return mAmPort;
    }

    /**
     * Sets the AM port
     */
    public void setAmPort(Rsp2AmPort amPort)
    {
        mAmPort = amPort;
    }

    /**
     * Antenna setting
     */
    @JacksonXmlProperty(isAttribute = true, localName = "antenna")
    public Rsp2Antenna getAntenna()
    {
        return mAntenna;
    }

    /**
     * Sets the antenna.
     */
    public void setAntenna(Rsp2Antenna antenna)
    {
        mAntenna = antenna;
    }
}
