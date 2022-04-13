package Classes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class FilaCarros {
    int n;//Tamanho da fila
    int p;//Ponteiro out
    volatile int count;
    Semaphore  mutex;
    Carro buffer[];
    Random random = new Random();



    public FilaCarros(int n){//Construtor
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
            buffer[i] = new Carro(i, random.nextInt(5)+1);
        }
    }
    public void sequencial(/*String file*/){
        Carro temp;
        while (count>0){
            temp = semProtecao();

            //tempo que o carro consome do elevador
            try {
                Thread.sleep(temp.getTempo()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(" Consome = "+ temp.getId() + " tempo = " + temp.getTempo());

//            //gravar no arquivo
//            try {
//                File arquivo = new File(file);
//                if (!arquivo.exists()) {
//                    //cria um arquivo (vazio)
//                    arquivo.createNewFile();
//                }
//                FileWriter fw = new FileWriter(arquivo, true);
//                BufferedWriter bw = new BufferedWriter(fw);
//                bw.write(+ temp.getId() + ", " + temp.getTempo());
//                bw.newLine();
//                bw.close();
//                fw.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        }

    }

    public Carro comProtecao() {//consome um carro

        try{
            mutex.acquire();
        }catch(Exception e){

        }

        Carro valor=buffer[p];
        p =(p+1)%n;
        count--;
        mutex.release();

        return valor;
    }


    public Carro semProtecao() {// sem proteção da seção critica

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
