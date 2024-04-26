package com.asodesunidos.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.asodesunidos.R;
import com.asodesunidos.entity.Customer;
import com.asodesunidos.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomerActivity extends SuperActivity {

    TextView dateEdt;
    TextView name;
    TextView idCard;
    TextView phone;
    TextView salary;
    TextView civilState;
    TextView addressTxt;

    Button addCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        name = findViewById(R.id.nameTxt);
        idCard = findViewById(R.id.idCardTxt);
        phone = findViewById(R.id.phoneTxt);
        salary = findViewById(R.id.salaryTxt);
        civilState = findViewById(R.id.civilStateTxt);
        addressTxt = findViewById(R.id.addressTxt);

        addCustomer = findViewById(R.id.buttonAddCustomer);
        // on below line we are initializing our variables.
        dateEdt = findViewById(R.id.dateTxt);

        // on below line we are adding click listener
        // for our pick date button
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        CustomerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });


    }
    @Override
    protected Context context() {
        return CustomerActivity.this;
    }


    public void createCustomer(View view){
        try {
            if (searchIdCard()) {
                long result = createLoginForUser(view);
                Customer customer = new Customer(Integer.parseInt(String.valueOf(result)), idCard.getText().toString(),
                        name.getText().toString(), Double.parseDouble(salary.getText().toString()), phone.getText().toString(), dateEdt.getText().toString(),
                        civilState.getText().toString(), addressTxt.getText().toString());
                database().getCustomerDAO().insert(customer);

                showToast("Se agregó el nuevo cliente");
            } else {
                showToast("No se pudo agreagar el nuevo cliente");
            }
        }catch (Exception exception) {
            showToast("No se pudo agreagar el nuevo cliente");
        }
    }


    public boolean searchIdCard(){
        List<Customer> customers = database().getCustomerDAO().findAll();

        for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).getIdentificationCard().equals(idCard.getText().toString())){
                idCard.setError("El número de cédula ya existe.");
                return false;
            }
        }
        return true;
    }

    private long createLoginForUser(View view){
        String id = idCard.getText().toString();
        User user = new User( id, "password", 0);
        return database().getUserDAO().insert(user);
    }
}





