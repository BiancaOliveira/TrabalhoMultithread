package Classes;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class ListaCarros {
    int n;//Tamanho da fila
    int p;//Ponteiro out
    volatile int count;
    Semaphore  mutex;
    Carro buffer[];
    Random random = new Random();



    public ListaCarros(int n){//Construtor
        this.n = n;
        buffer = new Carro[n];
        mutex = new Semaphore(1);
        count =n;
        p=0;
        criarCarros();

    }
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private void criarCarros(){//Popula a lista com carros
        for(int i = 0; i < n; i++){
            buffer[i] = new Carro(i, random.nextInt(10)+1);
        }
    }

    public Carro consome() {//consome um carro

        try{
            mutex.acquire();
        }catch(Exception e){

        }
   /*     int aux = p;*/

        Carro valor=buffer[p];
        p =(p+1)%n;
        count --;
        mutex.release();

        return valor;
    }


    public Carro consomeSemProtecao() {// sem proteção na seção critica

        /*int aux = p;*/
        Carro valor=buffer[p];
        p =(p+1)%n;
        count --;
        return valor;
    }

    public void imprimir(){
        for(int i = 0; i < n; i++){
          System.out.println(i + " id = " + buffer[i].getId() + " tempo = " + buffer[i].getTempo());
        }
    }

}
