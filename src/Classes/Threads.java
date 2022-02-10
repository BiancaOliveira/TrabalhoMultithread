package Classes;
import java.io.*;


public class Threads {
    int n;
    Thread th[];
    ListaCarros car[];
    int op;
    String file;

    public Threads (int n, String arq){
        this.n = n;
        th = new Thread[n];
        file = arq;

    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public void criarThreads (ListaCarros list){
        Consumidor con = new Consumidor(list);
        for(int i = 0; i<n; i++) {
            th[i] = new Thread(con);
            th[i].start();
        }
        try {
            for(int i = 0; i<n; i++) {

                th[i].join();
            }
        } catch(Exception e) {
            System.out.println("Erro");
        }

    }

    public class Consumidor implements Runnable{
        ListaCarros b;

        public Consumidor(ListaCarros _b){
            b= _b;
/*
            b.imprimir();
*/
        }
        public void run(){
            Carro temp;


            
            while(b.getCount()!= 0){
//                System.out.println("op  -- " + op);
                if(op == 1){
                    temp= b.consomeSemProtecao();
//                    System.out.println("Consome Sem proteção");
                }else{
                    temp= b.consome();
//                    System.out.println("Consome com proteção");
                }

                try {
                    Thread.sleep(temp.getTempo()*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(temp == null){
                    break;
                }

                System.out.println(Thread.currentThread().getName() + " Consome = "+ temp.getId() + " tempo = " + temp.getTempo());

                try {
                    File arquivo = new File(file);
                    if (!arquivo.exists()) {
                        //cria um arquivo (vazio)
                        arquivo.createNewFile();
                    }
                    FileWriter fw = new FileWriter(arquivo, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(Thread.currentThread().getName() + ", "+ temp.getId() + ", " + temp.getTempo());
                    bw.newLine();
                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
