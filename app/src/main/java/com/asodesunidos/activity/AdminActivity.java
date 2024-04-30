package com.asodesunidos.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.app.AlertDialog;

import com.asodesunidos.R;

public class AdminActivity extends SuperActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    @Override
    protected Context context() { return AdminActivity.this; }

    private void init() {
        findViewById(R.id.addCustomerBtn).setOnClickListener(view -> changeView(CustomerActivity.class));
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new androidx.appcompat.app.AlertDialog.Builder(AdminActivity.this)
                        .setTitle("Cerrar Sesión")
                        .setMessage("¿Estás seguro de cerrar sesión?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AdminActivity.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); // Finalizar esta actividad
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
                findViewById(R.id.addLoanClient).setOnClickListener(view -> changeView(AddLoanActivity.class));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Estás seguro de cerrar sesión?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AdminActivity.this, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}