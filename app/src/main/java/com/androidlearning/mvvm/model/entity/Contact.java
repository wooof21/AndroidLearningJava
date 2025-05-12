package com.androidlearning.mvvm.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Builder;
import lombok.Data;

/**
 * Entity: represent a table in SQLite DB, corresponds to one table and
 * the fields properties or the variable within the entity class represents
 * columns in the table
 */
@Entity(tableName = "contacts_table")
@Data
public class Contact implements Parcelable {

    /**
     * ColumnInfo: used to specify additional details about how a field, property or variable
     * in an entity class maps to a column in the DB table
     *  - to explicitly specify the name of the DB column that corresponds to a field
     *  in the entity
     *
     *  id maps to contacts_id in table
     */
    @ColumnInfo(name = "contacts_id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "contacts_name")
    private String name;
    @ColumnInfo(name = "contacts_email")
    private String email;

    @Builder
    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Contact(Parcel parcel) {
        this.name = parcel.readString();
        this.email = parcel.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(email);
    }
}
