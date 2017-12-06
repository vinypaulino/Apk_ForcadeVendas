package com.najasoftware.fdv.helper;

import android.widget.EditText;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.activity.LoginActivity;
import com.najasoftware.fdv.model.Vendedor;

/**
 * Created by Lemoel Marques - NajaSoftware on 29/04/2016.
 * lemoel@gmail.com
 */
public class LoginHelper {

    private EditText campoLogin;
    private EditText campoSenha;
    private Vendedor vendedor;


    public LoginHelper(LoginActivity activity) {
        campoLogin = (EditText) activity.findViewById(R.id.tLogin);
        campoSenha = (EditText) activity.findViewById(R.id.tSenha);
        vendedor = new Vendedor();
    }

    public Vendedor getVendedor() {
        if (validarCampos()) {
            vendedor.setLogin(campoLogin.getText().toString());
            vendedor.setSenha(campoSenha.getText().toString());
            return vendedor;
        } else {
            return null;
        }
    }

    public boolean validarCampos() {

        boolean login = false;
        boolean senha = false;

        if (campoLogin.getText().toString().trim().length() > 0 && campoLogin.getText().toString().trim() != null) {
            login = true;
        }

        if (campoSenha.getText().toString().trim().length() > 0 && campoSenha.getText().toString().trim() != null) {
            senha = true;
        }

        return (login && senha) ? true : false;
    }

}
