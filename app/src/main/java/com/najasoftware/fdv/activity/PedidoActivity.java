package com.najasoftware.fdv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.R;
import com.najasoftware.fdv.adapter.ItemAdapter;
import com.najasoftware.fdv.dao.ConfigFtpDAO;
import com.najasoftware.fdv.dao.FormaPgtoDAO;
import com.najasoftware.fdv.dao.ItemDAO;
import com.najasoftware.fdv.dao.PedidoDAO;
import com.najasoftware.fdv.helper.PedidoHelper;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.ConfigFtp;
import com.najasoftware.fdv.model.FormaPgto;
import com.najasoftware.fdv.model.Item;
import com.najasoftware.fdv.model.Pedido;

import org.parceler.Parcels;
import org.parceler.apache.commons.lang.StringUtils;

import java.util.List;

public class PedidoActivity extends BaseActivity {

    private Pedido pedido;
    private RecyclerView recyclerView;
    private FdvApplication app;
    private List<Item> itens;
    private Cliente cliente;
    private PedidoHelper pedidoHelper;
    private ConfigFtpDAO configFtpDAO;
    private ConfigFtp configFtp;
    private Spinner comboFormaPgto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        setUpToolBar();

        app = FdvApplication.getInstance();
        pedido = app.getPedido();

        String title = "";
        pedidoHelper = new PedidoHelper(this);

        comboFormaPgto = (Spinner) findViewById(R.id.comboFormaPgto);

        comboFormaPgto(pedido);

        if (pedido != null) {
            pedidoHelper.preencherActivity(pedido);
            title = pedido.getCliente().getNomeFantasia();
        } else if (getIntent().getParcelableExtra("cliente") != null) {
            cliente = Parcels.unwrap(getIntent().getParcelableExtra("cliente"));
            title = cliente.getNomeFantasia();
        }

        //Adiciona upNavigation (botão voltar) na action bar
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.fabAddItemPedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("Adicionar produto ao pedido");
                Intent intent = new Intent(PedidoActivity.this, CategoriaProdutoActivity.class);
                app = FdvApplication.getInstance();
                app.setPedido(pedido);
                startActivity(intent);
            }
        });

        findViewById(R.id.fabSalvarPedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = pedidoHelper.validaFormulario();
                if (msg.equals("ok")) {
                    salvar();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    finish();
                } else {
                    toast(msg);
                }
            }
        });

        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImgPedido);
        appBarImg.setImageResource(R.drawable.pedido);
    }

    private void comboFormaPgto(Pedido pedido) {

        FormaPgtoDAO formaPgtoDAO = new FormaPgtoDAO(this);
        List<FormaPgto> formasPgto = formaPgtoDAO.getFormasPgto();

        //Combo da Forma de Pgto
        final Spinner combo = (Spinner) findViewById(R.id.comboFormaPgto);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, formasPgto);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        combo.setAdapter(adapter);

        if (pedido != null) {
            FormaPgto formaPgto = pedido.getFormaPgto();
            if (formaPgto != null) {
                if (formasPgto.contains(formaPgto)) {
                    combo.setSelection(formasPgto.indexOf(formaPgto));
                }
            }
        }

        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    toast("Selecione a forma de pagamento");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedido, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search_produto).getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #000000>Produto</font>"));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (StringUtils.isNotBlank(newText)) {
                    carregaLista(newText);
                } else {
                    carregaLista(null);
                }
                return false;
            }
        });

        return true;
    }

    private void carregaLista(String busca) {

        // Busca os itens
        ItemDAO itemDAO = new ItemDAO(getContext());

        if (busca == null || busca.equals("")) {
            this.itens = itemDAO.buscalAll("", pedido);
        } else {
            this.itens = itemDAO.buscalAll(busca, pedido);
        }

        recyclerView = (RecyclerView) findViewById(R.id.rvItemPedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        // Atualiza a lista
        recyclerView.setAdapter(new ItemAdapter(getContext(), itens, onClickItem()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista(null);
//        comboFormaPgto.clearFocus();
//        comboFormaPgto.requestFocusFromTouch();
//        comboFormaPgto.requestFocus();
    }

    private ItemAdapter.ItemOnClickListener onClickItem() {
        return new ItemAdapter.ItemOnClickListener() {
            @Override
            public void onClickItem(View view, int idx) {
                Item item = itens.get(idx);
                editarItem(item);
            }

            @Override
            public void onLongClickItem(View view, int idx, String menu) {
                Item item = itens.get(idx);
                if (menu.equals("excluir")) {
                    PedidoDAO pedidoDAO = new PedidoDAO(PedidoActivity.this);
                    item.setPedido(pedido);
                    pedidoDAO.excluirItem(item);
                    pedidoHelper.preencherActivity(pedidoDAO.getPedido(pedido.getId()));
                    carregaLista(null);
                    toast("Produto removido com sucesso");
                } else if (menu.equals("editar")) {
                    editarItem(item);
                }
            }
        };
    }

    private void editarItem(Item item) {
        Intent intent = new Intent(PedidoActivity.this, AddItemPedidoActivity.class);
        intent.putExtra("item", Parcels.wrap(item));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_excluir_pedido) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete_pedido)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            PedidoDAO pedidoDAO = new PedidoDAO(PedidoActivity.this);
                            pedidoDAO.excluir(pedido);
                            app.setCliente(null);
                            app.setPedido(null);
                            toast("Excluido com sucesso!");
                            Intent intent = new Intent(PedidoActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
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
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {
        pedido.setFormaPgto(pedidoHelper.getPedido().getFormaPgto());
        pedido.setObs(pedidoHelper.getPedido().getObs());
        PedidoDAO pedidoDAO = new PedidoDAO(getContext());

        int retorno = pedidoDAO.salvarPedido(pedido);

        if (retorno != -1) {
            toast("Pedido salvo com sucesso");
        } else {
            toast("Erro ao salvar o pedido");
        }
    }

}