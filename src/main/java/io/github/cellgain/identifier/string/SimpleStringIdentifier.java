/*
 * *****************************************************************************
 *  Copyright (C) 2014-2020 Dennis Sheirer
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

package io.github.cellgain.identifier.string;

import io.github.cellgain.identifier.Form;
import io.github.cellgain.identifier.IdentifierClass;
import io.github.cellgain.identifier.Role;
import io.github.cellgain.protocol.Protocol;

/**
 * Simple string identifier
 */
public class SimpleStringIdentifier extends StringIdentifier
{
    public SimpleStringIdentifier(String value, IdentifierClass identifierClass, Form form, Role role)
    {
        super(value, identifierClass, form, role);
    }

    @Override
    public Protocol getProtocol()
    {
        return Protocol.UNKNOWN;
    }
}