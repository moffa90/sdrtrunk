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

package io.github.cellgain.channel.state;

import io.github.cellgain.module.decode.event.ActivitySummaryProvider;
import io.github.cellgain.module.decode.event.IDecodeEvent;
import io.github.cellgain.module.decode.event.IDecodeEventProvider;
import io.github.cellgain.identifier.IdentifierUpdateListener;
import io.github.cellgain.identifier.IdentifierUpdateProvider;
import io.github.cellgain.message.IMessage;
import io.github.cellgain.message.IMessageListener;
import io.github.cellgain.module.Module;
import io.github.cellgain.module.decode.DecoderType;
import io.github.cellgain.sample.Broadcaster;
import io.github.cellgain.sample.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDecoderState extends Module implements ActivitySummaryProvider, Listener<IMessage>,
        IDecodeEventProvider, IDecoderStateEventListener, IDecoderStateEventProvider, IMessageListener,
    IdentifierUpdateProvider, IdentifierUpdateListener
{
    private final static Logger mLog = LoggerFactory.getLogger(AbstractDecoderState.class);
    protected String DIVIDER1 = "======================================================\n";
    protected String DIVIDER2 = "------------------------------------------------------\n";
    /* This has to be a broadcaster in order for references to persist */
    protected Broadcaster<IDecodeEvent> mDecodeEventBroadcaster = new Broadcaster<>();
    protected Listener<DecoderStateEvent> mDecoderStateListener;
    private DecoderStateEventListener mDecoderStateEventListener = new DecoderStateEventListener();

    public abstract DecoderType getDecoderType();

    /**
     * Provides subclass reference to the decode event broadcaster
     */
    protected Broadcaster<IDecodeEvent> getDecodeEventBroadcaster()
    {
        return mDecodeEventBroadcaster;
    }

    @Override
    public Listener<IMessage> getMessageListener()
    {
        return this;
    }

    /**
     * Implements the IDecoderStateEventListener interface to receive state
     * reset events.
     */
    public abstract void receiveDecoderStateEvent(DecoderStateEvent event);

    /**
     * Activity Summary - textual summary of activity observed by the channel state.
     */
    public abstract String getActivitySummary();

    /**
     * Broadcasts a decode event to any registered listeners
     */
    protected void broadcast(IDecodeEvent event)
    {
        mDecodeEventBroadcaster.broadcast(event);
    }

    /**
     * Adds a call event listener
     */
    @Override
    public void addDecodeEventListener(Listener<IDecodeEvent> listener)
    {
        mDecodeEventBroadcaster.addListener(listener);
    }

    /**
     * Removes the call event listener
     */
    @Override
    public void removeDecodeEventListener(Listener<IDecodeEvent> listener)
    {
        mDecodeEventBroadcaster.removeListener(listener);
    }

    @Override
    public Listener<DecoderStateEvent> getDecoderStateListener()
    {
        return mDecoderStateEventListener;
    }

    /**
     * Broadcasts a channel state event to any registered listeners
     */
    protected void broadcast(DecoderStateEvent event)
    {
        if(mDecoderStateListener != null)
        {
            mDecoderStateListener.receive(event);
        }
    }

    /**
     * Adds a decoder state event listener
     */
    @Override
    public void setDecoderStateListener(Listener<DecoderStateEvent> listener)
    {
        mDecoderStateListener = listener;
    }

    /**
     * Removes the decoder state event listener
     */
    @Override
    public void removeDecoderStateListener()
    {
        mDecoderStateListener = null;
    }

    private class DecoderStateEventListener implements Listener<DecoderStateEvent>
    {
        @Override
        public void receive(DecoderStateEvent event)
        {
            receiveDecoderStateEvent(event);
        }
    }

}
