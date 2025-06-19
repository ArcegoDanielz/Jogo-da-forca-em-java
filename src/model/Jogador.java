package br.edu.unoesc.jogodaforca.model;

public class Jogador {
    private String nome;
    private int tentativasRestantes;

    public Jogador(String nome, int tentativas) {
        this.nome = nome;
        this.tentativasRestantes = tentativas;
    }

    public String getNome() {
        return nome;
    }

    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    public void perderTentativa() {
        if (tentativasRestantes > 0) {
            tentativasRestantes--;
        }
    }

    public boolean aindaTemTentativas() {
        return tentativasRestantes > 0;
    }
}
