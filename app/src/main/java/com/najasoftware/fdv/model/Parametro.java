package com.najasoftware.fdv.model;

import java.io.Serializable;

/**
 * Created by Naja on 30/08/2016.
 */
public class Parametro implements Serializable {

    private Long id;
    private boolean verTodosClientes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVerTodosClientes() {
        return verTodosClientes;
    }

    public void setVerTodosClientes(boolean verTodosClientes) {
        this.verTodosClientes = verTodosClientes;
    }

}
