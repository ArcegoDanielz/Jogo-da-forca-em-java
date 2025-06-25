# Documentação do Projeto - Jogo da Forca

Este documento contém os diagramas de engenharia de software que modelam o sistema do Jogo da Forca.

## 1. Diagrama de Caso de Uso

O diagrama a seguir descreve as principais interações do ator "Jogador" com o sistema.

```mermaid
graph TD
    subgraph Jogo da Forca
        A(Iniciar Jogo)
        B(Fazer Adivinhação)
        C(Concluir Jogo)
        D(Exibir Estado do Jogo)
        E(Validar Letra)
        F(Verificar Repetição)
        G(Verificar Palavra)
        H(Apresentar Tema)
        I(Exibir Vitória)
        J(Exibir Derrota)
        K(Exibir Palavra Parcial)
        L(Exibir Letras Erradas)
        M(Exibir Tentativas Restantes)
        N(Reiniciar Jogo)
        O(Sair do Jogo)
    end

    Jogador --> A
    A --o|includes| H
    Jogador --|> B
    B --o|includes| E
    B --o|includes| F
    B --o|includes| G
    G --o|includes| D
    D --o|includes| K
    D --o|includes| L
    D --o|includes| M
    B --o|includes| C
    C --|> I
    C --|> J
    I -.->>|extend| N
    I -.->>|extend| O
    J -.->>|extend| N
    J -.->>|extend| O

    classDef default fill:#f9f,stroke:#333,stroke-width:2px;
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