package com.androidlearning.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * 2 way data binding: extends BaseObservable
 *
 * BaseObservable: not strictly required, but useful to implement automatic property change notifications,
 * especially when using @Bindable annotation and customer getters/setters for properties in your
 * ViewModel or data source
 *  -> it provide the notify property change method which allow you to notify the data binding library when
 *  a property has changed
 *  -> essential for the library to automatically update UI elements the underlying data changes
 */
public class Person extends BaseObservable {
    private String name;
    @Bindable
    //mark name Bindable observable, which tells data binding to generate code to observe changes
    private String email;

    public void setEmail(String email) {
        this.email = email;
        //notify property change
        notifyPropertyChanged(BR.email);
    }
}
