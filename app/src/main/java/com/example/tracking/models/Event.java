package com.example.tracking.models;

/**
 * From Google samples
 * A class serving it's content only once, and returns null after the first access.
 * Returns null on each call after the first time, even having a non-null value
 * @param <T> Data model for this event
 */
public class Event <T> {
    boolean mHasBeenHandled = false;
    private final T mContent;

    public Event(T content) {
        mContent = content;
    }

    public T getContentIfNotHandled() {
        if (mHasBeenHandled) {
            return null;
        } else {
            mHasBeenHandled = true;
            return mContent;
        }
    }

    public static class Unit {}
}
