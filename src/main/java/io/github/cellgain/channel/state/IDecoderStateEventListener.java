package io.github.cellgain.channel.state;

import io.github.cellgain.sample.Listener;

public interface IDecoderStateEventListener
{
	public Listener<DecoderStateEvent> getDecoderStateListener();
}
