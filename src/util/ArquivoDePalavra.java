import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArquivoDePalavras {
    private List<PalavraComDica> palavrasComDica; // Agora armazena PalavraComDica
    private Random random;

    public ArquivoDePalavras(String nomeArquivo) throws IOException {
        palavrasComDica = new ArrayList<>();
        random = new Random();
        carregarPalavras(nomeArquivo);
    }

    private void carregarPalavras(String nomeArquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Processa cada linha para extrair palavra e dica
                String trimmedLine = linha.trim();
                if (!trimmedLine.isEmpty()) {
                    String[] partes = trimmedLine.split(";"); // Divide pela vírgula e ponto
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

    // Método para obter um objeto PalavraComDica aleatório
    public PalavraComDica getPalavraComDicaAleatoria() {
        if (palavrasComDica.isEmpty()) {
            return null; // ou lançar uma exceção específica
        }
        return palavrasComDica.get(random.nextInt(palavrasComDica.size()));
    }
}