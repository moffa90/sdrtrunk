package io.github.cellgain.channel.state;

import io.github.cellgain.sample.Listener;

public interface IDecoderStateEventProvider
{
	void setDecoderStateListener( Listener<DecoderStateEvent> listener );
	void removeDecoderStateListener();
}
