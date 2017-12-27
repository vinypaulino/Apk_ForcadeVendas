package com.najasoftware.fdv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.dao.ConfigFtpDAO;
import com.najasoftware.fdv.model.ConfigFtp;
import com.najasoftware.fdv.model.Credencial;
import com.najasoftware.fdv.service.ConsultaCnpjService;
import com.najasoftware.fdv.task.CategoriasAsyncTask;
import com.najasoftware.fdv.task.CidadesAsyncTask;
import com.najasoftware.fdv.task.ClienteAsyncTask;
import com.najasoftware.fdv.task.FormaPgtoAsyncTask;
import com.najasoftware.fdv.task.ProdutosAsyncTask;
import com.najasoftware.fdv.task.VendedoresAsyncTask;

import java.io.IOException;
import java.util.List;

import livroandroid.lib.utils.AndroidUtils;

public class ConfigGeralActivity extends BaseActivity {

    private ConfigFtpDAO configFtpDAO;
    private ConfigFtp configFtp;
    private String cnpj = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_geral);
        setUpToolBar();
        getSupportActionBar().setTitle("Configurações Gerais");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getCnpj();
    }

    public void toConfigFtp(View v) {
        startActivity(new Intent(getContext(), ConfigFtpActivity.class));
    }

    public void importarVendedores(View v) {
        //Checar se tem rede e conecta ao servidor FTP
        importarVendedoresTask(this);
    }

    public void importarVendedoresTask(Context context) {
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            new VendedoresAsyncTask(context,"05056416000145").execute();
        } else {
            toast("Necessário uma conexão de rede!");
        }
    }

    public void importarCidades(View v) {
        //Checar se tem rede e conecta ao servidor FTP
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            new CidadesAsyncTask(this,cnpj).execute();
        } else {
            toast("Necessário uma conexão de rede!");
        }
    }

    public void importarClientes(View v) {
        //Checar se tem rede e conecta ao servidor FTP
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            new ClienteAsyncTask(this,cnpj).execute();
        } else {
            toast("Necessário uma conexão com a internet!");
        }
    }

    public void importarCategorias(View v) {
        //Checar se tem rede e conecta ao servidor FTP
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            new CategoriasAsyncTask(this,cnpj).execute();
        } else {
            toast("Necessário uma conexão com a internet!");
        }
    }

    public void importarProdutos(View v) {
        //Checar se tem rede e conecta ao servidor FTP
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            new ProdutosAsyncTask(this,cnpj).execute();
        } else {
            toast("Necessário uma conexão com a internet!");
        }
    }

    public void importarFormaPgto(View v) {
        //Checar se tem rede e conecta ao servidor FTP
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            new FormaPgtoAsyncTask(this,cnpj).execute();
        } else {
            toast("Necessário uma conexão com a internet!");
        }
    }

    @Override
    protected void onResume() {

        Button btnImportVendedores = (Button) findViewById(R.id.btnVendedoresConfigGeral);
        Button btnImportClientes = (Button) findViewById(R.id.btnClientesConfigGeral);
        Button btnImportCategorias = (Button) findViewById(R.id.btnGruposProdutosConfigGeral);
        Button btnImportProdutos = (Button) findViewById(R.id.btnProdutoConfigGeral);
        Button btnImportFormaPgto = (Button) findViewById(R.id.btnFormaPgto);
        Button btnImportCidades = (Button) findViewById(R.id.btnCidades);

        configFtpDAO = new ConfigFtpDAO(this);
        configFtp = configFtpDAO.getConfigFtp();

        if (configFtp == null) {

            btnImportVendedores.setEnabled(false);
            btnImportClientes.setEnabled(false);
            btnImportCategorias.setEnabled(false);
            btnImportProdutos.setEnabled(false);
            btnImportFormaPgto.setEnabled(false);
            btnImportCidades.setEnabled(false);

        } else {

            btnImportVendedores.setEnabled(true);
            btnImportClientes.setEnabled(true);
            btnImportCategorias.setEnabled(true);
            btnImportProdutos.setEnabled(true);
            btnImportFormaPgto.setEnabled(true);
            btnImportCidades.setEnabled(true);

        }

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCnpj() {
        //Ler os dados do arquivo JSON
        List<Credencial> credencialList = null;
        try {
            credencialList = ConsultaCnpjService.getDadosIniciais(this);
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