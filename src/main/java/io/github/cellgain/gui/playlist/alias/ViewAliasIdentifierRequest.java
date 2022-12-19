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

package io.github.cellgain.gui.playlist.alias;

import io.github.cellgain.alias.id.AliasID;

/**
 * Request to view an alias identifier in the alias view by identifier editor
 */
public class ViewAliasIdentifierRequest extends AliasTabRequest
{
    private AliasID mAliasId;

    /**
     * Constructs an instance
     * @param aliasId to view
     */
    public ViewAliasIdentifierRequest(AliasID aliasId)
    {
        mAliasId = aliasId;
    }

    public AliasID getAliasId()
    {
        return mAliasId;
    }
}