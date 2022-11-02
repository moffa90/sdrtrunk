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
package io.github.cellgain.module.decode.p25.identifier;

import io.github.cellgain.identifier.Form;
import io.github.cellgain.identifier.Identifier;
import io.github.cellgain.identifier.IdentifierClass;
import io.github.cellgain.identifier.Role;
import io.github.cellgain.identifier.integer.IntegerIdentifier;
import io.github.cellgain.protocol.Protocol;

public class APCO25Nac extends IntegerIdentifier
{
    public APCO25Nac(int value)
    {
        super(value, IdentifierClass.NETWORK, Form.NETWORK_ACCESS_CODE, Role.BROADCAST);
    }

    @Override
    public Protocol getProtocol()
    {
        return Protocol.APCO25;
    }

    @Override
    public boolean isValid()
    {
        return getValue() > 0;
    }

    /**
     * Creates a new APCO-25 identifier
     */
    public static Identifier create(int nac)
    {
        return new APCO25Nac(nac);
    }
}
