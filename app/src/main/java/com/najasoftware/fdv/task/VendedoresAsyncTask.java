package com.najasoftware.fdv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.najasoftware.fdv.dao.VendedorDAO;
import com.najasoftware.fdv.model.Vendedor;
import com.najasoftware.fdv.service.VerdedorService;
import com.najasoftware.fdv.util.FtpUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class VendedoresAsyncTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progressDialog;
    private String cnpj;

    public VendedoresAsyncTask(Context ctx,String cnpj) {
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
        String nomeArquivo ="vendedores_fdv.json";
        if (conectado) {
            //fazer o download dos arquivos e desconectar
            boolean download = ftpUtil.download("/"+cnpj, nomeArquivo,context.getFilesDir() + "/"+ nomeArquivo);

            if (download) {

                //Ler os dados do arquivo JSON
                try {

                    List<Vendedor> vendedores = VerdedorService.getVendedores(context);

                    //Se tiver vendedores para importar
                    if (vendedores != null) {
                        VendedorDAO vendedorDAO = new VendedorDAO(context);
                        vendedorDAO.deleteAll();
                        for (Vendedor vendedor : vendedores) {
                            vendedorDAO.insere(vendedor);
                        }
                    } else {
                        return "Relação de vendedores vazia";
                    }

                } catch (IOException e) {
                    Log.e("Fdv", e.getMessage(), e);
                }
            } else {
                return "Arquivo de vendedores não encontrado no servidor!!, verifique o usuário do FTP e a pasta Home do usuário";
            }
        } else {
            return "Não conectado";
        }

//        File file = new File(context.getFilesDir(), nomeArquivo);
//        Log.d("Delete","Deletando arquivo: " + nomeArquivo + " " + file.delete());

        return "Vendedores Importados com sucesso";

    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}