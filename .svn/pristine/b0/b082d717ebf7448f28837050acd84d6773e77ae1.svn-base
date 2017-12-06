package com.najasoftware.fdv.dao;

import android.content.Context;
import android.database.Cursor;

import com.najasoftware.fdv.model.Cidade;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.Endereco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class EnderecoDAO extends BancoDAO {

    private final String TABLE = "ENDERECOS";
    private final String ID = "_id";
    private final String CLIENTE_CNPJ = "CLIENTE_CNPJ";
    private final String CIDADE_ID = "CIDADE_ID";
    private final String NOME_RUA = "NOME_RUA";
    private final String NUMERO = "NUMERO";
    private final String BAIRRO = "BAIRRO";
    private final String CEP = "CEP";
    private final String COMPLEMENTO = "COMPLEMENTO";
    private Context context;
    private String sql;

    public EnderecoDAO(Context context) {
        super(context);
        this.context = context;
    }

    public List<Endereco> getEnderecos(Cliente cliente) {

        List<Endereco> enderecos = new ArrayList<>();
        CidadeDAO cidadeDAO = new CidadeDAO(context);

        sql = "SELECT * FROM " + TABLE + " WHERE " + CLIENTE_CNPJ + " = ?;";
        String args[] = new String[]{cliente.getCnpj().toString()};

        try {
            Cursor c = getDb().rawQuery(sql, args);
            if (c.moveToNext()) {
                do {
                    Endereco end = new Endereco();
                    end.setRua(c.getString(c.getColumnIndex(NOME_RUA)));
                    end.setNumero(c.getString(c.getColumnIndex(NUMERO)));
                    end.setComplemento(c.getString(c.getColumnIndex(COMPLEMENTO)));
                    end.setBairro(c.getString(c.getColumnIndex(BAIRRO)));
                    end.setCep(c.getString(c.getColumnIndex(CEP)));

                    Cidade cidade = new Cidade();
                    cidade = cidadeDAO.getCidade(c.getLong(c.getColumnIndex(CIDADE_ID)));
                    end.setCidade(cidade);

                    enderecos.add(end);
                } while (c.moveToNext());
            }
        } finally {
            getDb().close();
        }
        return enderecos;
    }

    public void deleteAll() {
        sql = " DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }
}