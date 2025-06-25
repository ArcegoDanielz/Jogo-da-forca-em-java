# Documentação do Projeto - Jogo da Forca

Este documento contém os diagramas de engenharia de software que modelam o sistema do Jogo da Forca.

## 1. Diagrama de Caso de Uso

O diagrama a seguir descreve as principais interações do ator "Jogador" com o sistema.

```mermaid
graph LR
    %% Define um estilo para todos os nós: fundo cinza claro, borda escura.
    style * fill:#e0e0e0,stroke:#333,stroke-width:2px;

    Jogador --> Iniciar_Jogo[Iniciar Jogo]
    
    subgraph Jogo da Forca
        Iniciar_Jogo -->|includes| Apresentar_Tema[Apresentar Tema]
        Jogador --> Fazer_Adivinhacao[Fazer Adivinhação]
        
        Fazer_Adivinhacao -->|includes| Validar_Letra[Validar Letra]
        Fazer_Adivinhacao -->|includes| Verificar_Repeticao[Verificar Repetição]
        Fazer_Adivinhacao -->|includes| Verificar_Palavra[Verificar Palavra]
        
        Verificar_Palavra -->|includes| Exibir_Estado[Exibir Estado do Jogo]
        Exibir_Estado -->|includes| Exibir_Palavra_Parcial[Exibir Palavra Parcial]
        Exibir_Estado -->|includes| Exibir_Letras_Erradas[Exibir Letras Erradas]
        Exibir_Estado -->|includes| Exibir_Tentativas[Exibir Tentativas Restantes]

        Fazer_Adivinhacao -->|includes| Concluir_Jogo[Concluir Jogo]
        Concluir_Jogo --> Exibir_Vitoria[Exibir Vitória]
        Concluir_Jogo --> Exibir_Derrota[Exibir Derrota]

        Exibir_Vitoria -->|extend| Reiniciar_Jogo[Reiniciar Jogo]
        Exibir_Vitoria -->|extend| Sair_do_Jogo[Sair do Jogo]
        Exibir_Derrota -->|extend| Reiniciar_Jogo
        Exibir_Derrota -->|extend| Sair_do_Jogo
    end
```

## 2. Diagrama de Classes

Este diagrama mostra as principais classes do sistema, seus atributos, métodos e os relacionamentos entre elas.

```mermaid
classDiagram
    class JogoDaForca {
        +scanner: Scanner
        +main(String[] args)
    }

    class PalavraComTema {
        -palavra: String
        -tema: String
    }

    class GerenciadorJogo {
        +ACERTO
        +ERRO
        +LETRA_REPETIDA
    }

    JogoDaForca --> PalavraComTema
    JogoDaForca --> GerenciadorJogo
```

## 3. Diagrama de Sequência

O diagrama de sequência ilustra a ordem das interações entre os objetos do sistema durante uma partida.

```mermaid
sequenceDiagram
    participant Jogador
    participant JogoDaForca
    participant PalavraComTema
    participant GerenciadorJogo

    Jogador->>+JogoDaForca: iniciarJogo()
    Note right of JogoDaForca: Orquestra a criação do jogo.

    JogoDaForca->>+PalavraComTema: new()
    JogoDaForca->>+GerenciadorJogo: new()
    
    JogoDaForca->>PalavraComTema: getTema()
    PalavraComTema-->>JogoDaForca: Tema da Palavra
    JogoDaForca->>GerenciadorJogo: getPalavraParcial()
    GerenciadorJogo-->>JogoDaForca: Palavra com '_'
    
    JogoDaForca-->>-Jogador: exibirInterfaceInicial(tema, palavraParcial)

    loop Fazer Adivinhação
        Jogador->>+JogoDaForca: adivinharLetra(letra)
        
        JogoDaForca->>+GerenciadorJogo: processarJogada(letra)
        GerenciadorJogo->>PalavraComTema: contemLetra(letra)
        PalavraComTema-->>GerenciadorJogo: (true/false)
        
        alt Letra Correta
            GerenciadorJogo->>GerenciadorJogo: registrarAcerto()
        else Letra Errada ou Repetida
            GerenciadorJogo->>GerenciadorJogo: registrarErro()
        end
        
        GerenciadorJogo-->>-JogoDaForca: estadoAtualizado
        
        JogoDaForca->>GerenciadorJogo: getEstadoJogo()
        GerenciadorJogo-->>JogoDaForca: (palavraParcial, letrasErradas, tentativas)
        
        JogoDaForca-->>-Jogador: exibirEstado(palavraParcial, letrasErradas, tentativas)
    end

    alt Jogo Concluído
        JogoDaForca-->>Jogador: exibirVitoria()
    else Jogo Concluído
        JogoDaForca-->>Jogador: exibirDerrota()
    end
```

## 4. Diagrama de Atividade

O diagrama de atividade detalha o fluxo de trabalho do jogo, desde o início até a conclusão da partida.

```mermaid
graph TD
    subgraph Jogo da Forca
        Start(( )) --> A[Inicializar Jogo];
        A --> B{Palavra foi adivinhada?};
        B --o|Não| C[Exibir estado do jogo];
        C --> D[Solicitar letra ao jogador];
        D --> E{Letra já foi jogada?};
        E --o|Sim| C;
        E --o|Não| F{Letra pertence à palavra?};
        F --o|Sim| G[Revelar letra na palavra];
        G --> H{Todas as letras foram reveladas?};
        H --o|Não| I[Atualizar estado];
        I --> B;
        F --o|Não| J[Decrementar tentativas];
        J --> K{Tentativas esgotadas?};
        K --o|Não| I;
        B --o|Sim| L[Exibir Mensagem de Vitória];
        H --o|Sim| L;
        K --o|Sim| M[Exibir Mensagem de Derrota];
        L --> End(( ));
        M --> End(( ));
    end
```