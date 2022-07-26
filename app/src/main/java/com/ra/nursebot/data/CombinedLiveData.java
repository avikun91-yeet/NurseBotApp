package com.ra.nursebot.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class CombinedLiveData<T, K, S> extends MediatorLiveData<S> {
    private LiveData<T> source1;
    private LiveData<K> source2;

    private T data1;
    private K data2;
    private CombineFunction<T, K, S> combineFunction;

    public CombinedLiveData(LiveData<T> source1, LiveData<K> source2, CombineFunction<T, K, S> combineFunction) {
        this.source1 = source1;
        this.source2 = source2;
        this.combineFunction = combineFunction;

        addSource(source1, t -> {
            data1 = t;
            setValue(combineFunction.apply(data1, data2));
        });
        addSource(source2, k -> {
            data2 = k;
            setValue(combineFunction.apply(data1, data2));
        });
    }

    public interface CombineFunction<T, K, S> {
        S apply(T t, K k);
    }

}
