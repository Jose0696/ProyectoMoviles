package com.asodesunidos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asodesunidos.R;
import com.asodesunidos.entity.Customer;
import com.asodesunidos.entity.Loan;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.List;

public class AddLoanActivity extends SuperActivity {

    // Spinners
    String[] tiposPrestamo = {"Hipotecario", "Educación", "Personal", "Viajes"};

    String[] plazosPrestamo = {"3 años", "5 años", "10 años"};

    Spinner tiposPrestamoSpinner;
    Spinner plazosPrestamoSpinner;

    //Buttons

    Button calcularBtn;
    Button buscarCedulaBtn;
    Button agregarPrestamoBtn;


    // Texview

    TextView cedulaTv;
    TextView nombreClienteTv;
    TextView salarioClienteTv;
    TextView salarioMaximoTv;
    TextView cuotaCalculadaTv;

    TextView montoPrestarTv;

    double salarioMax;
    int idCliente;

    int periodo;

    float montoTotal;

    float porcentaje;

    float cuota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        tiposPrestamoSpinner = (Spinner) findViewById(R.id.tiposSpinner);

        plazosPrestamoSpinner = (Spinner) findViewById(R.id.plazosSpinner);

        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposPrestamo);

        ArrayAdapter<String> adapterPlazos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plazosPrestamo);

        tiposPrestamoSpinner.setAdapter(adapterTipos);
        plazosPrestamoSpinner.setAdapter(adapterPlazos);


        calcularBtn = (Button) findViewById(R.id.btnCalcular);
         buscarCedulaBtn = (Button) findViewById(R.id.searchIdCard);
         agregarPrestamoBtn = (Button) findViewById(R.id.btnAgregarPrestamo);


        // Texview

         cedulaTv = (TextView) findViewById(R.id.idCard);
         nombreClienteTv = (TextView) findViewById(R.id.customerName);
         salarioClienteTv = (TextView) findViewById(R.id.customerSalary);
         salarioMaximoTv = (TextView) findViewById(R.id.maxSalary);
         cuotaCalculadaTv = (TextView) findViewById(R.id.cuotaCalculada);
        montoPrestarTv = (TextView) findViewById(R.id.montoDeseado);
    }


    @SuppressLint("DefaultLocale")
    public void searchUser(View view){
        if(validarCampos(view)) {
            Customer customer = null;

            List<Customer> customers = database().getCustomerDAO().findAll();

            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getIdentificationCard().equals(cedulaTv.getText().toString()))
                    customer = customers.get(i);
            }

            if (customer != null) {
                idCliente = customer.getUid();
                nombreClienteTv.setText(customer.getName());
                salarioClienteTv.setText(String.format("%s\n%s", getResources().getString(R.string.salario), String.format("%.0f", customer.getSalary())));
                salarioMax = calcSalarioMaximo(customer.getSalary());
                salarioMaximoTv.setText(String.format("%s\n%s", getResources().getString(R.string.action_form_SalarioMaximo), String.format("%.02f", salarioMax)));
            } else {
                showToast("El cliente consultado no existe");
            }
        }
    }
    private float calcSalarioMaximo(double salario){
        return (float) (salario * 0.45);
    }


    @SuppressLint("DefaultLocale")
    public void calcularCuota(View view){
        if(validarCalcular()){
            float montoPrestamo = Float.parseFloat(montoPrestarTv.getText().toString());
            float interesMensual = interesMensualCalc();
            float cuota = 0;
            int numeroCuotas = numeroCuotasCalc(view);

            cuota = (float) ((montoPrestamo * interesMensual)/ (1-Math.pow(1+interesMensual,-numeroCuotas)));

            String stringdouble= Float.toString(cuota);

            cuotaCalculadaTv.setText(stringdouble);

            montoTotal = cuota * numeroCuotas;
            this.cuota = cuota;
        }
    }


    public void agregarPrestamoCliente(View view){

        if(validarCalcular()){
            Loan loanCust = new Loan();
            loanCust.setCustomerId(idCliente);
            loanCust.setLoantype(tiposPrestamoSpinner.getSelectedItem().toString());
            loanCust.setPeriod(periodo);
            loanCust.setTotalCredit(montoTotal);
            loanCust.setPercentage(porcentaje);
            loanCust.setCuota(cuota);
            database().getLoanDAO().insert(loanCust);
            showToast("Se agregó el préstamo al cliente");
        }else{
            showToast("No se pudo agregar el préstamo");
        }
    }

    private int numeroCuotasCalc(View view){
        String opcSpinner = plazosPrestamoSpinner.getSelectedItem().toString();
        int cuotas = 0;
        switch (opcSpinner){
            case "3 años":
                cuotas = 12 * 3;
                periodo = 3;
                break;
            case "5 años":
                cuotas = 12 * 5;
                periodo = 5;
                break;
            case "10 años":
                cuotas = 12 * 10;
                periodo = 10;
                break;
        }
        return cuotas;
    }

    private float interesMensualCalc(){
        String opcSpinner = tiposPrestamoSpinner.getSelectedItem().toString();
        float interes = 0;
        switch (opcSpinner){
            case "Hipotecario":
                interes = (float) ( 0.075 );
                porcentaje = (float) 0.075;
                break;
            case "Educación":
                interes = (float) ( 0.08 );
                porcentaje = (float) 0.08;
                break;
            case "Personal":
                interes = (float) ( 0.1);
                porcentaje = (float) 0.1;
                break;
            case "Viajes":
                interes = (float) (0.12);
                porcentaje = (float) 0.12;
                break;
        }
        return interes;
    }

    private boolean validarCalcular(){
        boolean flag = true;

        if(montoPrestarTv.getText().toString().isEmpty()) {
            montoPrestarTv.setError("El campo no puede estar en blanco.");
            flag = false;

        }
        else if( Float.parseFloat(montoPrestarTv.getText().toString()) > salarioMax) {
            montoPrestarTv.setError("El monto solicitado no puede ser mayor al 45% del salario.");
            flag = false;
        }else if(Float.parseFloat(montoPrestarTv.getText().toString()) == 0){
            montoPrestarTv.setError("El monto solicitado no puede ser 0.");
            flag = false;
        }

        if(cedulaTv.getText().toString().isEmpty()) {
            cedulaTv.setError("El campo cédula no puede estar en blanco.");
            flag = false;
        }
        return flag;
    }


    private boolean validarCampos(View view){
        if(cedulaTv.getText().toString().isEmpty()) {
            cedulaTv.setError("El campo cédula no puede estar en blanco.");
            return false;
        }
        return true;
    }

    @Override
    protected Context context() {
        return this;
    }
}