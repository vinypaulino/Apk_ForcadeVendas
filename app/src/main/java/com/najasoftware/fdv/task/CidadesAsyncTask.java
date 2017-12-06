package com.najasoftware.fdv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.najasoftware.fdv.dao.CidadeDAO;
import com.najasoftware.fdv.model.Cidade;
import com.najasoftware.fdv.service.CidadesService;
import com.najasoftware.fdv.util.FtpUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class CidadesAsyncTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final String cnpj;
    private ProgressDialog progressDialog;

    public CidadesAsyncTask(Context ctx,String cnpj) {
        this.context = ctx;
        this.cnpj = cnpj;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Aguarde...", "Baixando dados!!");
    }

    @Override
    protected String doInBackground(Object[] params) {

        //Conectar ao servidor ftp;
        FtpUtil ftpUtil = new FtpUtil(context);
        boolean conectado = ftpUtil.conectar(null);
        String nomeArquivo = "cidades_fdv.json";

        if (conectado) {
            //fazer o download dos arquivos e desconectar
            boolean download = ftpUtil.download("/"+cnpj, nomeArquivo,context.getFilesDir() + "/"+ nomeArquivo);

            if (download) {

                try {
                    //Ler os dados do arquivo JSON
                    List<Cidade>cidades = CidadesService.getCidades(context);

                    //Se tiver vendedores para importar
                    if (cidades != null) {
                        CidadeDAO cidadeDAO = new CidadeDAO(context);
                        cidadeDAO.deleteAll();
                        cidadeDAO.insere(cidades);
                        cidadeDAO.close();
                    } else {
                        return "Relação de cidades vazia";
                    }

                } catch (IOException e) {
                    Log.e("Fdv", e.getMessage(), e);
                }
            } else {
                return "Arquivo de cidades não encontrado no servidor!!, verifique o usuário do FTP e a pasta Home do usuário";
            }
        } else {
            return "Não conectado";
        }

        File file = new File(context.getFilesDir(), nomeArquivo);
        Log.d("Delete","Deletando arquivo: " + nomeArquivo + " " + file.delete());

        return "Cidades Importadas com sucesso";
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
