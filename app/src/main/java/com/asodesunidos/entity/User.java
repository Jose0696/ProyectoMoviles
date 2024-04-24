package com.asodesunidos.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @NonNull
    private int uid;

    @ColumnInfo
    @NonNull
    private String username;

    @ColumnInfo
    @NonNull
    private String password;

    @ColumnInfo
    @NonNull
    private int superuser;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public User() {

    }
    public User(@NonNull String username, @NonNull String password, int superuser) {
        this.username = username;
        this.password = password;
        this.superuser = superuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSuperuser() { return superuser; }
    public boolean isSuperuser() {
        return superuser > 0;
    }

    public void setSuperuser(boolean flag) {
        superuser = flag ? 1 : 0;
    }
    public void setSuperuser(int value) { superuser = value; }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", superuser=" + isSuperuser() +
                '}';
    }
}
