package com.asodesunidos.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asodesunidos.R;
import com.asodesunidos.entity.Loan;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class CustomerPrincipalActivity extends SuperActivity {
    int userId;
    String userName;
    TextView welcome;

    Button personalInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_principal);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        welcome = (TextView) findViewById(R.id.welcomeCustomer);

        userId = getIntent().getIntExtra("idCustomer",0);

        userName = getIntent().getStringExtra("customerName");
        welcome.setText("Bienvenido " + userName);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        personalInfo = (Button) findViewById(R.id.personalInformation);
        findViewById(R.id.calc).setOnClickListener(view -> changeView(CustomerCalcCuotaActivity.class));

    }

    public void viewLoansCustomer(View view){
        List<Loan> loansList;

        loansList = database().getLoanDAO().find(userId);

        if(loansList.size() > 0){
            Intent i = new Intent(this, ViewLoansCustomerActivity.class);
            i.putExtra("idCustomer", userId);
            startActivity(i);
        }else{
            showToast("El usuario no posee prestamos disponibles");
        }



    }

    public void personalInformationCustomer(View view){
        Intent i = new Intent(this, CustomerInformationActivity.class);
        i.putExtra("idCustomer", userId);
        startActivity(i);
    }


    public void savingManage(View view){
        Intent i = new Intent(this, CustomerSavings.class);
        i.putExtra("idCustomer", userId);
        startActivity(i);
    }

    public void logout(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Estás seguro de cerrar sesión?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cierra la sesión y vuelve a la pantalla de inicio de sesión
                        finish();
                        Intent intent = new Intent(CustomerPrincipalActivity.this, Login.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancela el cierre de sesión y vuelve a la actividad principal
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    protected Context context() { return this;
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
                            Intent intent = new Intent(CustomerPrincipalActivity.this, Login.class);
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