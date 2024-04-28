package com.asodesunidos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asodesunidos.R;
import com.asodesunidos.entity.Loan;

import java.util.List;

public class CustomerPrincipalActivity extends SuperActivity {

    int userId;
    String userName;
    TextView welcome;

    //Buttons

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
        finish();
    }
    @Override
    protected Context context() { return this;
    }
}