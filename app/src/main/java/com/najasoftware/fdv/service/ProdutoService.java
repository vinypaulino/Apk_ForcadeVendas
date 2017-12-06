package com.najasoftware.fdv.service;

import android.content.Context;
import android.util.Log;

import com.najasoftware.fdv.model.CategoriaProduto;
import com.najasoftware.fdv.model.Produto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.FileUtils;

/**
 * Created by Lemoel Marques - NajaSoftware on 02/05/2016.
 * lemoel@gmail.com
 */
public class ProdutoService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "ProdutoService";

    public static List<Produto> getProdutos(Context context) throws IOException {
        try {
            String fileName = String.format("produtos_fdv.json");
            Log.d(TAG,"Abrindo arquivo: " + fileName);

            //Lê o arquivo da memoria interna
            String json = FileUtils.readFile(context,fileName,"UTF-8");
            if(json == null) {
                Log.d(TAG,"Arquivo " + fileName + " Não encontrado");
                return null;
            }
            List<Produto> produtos = parserJson(context,json);
            return produtos;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao ler os produtos: " + e.getMessage(),e);
            return null;
        }
    }

    private static List<Produto> parserJson(Context context, String json) throws IOException {

        List<Produto> produtos = new ArrayList<Produto>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("produtos");
            JSONArray jsonProdutos = obj.getJSONArray("produto");

            for(int i = 0; i< jsonProdutos.length(); i++) {
                JSONObject jsonProduto = jsonProdutos.getJSONObject(i);
                Produto p = new Produto();

                //Lê informações de cada produto
                p.setId(jsonProduto.optLong("pro_id"));
                p.setNome(jsonProduto.optString("pro_descricao"));
                p.setPreco(jsonProduto.optDouble("pro_valor"));
                p.setTipoUnidade(jsonProduto.optString("tp_unidade"));
                p.setCategoria(new CategoriaProduto(jsonProduto.getLong("gru_id"),null,null));
                p.setUrlFoto(jsonProduto.getString("url_foto"));
                p.setDescricao(jsonProduto.getString("descricao"));

                if (LOG_ON) {
                    Log.d(TAG, "Produto " + p.getNome());
                }

                produtos.add(p);
            }

            if (LOG_ON) {
                Log.d(TAG,produtos.size() + " encontrados. ");
            }

        } catch (JSONException e) {
            Log.d(TAG,e.getMessage() + " Lemoel ");
            throw  new IOException(e.getMessage(),e);
        }
        return produtos;
    }

}
