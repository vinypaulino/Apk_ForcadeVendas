package com.najasoftware.fdv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.najasoftware.fdv.dao.CategoriaDAO;
import com.najasoftware.fdv.model.CategoriaProduto;
import com.najasoftware.fdv.service.CategoriaProdutoService;
import com.najasoftware.fdv.util.FtpUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class CategoriasAsyncTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final String cnpj;
    private ProgressDialog progressDialog;

    public CategoriasAsyncTask(Context ctx,String cnpj) {
        this.context = ctx;
        this.cnpj   = cnpj;
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
        String nomeArquivo = "categorias_fdv.json";

        if (conectado) {

            //fazer o download dos arquivos e desconectar
            boolean download = ftpUtil.download("/"+cnpj, nomeArquivo, context.getFilesDir() + "/" + nomeArquivo);

            if (download) {
                //Ler os dados do arquivo JSON
                try {
                    List<CategoriaProduto> categorias = CategoriaProdutoService.getCategorias(context);

                    CategoriaProduto categoriaProduto = new CategoriaProduto(Long.parseLong("0"),"Todos Produtos",null);
                    categorias.add(0,categoriaProduto);

                    if (categorias != null) {
                        CategoriaDAO categoriaDAO = new CategoriaDAO(context);
                        categoriaDAO.deleteAll();
                        for (CategoriaProduto ct : categorias) {
                            categoriaDAO.insere(ct);
                        }
                        categoriaDAO.close();
                    } else {
                        return "Relação de grupo de produtos vazia";
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

        File file = new File(context.getFilesDir(),nomeArquivo);
        Log.d("Delete","Deletando arquivo: " + nomeArquivo + " " + file.delete());

        return "Grupos Importados com sucesso";
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}