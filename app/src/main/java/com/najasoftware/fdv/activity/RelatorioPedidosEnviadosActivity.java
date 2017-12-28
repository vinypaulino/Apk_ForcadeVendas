package com.najasoftware.fdv.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.adapter.PedidoAdapter;
import com.najasoftware.fdv.dao.PedidoDAO;
import com.najasoftware.fdv.model.Pedido;
import com.najasoftware.fdv.model.PedidoStatusEnum;

import java.util.List;

public class RelatorioPedidosEnviadosActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_pedidos_enviados);
        setUpToolBar();
        getSupportActionBar().setTitle("Pedidos enviados");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista(null);
    }

    private void carregaLista(Object o) {
        // Busca os pedidos
        PedidoDAO pedidoDAO = new PedidoDAO(getContext());
        List<Pedido> pedidos = pedidoDAO.pedidos(PedidoStatusEnum.ENVIADO);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new PedidoAdapter(getContext(), pedidos, onClickPedido()));
    }

    private PedidoAdapter.PedidosOnClickListener onClickPedido() {
        return new PedidoAdapter.PedidosOnClickListener() {
            @Override
            public void onClickPedido(View view, int idx) {
                toast("Click Pedido");
            }

            @Override
            public void onLongClickPedido(View view, int idx, String menu) {
                if (menu == "editar") {
                    toast("Editar");
                } else {
                    toast("Ligar");
                }

            }

        };
    }
}
