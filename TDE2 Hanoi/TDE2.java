import java.util.Scanner;

public class TDE2 {
    public static void main(String[] args) {
        // O teclado
        Scanner teclado = new Scanner(System.in);

        // A variável para contar quantos movimentos foram feitos
        int movimentos = 0;

        System.out.println("Bem-vindo ao jogo!");
        System.out.println("Primeiro, digite o tamanho que você deseja que as pilhas tenham: ");
        // Aqui vemos o tamanho de itens que as pilhas devem ter
        int tamanho = teclado.nextInt();
        // Aqui em baixo criamos as três pilhas
        Pilha pilhaA = new Pilha();
        Pilha pilhaB = new Pilha();
        Pilha pilhaC = new Pilha();

        // A pilhaA serve como a pilha 1 em que todos os itens são colocados primeiros
        // Inserimos os números aleatórios
        pilhaA.inserirrandom(tamanho);

        // Ver se o usuário quer que a ordem das pilhas seja crescente ou decrescente
        System.out.println("Qual a ordem deve ser seguida: 1-Crescente 2-Decrescente: ");
        // Se o usuário não colocar nenhuma ordem, vai para a ordem crescente mesmo
        int ordem = teclado.nextInt();
        if (ordem != 1 && ordem != 2) {
            ordem = 1;
        }

        // Agora começamos com o corpo de while principal onde o usuário escolhe o que quer fazer
        while (true) {
            System.out.println("Pilha A: ");
            pilhaA.imprimir();
            System.out.println("Pilha B: ");
            pilhaB.imprimir();
            System.out.println("Pilha C: ");
            pilhaC.imprimir();
            // Aqui o usuário escolhe o que fazer
            System.out.println("O que deseja fazer: 0-Sair do jogo 1-Movimentar 2-Solução Automática");
            int escolha = teclado.nextInt();

            if (escolha == 0) {
                System.out.println("Terminando programa...");
                break;
            } else if (escolha == 1) {
                System.out.println("Movimentação escolhida!");
                // Para pegar a pilha de origem e destino
                System.out.println("Diga a pilha de origem que vai sofrer a retirada: 1-A 2-B 3-C");
                int origem = teclado.nextInt();
                System.out.println("Diga a pilha de destino que vai sofrer a inserção: 1-A 2-B 3-C");
                int destino = teclado.nextInt();

                if (!(origem >= 1 && origem <= 3 && destino >= 1 && destino <= 3)) {
                    // As escolhas para origem e/ou destino estão erradas (não são 1, 2 ou 3)
                    System.out.println("Escolhas inválidas. Origem e destino devem ser 1, 2 ou 3.");
                    continue;
                }

                // Uma checagem para ver se a pilha de origem é diferente da pilha de destino
                if (origem == destino) {
                    System.out.println("A origem e destino são iguais!");
                    continue;
                }

                // Pegamos as pilhas de origem e destino de acordo com o usuário
                Pilha origemPilha = escolherPilha(origem, pilhaA, pilhaB, pilhaC);
                Pilha destinoPilha = escolherPilha(destino, pilhaA, pilhaB, pilhaC);

                // Se a pilha de origem estiver vazia, não fazemos as mudanças
                if (origemPilha.getSize() == 0) {
                    System.out.println("A pilha " + nomePilha(origem) + " está vazia!");
                    continue;
                }

                // O valor da pilha de origem
                int valor = origemPilha.getValor();

                // Verificamos se alguma pilha está cheia e se ela está ordenada
                // Também verificamos se podemos colocar o número na pilha de destino
                if (deveEmpilhar(ordem, valor, destinoPilha)) {
                    origemPilha.remove();
                    destinoPilha.insert(valor);
                    movimentos++;
                    if (destinoPilha.getSize() == tamanho && destinoPilha.ordenada(ordem)) {
                        System.out.println("A solução foi encontrada!");
                        imprimirTodasAsPilhas(pilhaA, pilhaB, pilhaC);
                        System.out.println("Ordenação concluída em " + movimentos + " jogadas.");
                        break;
                    }
                } else {
                    System.out.println("Não é possível empilhar um valor maior ou menor do que o topo da pilha " + nomePilha(destino) + " na ordem selecionada.");
                }
            } else if (escolha == 2) {
                System.out.println("Solução automática escolhida!");
                // Verificamos se a pilha A já está ordenada
                if (pilhaA.ordenada(ordem)) {
                    System.out.println("A solução já foi encontrada!");
                    break;
                }
                while (true) {
                    boolean solucaoEncontrada = false;

                    for (int origem = 1; origem <= 3; origem++) {
                        Pilha origemPilha = escolherPilha(origem, pilhaA, pilhaB, pilhaC);
                        int valor = origemPilha.getValor();

                        for (int destino = 1; destino <= 3; destino++) {
                            if (origem == destino) {
                                continue; // Evitar movimentos de origem para o mesmo destino
                            }
                            Pilha destinoPilha = escolherPilha(destino, pilhaA, pilhaB, pilhaC);

                            // Verificar se o movimento é permitido
                            if (origemPilha != null && origemPilha.getSize() > 0 && deveEmpilhar(ordem, valor, destinoPilha)) {
                                // Realizar o movimento
                                trocarDePilha(origemPilha, destinoPilha, ordem);

                                // Atualizar a contagem de movimentos apenas para movimentos válidos
                                movimentos++;

                                // Imprimir as pilhas após cada movimento válido
                                imprimirTodasAsPilhas(pilhaA, pilhaB, pilhaC);

                                if (destinoPilha.getSize() == tamanho && destinoPilha.ordenada(ordem)) {
                                    System.out.println("A solução foi encontrada!");
                                    destinoPilha.imprimir();
                                    System.out.println("Ordenação concluída em " + movimentos + " jogadas.");
                                    solucaoEncontrada = true;
                                    break;
                                }
                            }
                        }
                        if (solucaoEncontrada) {
                            break;
                        }
                    }
                    if (solucaoEncontrada) {
                        return;
                    }
                }
            } else {
                System.out.println("Algo deu errado! Tente novamente!");
            }
        }
        System.out.println("Obrigado por jogar!");
    }

