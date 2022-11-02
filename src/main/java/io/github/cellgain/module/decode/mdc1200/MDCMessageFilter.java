package io.github.cellgain.module.decode.mdc1200;

import io.github.cellgain.filter.Filter;
import io.github.cellgain.filter.FilterElement;
import io.github.cellgain.message.IMessage;

import java.util.*;

public class MDCMessageFilter extends Filter<IMessage>
{
    private Map<MDCMessageType, FilterElement<MDCMessageType>> mElements = new EnumMap<>(MDCMessageType.class);

    public MDCMessageFilter()
    {
        super("MDC-1200 Message Filter");

        for(MDCMessageType type : MDCMessageType.values())
        {
            if(type != MDCMessageType.UNKNOWN)
            {
                mElements.put(type, new FilterElement<MDCMessageType>(type));
            }
        }
    }

    @Override
    public boolean passes(IMessage message)
    {
        if(mEnabled && canProcess(message))
        {
            MDCMessage mdc = (MDCMessage) message;

            if(mElements.containsKey(mdc.getMessageType()))
            {
                return mElements.get(mdc.getMessageType()).isEnabled();
            }
        }

        return false;
    }

    @Override
    public boolean canProcess(IMessage message)
    {
        return message instanceof MDCMessage;
    }

    @Override
    public List<FilterElement<?>> getFilterElements()
    {
        return new ArrayList<FilterElement<?>>(mElements.values());
    }
}
