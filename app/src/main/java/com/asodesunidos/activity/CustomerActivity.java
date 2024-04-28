package com.asodesunidos.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.asodesunidos.R;
import com.asodesunidos.entity.Customer;
import com.asodesunidos.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomerActivity extends SuperActivity {
    TextView dateEdt, name, idCard, phone, salary, civilState, addressTxt;
    Spinner civilStateSpinner;
    String[] tiposPrestamo = {"Soltero/a", "Unión Libre", "Casado/a", "Separado/a", "Divorciado/a", "Viudo/a"};
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

        // Spinner y adapter para tipos de préstamo
        civilStateSpinner = findViewById(R.id.civilStateSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposPrestamo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        civilStateSpinner.setAdapter(adapter);

        civilStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Cuando se selecciona un estado civil en el Spinner, actualiza el TextView
                String selectedCivilState = tiposPrestamo[position];
                civilState.setText(selectedCivilState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejar la situación cuando no se selecciona nada en el Spinner
            }
        });

        addCustomer = findViewById(R.id.buttonAddCustomer);
        dateEdt = findViewById(R.id.dateTxt);
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        CustomerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
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





