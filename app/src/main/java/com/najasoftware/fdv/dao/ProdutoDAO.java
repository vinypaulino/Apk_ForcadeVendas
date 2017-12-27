package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.najasoftware.fdv.model.CategoriaProduto;
import com.najasoftware.fdv.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/03/2016.
 * lemoel@gmail.com
 */
public class ProdutoDAO extends BancoDAO {

    private static final String TABLE = "PRODUTOS";
    private static String ID = "_id";
    private static String NOME = "NOME";
    private static String PRECO = "PRECO";
    private static String DESCRICAO = "DESCRICAO";
    private static String TP_UNIDADE = "TP_UNIDADE";
    private static String CATEGORIA_ID = "CATEGORIA_ID";
    private static String URL_FOTO = "URL_FOTO";
    private final Context context;
    private String sql;

    public ProdutoDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void insere(Produto produto) {
        ContentValues dados = pegaDadosDoProduto(produto);
        getDb().insert(TABLE, null, dados);
    }

    private ContentValues pegaDadosDoProduto(Produto produto) {
        ContentValues dados = new ContentValues();
        dados.put(ID, produto.getId());
        dados.put(NOME, produto.getNome());
        dados.put(PRECO, produto.getPreco());
        dados.put(TP_UNIDADE, produto.getTipoUnidade());
        dados.put(DESCRICAO, produto.getDescricao());
        dados.put(CATEGORIA_ID, produto.getCategoria().getId());
        dados.put(URL_FOTO, produto.getUrlFoto());
        return dados;
    }

    public List<Produto> buscaProdutos(CategoriaProduto categoria) {

        String where = "";

        if (categoria.getId() != 0) {
            where = " WHERE CAT._id = " + categoria.getId();
        }


        sql = " SELECT P._id, P.NOME, P.PRECO, P.DESCRICAO, P.TP_UNIDADE, P.URL_FOTO, CAT._id CATEGORIA_ID, CAT.NOME CATEGORIA_NOME, CAT.URL_FOTO CATEGORIA_URL " +
                " FROM " + TABLE + " P " +
                " INNER JOIN CATEGORIAS CAT ON CAT._id =  P.CATEGORIA_ID" +
                where +
                " ORDER BY CAT.NOME, P.NOME " +
                "LIMIT 30";

        List<Produto> produtos = new ArrayList<Produto>();

        try {
            //Cursor
            Cursor c = getDb().rawQuery(sql, null);
            produtos = toList(c);
            c.close();
            return produtos;
        } finally {
            getDb().close();
        }

    }

    public void altera(Produto produto) {
        ContentValues dados = pegaDadosDoProduto(produto);
        String[] params = {produto.getId().toString()};
        getDb().update(TABLE, dados, ID + " = ?", params);
    }

    public Produto getProduto(long id) {

        Produto produto = new Produto();
        String where = ID + " = ?";
        String args[] = new String[]{Long.toString(id)};

        try {
            Cursor c = getDb().query(TABLE, null, where, args, null, null, null);
            if (c.moveToNext()) {
                produto.setId(c.getLong(c.getColumnIndex(ID)));
                produto.setNome(c.getString(c.getColumnIndex(NOME)));
                produto.setPreco(c.getDouble(c.getColumnIndex(PRECO)));
                produto.setDescricao(c.getString(c.getColumnIndex(DESCRICAO)));
                produto.setTipoUnidade(c.getString(c.getColumnIndex(TP_UNIDADE)));
                produto.setUrlFoto(c.getString(c.getColumnIndex(URL_FOTO)));
            }
            c.close();
            return produto;
        } finally {
            getDb().close();
        }
    }

    public List<Produto> buscarAll(String query, CategoriaProduto categoria) {

        String where = "";
        if (categoria.getId() != 0){
            where =  " AND PRODUTOS.CATEGORIA_ID = " + categoria.getId();
        }

        sql = " SELECT PRODUTOS.*, CATEGORIAS.NOME CATEGORIA_NOME\n" +
                " FROM PRODUTOS\n" +
                " INNER JOIN CATEGORIAS ON CATEGORIAS._id = PRODUTOS.CATEGORIA_ID\n" +
                " WHERE (PRODUTOS.NOME LIKE ? or PRODUTOS._id like ? )\n" +
                 where +
                " ORDER BY PRODUTOS.NOME " +
                "LIMIT 30";
        query = "%" + query + "%";
        List<Produto> produtos = new ArrayList<>();
        String args[] = new String[]{query,query};

        try {
            Cursor c = getDb().rawQuery(sql, args);
            produtos = toList(c);
            c.close();
            return produtos;
        } finally {
            getDb().close();
        }

    }

    public List<Produto> toList(Cursor c) {

        List<Produto> produtos = new ArrayList<>();

        if (c.moveToNext()) {
            do {
                Produto produto = new Produto();
                produto.setId(c.getLong(c.getColumnIndex(ID)));
                produto.setNome(c.getString(c.getColumnIndex(NOME)));
                produto.setPreco(c.getDouble(c.getColumnIndex(PRECO)));
                produto.setDescricao(c.getString(c.getColumnIndex(DESCRICAO)));
                produto.setTipoUnidade(c.getString(c.getColumnIndex(TP_UNIDADE)));
                produto.setUrlFoto(c.getString(c.getColumnIndex(URL_FOTO)));
                produto.setCategoria(new CategoriaProduto(c.getLong(c.getColumnIndex("CATEGORIA_ID")), c.getString(c.getColumnIndex("CATEGORIA_NOME")), null));
                produtos.add(produto);
            } while (c.moveToNext());
        }
        return produtos;
    }

    public void deleteAll() {
        sql = " DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }

    public int isProduto(CategoriaProduto categoria) {

        if (categoria.getId() == 0) {
            return 1;
        }

        sql = "SELECT NOME FROM " + TABLE + " WHERE CATEGORIA_ID = ? LIMIT 1";
        String args[] = new String[]{categoria.getId().toString()};
        Cursor c = getDb().rawQuery(sql, args);
        return c.getCount();
    }

}