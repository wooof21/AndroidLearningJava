package com.androidlearning.mvvm.model.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.androidlearning.mvvm.model.dao.ContactDAO;
import com.androidlearning.mvvm.model.database.ContactDB;
import com.androidlearning.mvvm.model.entity.Contact;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Data;

@Data
public class ContactRepo {

    //Available Data Source: Room DB
    //Usually when there is only one Data Source, no need repository
    //repository is useful when there are multiple data source to perform one operation

    private final ContactDAO contactDAO;

    /**
     * DB operations are needed to run in background thread, not main thread to prevent block UI
     * Use handler and executor service to run background
     */

    private final ExecutorService executor;
    private final Handler handler;

    public ContactRepo(Application application) {
        ContactDB contactDB = ContactDB.getInstance(application);

        this.contactDAO = contactDB.getContactDAO();
        //use SingleThreadExecutor for DB operation to avoid race condition
        this.executor = Executors.newSingleThreadExecutor();
        //Handler: used to update UI, need to be on main thread
        this.handler = new Handler(Looper.getMainLooper());
    }

    //Method in DAO being executed from Repository
    public void addContact(Contact contact) {
        this.executor.execute(() -> contactDAO.insertContact(contact));
    }

    public void deleteContact(Contact contact) {
        this.executor.execute(() -> contactDAO.deleteContact(contact));
    }

    /**
     * getAllContacts: has return type and often need to update UI
     *
     * Use LiveData: to expose data from room DB to ViewModel and ultimately to UI components
     */
    public LiveData<List<Contact>> getAllContacts() {
        return contactDAO.getAllContacts();
    }

}
