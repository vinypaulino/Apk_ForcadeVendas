package com.najasoftware.fdv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.R;
import com.najasoftware.fdv.adapter.CategoriaAdapter;
import com.najasoftware.fdv.dao.CategoriaDAO;
import com.najasoftware.fdv.dao.ProdutoDAO;
import com.najasoftware.fdv.model.CategoriaProduto;

import org.parceler.Parcels;
import org.parceler.apache.commons.lang.StringUtils;

import java.util.List;

public class CategoriaProdutoActivity extends BaseActivity {

    private List<CategoriaProduto> categorias;
    private RecyclerView recyclerView;
    private FdvApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_produto);
        setUpToolBar();
        getSupportActionBar().setTitle("Categorias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_busca_categoria, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.item_search_categoria).getActionView();
        mSearchView.setQueryHint("Categoria");
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
                    //Caso não tenha informações de busca
                    carregaLista(null);
                }
                return false;
            }
        });
        return true;
    }

    private void carregaLista(String busca) {
        // Busca os clientes
        CategoriaDAO categoriaDAO = new CategoriaDAO(getContext());

        if (busca == null) {
            this.categorias = categoriaDAO.getCategorias();
        } else {
            this.categorias = categoriaDAO.getCategorias(busca);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategorias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        // Atualiza a lista
        recyclerView.setAdapter(new CategoriaAdapter(getContext(), categorias, onClickCategoria()));
    }

    private CategoriaAdapter.CategoriaOnClickListener onClickCategoria() {
        return new CategoriaAdapter.CategoriaOnClickListener() {
            @Override
            public void onClickCategoria(View view, int idx) {
                CategoriaProduto categoria = categorias.get(idx);
                ProdutoDAO produtoDAO = new ProdutoDAO(CategoriaProdutoActivity.this);
                int qtdeProduto = produtoDAO.isProduto(categoria);
                if (qtdeProduto > 0) {
                    Intent intent = new Intent(CategoriaProdutoActivity.this, ProdutoActivity.class);
                    intent.putExtra("categoria", Parcels.wrap(categoria));
                    startActivity(intent);
                } else {
                    toast("Não existe produto para esta categoria");
                }

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista(null);
    }
}
