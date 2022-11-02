/*
 *
 *  * ******************************************************************************
 *  * Copyright (C) 2014-2019 Dennis Sheirer
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *  * *****************************************************************************
 *
 *
 */

package io.github.cellgain.module.decode.passport.identifier;

import io.github.cellgain.identifier.Role;
import io.github.cellgain.identifier.radio.RadioIdentifier;
import io.github.cellgain.protocol.Protocol;

/**
 * Passport Radio ID
 */
public class PassportRadioId extends RadioIdentifier implements Comparable<PassportRadioId>
{
    /**
     * Constructs a Passport talkgroup with a TO role.
     */
    public PassportRadioId(Integer value)
    {
        super(value, Role.FROM);
    }

    @Override
    public Protocol getProtocol()
    {
        return Protocol.PASSPORT;
    }

    /**
     * Creates a Passport talkgroup (ie mobile id) with the value as the TO role
     */
    public static PassportRadioId create(int value)
    {
        return new PassportRadioId(value);
    }

    @Override
    public int compareTo(PassportRadioId o)
    {
        return Integer.compare(getValue(), o.getValue());
    }
}
