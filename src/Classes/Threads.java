package Classes;


public class Threads {
    int n;
    Thread th[];
    int op;
    //String file;

    public Threads (int n /*String arq*/){
        this.n = n;
        th = new Thread[n];
       // file = arq;

    }

    public void setOp(int op) {
        this.op = op;
    }

    public void criarThreads (FilaCarros list){
        consumidor con = new consumidor(list);
        for(int i = 0; i<n; i++) {
            th[i] = new Thread(con);
            th[i].start();
        }
        try {
            for(int i = 0; i<n; i++) {

                th[i].join();
            }
        } catch(Exception e) {
            System.out.println("Erro threads");
        }
    }

    public class consumidor implements Runnable{
        FilaCarros b;

        public consumidor(FilaCarros _b){
            b= _b;
/*
            b.imprimir();
*/
        }
        public void run(){
            Carro temp;
            
            while(b.getCount()> 0){
                //testa pra ver se é para rodar sem proteção da seção critica ou com proteção da seção critica
                if(op == 1){
                    temp= b.semProtecao();
//                    System.out.println("Consome Sem proteção");
                }else{
                    temp= b.comProtecao();
//                    System.out.println("Consome com proteção");
                }
                //tempo que o carro consome do elevador
                try {
                    Thread.sleep(temp.getTempo()*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(temp == null){
                    break;
                }

//                System.out.println(Thread.currentThread().getName() + " Consome = "+ temp.getId() + " tempo = " + temp.getTempo());
//
//                //gravar no arquivo
//                try {
//                    File arquivo = new File(file);
//                    if (!arquivo.exists()) {
//                        //cria um arquivo (vazio)
//                        arquivo.createNewFile();
//                    }
//                    FileWriter fw = new FileWriter(arquivo, true);
//                    BufferedWriter bw = new BufferedWriter(fw);
//                    bw.write(Thread.currentThread().getName() + ", "+ temp.getId() + ", " + temp.getTempo());
//                    bw.newLine();
//                    bw.close();
//                    fw.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            }
//            System.out.println(" threads ativas " + Thread.activeCount());
        }
    }
}
