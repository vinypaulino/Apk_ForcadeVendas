package com.najasoftware.fdv.model;

import java.io.Serializable;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/03/2016.
 * lemoel@gmail.com
 */
@org.parceler.Parcel
public class Produto implements Serializable {

    private Long id;
    private String nome;
    private Double preco;
    private String descricao;
    private String tipoUnidade;
    private String urlFoto;
    private CategoriaProduto categoriaProduto;

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
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

    public String toString(){
        return  getNome();
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public CategoriaProduto getCategoria() {
        return categoriaProduto;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoriaProduto = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produto produto = (Produto) o;

        return id.equals(produto.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
