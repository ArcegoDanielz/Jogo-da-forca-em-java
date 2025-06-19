package br.edu.unoesc.jogodaforca.model;

import java.util.HashSet;
import java.util.Set;

public class Palavra {
    private String palavraSecreta;
    private Set<Character> letrasCorretas = new HashSet<>();
    private Set<Character> letrasErradas = new HashSet<>();

    public Palavra(String palavra) {
        this.palavraSecreta = palavra.toUpperCase();
    }

    public boolean adivinharLetra(char letra) {
        letra = Character.toUpperCase(letra);
        if (palavraSecreta.indexOf(letra) >= 0) {
            letrasCorretas.add(letra);
            return true;
        } else {
            letrasErradas.add(letra);
            return false;
        }
    }

    public boolean letraJaTentada(char letra) {
        letra = Character.toUpperCase(letra);
        return letrasCorretas.contains(letra) || letrasErradas.contains(letra);
    }

    public boolean palavraCompleta() {
        for (char c : palavraSecreta.toCharArray()) {
            if (!letrasCorretas.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public String mostrarPalavraParcial() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavraSecreta.toCharArray()) {
            sb.append(letrasCorretas.contains(c) ? c + " " : "_ ");
        }
        return sb.toString();
    }

    public Set<Character> getLetrasErradas() {
        return letrasErradas;
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public void adivinharTodasAsLetras() {
        for (char c : palavraSecreta.toCharArray()) {
            letrasCorretas.add(c);
        }
    }
}