package io.github.cellgain.message;

import io.github.cellgain.sample.Listener;

public interface IMessageListener
{
    Listener<IMessage> getMessageListener();
}
