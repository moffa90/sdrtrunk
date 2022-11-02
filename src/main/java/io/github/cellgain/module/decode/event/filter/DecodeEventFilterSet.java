package io.github.cellgain.module.decode.event.filter;

import io.github.cellgain.filter.FilterSet;
import io.github.cellgain.module.decode.event.IDecodeEvent;

public class DecodeEventFilterSet extends FilterSet<IDecodeEvent> {
    public DecodeEventFilterSet() {
        super ("All Messages");

        addFilter(new DecodedCallEventFilter());
        addFilter(new DecodedCallEncryptedEventFilter());
        addFilter(new DecodedDataEventFilter());
        addFilter(new DecodedCommandEventFilter());
        addFilter(new DecodedRegistrationEventFilter());
    }
}
