package com.najasoftware.fdv.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Lemoel Marques - NajaSoftware on 17/03/2016.
 * lemoel@gmail.com
 */
public class GravarArquivo {

    private File file;

    public GravarArquivo(File file) {
        this.file = file;
    }

    public boolean gravar(String dados, String fileName) {

        FileOutputStream fileOutputStream;
        DataOutputStream dataOutputStream;

        File file = new File(this.file, fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            dataOutputStream = new DataOutputStream(fileOutputStream);
            dataOutputStream.write(dados.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}