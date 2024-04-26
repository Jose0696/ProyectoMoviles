package com.asodesunidos.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.asodesunidos.R;
import com.asodesunidos.dao.CrudDAO;
import com.asodesunidos.entity.Customer;
import com.asodesunidos.entity.Saving;
import com.asodesunidos.tag.SavingType;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerSavings extends SuperActivity {
    int userId;
    TextView textChrist, textScholar, textMark, textExtraordinary;
    Button savingChrist, savingScholar, savingMark, savingExtraordinary, saveSaving;
    EditText edChrist, edScholar, edMark, edExtraordinary;

    private int montoNavideno;
    private int montoEscolar = 0;
    private int montoMarchamo = 0;
    private int montoExtraordinario = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_savings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        textChrist = findViewById(R.id.navidenoTextView);
        textScholar = findViewById(R.id.escolarTextView);
        textMark = findViewById(R.id.marchamoTextView);
        textExtraordinary = findViewById(R.id.extraordinarioTextView);

        edChrist = findViewById(R.id.montoNavidenoEditText);
        edScholar = findViewById(R.id.montoEscolarEditText);
        edMark = findViewById(R.id.montoMarchamoEditText);
        edExtraordinary = findViewById(R.id.montoExtraordinarioEditText);

        savingChrist = findViewById(R.id.activarNavidenoButton);
        savingScholar = findViewById(R.id.activarEscolarButton);
        savingMark = findViewById(R.id.activarMarchamoButton);
        savingExtraordinary = findViewById(R.id.activarExtraordinarioButton);
        saveSaving = findViewById(R.id.guardarButton);

        userId = getIntent().getIntExtra("idCustomer",0);
        savingChrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edChrist = findViewById(R.id.montoNavidenoEditText);
                montoNavideno = Integer.parseInt(edChrist.getText().toString());

                saveSaving("navidenno",montoNavideno,SavingType.NAVIDENO, 1);
            }
        });

        savingScholar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edScholar = findViewById(R.id.montoEscolarEditText);
                montoEscolar = Integer.parseInt(edScholar.getText().toString());

                saveSaving("escolar",montoEscolar,SavingType.ESCOLAR,2);

            }
        });

        savingMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edMark = findViewById(R.id.montoMarchamoEditText);
                montoMarchamo = Integer.parseInt(edMark.getText().toString());

                saveSaving("marchamo",montoMarchamo,SavingType.MARCHAMO,3);
            }
        });

        savingExtraordinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edExtraordinary = findViewById(R.id.montoExtraordinarioEditText);
                montoExtraordinario = Integer.parseInt(edExtraordinary.getText().toString());

                saveSaving("extraordinario",montoExtraordinario,SavingType.EXTRAORDINARIO,4);
            }
        });
    }
    protected Context context() {
        return CustomerSavings.this;
    }

    private void showCouta(SavingType typeSaving){
        EditText editText = null;
        TextView textView = null;

        switch (typeSaving) {
            case NAVIDENO:
                editText = edChrist;
                textView = textChrist;
                break;
            case ESCOLAR:
                editText = edScholar;
                textView = textScholar;
                break;
            case MARCHAMO:
                editText = edMark;
                textView = textMark;
                break;
            case EXTRAORDINARIO:
                editText = edExtraordinary;
                textView = textExtraordinary;
                break;
        }

        String montoStr = editText.getText().toString().trim();
        if (!montoStr.isEmpty()) {
            int monto = Integer.parseInt(montoStr);
            if (monto >= 5000) {
                textView.setText(String.format("%s: Cuota %d", typeSaving.getDisplayName(), monto));

                switch (typeSaving) {
                    case NAVIDENO:
                        montoNavideno = monto;
                        break;
                    case ESCOLAR:
                        montoEscolar = monto;
                        break;
                    case MARCHAMO:
                        montoMarchamo = monto;
                        break;
                    case EXTRAORDINARIO:
                        montoExtraordinario = monto;
                        break;
                }
            } else {
                editText.setError("Monto mínimo: 5000 colones");
            }
        } else {
            editText.setError("Ingrese un monto válido");
        }

    }


    private void saveSaving(String savingType, double amount, SavingType typesave, int id) {
        try {
            List<Saving> savings = database().getSavingDAO().findAll().stream().filter(e ->
                    e.getTypeSaving().equals(savingType) && e.getCustomerId() == userId).collect(Collectors.toList());

            if (savings.isEmpty()) {
                database().getSavingDAO().insert(new Saving(id, savingType, userId, amount));
            } else {
                Saving current = savings.get(0);
                current.setMount(current.getMount() + amount);
                database().getSavingDAO().update(current);
            }
            showCouta(typesave);
        } catch (NumberFormatException e) {

            e.printStackTrace();

            showToast("Error al ingresar el monto" + savingType);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

