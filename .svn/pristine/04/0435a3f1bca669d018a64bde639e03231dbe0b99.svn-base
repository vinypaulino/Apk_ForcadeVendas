package com.najasoftware.fdv.model;

import java.io.Serializable;

/**
 * Created by Lemoel Marques - NajaSoftware on 03/05/2016.
 * lemoel@gmail.com
 */
@org.parceler.Parcel
public class Telefone implements Serializable {

    private Long id;
    private Cliente cliente;
    private String numero;

    public Telefone(Long id, Cliente cliente, String numero) {
        this.cliente = cliente;
        this.numero = numero;
        this.id = id;
    }

    public Telefone() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Telefone telefone = (Telefone) o;

        if (!cliente.equals(telefone.cliente)) return false;
        return numero.equals(telefone.numero);

    }

    @Override
    public int hashCode() {
        int result = cliente.hashCode();
        result = 31 * result + numero.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
