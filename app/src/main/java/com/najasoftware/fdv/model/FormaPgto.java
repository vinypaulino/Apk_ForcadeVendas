package com.najasoftware.fdv.model;

import java.io.Serializable;

/**
 * Created by Lemoel Marques - NajaSoftware on 13/04/2016.
 * lemoel@gmail.com
 */

public class FormaPgto  implements Serializable {

    private Long id_;
    private String nome;

    public FormaPgto(Long id_) {
        this.id_ = id_;
    }

    public FormaPgto(Long id_, String nome) {
        this.id_ = id_;
        this.nome = nome;
    }

    public FormaPgto(){}

    public Long getId() {
        return id_;
    }

    public void setId(Long id_) {
        this.id_ = id_;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormaPgto formaPgto = (FormaPgto) o;

        if (!id_.equals(formaPgto.id_)) return false;
        return nome.equals(formaPgto.nome);

    }

    @Override
    public int hashCode() {
        int result = id_.hashCode();
        result = 31 * result + nome.hashCode();
        return result;
    }
}