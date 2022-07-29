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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.dsheirer.source.tuner.configuration.TunerConfiguration;

/**
 * Abstract RSP tuner configuration
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rsp1aTunerConfiguration.class, name = "rsp1aTunerConfiguration"),
        @JsonSubTypes.Type(value = Rsp2TunerConfiguration.class, name = "rsp2TunerConfiguration"),
        @JsonSubTypes.Type(value = RspDuoTuner1Configuration.class, name = "rspDuoTuner1Configuration"),
        @JsonSubTypes.Type(value = RspDuoTuner2Configuration.class, name = "rspDuoTuner2Configuration"),
        @JsonSubTypes.Type(value = RspDxTunerConfiguration.class, name = "rspDxTunerConfiguration"),
})
public abstract class RspTunerConfiguration extends TunerConfiguration
{
    private int mDecimation = 1;

    /**
     * JAXB Constructor
     */
    public RspTunerConfiguration()
    {
    }

    /**
     * Constructs an instance with the specified unique id
     * @param uniqueId for the tuner
     */
    public RspTunerConfiguration(String uniqueId)
    {
        super(uniqueId);
    }

    /**
     * Sample rate decimation setting (1, 2, 4, or 8).
     */
    @JacksonXmlProperty(isAttribute = true, localName = "decimation")
    public int getDecimation()
    {
        return mDecimation;
    }

    /**
     * Sets the sample rate decimation setting.
     */
    public void setDecimation(int decimation)
    {
        mDecimation = decimation;
    }
}
