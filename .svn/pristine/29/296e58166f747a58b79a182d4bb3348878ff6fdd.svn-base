package com.najasoftware.fdv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.R;
import com.najasoftware.fdv.dao.ConfigFtpDAO;
import com.najasoftware.fdv.dao.VendedorDAO;
import com.najasoftware.fdv.helper.LoginHelper;
import com.najasoftware.fdv.model.ConfigFtp;
import com.najasoftware.fdv.model.Vendedor;

import java.io.File;

public class LoginActivity extends BaseActivity {

    private Button entrar;
    private Button configurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        entrar = (Button) findViewById(R.id.btLogin);
        configurar = (Button) findViewById(R.id.btConfigurarTelaLogin);

        File file = new File(this.getFilesDir(), "credencial.json");

        if (!file.exists()) {
            finish();
            startActivity(new Intent(LoginActivity.this, LocalizaCnpjActivity.class));
        }
    }

    public void onClickBtLogin(View v) {

        VendedorDAO vendedorDAO = new VendedorDAO(this);
        Vendedor vendedorForm, vendedor;
        LoginHelper loginHelper = new LoginHelper(LoginActivity.this);
        vendedorForm = loginHelper.getVendedor();

        if (vendedorForm != null) {
            vendedor = vendedorDAO.getVendedor(vendedorForm.getLogin());
            if (vendedor.getId() != null) {
                if (vendedor.getLogin().equals(vendedorForm.getLogin()) && vendedor.getSenha().equals(vendedorForm.getSenha())) {
                    FdvApplication app = FdvApplication.getInstance();
                    app.setVendedor(vendedor);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    toast("Login ou senha incorretos.");
                }
            } else {
                toast("Usuário não cadastrado.");
            }

        } else {
            toast("Preecher dados para login");
        }
    }

    public void onClickBtConfiguracoes(View v) {
        Intent intent = new Intent(LoginActivity.this, ConfigGeralActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConfigFtpDAO configFtpDAO = new ConfigFtpDAO(this);
        ConfigFtp configFtp = configFtpDAO.getConfigFtp();
        if (configFtp == null) {
            entrar.setEnabled(false);
        } else {
            entrar.setEnabled(true);
        }
    }
}