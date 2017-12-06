package com.najasoftware.fdv.helper;

import android.widget.EditText;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.activity.LocalizaCnpjActivity;
import com.najasoftware.fdv.model.Credencial;

/**
 * Created by Lemoel Marques - NajaSoftware on 07/06/2016.
 * lemoel@gmail.com
 */
public class LocalizaCnpjHelper {

    private EditText campoLogin;
    private EditText campoSenha;
    private Credencial credencial;


    public LocalizaCnpjHelper(LocalizaCnpjActivity activity) {
        campoLogin = (EditText) activity.findViewById(R.id.tCnpj);
        campoSenha = (EditText) activity.findViewById(R.id.tSenhaCnpj);
        credencial = new Credencial();
    }

    public Credencial getCredencial() {
        credencial.setCnpj(campoLogin.getText().toString().trim());
        credencial.setSenha(campoSenha.getText().toString().trim());
        return credencial;
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
