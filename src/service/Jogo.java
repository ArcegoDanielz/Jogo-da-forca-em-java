package br.edu.unoesc.jogodaforca.service;
import br.edu.unoesc.jogodaforca.model.Jogador;    // <-- CORREÇÃO
import br.edu.unoesc.jogodaforca.model.Palavra;    // <-- CORREÇÃO
import br.edu.unoesc.jogodaforca.model.PalavraComDica; // <-- CORREÇÃO

import java.util.Scanner;

public class Jogo {
    private Jogador jogador;
    private Palavra palavra;
    private String dicaDaPalavra;
    private int erros = 0;
    private Scanner scanner;
    private int tentativasDeChuteDePalavra = 2;

    public Jogo(String nome, PalavraComDica palavraComDica, Scanner scanner) {
        int comprimentoPalavra = palavraComDica.getPalavra().length();
        int numTentativasIniciais = determinarTentativasPorComprimento(comprimentoPalavra);

        this.jogador = new Jogador(nome, numTentativasIniciais);
        this.palavra = new Palavra(palavraComDica.getPalavra());
        this.dicaDaPalavra = palavraComDica.getDica();
        this.scanner = scanner;

        System.out.println("Palavra tem " + comprimentoPalavra + " letras. Você terá " + numTentativasIniciais + " tentativas.");
    }

    private int determinarTentativasPorComprimento(int comprimentoPalavra) {
        if (comprimentoPalavra <= 5) {
            return 5;
        } else if (comprimentoPalavra <= 10) {
            return 6;
        } else {
            return 7;
        }
    }

    public void jogar() {
        System.out.println("Dica: " + dicaDaPalavra);

        while (!palavra.palavraCompleta() && jogador.aindaTemTentativas()) {
            System.out.println("\nPalavra: " + palavra.mostrarPalavraParcial());
            System.out.println("Tentativas restantes: " + jogador.getTentativasRestantes());
            System.out.println("Letras erradas: " + palavra.getLetrasErradas());
            System.out.println("Tentativas de chute da palavra completa restantes: " + tentativasDeChuteDePalavra);
            mostrarForca(this.erros); // Mostra a forca no início de cada rodada

            System.out.print("Digite uma letra (ou 'CHUTAR' para tentar a palavra completa): ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.equals("CHUTAR")) {
                if (tentativasDeChuteDePalavra > 0) {
                    tentativasDeChuteDePalavra--;
                    System.out.print("Qual é a palavra completa? ");
                    String chutePalavra = scanner.nextLine().trim().toUpperCase();

                    if (chutePalavra.equals(palavra.getPalavraSecreta())) {
                        palavra.adivinharTodasAsLetras();
                    } else {
                        System.out.println("Palavra incorreta! Você perdeu uma tentativa de chute.");
                        if (tentativasDeChuteDePalavra == 0) {
                            System.out.println("Você esgotou suas tentativas de chute da palavra completa!");
                        }
                    }
                } else {
                    System.out.println("Você não tem mais tentativas para chutar a palavra completa.");
                }
                continue; // Volta para o início do loop
            }

            if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Entrada inválida. Por favor, digite apenas uma letra ou 'CHUTAR'.");
                continue;
            }

            char letra = entrada.charAt(0);

            if (palavra.letraJaTentada(letra)) {
                System.out.println("Letra '" + Character.toUpperCase(letra) + "' já tentada. Tente outra.");
                continue;
            }

            if (palavra.adivinharLetra(letra)) {
                System.out.println("Boa! Letra correta!");
            } else {
                jogador.perderTentativa();
                this.erros++;
                System.out.println("Letra incorreta. Tente novamente.");
            }
        }

        // --- Lógica de Fim de Jogo ---
        System.out.println("\n--- FIM DE JOGO ---");
        if (palavra.palavraCompleta()) {
            System.out.println("Parabéns, " + jogador.getNome() + "! Você venceu! A palavra era: " + palavra.getPalavraSecreta());
        } else {
            System.out.println("Você perdeu! A palavra era: " + palavra.getPalavraSecreta());
            mostrarForca(this.erros); // Mostra a forca final
        }
    }

    private static final String[] FORCA_VISUAL = {
            "  +---+\n      |\n      |\n      |\n     ===", // 0 erros
            "  +---+\n  O   |\n      |\n      |\n     ===", // 1 erro
            "  +---+\n  O   |\n  |   |\n      |\n     ===", // 2 erros
            "  +---+\n  O   |\n /|   |\n      |\n     ===", // 3 erros
            "  +---+\n  O   |\n /|\\  |\n      |\n     ===", // 4 erros
            "  +---+\n  O   |\n /|\\  |\n /    |\n     ===", // 5 erros
            "  +---+\n  O   |\n /|\\  |\n / \\  |\n     ==="  // 6 erros (máximo)
    };

    private static void mostrarForca(int erros) {
        if (erros < FORCA_VISUAL.length) {
            System.out.println(FORCA_VISUAL[erros]);
        }
    }
}