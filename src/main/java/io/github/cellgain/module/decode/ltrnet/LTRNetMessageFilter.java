package io.github.cellgain.module.decode.ltrnet;

import io.github.cellgain.module.decode.ltrnet.message.LtrNetMessage;
import io.github.cellgain.filter.Filter;
import io.github.cellgain.filter.FilterElement;
import io.github.cellgain.message.IMessage;

import java.util.*;

public class LTRNetMessageFilter extends Filter<IMessage>
{
    private Map<LtrNetMessageType, FilterElement<LtrNetMessageType>> mElements = new EnumMap<>(LtrNetMessageType.class);

    public LTRNetMessageFilter()
    {
        super("LTR-Net Message Filter");

        for(LtrNetMessageType type: LtrNetMessageType.values())
        {
            mElements.put(type, new FilterElement<>(type));
        }
    }

    @Override
    public boolean passes(IMessage message)
    {
        if(mEnabled && canProcess(message))
        {
            LtrNetMessage ltr = (LtrNetMessage) message;

            if(mElements.containsKey(ltr.getLtrNetMessageType()))
            {
                return mElements.get(ltr.getLtrNetMessageType()).isEnabled();
            }
        }

        return false;
    }

    @Override
    public boolean canProcess(IMessage message)
    {
        return message instanceof LtrNetMessage;
    }

    @Override
    public List<FilterElement<?>> getFilterElements()
    {
        return new ArrayList<FilterElement<?>>(mElements.values());
    }
}
