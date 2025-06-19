import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner criado uma única vez
        String nome = "";

        System.out.println("🌟 Bem-vindo ao jogo da Forca: uma jornada de superação, valentia e aprendizado! 🌟");

        System.out.print("Digite seu nome: ");
        nome = scanner.nextLine();

        boolean jogarNovamente = true;

        while (jogarNovamente) {
            try {
                ArquivoDePalavras arquivo = new ArquivoDePalavras("palavras.txt");
                PalavraComDica palavraComDicaEscolhida = arquivo.getPalavraComDicaAleatoria(); // Obtém PalavraComDica

                if (palavraComDicaEscolhida == null) {
                    System.out.println("Não foi possível carregar uma palavra para o jogo. O arquivo palavras.txt pode estar vazio ou com problemas.");
                    jogarNovamente = false;
                    continue;
                }

                System.out.println("\nComeçando um novo jogo!");

                // Passa o objeto PalavraComDica e o scanner para o Jogo
                Jogo jogo = new Jogo(nome, palavraComDicaEscolhida, scanner);
                jogo.jogar();

                System.out.println("\nDeseja jogar novamente? (s/n): ");
                String resposta = scanner.nextLine();
                if (!resposta.equalsIgnoreCase("s")) {
                    jogarNovamente = false;
                }

            } catch (IOException e) {
                System.out.println("Erro ao carregar as palavras. Verifique o arquivo palavras.txt e certifique-se de que ele contém apenas uma palavra por linha no formato PALAVRA;DICA.");
                System.out.println("Detalhes do erro: " + e.getMessage());
                jogarNovamente = false;
            }
        }

        System.out.println("Obrigado por jogar, " + nome + "! Até a próxima! 👋");
        scanner.close(); // Fecha o scanner ao final do programa
    }
}