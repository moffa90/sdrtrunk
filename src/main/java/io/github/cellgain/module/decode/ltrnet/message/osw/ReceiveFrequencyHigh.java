/*
 * ******************************************************************************
 * sdrapp
 * Copyright (C) 2014-2018 Dennis Sheirer
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
 * *****************************************************************************
 */

package io.github.cellgain.module.decode.ltrnet.message.osw;

import io.github.cellgain.bits.CorrectedBinaryMessage;
import io.github.cellgain.identifier.Identifier;
import io.github.cellgain.module.decode.ltrnet.LtrNetMessageType;

import java.util.Collections;
import java.util.List;

public class ReceiveFrequencyHigh extends FrequencyHigh
{
    /**
     * Constructs a message
     *
     * @param message
     * @param timestamp
     */
    public ReceiveFrequencyHigh(CorrectedBinaryMessage message, long timestamp)
    {
        super(message, timestamp);
    }

    @Override
    public LtrNetMessageType getLtrNetMessageType()
    {
        return LtrNetMessageType.OSW_RECEIVE_FREQUENCY_HIGH;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("LCN:").append(getChannel());
        sb.append(" RECEIVE FREQUENCY:").append(getFrequency()).append(" - HIGH");
        return sb.toString();
    }

    @Override
    public List<Identifier> getIdentifiers()
    {
        return Collections.EMPTY_LIST;
    }
}