package com.najasoftware.fdv.model;

import java.io.Serializable;

/**
 * Created by Lemoel Marques - NajaSoftware on 03/05/2016.
 * lemoel@gmail.com
 */
public class Cidade implements Serializable {

    private Long id;
    private String nome;
    private Uf uf;

    public Cidade(Long id, String nome,Uf uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;

    }

    public Cidade() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cidade cidade = (Cidade) o;

        if (!id.equals(cidade.id)) return false;
        return nome.equals(cidade.nome);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }
}
