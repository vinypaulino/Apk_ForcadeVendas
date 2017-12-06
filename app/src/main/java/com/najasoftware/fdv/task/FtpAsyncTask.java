package com.najasoftware.fdv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.najasoftware.fdv.model.ConfigFtp;
import com.najasoftware.fdv.util.FtpUtil;

/**
 * Created by Lemoel Marques - NajaSoftware on 25/05/2016.
 * lemoel@gmail.com
 */
public class FtpAsyncTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progressDialog;
    private ConfigFtp ftp;

    public FtpAsyncTask(Context ctx,ConfigFtp ftp) {
        this.context = ctx;
        this.ftp = ftp;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Aguarde...", "Testando Conexão FTP!!");
    }

    @Override
    protected String doInBackground(Object[] params) {
        FtpUtil ftpUtil = new FtpUtil(context);
        boolean retorno = ftpUtil.checkConexaoFTP(ftp);
        return (retorno) ? "Conectado com sucessso!!" : "Não foi possível conectar ao servidor!";
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
