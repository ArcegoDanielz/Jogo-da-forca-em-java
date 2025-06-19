import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {
    private Jogador jogador;
    private Palavra palavra;
    private String dicaDaPalavra;
    private int erros = 0;
    private Scanner scanner;

    private int tentativasDeChuteDePalavra = 2;

    // Construtor modificado para receber PalavraComDica e o Scanner
    public Jogo(String nome, PalavraComDica palavraComDica, Scanner scanner) {
        // 1. Obter o comprimento da palavra secreta
        int comprimentoPalavra = palavraComDica.getPalavra().length();

        // 2. Determinar o numero de tentativas baseado no comprimento
        int numTentativasIniciais = determinarTentativasPorComprimento(comprimentoPalavra);

        // 3. Inicializar o jogador com o número de tentativas dinâmico
        this.jogador = new Jogador(nome, numTentativasIniciais);

        this.palavra = new Palavra(palavraComDica.getPalavra());
        this.dicaDaPalavra = palavraComDica.getDica();
        this.scanner = scanner;

        System.out.println("Palavra tem " + comprimentoPalavra + " letras. Você terá " + numTentativasIniciais + " tentativas."); // Mensagem para feedback
    }

    // --- NOVO MÉTODO: Determina o número de tentativas baseado no comprimento da palavra ---
    private int determinarTentativasPorComprimento(int comprimentoPalavra) {
        if (comprimentoPalavra <= 5) {
            return 5; // Palavras curtas (até 5 letras): 5 tentativas
        } else if (comprimentoPalavra <= 10) { // De 6 a 10 letras
            return 6; // Palavras médias (6 a 10 letras): 6 tentativas (padrão)
        } else { // Mais de 10 letras
            return 7; // Palavras longas (11+ letras): 7 tentativas
        }
    }

    public void jogar() {
        System.out.println("Dica: " + dicaDaPalavra);

        while (!palavra.palavraCompleta() && jogador.aindaTemTentativas()) {
            System.out.println("\nPalavra: " + palavra.mostrarPalavraParcial());
            System.out.println("Tentativas restantes: " + jogador.getTentativasRestantes());
            System.out.println("Letras erradas: " + palavra.getLetrasErradas());
            System.out.println("Tentativas de chute da palavra completa restantes: " + tentativasDeChuteDePalavra);

            System.out.print("Digite uma letra (ou 'CHUTAR' para tentar a palavra completa): ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            // --- LÓGICA PARA CHUTE DA PALAVRA ---
            if (entrada.equals("CHUTAR")) {
                if (tentativasDeChuteDePalavra > 0) {
                    tentativasDeChuteDePalavra--;
                    System.out.print("Qual é a palavra completa? ");
                    String chutePalavra = scanner.nextLine().trim().toUpperCase();

                    if (chutePalavra.equals(palavra.getPalavraSecreta())) {
                        palavra.adivinharTodasAsLetras();
                        System.out.println("\nPARABÉNS! Você acertou a palavra completa!");
                        break;
                    } else {
                        System.out.println("Palavra incorreta! Você perdeu uma tentativa de chute.");
                        if (tentativasDeChuteDePalavra == 0) {
                            System.out.println("Você esgotou suas tentativas de chute da palavra completa!");
                            // Não precisa perder uma tentativa normal aqui, pois ele já perdeu a tentativa de chute.
                            // Deixar ele perder a tentativa normal só se você quiser penalizar DUAS VEZES (chute + tentativa normal).
                            // A lógica atual é que se ele errar o chute final, ele não pode mais chutar, mas continua tentando letras.
                            // Se ele não tiver mais tentativas normais, o jogo acaba no próximo loop.
                        }
                    }
                } else {
                    System.out.println("Você não tem mais tentativas para chutar a palavra completa.");
                }
                continue;
            }
            // --- FIM DA LÓGICA PARA CHUTE DA PALAVRA ---

            // Se não foi um chute de palavra, processa como uma letra
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

            mostrarForca(this.erros);
        }

        if (palavra.palavraCompleta()) {
            System.out.println("\nParabéns, " + jogador.getNome() + "! Você venceu! A palavra era: " + palavra.getPalavraSecreta());
        } else {
            System.out.println("\nVocê perdeu! A palavra era: " + palavra.getPalavraSecreta());
            mostrarForca(this.erros);
        }
    }

    private static final String[] FORCA_VISUAL = {
            "  +---+\n      |\n      |\n      |\n     ===",
            "  +---+\n  O   |\n      |\n      |\n     ===",
            "  +---+\n  O   |\n  |   |\n      |\n     ===",
            "  +---+\n  O   |\n /|   |\n      |\n     ===",
            "  +---+\n  O   |\n /|\\  |\n      |\n     ===",
            "  +---+\n  O   |\n /|\\  |\n /    |\n     ===",
            "  +---+\n  O   |\n /|\\  |\n / \\  |\n     ==="
    };

    private static void mostrarForca(int erros) {
        // Garante que o índice não exceda o tamanho do array
        System.out.println(FORCA_VISUAL[Math.min(erros, FORCA_VISUAL.length - 1)]);
    }
}