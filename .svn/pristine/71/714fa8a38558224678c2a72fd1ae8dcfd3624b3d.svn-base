package com.najasoftware.fdv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.najasoftware.fdv.dao.FormaPgtoDAO;
import com.najasoftware.fdv.model.FormaPgto;
import com.najasoftware.fdv.service.FormaPgtoService;
import com.najasoftware.fdv.util.FtpUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class FormaPgtoAsyncTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final String cnpj;
    private ProgressDialog progressDialog;

    public FormaPgtoAsyncTask(Context ctx, String cnpj) {
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
        String nomeArquivo = "forma_pgto_fdv.json";

        if (conectado) {
            //fazer o download dos arquivos e desconectar
            boolean download = ftpUtil.download("/"+cnpj, nomeArquivo, context.getFilesDir() + "/" + nomeArquivo);

            if (download) {
                try {
                    List<FormaPgto> formaPgtoList = FormaPgtoService.getFormaPgto(context);

                    if (formaPgtoList != null) {
                        FormaPgtoDAO formaPgtoDAO = new FormaPgtoDAO(context);
                        formaPgtoDAO.deleteAll();
                        formaPgtoList.add(0, new FormaPgto(Long.parseLong("0"), "Forma Pgto"));
                        for (FormaPgto fp : formaPgtoList) {
                            formaPgtoDAO.insere(fp);
                        }
                        formaPgtoDAO.close();
                    } else {
                        return "Relação de forma de pagamento vazia";
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

        File file = new File(context.getFilesDir(), nomeArquivo);
        Log.d("Delete","Deletando arquivo: " + nomeArquivo + " " + file.delete());

        return "Forma de Pagamento importados com sucesso!";
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
