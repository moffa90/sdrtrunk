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
package io.github.cellgain.module.decode.tait;

import io.github.cellgain.module.decode.event.DecodeEvent;
import io.github.cellgain.module.decode.event.PlottableDecodeEvent;
import io.github.cellgain.module.decode.tait.identifier.TaitIdentifier;
import io.github.cellgain.channel.state.DecoderState;
import io.github.cellgain.channel.state.DecoderStateEvent;
import io.github.cellgain.channel.state.DecoderStateEvent.Event;
import io.github.cellgain.channel.state.State;
import io.github.cellgain.identifier.IdentifierClass;
import io.github.cellgain.identifier.MutableIdentifierCollection;
import io.github.cellgain.message.IMessage;
import io.github.cellgain.module.decode.DecoderType;
import org.jdesktop.swingx.mapviewer.GeoPosition;

import java.util.TreeSet;

public class Tait1200DecoderState extends DecoderState
{
    private TreeSet<TaitIdentifier> mIdents = new TreeSet<>();

    public Tait1200DecoderState()
    {
    }

    @Override
    public DecoderType getDecoderType()
    {
        return DecoderType.TAIT_1200;
    }

    @Override
    public void reset()
    {
        super.reset();
        mIdents.clear();
        resetState();
    }

    @Override
    public void start()
    {

    }

    @Override
    public void stop()
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public void receive(IMessage message)
    {
        if(message instanceof Tait1200GPSMessage)
        {
            Tait1200GPSMessage gps = (Tait1200GPSMessage)message;

            mIdents.add(gps.getFromIdentifier());
            mIdents.add(gps.getToIdentifier());

            GeoPosition position = gps.getGPSLocation();

            if(position != null)
            {
                MutableIdentifierCollection ic = new MutableIdentifierCollection(getIdentifierCollection().getIdentifiers());
                ic.remove(IdentifierClass.USER);
                ic.update(message.getIdentifiers());

                PlottableDecodeEvent event = PlottableDecodeEvent.plottableBuilder(gps.getTimestamp())
                    .eventDescription("GPS")
                    .identifiers(ic)
                    .location(position)
                    .speed(gps.getSpeed())
                    .build();

                broadcast(event);
            }

            broadcast(new DecoderStateEvent(this, Event.DECODE, State.DATA));
        }
        else if(message instanceof Tait1200ANIMessage)
        {
            Tait1200ANIMessage ani = (Tait1200ANIMessage)message;
            mIdents.add(ani.getFromIdentifier());
            mIdents.add(ani.getToIdentifier());

            MutableIdentifierCollection ic = new MutableIdentifierCollection(getIdentifierCollection().getIdentifiers());
            ic.remove(IdentifierClass.USER);
            ic.update(message.getIdentifiers());

            broadcast(DecodeEvent.builder(ani.getTimestamp())
                .eventDescription("ANI")
                .identifiers(ic)
                .details("Automatic Number Identification")
                .build());

            broadcast(new DecoderStateEvent(this, Event.DECODE, State.CALL));
        }
    }

    @Override
    public String getActivitySummary()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("=============================\n");
        sb.append("Decoder:\tTait-1200I\n\n");

        if(!mIdents.isEmpty())
        {
            sb.append("Radio Identifiers:\n");

            for(TaitIdentifier taitIdentifier : mIdents)
            {
                sb.append("\t").append(taitIdentifier).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Responds to reset events issued by the channel state
     */
    @Override
    public void receiveDecoderStateEvent(DecoderStateEvent event)
    {
        switch(event.getEvent())
        {
            case REQUEST_RESET:
                resetState();
                break;
            default:
                break;
        }
    }
}
