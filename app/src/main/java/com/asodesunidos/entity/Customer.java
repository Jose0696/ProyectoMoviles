package com.asodesunidos.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers",indices = {@Index(value = {"identificationCard"}, unique = true)})
public class Customer {

    @PrimaryKey
    @ColumnInfo (name = "uid")
    @NonNull
    private int uid;

    @ColumnInfo (name = "identificationCard")
    @NonNull
    private String identificationCard;

    @ColumnInfo (name = "name")
    @NonNull
    private String name;

    @ColumnInfo (name = "salary")
    @NonNull
    private double salary;

    @ColumnInfo (name = "phoneNumber")
    @NonNull
    private String phoneNumber;

    @ColumnInfo (name = "birthdate")
    @NonNull
    private String birthdate;

    @ColumnInfo (name = "civilStatus")
    @NonNull
    private String civilStatus;

    @ColumnInfo (name = "direction")
    @NonNull
    private String direction;

    public Customer(int uid, @NonNull String identificationCard, @NonNull String name,
                    double salary, @NonNull String phoneNumber, @NonNull String birthdate,
                    @NonNull String civilStatus, @NonNull String direction) {
        this.uid = uid;
        this.identificationCard = identificationCard;
        this.name = name;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.civilStatus = civilStatus;
        this.direction = direction;
    }

    public Customer() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(@NonNull String identificationCard) {
        this.identificationCard = identificationCard;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(@NonNull String birthdate) {
        this.birthdate = birthdate;
    }

    @NonNull
    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(@NonNull String civilStatus) {
        this.civilStatus = civilStatus;
    }

    @NonNull
    public String getDirection() {
        return direction;
    }

    public void setDirection(@NonNull String direction) {
        this.direction = direction;
    }
}
