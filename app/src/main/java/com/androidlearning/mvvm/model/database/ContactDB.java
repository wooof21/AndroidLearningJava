package com.androidlearning.mvvm.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.androidlearning.mvvm.model.dao.ContactDAO;
import com.androidlearning.mvvm.model.entity.Contact;

@Database(entities = {Contact.class}, version = 2, exportSchema = false)
public abstract class ContactDB extends RoomDatabase {

    //link DAO to database
    public abstract ContactDAO getContactDAO();

    //Single pattern
    private static ContactDB dbInstance;

    //synchronized: prevent any cleaning of contact DB
    public static synchronized ContactDB getInstance(Context context) {
        if(dbInstance == null) {
            dbInstance = Room
                    .databaseBuilder(context.getApplicationContext(), ContactDB.class, "contact_db")
                    //if a new version of the DB schema is detected due to changes in entity structure
                    //room will drop and recreate the DB effectively discarding all existing data
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dbInstance;
    }
}
