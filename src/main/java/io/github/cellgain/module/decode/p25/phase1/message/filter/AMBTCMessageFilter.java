/*
 * ******************************************************************************
 * sdrapp
 * Copyright (C) 2014-2019 Dennis Sheirer
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

package io.github.cellgain.module.decode.p25.phase1.message.filter;

import io.github.cellgain.module.decode.p25.phase1.message.pdu.ambtc.AMBTCMessage;
import io.github.cellgain.filter.Filter;
import io.github.cellgain.filter.FilterElement;
import io.github.cellgain.message.IMessage;

import java.util.Collections;
import java.util.List;

public class AMBTCMessageFilter extends Filter<IMessage>
{
    public AMBTCMessageFilter()
    {
        super("Alternate Multi-Block Trunking Control");
    }

    @Override
    public boolean passes(IMessage message)
    {
        return mEnabled && canProcess(message);
    }

    @Override
    public boolean canProcess(IMessage message)
    {
        return message instanceof AMBTCMessage;
    }

    @Override
    public List<FilterElement<?>> getFilterElements()
    {
        return Collections.EMPTY_LIST;
    }
}