package com.najasoftware.fdv.model;

import java.io.Serializable;

/**
 * Created by Lemoel Marques - NajaSoftware on 12/04/2016.
 * lemoel@gmail.com
 */
@org.parceler.Parcel
public class CategoriaProduto implements Serializable {

    private Long id;
    private String nome;
    private String urlFoto;


    public CategoriaProduto(Long id, String nome, String urlFoto) {
        this.id = id;
        this.nome = nome;
        this.urlFoto = urlFoto;
    }

    public CategoriaProduto() {
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

        CategoriaProduto categoria = (CategoriaProduto) o;

        return id != null ? id.equals(categoria.id) : categoria.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
