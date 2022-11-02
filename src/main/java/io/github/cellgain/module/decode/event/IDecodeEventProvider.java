package io.github.cellgain.module.decode.event;

import io.github.cellgain.sample.Listener;

public interface IDecodeEventProvider
{
	void addDecodeEventListener(Listener<IDecodeEvent> listener );
	void removeDecodeEventListener(Listener<IDecodeEvent> listener );
}
