package io.github.cellgain.module.decode.event.filter;

public interface EventClearHandler {
    void onHistoryLimitChanged(int newHistoryLimit);
    void onClearHistoryClicked();
}
