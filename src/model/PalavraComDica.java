package br.edu.unoesc.jogodaforca.model;

public class PalavraComDica {
    private String palavra;
    private String dica;

    public PalavraComDica(String palavra, String dica) {
        this.palavra = palavra;
        this.dica = dica;
    }

    public String getPalavra() {
        return palavra;
    }

    public String getDica() {
        return dica;
    }
}