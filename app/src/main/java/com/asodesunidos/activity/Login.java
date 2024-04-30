package com.asodesunidos.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asodesunidos.R;
import com.asodesunidos.entity.Customer;
import com.asodesunidos.entity.User;

public class Login extends SuperActivity {
    private TextView usernameTxtView = null;
    private TextView passwordTxtView = null;
    private Button loginBtn = null;

    private Button close;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTxtView = (TextView) findViewById(R.id.usernameTxt);
        passwordTxtView = (TextView) findViewById(R.id.passwordTxt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(startSessionListener());

        close = (Button) findViewById(R.id.closeBtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        User user = new User("admin", "password", 1);
        database().getUserDAO().insert(user);
    }

    @Override
    protected Context context() { return Login.this; }

    private View.OnClickListener startSessionListener() {
        return view -> {
            String username = usernameTxtView.getText().toString();
            String password = passwordTxtView.getText().toString();
            if(validate(username,password)){
                User user = database().getUserDAO().check(username, password);
                if(user == null) {
                    showToast("Credenciales incorrectas");
                    return;
                }
                if(!user.isSuperuser()) {
                    Customer customer = database().getCustomerDAO().findCustomer(user.getUid());
                    if(customer == null){
                        showToast("Error encontrando el usuario en la tabla users");
                        return;
                    }
                    int userId = customer.getUid();
                    showToast("Inicio de sesión exitoso");
                    Intent i = new Intent(this, CustomerPrincipalActivity.class);
                    i.putExtra("idCustomer", userId);
                    i.putExtra("customerName", customer.getName());
                    limpiarCampos();
                    startActivity(i);
                }else{
                    limpiarCampos();
                    changeView(AdminActivity.class);
                }
            }
        };
    }

    private Boolean validate(String username, String password){
        Boolean flag = true;

        if(username.isEmpty()) {
            usernameTxtView.setError("Campo obligatorio.");
            flag = false;
        }
        if(password.isEmpty()) {
            passwordTxtView.setError("Campo obligatorio.");
            flag = false;
        }
        if(!flag){
            showToast("Debe completar los datos para avanzar.");
        }
            return flag;
    }

    private void limpiarCampos(){
        passwordTxtView.setText("");
        usernameTxtView.setText("");
    }

}