package com.najasoftware.fdv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.R;
import com.najasoftware.fdv.dao.FormaPgtoDAO;
import com.najasoftware.fdv.dao.ItemDAO;
import com.najasoftware.fdv.dao.PedidoDAO;
import com.najasoftware.fdv.helper.AddItemPedidoHelper;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.ClienteStatusEnum;
import com.najasoftware.fdv.model.CrudEnum;
import com.najasoftware.fdv.model.Item;
import com.najasoftware.fdv.model.Pedido;
import com.najasoftware.fdv.model.Produto;
import com.najasoftware.fdv.util.Util;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lemoel Marques - NajaSoftware on 04/03/2016.
 * lemoel@gmail.com
 */
public class AddItemPedidoActivity extends BaseActivity {

    private Item item;
    private Produto produto;
    private Pedido pedido;
    private Cliente cliente;
    private AddItemPedidoHelper addItemPedidoHelper;
    private FdvApplication app;
    private ActionBar actionBar;
    private CrudEnum crudEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_pedido);

        //Configura a toolbar como Action Bar
        setUpToolBar();

        addItemPedidoHelper = new AddItemPedidoHelper(this);

        //Adiciona upNavigation (botão voltar) na action bar
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        app = FdvApplication.getInstance();

        //Se for um novo item do pedido o produto vem preenchido
        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));

        //Em caso de Edição vem um item do pedido
        Item itemEditar = Parcels.unwrap(getIntent().getParcelableExtra("item"));

        //Buscando o cliente em uso neste pedido
        cliente = app.getCliente();

        //Pedido, se for edição vem com um pedido já
        pedido = app.getPedido();

        //

        if (produto != null) {
            crudEnum = CrudEnum.NOVO_ITEM;
            actionBar.setTitle(produto.getNome());
            item = new Item();
            item.setNome(produto.getNome());
            item.setProduto(produto);
            addItemPedidoHelper.preencherActivity(item);
        } else if (itemEditar != null) {
            item = itemEditar;
            crudEnum = CrudEnum.EDITAR_ITEM;
            actionBar.setTitle(item.getProduto().getNome());
            addItemPedidoHelper.preencherActivity(item);
        }

        //Adicionado para calcular o valor total quando clica no produto (Vinícius) 30-11-2016
        addItemPedidoHelper.recalculaChangeQtde("1");

        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImg);
        //Picasso.with(getContext()).load(produto.getUrlFoto()).into(appBarImg);
        appBarImg.setImageResource(R.drawable.supermercado_internet);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarItem();
            }
        });

        changeQuantidade();
        changeDesconto();

    }


    //Quanto a quantidade e trocada
    private void changeQuantidade() {
        EditText etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        etQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String qtde = String.valueOf(s).toString().trim();
                addItemPedidoHelper.recalculaChangeQtde(qtde);
            }
        });
    }

    // change do desconto
    private void changeDesconto() {
        EditText etDesconto = (EditText) findViewById(R.id.etDesconto);
        etDesconto.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String desconto = String.valueOf(s).toString().trim();
                addItemPedidoHelper.recalculaChangeDesconto(desconto);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_item_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_item_salvar:
                salvarItem();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void salvarItem() {

        Util util = new Util();

        String msg = addItemPedidoHelper.validaCampos();

        if (msg.equals("ok")) {

            if (pedido == null) {
                pedido = new Pedido();
                pedido.setStatus(1);
                pedido.setDataVenda(util.dataAtual());

                //Guardando cliente no pedido
                pedido.setCliente(cliente);

                FdvApplication app = FdvApplication.getInstance();
                pedido.setVendedor(app.getVendedor());

                FormaPgtoDAO formaPgtoDAO = new FormaPgtoDAO(this);
                pedido.setFormaPgto(formaPgtoDAO.getFormasPgto(Long.parseLong("0")));
            }

            PedidoDAO pedidoDAO = new PedidoDAO(AddItemPedidoActivity.this);
            Item i = new Item();
            i = addItemPedidoHelper.getItem();

            item.setQtde(i.getQtde());
            item.setTotalComDesconto(i.getTotalComDesconto());
            item.setTotalSemDesconto(i.getTotalSemDesconto());
            item.setDesconto(i.getDesconto());

            //Colocando o item dentro do pedido para salvar
            List<Item> itens = new ArrayList<>();
            itens.add(item);
            pedido.setItens(itens);

            Double qtde = addItemPedidoHelper.getItem().getQtde();
            Double preco = addItemPedidoHelper.getItem().getPrecoSugerido();
            Double desconto = Double.parseDouble(util.aproximar(addItemPedidoHelper.getItem().getDesconto()));
            Double totalComDescontoItem = Double.parseDouble(util.aproximar((qtde * preco) - desconto));
            Double totalSemDescontoItem = Double.parseDouble(util.aproximar(qtde * preco));


            //Trocando o status do cliente para alterado
            //Cliente c = pedido.getCliente();
            //c.setStatus(ClienteStatusEnum.ALTERADO.getStatus());
            //pedido.setCliente(c);

            //Pedido nulo é o primeiro item que esta sendo salvo
            if (pedido.getId() == null) {

                pedido.setTotalComDesconto(totalComDescontoItem);
                pedido.setTotalSemDesconto(totalSemDescontoItem);
                pedido.setDesconto(desconto);

                Long id = pedidoDAO.gravar(pedido);
                if (id != -1) {
                    //Buscando o Pedido COM TODOS OS ITENS DO PEDIDO
                    pedido = pedidoDAO.getPedido(id);
                }

            } else {//Já existe itens salvos, esta sendo adicionado novos itens ou editando
                pedidoDAO.udpate(pedido, crudEnum);
                //Buscando o Pedido COM TODOS OS ITENS DO PEDIDO
                pedido = pedidoDAO.getPedido(pedido.getId());
            }

            if (pedido.getId() != null) {
                //Itens do pedido
                ItemDAO itemDAO = new ItemDAO(this);
                itens = itemDAO.getItens(pedido.getId());

                //Gravando todos os itens dentro do pedido, tudo veio do banco agora e não da tela
                pedido.setItens(itens);

                Intent intent = new Intent(AddItemPedidoActivity.this, PedidoActivity.class);
                FdvApplication app = FdvApplication.getInstance();
                app.setPedido(pedido);
                toast("Produto adiconado ao pedido!!");
                startActivity(intent);
                finish();
            } else {
                toast("Erro ao salvar o pedido");
            }

        } else {

            toast(msg);
        }
    }

}