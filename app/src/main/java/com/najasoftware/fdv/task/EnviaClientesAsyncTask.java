package com.najasoftware.fdv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.dao.ClienteDAO;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.ClienteStatusEnum;
import com.najasoftware.fdv.model.Credencial;
import com.najasoftware.fdv.service.ClienteService;
import com.najasoftware.fdv.service.ConsultaCnpjService;
import com.najasoftware.fdv.util.FtpUtil;
import com.najasoftware.fdv.util.Util;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 08/03/2016.
 * lemoel@gmail.com
 */
public class EnviaClientesAsyncTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progressDialog;
    private List<Cliente> clientes;
    private String cnpj;

    public EnviaClientesAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Aguarde...", "Enviando clientes!!!");
    }

    @Override
    protected String doInBackground(Object[] params) {
        return enviaClientes();
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private String enviaClientes() {
        String msg = "";
        //Conectar ao servidor e enviar o arquivo.
        FtpUtil ftpUtil = new FtpUtil(context);
        boolean conectado;
        getCnpj();
        conectado = ftpUtil.conectar(null);

        //Verificando o servidor primeiro, so gerar o arquivo caso
        //o servidor esteja configurado
        if (conectado) {
            ClienteDAO clienteDAO = new ClienteDAO(context);
            FdvApplication app = FdvApplication.getInstance();
            boolean gerarJsonCliente = false;
            Util util = new Util();

            String nomeArquivoClientes = "clientes_" + app.getVendedor().getId().toString()  + "_" + util.dataHora() + ".json";
            clientes = clienteDAO.getClientes(ClienteStatusEnum.ALTERADO);

            if (clientes.size() > 0) {
                ClienteService clienteService = new ClienteService(context);
                gerarJsonCliente = clienteService.gerarJson(nomeArquivoClientes, clientes);
            }

            if (gerarJsonCliente == true) {
                boolean enviaClientes = false;
                enviaClientes = ftpUtil.upload(nomeArquivoClientes, "/"+ cnpj + "/" + nomeArquivoClientes);

                if (enviaClientes == true) {
                    msg += " Clientes enviados com sucesso!\n";
                    clienteDAO.statusTo(clientes, ClienteStatusEnum.DEFAULT);
                } else {
                    msg += " Erro ao enviar Clientes!\n";
                }
            } else {
                msg += "Não havia dados de clientes para envio!\n";
            }

        }

        ftpUtil.desconectar();
        return msg;

    }

    private void getCnpj() {
        //Ler os dados do arquivo JSON
        List<Credencial> credencialList = null;
        try {
            credencialList = ConsultaCnpjService.getDadosIniciais(context);
            //Se tiver credenciais para importar
            if (credencialList != null) {
                //Que veio do arquivo baixado
                Credencial credDoArquivoJson = credencialList.get(0);
                cnpj = credDoArquivoJson.getCnpj();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}