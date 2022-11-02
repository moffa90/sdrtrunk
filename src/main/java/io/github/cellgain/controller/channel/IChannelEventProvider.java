package io.github.cellgain.controller.channel;

import io.github.cellgain.sample.Listener;

public interface IChannelEventProvider
{
	void setChannelEventListener( Listener<ChannelEvent> listener );
	void removeChannelEventListener();
}
