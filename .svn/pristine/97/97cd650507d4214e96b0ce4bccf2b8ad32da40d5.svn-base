package com.najasoftware.fdv.service;

import android.content.Context;

import com.najasoftware.fdv.model.Item;
import com.najasoftware.fdv.model.Pedido;
import com.najasoftware.fdv.util.GravarArquivo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lemoel Marques - NajaSoftware on 27/05/2016.
 * lemoel@gmail.com
 */
public class PedidoService {

    private Context context;

    public PedidoService(Context context) {
        this.context = context;
    }

    public Set<String> toJson(String nomeArquivo, List<Pedido> pedidos) {

        Set<String> nomesDosArquivos = new HashSet<>();

        try {

            for (Pedido p : pedidos) {

                JSONObject jsonPedido = new JSONObject();
                jsonPedido.put("id", p.getId());
                jsonPedido.put("cliente_cnpj", p.getCliente().getCnpj());
                jsonPedido.put("vendedor_id", p.getVendedor().getId());

                if (p.getObs() != null) {
                    jsonPedido.put("obs", p.getObs());
                }

                jsonPedido.put("dt_venda", p.getDataVenda().toString());
                jsonPedido.put("total_sem_desconto", p.getTotalSemDesconto());
                jsonPedido.put("total_com_desconto", p.getTotalComDesconto());
                jsonPedido.put("desconto", p.getDesconto());
                jsonPedido.put("forma_pgto_id", p.getFormaPgto().getId());

                //Array contendo os objetos itens do pedido
                JSONArray jsonArrayItensDoPedido = new JSONArray();

                for (Item i : p.getItens()) {
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put("id", i.getProduto().getId());
                    jsonItem.put("nome", i.getNome());
                    jsonItem.put("pedido_id", p.getId());
                    jsonItem.put("preco_sugerido", i.getPrecoSugerido());
                    jsonItem.put("qtde", i.getQtde());
                    jsonItem.put("total_sem_desconto", i.getTotalSemDesconto());
                    jsonItem.put("total_com_desconto", i.getTotalComDesconto());
                    jsonItem.put("desconto", i.getDesconto());
                    jsonArrayItensDoPedido.put(jsonItem);
                }

                jsonPedido.put("itens", jsonArrayItensDoPedido);

                JSONObject pedido = new JSONObject();
                pedido.put("pedido",jsonPedido);

                String stringGravacao = pedido.toString();

                //Grava a string enviada no arquivo dentro do dispositivo
                GravarArquivo gravarArquivo = new GravarArquivo(context.getFilesDir());
                String nomeFinalArquivo = nomeArquivo + "_" + p.getId() + ".json";
                boolean gravadoSucesso = gravarArquivo.gravar(stringGravacao,nomeFinalArquivo);

                if (gravadoSucesso == true) {
                    nomesDosArquivos.add(nomeFinalArquivo);
                }

            }

        } catch (Exception e) {

        }

        return nomesDosArquivos;

    }

    public boolean delete(String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file.delete();
    }

}