    public static Pilha escolherPilha(int numeroPilha, Pilha pilhaA, Pilha pilhaB, Pilha pilhaC) {
        switch (numeroPilha) {
            case 1:
                return pilhaA;
            case 2:
                return pilhaB;
            case 3:
                return pilhaC;
            default:
                return null;
        }
    }

    public static String nomePilha(int numeroPilha) {
        switch (numeroPilha) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            default:
                return "";
        }
    }

    public static boolean deveEmpilhar(int ordem, int valor, Pilha destinoPilha) {
        if (destinoPilha.getSize() == 0) {
            return true; // Se a pilha de destino estiver vazia, sempre podemos empilhar
        }

        int topo = destinoPilha.getValor();
        return (ordem == 1 && valor <= topo) || (ordem == 2 && valor >= topo);
    }

    public static void imprimirTodasAsPilhas(Pilha pilhaA, Pilha pilhaB, Pilha pilhaC) {
        System.out.println("------------------");
        System.out.println("Pilha A:");
        pilhaA.imprimir();
        System.out.println("Pilha B:");
        pilhaB.imprimir();
        System.out.println("Pilha C:");
        pilhaC.imprimir();
        System.out.println("------------------");
    }

    public static void trocarDePilha(Pilha pilhaOrigem, Pilha pilhaDestino, int ordem) {
        if (pilhaOrigem.getSize() == 0) {
            System.out.println("A pilha de origem está vazia!");
            return;
        }

        int valor = pilhaOrigem.getValor();

        if (pilhaDestino.getSize() == 0 || deveEmpilhar(ordem, valor, pilhaDestino)) {
            pilhaOrigem.remove();
            pilhaDestino.insert(valor);
        }
    }
}