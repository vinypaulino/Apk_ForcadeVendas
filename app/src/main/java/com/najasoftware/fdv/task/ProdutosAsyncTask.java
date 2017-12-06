package com.najasoftware.fdv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.najasoftware.fdv.dao.ParametrosDAO;
import com.najasoftware.fdv.dao.ProdutoDAO;
import com.najasoftware.fdv.model.Parametro;
import com.najasoftware.fdv.model.Produto;
import com.najasoftware.fdv.service.ParametroService;
import com.najasoftware.fdv.service.ProdutoService;
import com.najasoftware.fdv.util.FtpUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class ProdutosAsyncTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final String cnpj;
    private ProgressDialog progressDialog;

    public ProdutosAsyncTask(Context ctx, String cnpj) {
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
        String nomeArquivo =  "produtos_fdv.json";
        String nomeArquivoParametro =  "parametros_fdv.json";

        if (conectado) {
            //fazer o download dos arquivos e desconectar
            boolean download = ftpUtil.download("/"+cnpj, nomeArquivo, context.getFilesDir() + "/"+ nomeArquivo);
            boolean downloadParametro = false;

            if (ftpUtil.conectar(null)){
                downloadParametro = ftpUtil.download("/"+cnpj, nomeArquivoParametro, context.getFilesDir() + "/"+ nomeArquivoParametro);
            }

            if (download) {
                try {

                    Parametro parametro;
                    List<Produto> produtos = ProdutoService.getProdutos(context);

                    if (downloadParametro == true) {
                        parametro = ParametroService.getParametros(context);
                        if (parametro != null) {
                            ParametrosDAO parametrosDAO = new ParametrosDAO(context);
                            parametrosDAO.deleteAll();
                            parametrosDAO.insere(parametro);
                        }
                    }

                    if (produtos != null) {
                        ProdutoDAO produtoDAO = new ProdutoDAO(context);
                        produtoDAO.deleteAll();
                        for (Produto p : produtos) {
                            produtoDAO.insere(p);
                        }
                    } else {
                        return "Relação de produtos vazia!";
                    }

                } catch (IOException e) {
                    Log.e("Fdv", e.getMessage(), e);
                }
            }  else {
                return "Arquivo de vendedores não encontrado no servidor!!, verifique o usuário do FTP e a pasta Home do usuário";
            }
        } else {
            return "Não conectado";
        }

        File file = new File(context.getFilesDir(),nomeArquivo);
        Log.d("Delete","Deletando arquivo: " + nomeArquivo + " " + file.delete());

        return "Produtos Importados com sucesso";
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
