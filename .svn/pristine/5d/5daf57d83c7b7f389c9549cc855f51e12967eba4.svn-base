package com.najasoftware.fdv.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.dao.ConfigFtpDAO;
import com.najasoftware.fdv.helper.ConfigFtpHelper;
import com.najasoftware.fdv.model.ConfigFtp;
import com.najasoftware.fdv.task.FtpAsyncTask;

import livroandroid.lib.utils.AndroidUtils;

public class ConfigFtpActivity extends BaseActivity {

    private ConfigFtpHelper configHelper;
    //private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_ftp);
        setUpToolBar();
        //nestedScrollView = (NestedScrollView) findViewById(R.id.nested);

        //Adiciona upNavigation (botão voltar) na action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ConfigFtpDAO configFtpDAO = new ConfigFtpDAO(this);
        ConfigFtp configFtp = configFtpDAO.getConfigFtp();

        configHelper = new ConfigFtpHelper(this);

        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImgFtp);
        appBarImg.setImageResource(R.drawable.network_nova);

        if (configFtp != null) {
            configHelper.preecherActivity(configFtp);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_salvar_config_ftp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = salvar();
                if (b) {
                    toast("Configurações gravadas com sucesso");
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_item_pedido, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_item_salvar:
                boolean b = salvar();
                if (b) finish();
                break;
        }
        return false;
    }

    public boolean salvar() {

        boolean b = configHelper.validaCampos();

        if (b) {
            ConfigFtp configFtp = configHelper.getConfigFtp();
            ConfigFtpDAO configFtpDAO = new ConfigFtpDAO(this);
            configFtpDAO.salvar(configFtp);
            return true;
        } else {
            return false;
        }
    }

    public void testarConexao(View v) {

        boolean b = configHelper.validaCampos();
        if (b) {
            if (AndroidUtils.isNetworkAvailable(this)) {
                ConfigFtp ftp = configHelper.getConfigFtp();
                new FtpAsyncTask(this, ftp).execute();
            } else {
                toast("Conexão de rede indisponível");
            }

        } else {
            toast("Erro na validação");
        }
    }

}
