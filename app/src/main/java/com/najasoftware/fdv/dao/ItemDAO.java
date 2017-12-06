package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;

import com.najasoftware.fdv.model.Item;
import com.najasoftware.fdv.model.Pedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel on 09/03/2016.
 */
public class ItemDAO extends BancoDAO {

    private final String TABLE = "ITENS";
    private final String ID = "_id";
    private final String NOME = "NOME";
    private final String PEDIDO_ID = "PEDIDO_ID";
    private final String PRODUTO_ID = "PRODUTO_ID";
    private final String PRECO_SUGERIDO = "PRECO_SUGERIDO";
    private final String DESCONTO = "DESCONTO";
    private final String QTDE = "QTDE";
    private final String TOTAL_SEM_DESCONTO = "TOTAL_SEM_DESCONTO";
    private final String TOTAL_COM_DESCONTO = "TOTAL_COM_DESCONTO";
    private Context context;

    public ItemDAO(Context context) {
        super(context);
        this.context = context;
    }

    /*
      Busca todos os itens de um determinado pedido
      @param Pedido pedido que queremos os itens
      @author Lemoel Marques Vieira lemoel@gmail.com
     */
    public List<Item> getItens(Pedido pedido) {

        String sql = "SELECT * FROM " + TABLE + " WHERE " + PEDIDO_ID + " = ?;";
        String args[] = new String[]{pedido.getId().toString()};
        try {
            Cursor c = getDb().rawQuery(sql, args);
            List<Item> itens = toList(c);
            c.close();
            return itens;
        } finally {
            getDb().close();
        }

    }

    public List<Item> getItens(Long pedidoId) throws SQLiteException {
        String where = " PEDIDO_ID = ?";
        String args[] = new String[]{pedidoId.toString()};
        List<Item> itens = new ArrayList<>();

        try {
            Cursor c = getDb().query("ITENS", null, where, args, null, null, null);
            itens = toList(c);
            c.close();
            return itens;
        } finally {
            getDb().close();
        }
    }

    public void excluir(Item itemExcluir) {
        String[] params = {itemExcluir.getId().toString()};
        try {
            getDb().delete(TABLE, ID + " = ?", params);
        } finally {
            getDb().close();
        }
    }

    /*
    * Busca em toda a tabela de item por uma string qualquer de um pedido
    * @param String a string que será buscada nos itens do pedido
    * @param Pedido o pedido em que será feira a busca dos itens
    * @author Lemoel Marques Vieira lemoel@gmail.com
     */
    public List<Item> buscalAll(String query, Pedido pedido) {

        query = "%" + query + "%";

        String where = " pedido_id = " + pedido.getId().toString() + " " +
                " AND (nome LIKE ? " +
                "or produto_id LIKE ? )";
        String args[] = new String[]{query,query};

        try {
            Cursor c = getDb().query(TABLE, null, where, args, null, null, null);
            List<Item> itens = toList(c);
            c.close();
            return itens;
        } finally {
            getDb().close();
        }

    }

    /*
    * Percorre o cursor e grava os dados dentro de uma lista de itens
    * @param Cursor
    * @author Lemoel Marques Vieira lemoel@gmail.com
     */
    private List<Item> toList(Cursor c) {
        List<Item> itens = new ArrayList<>();
        if (c.moveToNext()) {
            do {
                Item item = new Item();
                item.setId(c.getLong(c.getColumnIndex(ID)));
                //item.setPedido(new PedidoDAO(context).getPedido(c.getLong(c.getColumnIndex(PEDIDO_ID))));
                item.setPedido(new Pedido(c.getLong(c.getColumnIndex(PEDIDO_ID))));
                item.setNome(c.getString(c.getColumnIndex("NOME")));
                item.setProduto(new ProdutoDAO(context).getProduto(c.getLong(c.getColumnIndex("PRODUTO_ID"))));
                item.setPrecoSugerido(c.getDouble(c.getColumnIndex("PRECO_SUGERIDO")));
                item.setQtde(c.getDouble(c.getColumnIndex("QTDE")));
                item.setTotalComDesconto(c.getDouble(c.getColumnIndex("TOTAL_COM_DESCONTO")));
                item.setTotalSemDesconto(c.getDouble(c.getColumnIndex("TOTAL_SEM_DESCONTO")));
                item.setDesconto(c.getDouble(c.getColumnIndex("DESCONTO")));
                itens.add(item);
            } while (c.moveToNext());
        }
        return itens;
    }

    @NonNull
    private ContentValues pegaDadosItem(Item item) {
        ContentValues dados = new ContentValues();

        if (item.getId() != null) dados.put(ID, item.getId());
        dados.put(NOME, item.getNome().toString().trim());
        dados.put(PEDIDO_ID, item.getPedido().getId());
        dados.put(PRODUTO_ID, item.getProduto().getId().toString().trim());
        dados.put(PRECO_SUGERIDO, item.getPrecoSugerido().toString().trim());
        dados.put(QTDE, item.getQtde().toString().trim());
        dados.put(TOTAL_SEM_DESCONTO, item.getTotalSemDesconto().toString().trim());
        dados.put(TOTAL_COM_DESCONTO, item.getTotalComDesconto().toString().trim());
        dados.put(DESCONTO, item.getDesconto().toString().trim());
        return dados;
    }

    public Long gravar(Item item) {
        ContentValues dados = new ContentValues();
        dados = pegaDadosItem(item);
        Long id = getDb().insert(TABLE, null, dados);
        return id;
    }

    public void update(Item item) {
        ContentValues dadosItem = new ContentValues();
        dadosItem = pegaDadosItem(item);
        getDb().update(TABLE,dadosItem, ID + " = ? ", new String[]{String.valueOf(item.getId())});
    }
}
