package com.androidlearning.mvvm.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.androidlearning.mvvm.model.entity.Contact;

import java.util.List;

/**
 * DAO: Data Access Object
 * an interface that defines a set of methods for performing DB operations on entity:
 * e.g. Insert, Delete, Update, Select ...
 */
@Dao
public interface ContactDAO {

    @Insert
    void insertContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    //define custom SQL query
    //SELECT all FROM <table_name>
    @Query("SELECT * FROM contacts_table")
    LiveData<List<Contact>> getAllContacts();
}
