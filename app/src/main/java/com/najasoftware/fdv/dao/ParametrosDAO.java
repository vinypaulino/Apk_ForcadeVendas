package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.najasoftware.fdv.model.CategoriaProduto;
import com.najasoftware.fdv.model.Parametro;
import com.najasoftware.fdv.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/03/2016.
 * lemoel@gmail.com
 */
public class ParametrosDAO extends BancoDAO {

    private static final String TABLE = "PARAMETROS";
    private static String VER_TODOS_CLIENTE = "VER_TODOS_CLIENTE";

    private final Context context;
    private String sql;

    public ParametrosDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void insere(Parametro parametro) {
        ContentValues dados = pegaDadosDoParametro(parametro);
        getDb().insert(TABLE, null, dados);
    }

    private ContentValues pegaDadosDoParametro(Parametro parametro) {
        ContentValues dados = new ContentValues();
        dados.put(VER_TODOS_CLIENTE, parametro.isVerTodosClientes());
        return dados;
    }

    public Parametro getParametro() {

        Parametro parametro = new Parametro();

        try {
            Cursor c = getDb().query(TABLE, null,null,null, null, null, null);
            if (c.moveToNext()) {
                String x = c.getString(c.getColumnIndex("VER_TODOS_CLIENTE"));
                boolean verTodosClientes = Boolean.parseBoolean((x.equals("1")) ? "true" : "false");
                parametro.setVerTodosClientes(verTodosClientes);
            }
            c.close();
            return parametro;
        } finally {
            getDb().close();
        }
    }

    public void deleteAll() {
        sql = " DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }

}