package com.asodesunidos.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.asodesunidos.config.DatabaseConfig;
import com.asodesunidos.config.SQLiteHelper;
import com.asodesunidos.dao.CrudDAO;
//import com.asodesunidos.dao.SavingDAO;
import com.asodesunidos.dao.UserDAO;
import com.asodesunidos.entity.User;

public abstract class SuperActivity extends AppCompatActivity {
    private final String DATABASE_NAME = "asodesunidos.db";
    private static DatabaseConfig database = null;

    public SuperActivity() {
        super();
    }

    protected DatabaseConfig database() {
        if(database == null)
            database = Room.databaseBuilder(context(), DatabaseConfig.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return database;
    }

    protected void showToast(String text) {
        Toast.makeText(context(), text, Toast.LENGTH_LONG).show();
    }

    protected void changeView(Class<? extends SuperActivity> klass) {
        Intent intent = new Intent(context(), klass);
        super.startActivity(intent);
    }

    protected abstract Context context();


}