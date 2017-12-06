package com.najasoftware.fdv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.dao.ConfigFtpDAO;
import com.najasoftware.fdv.dao.PedidoDAO;
import com.najasoftware.fdv.model.ConfigFtp;
import com.najasoftware.fdv.model.PedidoStatusEnum;
import com.najasoftware.fdv.task.EnviaClientesAsyncTask;
import com.najasoftware.fdv.task.EnviaPedidosTask;
import com.najasoftware.fdv.util.Util;

import livroandroid.lib.utils.AndroidUtils;
import livroandroid.lib.utils.NavDrawerUtil;
import livroandroid.lib.utils.Prefs;

/**
 * Created by Lemoel Marques - NajaSoftware on 06/04/2016.
 * lemoel@gmail.com
 */
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {

    protected DrawerLayout drawerLayout;

    protected void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    // Configura o Nav Drawer
    protected void setUpNavDrawer() {
        // Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        // Ícone do menu do nav drawer
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_fdv);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_fdv);
        if (navigationView != null && drawerLayout != null) {
            // Atualiza a imagem e textos do header
            NavDrawerUtil.setHeaderValues(navigationView, R.id.containerNavDrawerListViewHeaderFdv, R.drawable.nav_drawer_header, R.drawable.ic_no_image, R.string.nav_drawer_username, R.string.nav_drawer_email);
            // Trata o evento de clique no menu.
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // Seleciona a linha
                            menuItem.setChecked(true);
                            // Fecha o menu
                            drawerLayout.closeDrawers();
                            // Trata o evento do menu
                            onNavDrawerItemSelected(menuItem);
                            return true;
                        }
                    });
        }
    }

    // Trata o evento de toque nos itens de menu
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_importar_dados:
                startActivity(new Intent(getContext(), ConfigGeralActivity.class));
                snack(drawerLayout, "Importar dados do Servidor");
                break;
            case R.id.nav_enviar_dados:
                ConfigFtpDAO configFtpDAO;
                ConfigFtp configFtp = new ConfigFtp();
                Util util = new Util();
                //boolean rede = util.checkRede();
                if (AndroidUtils.isNetworkAvailable(getContext())) {
                    configFtpDAO = new ConfigFtpDAO(this);
                    configFtp = configFtpDAO.getConfigFtp();

                    if (configFtp != null) {
                        new EnviaPedidosTask(this, PedidoStatusEnum.AGUARDANDO_ENVIO).execute();
                    } else {
                        toast("Necessário configurar Servidor FTP!");
                        startActivity(new Intent(this, ConfigFtpActivity.class));
                    }

                } else {
                    toast("Necessário uma conexão com a internet!");
                }

                break;

            case R.id.nav_enviar_clientes:
                //boolean rede = util.checkRede();
                if (AndroidUtils.isNetworkAvailable(getContext())) {
                    configFtpDAO = new ConfigFtpDAO(this);
                    configFtp = configFtpDAO.getConfigFtp();

                    if (configFtp != null) {
                        new EnviaClientesAsyncTask(this).execute();
                    } else {
                        toast("Necessário configurar Servidor FTP!");
                        startActivity(new Intent(this, ConfigFtpActivity.class));
                    }

                } else {
                    toast("Necessário uma conexão com a internet!");
                }
                break;

            case R.id.nav_produtos:
                startActivity(new Intent(getContext(), ProdutoActivity.class));
                toast("Produtos");
                break;

            case R.id.nav_pedidos_enviados:
                startActivity(new Intent(getContext(), RelatorioPedidosEnviadosActivity.class));
                break;

            case R.id.nav_limpar_banco:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.limpar_banco)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PedidoDAO pedidoDAO = new PedidoDAO(BaseActivity.this);
                                pedidoDAO.excluirPedidos(PedidoStatusEnum.ENVIADO);
                                toast("Apagado com sucesso!");
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("EXCLUIR");
                d.show();
                break;
            case R.id.nav_item_site_naja:
                Uri uri = Uri.parse("http://www.najasoftware.com.br");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;

            case R.id.nav_item_sair:
                finish();
                break;

            case R.id.nav_clientes:
                startActivity(new Intent(getContext(), CadClienteActivity.class));
                break;

        }
    }


    @Override
    /*trata o evento gerado pelo toque no menu que fica no canto superior esquerdo
       sendo responsavel por abrir o menu lateral.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Trata o clique no botão que abre o menu.
                if (drawerLayout != null) {
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o menu lateral
    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Fecha o menu lateral
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

}