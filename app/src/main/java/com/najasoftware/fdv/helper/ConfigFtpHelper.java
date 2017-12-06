package com.najasoftware.fdv.helper;

import android.widget.EditText;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.activity.ConfigFtpActivity;
import com.najasoftware.fdv.model.ConfigFtp;

/**
 * Created by Lemoel Marques - NajaSoftware on 21/03/2016.
 * lemoel@gmail.com
 */
public class ConfigFtpHelper {

    private EditText campoNome;
    private EditText campoSenha;
    private EditText campoHost;
    private EditText campoPorta;

    private ConfigFtp configFtp;

    public ConfigFtpHelper(ConfigFtpActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.et_config_nome);
        campoSenha = (EditText) activity.findViewById(R.id.et_config_senha);
        campoHost = (EditText) activity.findViewById(R.id.et_config_host);
        campoPorta = (EditText) activity.findViewById(R.id.et_config_porta);
        configFtp = new ConfigFtp();
    }

    public ConfigFtp getConfigFtp(){
        configFtp.setLogin(campoNome.getText().toString().trim());
        configFtp.setSenha(campoSenha.getText().toString().trim());
        configFtp.setHost(campoHost.getText().toString().trim());

        if (campoPorta.getText().toString().trim().equals("")) {
            configFtp.setPorta(21);
        } else {
            configFtp.setPorta(Integer.parseInt(campoPorta.getText().toString().trim()));
        }

        return configFtp;
    }

    public void preecherActivity(ConfigFtp configFtp) {
        campoNome.setText(configFtp.getLogin());
        campoSenha.setText(configFtp.getSenha());
        campoHost.setText(configFtp.getHost());
        campoPorta.setText(Integer.toString(configFtp.getPorta()));
    }

    public boolean validaCampos (){

        String nome = campoNome.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();
        String host = campoHost.getText().toString().trim();

        if(nome.equals("")) {
            campoNome.setError("Campo Login obrigatório");
            return false;
        } else if(senha.equals("")) {
            campoSenha.setError("Campo Senha obrigatório");
            return false;
        } else if (host.equals("")) {
            campoHost.setError("Nome ou ip do servidor obrigatório");
            return false;
        }
        return true;
    }

}
