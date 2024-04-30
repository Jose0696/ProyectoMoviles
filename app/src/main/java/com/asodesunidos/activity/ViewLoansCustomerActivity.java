package com.asodesunidos.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.asodesunidos.R;
import com.asodesunidos.entity.Loan;

import java.util.List;

public class ViewLoansCustomerActivity extends SuperActivity {

    List<Loan> loansList;
    int rowCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_loans_customer);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        int userId = getIntent().getIntExtra("idCustomer", 0);

        loansList = database().getLoanDAO().find(userId);

        loansList.get(0);

        TableLayout tableLayout = findViewById(R.id.table_layout);

        if(loansList.size() != 0) {


            for (Loan prestamo : loansList) {

                TableRow tableRow = new TableRow(this);

                TextView loanTypeTextView = new TextView(this);
                loanTypeTextView.setText(prestamo.getLoantype());
                tableRow.addView(loanTypeTextView);
                loanTypeTextView.setGravity(Gravity.CENTER);


                TextView totalCreditTextView = new TextView(this);
                totalCreditTextView.setText(String.valueOf(prestamo.getTotalCredit()));
                tableRow.addView(totalCreditTextView);

                TextView periodTextView = new TextView(this);
                periodTextView.setText(String.format("%s años", String.valueOf(prestamo.getPeriod())));
                tableRow.addView(periodTextView);

                TextView percentageTextView = new TextView(this);
                percentageTextView.setText(String.valueOf(prestamo.getPercentage()));
                tableRow.addView(percentageTextView);

                TextView cuotaTextView = new TextView(this);
                cuotaTextView.setText(String.valueOf(prestamo.getCuota()));
                tableRow.addView(cuotaTextView);

                Button pagarButton = new Button(this);
                pagarButton.setText(R.string.pagar);

                if (prestamo.getTotalCredit() == 0) {
                    pagarButton.setEnabled(false);
                    pagarButton.setBackgroundColor(Color.GRAY);
                } else {
                    pagarButton.setEnabled(true);
                }

                int position = rowCount;
                rowCount++;

                pagarButton.setTag(position);

                pagarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int rowPosition = (int) v.getTag();

                        Loan prestamo = loansList.get(rowPosition);

                        if (prestamo.getTotalCredit() > 0) {
                            prestamo.setTotalCredit(prestamo.getTotalCredit() - prestamo.getCuota());
                        } else {
                            showToast("No se pueden realizar más pagos a este préstamo.");
                        }
                        database().getLoanDAO().update(prestamo);
                        totalCreditTextView.setText(String.valueOf(prestamo.getTotalCredit()));
                        showToast("Se realizó el pago con éxito");
                    }
                });

                tableRow.addView(pagarButton);
                tableLayout.addView(tableRow);
            }
        }else{

            showToast("El usuario no tiene prestamos asignados");
            changeView(CustomerPrincipalActivity.class);

        }
    }


    @Override
    protected Context context() {
        return this;
    }
}