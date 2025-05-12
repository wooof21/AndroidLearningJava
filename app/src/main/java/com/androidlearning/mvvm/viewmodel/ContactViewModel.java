package com.androidlearning.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.androidlearning.mvvm.model.entity.Contact;
import com.androidlearning.mvvm.model.repository.ContactRepo;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {


    /**
     * If you need to use context inside the ViewModel
     * use AndroidViewModel(AVM)
     * because it contains the application context
     *
     * AndroidViewModel: is a subclass of ViewModel and similar to them,
     * they are designed to store and manage UI-related data
     * responsible to prepare & provide data for UI and automatically allow
     * data to survive configuration change
     */
    private ContactRepo repo;

    //LiveData
    private LiveData<List<Contact>> contacts;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        this.repo = new ContactRepo(application);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return repo.getAllContacts();
    }

    public void addContact(Contact contact) {
        repo.addContact(contact);
    }

    public void deleteContact(Contact contact) {
        repo.deleteContact(contact);
    }
}
