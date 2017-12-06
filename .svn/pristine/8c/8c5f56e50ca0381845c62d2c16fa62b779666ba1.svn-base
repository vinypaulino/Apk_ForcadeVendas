package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.najasoftware.fdv.model.FormaPgto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 13/04/2016.
 * lemoel@gmail.com
 */
public class FormaPgtoDAO extends BancoDAO {

    private final String TABLE = "FORMA_PGTO";
    private final String ID = "_id";
    private final String NOME = "NOME";
    private String sql;
    private Context context;

    public FormaPgtoDAO(Context context) {
        super(context);
        this.context = context;
    }

    public List<FormaPgto> getFormasPgto() {

        List<FormaPgto> formaPgtos = new ArrayList<FormaPgto>();

        try {
            //Cursor SELECT * FROM FORMA PAGAMENTO;
            Cursor c = getDb().query(TABLE, null, null, null, null, null, null);
            formaPgtos = this.toList(c);
            c.close();
            return formaPgtos;
        } finally {
            getDb().close();
        }

    }

    public FormaPgto getFormasPgto(Long idFormaPgto) {

        String where = ID + " = ?";
        String args[] = new String[]{Long.toString(idFormaPgto)};
        FormaPgto formaPgto = new FormaPgto();

        try {
            //Cursor SELECT * FROM FORMA PAGAMENTO;
            Cursor c = getDb().query(TABLE, null, where, args, null, null, null);

            if (c.moveToNext()) {
                formaPgto.setId(c.getLong(c.getColumnIndex(ID)));
                formaPgto.setNome(c.getString(c.getColumnIndex(NOME)));
                c.close();
            }

            return formaPgto;
        } finally {
            getDb().close();
        }

    }


    private List<FormaPgto> toList(Cursor c) {

        List<FormaPgto> formasPgto = new ArrayList<>();

        if (c.moveToNext()) {
            do {
                FormaPgto formaPgto = new FormaPgto();
                formaPgto.setId(c.getLong(c.getColumnIndex(ID)));
                formaPgto.setNome(c.getString(c.getColumnIndex(NOME)));
                formasPgto.add(formaPgto);
            } while (c.moveToNext());
        }

        return formasPgto;
    }

    public void deleteAll() {
        sql = " DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }


    @NonNull
    private ContentValues pegaDadosDeFormaPgto(FormaPgto formaPgto) {
        ContentValues dados = new ContentValues();
        dados.put(ID, formaPgto.getId());
        dados.put(NOME, formaPgto.getNome());
        return dados;
    }

    public void insere(FormaPgto fp) {
        ContentValues dados = pegaDadosDeFormaPgto(fp);
        getDb().insert(TABLE, null, dados);
    }
}