package com.androidlearning.firebase.phonebook;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.androidlearning.BR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//when dealing with Firebase, always create NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneBookUser extends BaseObservable {

    private String name;
    private String phoneNumber;
    private String groupUser;

    /**
     * Bindable: to indicate the property can be bound to a UI element
     *
     * When the value changes, it triggers a DataBinding update, causing associated UI element
     * to automatically reflect the updated value
     *
     * Simplifies the process of keeping UI elements synchronized with data changes, reducing the
     * need of manual UI updates
     */
    @Bindable
    public String getName() {
        return name;
    }

    /**
     * notifyPropertyChanged(BR.name): method call used to notify the DataBinding framework that
     * the value of an observable property has changed
     *
     * BR: binding resources - an automatically generated class provided by DataBinding library
     * contains the integer constant that represent the Bindable properties in the project
     *
     * constant are named after the properties name
     */
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    @Bindable
    public String getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
        notifyPropertyChanged(BR.groupUser);
    }
}
