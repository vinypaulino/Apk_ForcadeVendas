package com.najasoftware.fdv.dao;

import android.content.Context;

import com.najasoftware.fdv.model.ConfigFtp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lemoel Marques - NajaSoftware on 18/03/2016.
 * lemoel@gmail.com
 */
public class ConfigFtpDAO {

    private Context context;
    private static final String HOST = "ftp.najasoftware.com.br";
    private static final String LOGIN = "fdv@najasoftware.com.br";
    private static final String SENHA = "fdv10";
    private static final String PORTA = "21";

    public ConfigFtpDAO(Context context) {
        this.context = context;
    }

    public ConfigFtp getConfigFtp(){
        ConfigFtp configFtp = new ConfigFtp();
        configFtp.setHost(HOST);
        configFtp.setLogin(LOGIN);
        configFtp.setSenha(SENHA);
        configFtp.setPorta(Integer.parseInt(PORTA));
        return configFtp;
    }

    //Salva os dados de configuracao no arquivo configFTP.properties
    public boolean salvar(ConfigFtp configFtp) {

        FileOutputStream fileOutputStream;
        configFtp = new ConfigFtp(configFtp.getLogin(), configFtp.getSenha(), configFtp.getHost(), configFtp.getPorta());

        Properties prop = new Properties();
        prop.setProperty("configFtp.host", configFtp.getHost().trim());
        prop.setProperty("configFtp.usuario", configFtp.getLogin().trim());
        prop.setProperty("configFtp.senha", configFtp.getSenha().trim());
        prop.setProperty("configFtp.porta", Integer.toString(configFtp.getPorta()));

        File file = new File(context.getFilesDir(), "configFTP.properties");
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            prop.store(fileOutputStream, "CONFIGURACAO DE CONEXAO FTP");
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}