package com.najasoftware.fdv.model;

import java.io.Serializable;

/**
 * Created by Lemoel Marques - NajaSoftware on 23/05/2016.
 * lemoel@gmail.com
 */
public class Uf implements Serializable {

    private Long id;
    private String nome;
    private String sigla;

    public Uf(Long id) {
        this.id = id;
    }

    public Uf(){}

    public Uf(Long id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return getSigla();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Uf uf = (Uf) o;

        if (!id.equals(uf.id)) return false;
        return sigla.equals(uf.sigla);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sigla.hashCode();
        return result;
    }
}
