package io.github.cellgain.controller.channel;

import io.github.cellgain.sample.Listener;

public interface IChannelEventListener
{
	public Listener<ChannelEvent> getChannelEventListener();
}
