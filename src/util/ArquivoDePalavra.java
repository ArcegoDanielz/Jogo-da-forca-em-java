package br.edu.unoesc.jogodaforca.util;

import br.edu.unoesc.jogodaforca.model.PalavraComDica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArquivoDePalavras {
    private List<PalavraComDica> palavrasComDica;
    private Random random;

    public ArquivoDePalavras(String nomeArquivo) throws IOException {
        palavrasComDica = new ArrayList<>();
        random = new Random();
        carregarPalavras(nomeArquivo);
    }

    private void carregarPalavras(String nomeArquivo) throws IOException {
        // O caminho do arquivo agora deve ser relativo à raiz do projeto
        try (BufferedReader reader = new BufferedReader(new FileReader("data/" + nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String trimmedLine = linha.trim();
                if (!trimmedLine.isEmpty()) {
                    String[] partes = trimmedLine.split(";");
                    if (partes.length == 2) {
                        String palavra = partes[0].trim();
                        String dica = partes[1].trim();
                        palavrasComDica.add(new PalavraComDica(palavra, dica));
                    } else {
                        System.out.println("Atenção: Linha ignorada por formato inválido no arquivo " + nomeArquivo + ": " + linha);
                    }
                }
            }
        }
        if (palavrasComDica.isEmpty()) {
            throw new IOException("O arquivo de palavras está vazio ou não contém pares palavra;dica válidos.");
        }
    }

    public PalavraComDica getPalavraComDicaAleatoria() {
        if (palavrasComDica.isEmpty()) {
            return null;
        }
        return palavrasComDica.get(random.nextInt(palavrasComDica.size()));
    }
}