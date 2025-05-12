package com.androidlearning.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterViewModel extends ViewModel {

//    private int counter;

    /**
     * use MutableLiveData<Integer> for LiveData
     *
     * is an observable data holder class, designed to hold and observe
     * data changes, fit for implementing reactive and lifecycle aware
     * data driven UI components
     *
     * LiveData automatically updates the UI, whenever the underlying data changes
     * and ensure no issues like memory leaks by being lifecycle aware
     *
     */
    private final MutableLiveData<Integer> counter = new MutableLiveData<>();
    public void increaseCounter(View view) {
//        counter++;
        int currentCounter = counter.getValue() != null ? counter.getValue() : 0;

        /**
         * setValue() is called directly from caller thread,
         * synchronously notifies observers and changes LiveData value immediately.
         * It can be called only from MainThread.
         * postValue() uses inside something like this
         * new Handler(Looper.mainLooper()).post(() -> setValue()),
         * so it runs setValue via Handler in MainThread. It can be called from any thread.
         */
        counter.setValue(currentCounter + 1);
    }

    public LiveData<Integer> getCounter() {
        return counter;
    }
}
