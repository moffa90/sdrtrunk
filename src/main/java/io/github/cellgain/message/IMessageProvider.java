package io.github.cellgain.message;

import io.github.cellgain.sample.Listener;

public interface IMessageProvider
{
    void setMessageListener(Listener<IMessage> listener);
    void removeMessageListener();
}
