package br.edu.unoesc.jogodaforca;
import br.edu.unoesc.jogodaforca.model.PalavraComDica;
import br.edu.unoesc.jogodaforca.service.Jogo;
import br.edu.unoesc.jogodaforca.util.ArquivoDePalavras;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nome = "";

        System.out.println("🌟 Bem-vindo ao jogo da Forca: uma jornada de superação, valentia e aprendizado! 🌟");
        System.out.print("Digite seu nome: ");
        nome = scanner.nextLine();

        boolean jogarNovamente = true;

        while (jogarNovamente) {
            try {
                // Instancia o leitor de arquivos
                ArquivoDePalavras arquivo = new ArquivoDePalavras("palavras.txt");
                PalavraComDica palavraComDicaEscolhida = arquivo.getPalavraComDicaAleatoria();

                if (palavraComDicaEscolhida == null) {
                    System.out.println("Não foi possível carregar uma palavra para o jogo. O arquivo palavras.txt pode estar vazio ou com problemas.");
                    break; // Sai do loop se não houver palavras
                }

                System.out.println("\nComeçando um novo jogo para " + nome + "!");

                // Cria uma nova instância do jogo
                Jogo jogo = new Jogo(nome, palavraComDicaEscolhida, scanner);
                jogo.jogar();

                System.out.print("\nDeseja jogar novamente? (s/n): ");
                String resposta = scanner.nextLine();
                if (!resposta.equalsIgnoreCase("s")) {
                    jogarNovamente = false;
                }

            } catch (IOException e) {
                System.out.println("Erro crítico ao carregar as palavras.");
                System.out.println("Verifique se a pasta 'data' existe na raiz do projeto e se o arquivo 'palavras.txt' está dentro dela.");
                System.out.println("O formato deve ser: PALAVRA;DICA");
                // System.out.println("Detalhes do erro: " + e.getMessage()); // Descomente para depuração
                jogarNovamente = false;
            }
        }

        System.out.println("Obrigado por jogar, " + nome + "! Até a próxima! 👋");
        scanner.close();
    }
}