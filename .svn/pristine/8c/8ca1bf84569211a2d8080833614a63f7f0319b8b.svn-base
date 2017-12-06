package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;

import com.najasoftware.fdv.model.CategoriaProduto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 12/04/2016.
 * lemoel@gmail.com
 */
public class CategoriaDAO extends BancoDAO {

    //Colunas
    private final String TABLE = "CATEGORIAS";
    private final String ID = "_id";
    private final String NOME = "NOME";
    private final String URL_FOTO = "URL_FOTO";
    protected SQLiteDatabase db;
    private Context context;
    private String sql;

    public CategoriaDAO(Context context) {
        super(context);
        this.context = context;
    }

    public List<CategoriaProduto> getCategorias() throws SQLiteException {
        List<CategoriaProduto> categorias = new ArrayList<CategoriaProduto>();
        try {
            //Cursor SELECT * FROM CATEGORIA;
            Cursor c = getDb().query(TABLE, null, null, null, null, null, null);
            categorias = this.toList(c);
            c.close();
            return categorias;
        } finally {
            getDb().close();
        }

    }

    public List<CategoriaProduto> getCategorias(String query) {

        sql = "SELECT * \n" +
                " FROM CATEGORIAS\n" +
                " WHERE (" + NOME + " LIKE ? or " + ID + " like ? )" +
                " ORDER BY " + NOME;

        query = "%" + query + "%";
        List<CategoriaProduto> categorias = new ArrayList<>();
        String args[] = new String[]{query, query};

        try {
            Cursor c = getDb().rawQuery(sql, args);
            categorias = this.toList(c);
            c.close();
            return categorias;
        } finally {
            getDb().close();
        }

    }

    private List<CategoriaProduto> toList(Cursor c) {

        List<CategoriaProduto> categorias = new ArrayList<>();

        if (c.moveToNext()) {
            do {
                CategoriaProduto categoria = new CategoriaProduto();
                categoria.setId(c.getLong(c.getColumnIndex(ID)));
                categoria.setNome(c.getString(c.getColumnIndex(NOME)));
                categoria.setUrlFoto(c.getString(c.getColumnIndex(URL_FOTO)));
                categorias.add(categoria);
            } while (c.moveToNext());
        }

        return categorias;
    }

    public void deleteAll() {
        sql = " DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }

    @NonNull
    private ContentValues pegaDadosDoCategoria(CategoriaProduto categoriaProduto) {
        ContentValues dados = new ContentValues();
        dados.put(ID, categoriaProduto.getId());
        dados.put(NOME, categoriaProduto.getNome());
        dados.put(URL_FOTO, categoriaProduto.getUrlFoto());
        return dados;
    }

    public void insere(CategoriaProduto ct) {
        ContentValues dados = pegaDadosDoCategoria(ct);
        getDb().insert(TABLE, null, dados);
    }
}