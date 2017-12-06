package com.najasoftware.fdv;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.najasoftware.fdv.dao.UfDAO;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.Pedido;
import com.najasoftware.fdv.model.Uf;
import com.najasoftware.fdv.model.Vendedor;
import com.najasoftware.fdv.service.UfsService;

import java.io.IOException;
import java.util.List;
/**
 * Created by Lemoel Marques - NajaSoftware on 06/04/2016.
 * lemoel@gmail.com
 */
public class FdvApplication extends MultiDexApplication {
    private static final String TAG = "Fdv";
    private static FdvApplication instance = null;
    private Vendedor vendedor;
    private Cliente cliente;
    private Pedido pedido;

    public static FdvApplication getInstance() {
            return instance;//Singleton
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "FdvApplication.onCreate()");
        instance = this;

        UfDAO ufDAO = new UfDAO(this);
        Uf uf = ufDAO.getUf(Long.parseLong("11"));

        if ( uf.getId() == null ){
            List<Uf> ufs = null;
            try {
                ufs = UfsService.getUfs(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ufDAO.deleteAll();
            ufDAO.insere(ufs);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "FdvApplication.onTerminate()");
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}