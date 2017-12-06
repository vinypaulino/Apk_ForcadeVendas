package com.najasoftware.fdv.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.helper.LocalizaCnpjHelper;
import com.najasoftware.fdv.model.Credencial;
import com.najasoftware.fdv.service.ConsultaCnpjService;
import com.najasoftware.fdv.util.FtpUtil;
import com.najasoftware.fdv.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import livroandroid.lib.utils.AndroidUtils;

public class LocalizaCnpjActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnpj);
    }

    public void consultarCnpj(View v) {

        LocalizaCnpjHelper localizaCnpjHelper = new LocalizaCnpjHelper(LocalizaCnpjActivity.this);
        boolean retorno = localizaCnpjHelper.validarCampos();

        if (retorno) {
            Credencial credencial = localizaCnpjHelper.getCredencial();
            //Checar se tem rede e conecta ao servidor FTP
            if (AndroidUtils.isNetworkAvailable(getContext())) {
                new ConsultaCnpjAsyncTask(this, credencial).execute();
            } else {
                toast("Necessário uma conexão de rede!");
            }
        } else {
            toast("Login ou Senha inválidos");
        }
    }

    public void login() {
        toast("Aguarde");
        startActivity(new Intent(LocalizaCnpjActivity.this, LoginActivity.class));
    }

    private class ConsultaCnpjAsyncTask extends AsyncTask<Object, Object, String> {

        private final Context context;
        private ProgressDialog progressDialog;
        private Credencial credencial;

        public ConsultaCnpjAsyncTask(Context ctx, Credencial credencial) {
            this.context = ctx;
            this.credencial = credencial;
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
            String nomeArquivoOrigem = credencial.getCnpj() + ".json";
            String nomeArquivoDestino = "credencial.json";

            if (conectado) {
                //fazer o download dos arquivos e desconectar
                boolean download = ftpUtil.download("/"+credencial.getCnpj(), nomeArquivoOrigem, context.getFilesDir() + "/" + nomeArquivoDestino);

                if (download) {

                    try {
                        //Ler os dados do arquivo JSON
                        List<Credencial> credencialList = ConsultaCnpjService.getDadosIniciais(context);

                        //Se tiver credenciais para importar
                        if (credencialList != null) {
                            //Que veio do arquivo baixado
                            Credencial credDoArquivoJson = credencialList.get(0);

                            if (credDoArquivoJson.getCnpj().equals(credencial.getCnpj())) {

                                Util util = new Util();

                                String md5SenhaArquivo = credDoArquivoJson.getSenha();
                                String keyArquivo = credDoArquivoJson.getKey();

                                String senha = credencial.getSenha() + keyArquivo;
                                String md5SenhaDigitadaUsuario = util.md5(senha);

                                if (md5SenhaArquivo.equals(md5SenhaDigitadaUsuario)) {
                                    //A credencial da classe recebe a do arquivo com todos os dados
                                    credencial = credDoArquivoJson;
                                    return "ok";
                                } else {
                                    //Remover arquivo
                                    File file = new File(context.getFilesDir(), nomeArquivoDestino);
                                    Log.d("Delete","Deletando arquivo: " + nomeArquivoDestino + " " + file.delete());
                                    return "Login ou senha incorretos";
                                }
                            }

                        } else {
                            return "Arquivo Vazio";
                        }

                    } catch (IOException e) {
                        Log.e("Fdv", e.getMessage(), e);
                    }
                } else {
                    return "nja-101";
                }
            } else {
                return "Não conectado";
            }
            return "Importado com sucesso";
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();

            if (s.equals("ok")) {
                finish();
                login();
            } else {
                toast(s);
            }
        }
    }
}
