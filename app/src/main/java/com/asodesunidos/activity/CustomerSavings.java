package com.asodesunidos.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

import com.asodesunidos.R;

import com.asodesunidos.entity.Saving;


import java.util.List;
import java.util.Objects;
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
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
            try {
                edChrist = findViewById(R.id.montoNavidenoEditText);
                montoNavideno = Integer.parseInt(edChrist.getText().toString());

                if (montoNavideno < 5000) {
                    showToast("El monto mínimo para Navideño es de 5000");
                } else {
                    saveSaving("navidenno", montoNavideno, generateRandomID());
                }
            }catch (Exception exception) {
                showToast("Campo vacío");
            }
        });

        savingScholar.setOnClickListener(v -> {
            try {
                edScholar = findViewById(R.id.montoEscolarEditText);
                montoEscolar = Integer.parseInt(edScholar.getText().toString());

                if (montoEscolar < 5000) {
                    showToast("El monto mínimo para Escolar es de 5000");
                } else {
                    saveSaving("escolar", montoEscolar, generateRandomID());
                }
            }catch (Exception exception) {
                showToast("Campo vacío");
            }
        });

        savingMark.setOnClickListener(v -> {
            try {
                edMark = findViewById(R.id.montoMarchamoEditText);
                montoMarchamo = Integer.parseInt(edMark.getText().toString());

                if (montoMarchamo < 5000) {
                    showToast("El monto mínimo para Marchamo es de 5000");
                } else {
                    saveSaving("marchamo", montoMarchamo, generateRandomID());
                }
            }catch (Exception exception) {
                showToast("Campo vacío");
            }
        });

        savingExtraordinary.setOnClickListener(v -> {
            try {
                edExtraordinary = findViewById(R.id.montoExtraordinarioEditText);
                montoExtraordinario = Integer.parseInt(edExtraordinary.getText().toString());

                if (montoExtraordinario < 5000) {
                    showToast("El monto mínimo para Extraordinario es de 5000");
                } else {
                    saveSaving("extraordinario", montoExtraordinario, generateRandomID());
                }
            }catch (Exception exception) {
                showToast("Campo vacío");
            }
        });


    }
    protected Context context() {
        return CustomerSavings.this;
    }

    public int generateRandomID() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        return (int) mostSignificantBits;
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


    private void displaySavings(TextView textChrist, TextView textScholar, TextView textMark, TextView textExtraordinary) {
        List<Saving> savings = database().getSavingDAO().findAll().stream()
                .filter(e -> e.getCustomerId() == userId)
                .collect(Collectors.toList());

        SavingsFilter savingsFilter = new SavingsFilter();
        savings.stream().filter(savingsFilter::isChristmas).findFirst().ifPresent(saving -> textChrist.setText(String.valueOf(saving.getMount())));
        savings.stream().filter(savingsFilter::isScholar).findFirst().ifPresent(saving -> textScholar.setText(String.valueOf(saving.getMount())));
        savings.stream().filter(savingsFilter::isMarchamo).findFirst().ifPresent(saving -> textMark.setText(String.valueOf(saving.getMount())));
        savings.stream().filter(savingsFilter::isExtraordinary).findFirst().ifPresent(saving -> textExtraordinary.setText(String.valueOf(saving.getMount())));
    }

    static class SavingsFilter {
        public boolean isChristmas(Saving saving) {
            return saving.getTypeSaving().equals("navidenno");
        }

        public boolean isScholar(Saving saving) {
            return saving.getTypeSaving().equals("escolar");
        }

        public boolean isMarchamo(Saving saving) {
            return saving.getTypeSaving().equals("marchamo");
        }

        public boolean isExtraordinary(Saving saving) {
            return saving.getTypeSaving().equals("extraordinario");
        }
    }

}
