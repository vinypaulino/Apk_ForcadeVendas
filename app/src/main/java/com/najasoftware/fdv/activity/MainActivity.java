package com.najasoftware.fdv.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.R;
import com.najasoftware.fdv.adapter.ClienteAdapter;
import com.najasoftware.fdv.dao.ClienteDAO;
import com.najasoftware.fdv.dao.ConfigFtpDAO;
import com.najasoftware.fdv.dao.PedidoDAO;
import com.najasoftware.fdv.fragments.AboutDialog;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.ConfigFtp;
import com.najasoftware.fdv.model.Pedido;
import com.najasoftware.fdv.model.PedidoStatusEnum;
import com.najasoftware.fdv.task.EnviaClientesAsyncTask;
import com.najasoftware.fdv.task.EnviaPedidosTask;
import com.najasoftware.fdv.util.PermissionUtils;
import com.najasoftware.fdv.util.Util;

import org.parceler.Parcels;

import java.util.List;

import livroandroid.lib.utils.AndroidUtils;

public class MainActivity extends BaseActivity {

    private Util util = new Util(this);
    private ConfigFtpDAO configFtpDAO;
    private ConfigFtp configFtp;
    private List<Cliente> clientes;
    private RecyclerView recyclerView;
    private FdvApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        setUpNavDrawer();
        app = FdvApplication.getInstance();

        String[] permissoes = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        PermissionUtils.validate(this, 0, permissoes);

        findViewById(R.id.fab_add_cliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadClienteActivity.class);
                intent.putExtra("cliente", Parcels.wrap(null));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search_cliente).getActionView();
        mSearchView.setQueryHint("Cliente");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /*
             * Responsavel pela busca dos clientes na parte superior da Tela Clientes
             */
            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 0) {
                    carregaLista(newText);
                } else {
                    //Caso não tenha informações de busca
                    carregaLista(null);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about) {
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;
        } else if (id == R.id.action_enviar_dados) {

            if (AndroidUtils.isNetworkAvailable(getContext())) {
                configFtpDAO = new ConfigFtpDAO(this);
                configFtp = configFtpDAO.getConfigFtp();

                //Chamar uma task para rodar em segundo plano
                if (configFtp != null) {
                    new EnviaPedidosTask(this, PedidoStatusEnum.AGUARDANDO_ENVIO).execute();
                } else {
                    Intent intent = new Intent(this, ConfigFtpActivity.class);
                    startActivity(intent);
                }
            } else {
                toast("Necessário uma conexão com a internet");
            }
        } else if (id == R.id.action_enviar_apenas_cliente) {

            if (AndroidUtils.isNetworkAvailable(getContext())) {
                configFtpDAO = new ConfigFtpDAO(this);
                configFtp = configFtpDAO.getConfigFtp();

                //Chamar uma task para rodar em segundo plano
                if (configFtp != null) {
                    new EnviaClientesAsyncTask(this).execute();
                } else {
                    Intent intent = new Intent(this, ConfigFtpActivity.class);
                    startActivity(intent);
                }
            } else {
                toast("Necessário uma conexão com a internet");
            }
        } else if (id == R.id.action_sair) {
            finish();
        } else if (id == R.id.action_download_dados) {
            startActivity(new Intent(getContext(), ConfigGeralActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void carregaLista(String busca) {
        // Busca os clientes
        ClienteDAO clienteDAO = new ClienteDAO(getContext());

        if (busca == null) {
            this.clientes = clienteDAO.buscaClientes();
        } else {
            this.clientes = clienteDAO.buscalAll(busca);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        // Atualiza a lista
        recyclerView.setAdapter(new ClienteAdapter(getContext(), clientes, onClickCliente()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.setPedido(null);
        app.setCliente(null);
        carregaLista(null);
    }

    private ClienteAdapter.ClienteOnClickListener onClickCliente() {
        return new ClienteAdapter.ClienteOnClickListener() {
            @Override
            public void onClickCliente(View view, int idx) {
                Cliente cliente = clientes.get(idx);
                app = FdvApplication.getInstance();

                //Tomar decisão sobre ir para a busca ou para o pedido;
                //Realizar uma busca na tabela de pedido;
                //Se tiver pedido mostrar;
                PedidoDAO pedidoDAO = new PedidoDAO(getContext());
                Pedido pedido = pedidoDAO.getPedido(cliente, PedidoStatusEnum.AGUARDANDO_ENVIO);

                //Já existe um pedido
                if (pedido != null) {
                    Intent intentVaiProPedido = new Intent(getContext(), PedidoActivity.class);
                    app.setCliente(null);
                    app.setPedido(pedido);
                    startActivity(intentVaiProPedido);
                } else { //nao existe um pedido para o cliente
                    Intent intent = new Intent(getContext(), CategoriaProdutoActivity.class);
                    app = FdvApplication.getInstance();
                    app.setCliente(cliente);
                    app.setPedido(null);
                    startActivity(intent);


//                    Intent intent = new Intent(getContext(), BuscarProdutosActivity.class);
//                    app = FdvApplication.getInstance();
//                    app.setCliente(cliente);
//                    app.setPedido(null);
//                    startActivity(intent);
                }

            }

            @Override
            public void onLongClickCliente(View view, int idx, String menu) {
                Cliente c = clientes.get(idx);
                if (menu.equals("ligar")) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        toast("Va em configurações de seu celular e de permissão para o aplicativo realizar ligações!!!");
                    } else {
                        Uri telefoneDoCliente = Uri.parse("tel:" + c.getTelefones().get(0).getNumero().toString());
                        Intent intent = new Intent(Intent.ACTION_CALL, telefoneDoCliente);
                        getContext().startActivity(intent);
                        toast("Aguarde...");
                    }
                } else if (menu.equals("editar")) {
                    toast("Aguarde...");
                    Intent intent = new Intent(getContext(), CadClienteActivity.class);
                    app.setCliente(c);
                    getContext().startActivity(intent);
                }
            }
        };
    }
}