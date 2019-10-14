package com.example.games.mineracao;

public class Noticia {
    private String linkimg;
    private String titulo;
    private String conteudo;
    private String link;

    public Noticia(){}

    public Noticia(String linkimg, String titulo, String conteudo, String link){
        this.linkimg = linkimg;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.link = link;
    }

    public String getLinkimg() {
        return linkimg;
    }

    public void setLinkimg(String linkimg) {
        this.linkimg = linkimg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
