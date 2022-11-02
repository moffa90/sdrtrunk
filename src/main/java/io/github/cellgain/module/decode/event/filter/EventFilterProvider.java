package io.github.cellgain.module.decode.event.filter;

import io.github.cellgain.filter.FilterSet;

public interface EventFilterProvider<T> {
    FilterSet<T> getFilterSet();
    void setFilterSet(FilterSet<T> filterSet);
}
