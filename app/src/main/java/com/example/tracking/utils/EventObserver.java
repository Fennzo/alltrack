package com.example.tracking.utils;

import androidx.lifecycle.Observer;

import com.example.tracking.models.Event;

/**
 * From Google samples
 * An extension over Observer class, designed for Events.
 * Calls each new event only once
 */
public class EventObserver <T> implements Observer<Event<? extends T>> {
    private final EventListener<T> mListener;

    public EventObserver(EventListener<T> eventListener) {
        mListener = eventListener;
    }

    @Override
    public void onChanged(Event<? extends T> event) {
        T content = event.getContentIfNotHandled();
        if (content != null) {
            mListener.onEventReceived(content);
        }
    }

    public interface EventListener<T> {
        void onEventReceived(T content);
    }
}
