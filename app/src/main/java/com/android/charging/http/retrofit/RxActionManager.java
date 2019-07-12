package com.android.charging.http.retrofit;

import rx.Subscription;

/**
 * author cowards
 * created on 2019\3\24 0024
 **/
public interface RxActionManager<T> {

    void add(T tag, Subscription subscription);

    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}
