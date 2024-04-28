package com.asodesunidos.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

import com.asodesunidos.R;

import com.asodesunidos.entity.Saving;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerSavings extends SuperActivity {
    int userId;
    TextView textChrist, textScholar, textMark, textExtraordinary;
    Button savingChrist, savingScholar, savingMark, savingExtraordinary;
    EditText edChrist, edScholar, edMark, edExtraordinary;

    private int montoNavideno;
    private int montoEscolar;
    private int montoMarchamo;
    private int montoExtraordinario;
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

        userId = getIntent().getIntExtra("idCustomer",0);

        displaySavings (textChrist,  textScholar,   textMark, textExtraordinary);


        savingChrist.setOnClickListener(v -> {
            edChrist = findViewById(R.id.montoNavidenoEditText);
            montoNavideno = Integer.parseInt(edChrist.getText().toString());

            saveSaving("navidenno",montoNavideno, generateRandomID());


        });

        savingScholar.setOnClickListener(v -> {
            edScholar = findViewById(R.id.montoEscolarEditText);
            montoEscolar = Integer.parseInt(edScholar.getText().toString());

            saveSaving("escolar",montoEscolar,generateRandomID());

        });

        savingMark.setOnClickListener(v -> {
            edMark = findViewById(R.id.montoMarchamoEditText);
            montoMarchamo = Integer.parseInt(edMark.getText().toString());

            saveSaving("marchamo",montoMarchamo,generateRandomID());
        });

        savingExtraordinary.setOnClickListener(v -> {
            edExtraordinary = findViewById(R.id.montoExtraordinarioEditText);
            montoExtraordinario = Integer.parseInt(edExtraordinary.getText().toString());

            saveSaving("extraordinario",montoExtraordinario,generateRandomID());
        });


    }
    protected Context context() {
        return CustomerSavings.this;
    }

    public int generateRandomID() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        int randomID = (int) mostSignificantBits;
        return randomID;
    }




    private void saveSaving(String savingType, double amount, int id) {
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
            displaySavings( textChrist, textScholar,  textMark, textExtraordinary);
           // showCouta(typesave);
        } catch (NumberFormatException e) {



            showToast("Error al ingresar el monto" + savingType);

        } catch (Exception e) {
            showToast("Error en el sistema");
        }
    }


    private void displaySavings ( TextView textChrist, TextView textScholar,  TextView textMark, TextView textExtraordinary){
        List<Saving> savings = database().getSavingDAO().findAll().stream().filter(e ->
                e.getCustomerId() == userId).collect(Collectors.toList());
        if(savings.stream().anyMatch(e -> e.getTypeSaving().equals("navidenno")) ) {
            Saving saving1 = savings.stream().filter( e -> e.getTypeSaving().equals("navidenno")).findFirst().get();
            textChrist.setText(String.valueOf(saving1.getMount()));
        }
        if(savings.stream().anyMatch(e -> e.getTypeSaving().equals("escolar")) ) {
            Saving saving1 = savings.stream().filter( e -> e.getTypeSaving().equals("escolar")).findFirst().get();
            textScholar.setText(String.valueOf(saving1.getMount()));
        }

        if(savings.stream().anyMatch(e -> e.getTypeSaving().equals("marchamo")) ) {
            Saving saving1 = savings.stream().filter( e -> e.getTypeSaving().equals("marchamo")).findFirst().get();
            textMark.setText(String.valueOf(saving1.getMount()));
        }
        if(savings.stream().anyMatch(e -> e.getTypeSaving().equals("extraordinario")) ) {
            Saving saving1 = savings.stream().filter( e -> e.getTypeSaving().equals("extraordinario")).findFirst().get();
            textExtraordinary.setText(String.valueOf(saving1.getMount()));
        }

    }

}

