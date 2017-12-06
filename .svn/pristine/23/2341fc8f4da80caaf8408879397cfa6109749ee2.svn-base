package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.najasoftware.fdv.model.Vendedor;

/**
 * Created by Lemoel Marques - NajaSoftware on 24/03/2016.
 * lemoel@gmail.com
 */
public class VendedorDAO extends BancoDAO {

    private Context context;
    private String sql;
    private final String ID = "_id";
    private final String LOGIN = "LOGIN";
    private final String SENHA = "SENHA";
    private final String NOME = "NOME";
    private final String TABLE = "VENDEDORES";

    public VendedorDAO(Context context) {
        super(context);
        this.context = context;
    }

    public Vendedor getVendedor(Long vendedorId) {

        Vendedor vendedor = new Vendedor();
        sql = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?;";
        String args[] = new String[]{vendedorId.toString()};

        try {
            Cursor c = getDb().rawQuery(sql, args);

            if (c.moveToNext()) {
                vendedor.setId(c.getLong(c.getColumnIndex(ID)));
                vendedor.setLogin(c.getString(c.getColumnIndex(LOGIN)));
                vendedor.setSenha(c.getString(c.getColumnIndex(SENHA)));
                vendedor.setNome(c.getString(c.getColumnIndex(NOME)));
            }
            c.close();
            return vendedor;
        } finally {
            getDb().close();
        }

    }

    public Vendedor getVendedor(String login) {

        Vendedor vendedor = new Vendedor();

        sql = "SELECT * FROM " + TABLE + " WHERE " + LOGIN  + " = ?;";
        String args[] = new String[]{login};

        try {
            Cursor c = getDb().rawQuery(sql, args);

            if (c.moveToNext()) {
                vendedor.setId(c.getLong(c.getColumnIndex(ID)));
                vendedor.setLogin(c.getString(c.getColumnIndex(LOGIN)));
                vendedor.setSenha(c.getString(c.getColumnIndex(SENHA)));
                vendedor.setNome(c.getString(c.getColumnIndex(NOME)));
            }
            c.close();
            return vendedor;
        } finally {
            getDb().close();
        }

    }

    @NonNull
    private ContentValues pegaDadosDoVendedor(Vendedor vendedor) {
        ContentValues dados = new ContentValues();
        dados.put(ID,vendedor.getId());
        dados.put(LOGIN,vendedor.getLogin());
        dados.put(SENHA, vendedor.getSenha());
        dados.put(NOME, vendedor.getNome());
        return dados;
    }

    public void insere(Vendedor vendedor) {
        ContentValues dados = pegaDadosDoVendedor(vendedor);
        getDb().insert(TABLE, null, dados);
    }

    public void deleteAll (){
        sql = "DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }

}