import java.util.Arrays;
import java.util.Random;
public class Pilha {
    Node head;
    int size;

    Pilha() {
        this.head = null;
        this.size = 0;
    }

    public void insert(int data) {
        Node newNode = new Node(data);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    public void remove() {
        if (head == null) {
            System.out.println("A pilha está vazia");
        }
        head = head.getNext();
        size--;
    }

    // Para inserir os números aleatorios com o tamanho do que usuario quer
    public void inserirrandom(int tamanho){
        // Crie uma instância da classe Random
        Random random = new Random();
        //int numeroIntervalo = random.nextInt(100) + 1;
        // Gera os numeros aleatorios e insere ele na pilha.

        for(int i = 0; i < tamanho; i++){
            int numeroRandom = random.nextInt(100) + 1;
            insert(numeroRandom);
        }
    }

    // Para imprimir o que tem na Pilha
    public void imprimir(){
        Node comeco = head;
        if(comeco != null){
            while(comeco != null){
                System.out.println(comeco.getData());
                comeco =  comeco.getNext();
            }
        }
    }

    public boolean ordenada(int ordem){
        // Acho que da pra copiar tudo que tem na pilha em uma lista e dar um buble sort nela
        // Se as duas forem iguais, isso significa que a pilha está ordenada do jeito certo e que a pilha está na ordem certa
        int[] pilhaOrdenada = new int[size];
        int[] pilhaUsuario = new int[size];

        // Pegamos um node
        Node comeco = head;

        // Pegamos tudo que tem na pilha e jogamos na listas
        for(int i = 0; i < size; i++){
            int elemento = comeco.getData();
            pilhaUsuario[i] = elemento; // Copia para o array pilhaUsuario
            pilhaOrdenada[i] = elemento; // Copia para o array pilhaOrdenada
            comeco = comeco.getNext();
        }

        // Damos um bubble sort na pilha
        pilhaOrdenada = bubblesort(pilhaOrdenada, ordem);

        // Por enquanto imprimimos as duas listas para testar e ver como elas estão
        System.out.println(Arrays.toString(pilhaUsuario));
        System.out.println(Arrays.toString(pilhaOrdenada));

        return Arrays.equals(pilhaOrdenada, pilhaUsuario);
    }

    // Uma função bubble sort para arrumar os itens das pilhas:
    // Talvez ver se ele recebe a ordem para so mudar o sinal
    public int[] bubblesort(int[] pilha, int ordem){
        // Aplicamos um bubblesort básico e retornamos a lista

        // Verificamos também a ordem que o usuario colocou
        // 1-Crescente 2-Decrescente

        boolean ordemCrescente = (ordem == 1);

        for (int i = 0; i < pilha.length - 1; i++) {
            for (int j = 0; j < pilha.length - i - 1; j++) {
                boolean ordemFinal = (ordemCrescente) ? pilha[j] > pilha[j + 1] : pilha[j] < pilha[j + 1];
                if (ordemFinal) {
                    int hold = pilha[j];
                    pilha[j] = pilha[j+1];
                    pilha[j + 1] = hold;
                }
            }
        }
        return pilha;
    }

    // Os get e set

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public int getValor(){
        return head.getData();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



}
