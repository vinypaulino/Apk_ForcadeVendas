package com.najasoftware.fdv.service;

import android.content.Context;
import android.util.Log;
import com.najasoftware.fdv.model.Parametro;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import livroandroid.lib.utils.FileUtils;

/**
 * Created by Lemoel Marques - NajaSoftware on 02/05/2016.
 * lemoel@gmail.com
 */
public class ParametroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "ProdutoService";

    public static Parametro getParametros(Context context) throws IOException {

        try {

            String fileName = String.format("parametros_fdv.json");
            Log.d(TAG,"Abrindo arquivo: " + fileName);

            //Lê o arquivo da memoria interna
            String json = FileUtils.readFile(context,fileName,"UTF-8");

            if(json == null) {
                Log.d(TAG,"Arquivo " + fileName + " Não encontrado");
                return null;
            }

            Parametro parametro = parserJson(context,json);
            return parametro;

        } catch (Exception e) {
            Log.e(TAG, "Erro ao ler os produtos: " + e.getMessage(),e);
            return null;
        }
    }

    public static Parametro parserJson(Context context,String json) throws  IOException {

        Parametro parametro = new Parametro();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("configuracoes");

            //Lê informações de cada produto
            parametro.setVerTodosClientes(obj.optBoolean("ContrVend"));

            if (LOG_ON) {
                Log.d(TAG, "Parametros " + parametro.isVerTodosClientes());
            }

        } catch (JSONException e) {
            Log.d(TAG,e.getMessage() + " Parametros ");
            throw  new IOException(e.getMessage(),e);
        }

        return parametro;

    }
}
