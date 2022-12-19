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
package io.github.cellgain.module.decode.ltrstandard.message;

import io.github.cellgain.bits.CorrectedBinaryMessage;
import io.github.cellgain.edac.CRC;
import io.github.cellgain.identifier.Identifier;
import io.github.cellgain.message.MessageDirection;
import io.github.cellgain.module.decode.ltrstandard.LtrStandardMessageType;

import java.util.Collections;
import java.util.List;

/**
 * Unknown message type
 */
public class UnknownMessage extends LTRMessage
{
    public UnknownMessage(CorrectedBinaryMessage message, MessageDirection direction, CRC crc)
    {
        super(message, direction, crc);
    }

    @Override
    public LtrStandardMessageType getMessageType()
    {
        return LtrStandardMessageType.UNKNOWN;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UNKNOWN AREA:").append(getArea());
        sb.append(" LCN:").append(getChannel());
        sb.append(" HOME:").append(getHomeRepeater());
        sb.append(" GRP:").append(getGroup());
        sb.append(" FREE:").append(getFree());
        return sb.toString();
    }

    @Override
    public List<Identifier> getIdentifiers()
    {
        return Collections.EMPTY_LIST;
    }
}