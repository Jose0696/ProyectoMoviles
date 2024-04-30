package com.asodesunidos.activity;

import android.annotation.SuppressLint;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CustomerInformationActivity extends SuperActivity {
    int userId;
    TextView dateEdt,name, idCard, phone, salary, civilState, addressTxt;

    Spinner civilStateSpinner;
    String[] tiposPrestamo = {"Soltero/a", "Unión Libre", "Casado/a", "Separado/a", "Divorciado/a", "Viudo/a"};
    Button updateCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        name = findViewById(R.id.nameTxt);
        idCard = findViewById(R.id.idCardTxt);
        phone = findViewById(R.id.phoneTxt);
        salary = findViewById(R.id.salaryTxt);
        civilState = findViewById(R.id.civilStateTxt);
        addressTxt = findViewById(R.id.addressTxt);

        civilStateSpinner = findViewById(R.id.civilStateSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposPrestamo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        civilStateSpinner.setAdapter(adapter);

        civilStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCivilState = tiposPrestamo[position];
                civilState.setText(selectedCivilState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        updateCustomer = findViewById(R.id.buttonUpdateCustomer);
        dateEdt = findViewById(R.id.dateTxt);


        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        CustomerInformationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },

                        year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });


        userId = getIntent().getIntExtra("idCustomer",0);
        consultarInformacion();
    }


    @SuppressLint("DefaultLocale")
    public void consultarInformacion(){

        Customer customer = database().getCustomerDAO().findCustomer(userId);
        if(customer != null) {
            idCard.setText(customer.getIdentificationCard());
            name.setText(customer.getName());
            salary.setText(String.format("%.0f", customer.getSalary()));
            phone.setText(customer.getPhoneNumber());
            dateEdt.setText(customer.getBirthdate());
            seleccionarEstadoCivil(customer.getCivilStatus());
            addressTxt.setText(customer.getDirection());

        }
    }
    private void seleccionarEstadoCivil(String estadoCivil) {
        int position = Arrays.asList(tiposPrestamo).indexOf(estadoCivil);
        civilStateSpinner.setSelection(position);
    }
    public void updateCustomer(View view){
        if(validarCampos()) {
            Customer cusUpdate = new Customer();

            cusUpdate.setIdentificationCard(idCard.getText().toString());
            cusUpdate.setName(name.getText().toString());
            cusUpdate.setSalary(Double.parseDouble(salary.getText().toString()));
            cusUpdate.setPhoneNumber(phone.getText().toString());
            cusUpdate.setBirthdate(dateEdt.getText().toString());
            cusUpdate.setCivilStatus(civilState.getText().toString());
            cusUpdate.setDirection(addressTxt.getText().toString());
            cusUpdate.setUid(userId);

            database().getCustomerDAO().update(cusUpdate);

            showToast("Se actualizó la información de manera exitosa");
            consultarInformacion();
        }
    }

    private boolean validarCampos() {
        boolean flag = true;

        if(name.getText().toString().isEmpty()){
            flag = false;
            name.setError("El campo no puede estar vacío");
        }
        if(salary.getText().toString().isEmpty()){
            flag = false;
            salary.setError("El campo no puede estar vacío");
        }
        if( phone.getText().toString().isEmpty()){
            flag = false;
            phone.setError("El campo no puede estar vacío");
        }
        if( dateEdt.getText().toString().isEmpty()){
            flag = false;
            dateEdt.setError("El campo no puede estar vacío");
        }
        if( civilState.getText().toString().isEmpty()){
            flag = false;
            civilState.setError("El campo no puede estar vacío");
        }
        if( addressTxt.getText().toString().isEmpty()){
            flag = false;
            addressTxt.setError("El campo no puede estar vacío");
        }
        return flag;
    }

    @Override
    protected Context context() {
    return this;
    }
}