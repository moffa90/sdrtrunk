/*******************************************************************************
 * sdr-trunk
 * Copyright (C) 2014-2018 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by  the Free Software Foundation, either version 3 of the License, or  (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License  along with this program.
 * If not, see <http://www.gnu.org/licenses/>
 *
 ******************************************************************************/

package io.github.cellgain.module.decode.mpt1327.identifier;

import io.github.cellgain.identifier.Form;
import io.github.cellgain.identifier.IdentifierClass;
import io.github.cellgain.identifier.Role;
import io.github.cellgain.identifier.integer.IntegerIdentifier;
import io.github.cellgain.protocol.Protocol;

public class MPT1327SiteIdentifier extends IntegerIdentifier
{
    public MPT1327SiteIdentifier(int site)
    {
        super(site, IdentifierClass.NETWORK, Form.SITE, Role.BROADCAST);
    }

    @Override
    public Protocol getProtocol()
    {
        return Protocol.MPT1327;
    }

    public static MPT1327SiteIdentifier create(int site)
    {
        return new MPT1327SiteIdentifier(site);
    }
}
