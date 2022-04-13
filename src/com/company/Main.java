package com.company;
import Classes.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static  void gravar(int fila, int thr, int op, long diff,int tipo){
        File arquivo;
        if(tipo == 1){
             arquivo = new File("testes\\arquivo_"+ fila +"_" + op +".txt");
        }else{
             arquivo = new File("testes\\manual\\arquivo_"+ fila +"_" + op +".txt");
        }

        try {
            if (!arquivo.exists()) {
                //cria um arquivo (vazio)
                arquivo.createNewFile();
            }
            FileWriter fw = new FileWriter(arquivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write( thr +", "+ diff  + "\n");
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {

        }

    }

    public static void automatico(int x){
        long init;
        long end;
        long diff;

        // write your code here

        int v[] = {64,128,256,512,1024};//Tamanhos das filas
        int t[] = {1,4,8,16,32,64,128,256,512};//Numero de threads

        //Inicia o teste
        for(int y=0; y<5; y++){
            for(int i=0; i<v.length; i++){// for de filas
                System.out.println("-------------- Fila " + v[i] +" --------------");
                FilaCarros fila  = new FilaCarros(v[i]);//Criar fila de tamanho v[i]
                for(int j = 0; j<t.length; j++){//for Threads
                    if(t[j]<=v[i]){
                        if(t[j]==1){
                            System.out.println("\n------ Sequencial ---------");
                            fila.setCount(v[i]);

                            init = System.currentTimeMillis();//Tempo inicial
                            fila.sequencial(/*"testes\\arquivo_"+ v[i]+"_"+ t[j] + "_1.txt"*/);
                            end = System.currentTimeMillis();//Tempo final
                            diff = end - init;//Tempo de Execução

                            //Salvar dado no arquivo
                            gravar(v[i],t[j],1,diff,1);
                            //fim salvar dado no arquivo
                            System.out.println("\nDemorou " + (diff) + " milisegundos");

                        }else{
                            if(x == 3){
                                System.out.println("\n------" + t[j] +" Threads  não protegida ---------");
                                Threads th = new Threads(t[j]/*"testes\\arquivo_"+ v[i]+"_"+ t[j] + "_1.txt"*/);//Criar n threads onde n==t[j]
                                th.setOp(1);
                                fila.setCount(v[i]);

                                init = System.currentTimeMillis();//Tempo inicial
                                th.criarThreads(fila);//Inicializa as threads e começa a consumir
                                end = System.currentTimeMillis();//Tempo final
                                diff = end - init;//Tempo de Execução

                                //Salvar dado no arquivo
                                gravar(v[i],t[j],1,diff,1);
                                //fim salvar dado no arquivo
                                System.out.println("\nDemorou " + (diff) + " milisegundos");

                                System.out.println("\n------" + t[j] +" Threads  protegida ---------");
                                Threads th2 = new Threads(t[j]/* "testes\\arquivo_"+ v[i]+"_"+ t[j] + "_2.txt"*/);//Criar n threads onde n==t[j]
                                th2.setOp(0);
                                fila.setCount(v[i]);

                                init = System.currentTimeMillis();//Tempo inicial
                                th2.criarThreads(fila);//Inicializa as threads e começa a consumir
                                end = System.currentTimeMillis();//Tempo final
                                diff = end - init;//Tempo de Execução

                                //Salvar dado no arquivo
                                gravar(v[i],t[j],2,diff,1);
                                //fim salvar dado no arquivo
                                System.out.println("\nDemorou " + (diff) + " milisegundos");

                            }else {
                                System.out.println("\n------" + t[i] +" Threads  ---------");
                                System.out.println();
                                Threads th = new Threads(t[j]/*,"testes\\arquivo_"+ v[i]+"_"+ t[j] + "_" + x +".txt"*/);//Criar n threads onde n==t[j]
                                th.setOp(x);
                                fila.setCount(v[i]);

                                init = System.currentTimeMillis();//Tempo inicial
                                th.criarThreads(fila);//Inicializa as threads e começa a consumir
                                end = System.currentTimeMillis();//Tempo final
                                diff = end - init;//Tempo de Execução

                                //Salvar dado no arquivo
                                gravar(v[i],t[j],x,diff,1);
                                //fim salvar dado no arquivo
                                System.out.println("\nDemorou " + (diff) + " milisegundos");
                            }
                        }
                    }
                }
            }
        }

    }

    public static void manual(int tam, int qtd, int x){
        long init;
        long end;
        long diff;

        //Inicia o teste

        System.out.println("-------------- Fila " + tam +" --------------");
        FilaCarros fila  = new FilaCarros(tam);//Criar fila
        if(qtd == 1){
            System.out.println("\n------ Sequencial ---------");
            fila.setCount(tam);

            init = System.currentTimeMillis();//Tempo inicial
            fila.sequencial(/*"testes\\manual\\arquivo_"+ tam+"_"+ qtd + "_1.txt"*/);
            end = System.currentTimeMillis();//Tempo final
            diff = end - init;//Tempo de Execução

            //Salvar dado no arquivo
            gravar(tam,qtd,1,diff,0);
            //fim salvar dado no arquivo
            System.out.println("\nDemorou " + (diff) + " milisegundos");
        }else {
            if (x == 3) {
                System.out.println("------" + qtd + " Threads não protegida ---------");
                Threads th = new Threads(qtd/*, "testes\\manual\\arquivo_" + tam + "_" + qtd + "_1.txt"*/);//Criar n threads
                th.setOp(1);
                fila.setCount(tam);

                init = System.currentTimeMillis();//Tempo inicial
                th.criarThreads(fila);//Inicializa as threads e começa a consumir
                end = System.currentTimeMillis();//Tempo final
                diff = end - init;//Tempo de Execução

                //Salvar dado no arquivo
                gravar(tam, qtd, 1, diff, 0);
                //fim salvar dado no arquivo
                System.out.println("\nDemorou " + (diff) + " milisegundos");

                System.out.println("------" + qtd + " Threads protegida ---------");
                Threads th2 = new Threads(qtd/*, "testes\\manual\\arquivo_" + tam + "_" + qtd + "_2.txt"*/);//Criar n threads
                th2.setOp(0);
                fila.setCount(tam);

                init = System.currentTimeMillis();//Tempo inicial
                th2.criarThreads(fila);//Inicializa as threads e começa a consumir
                end = System.currentTimeMillis();//Tempo final
                diff = end - init;//Tempo de Execução

                //Salvar dado no arquivo
                gravar(tam, qtd, 2, diff, 0);
                //fim salvar dado no arquivo
                System.out.println("\nDemorou " + (diff) + " milisegundos");

            } else {
                System.out.println("------" + qtd + " Threads ---------");
                Threads th = new Threads(qtd/*, "testes\\manual\\arquivo_" + tam + "_" + qtd + "_" + x + ".txt"*/);//Criar n threads
                th.setOp(x);
                fila.setCount(tam);

                init = System.currentTimeMillis();//Tempo inicial
                th.criarThreads(fila);//Inicializa as threads e começa a consumir
                end = System.currentTimeMillis();//Tempo final
                diff = end - init;//Tempo de Execução

                //Salvar dado no arquivo
                gravar(tam, qtd, x, diff, 0);
                //fim salvar dado no arquivo
                System.out.println("\nDemorou " + (diff) + " milisegundos");
            }
        }

    }

    public static int menuProteção() {
        int op= -1;
        Scanner ler = new Scanner(System.in);

        System.out.println("Escolha a opção");
        System.out.println("1 - Rodar  sem proteção da seção critica");
        System.out.println("2 - Rodar  com proteção da seção critica");
        System.out.println("3 - Ambos");
        System.out.println("0 - Voltar");
        System.out.printf("Opção = ");
        op= ler.nextInt();
        switch (op) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                System.out.println("Opção incorreta");
        }
        return 0;
    }

    public static void menu() {
        int op= -1, op2= -1;
        int x, tam, qtd,n = 1;
        Scanner ler = new Scanner(System.in);
        while (op != 0){
            System.out.println("\nEscolha a opção");
            System.out.println("1 - Rodar automaticamente");
            System.out.println("2 - Entrar com os dados");
            System.out.println("0 - Sair");
            System.out.printf("Opção = ");
            op= ler.nextInt();
            switch (op){

                case 1:
                    x=menuProteção();
                    if(x == 0){
                        menu();
                    }
                    automatico(x);
                    break;
                case 2:
                    System.out.println("\nEscolha a opção");
                    System.out.println("1- Rodar sequencial");
                    System.out.println("2- Rodar com threads");
                    System.out.println("0 - Voltar");
                    System.out.printf("Opção = ");
                    op2= ler.nextInt();
                    switch (op2){
                        case 1:
                            System.out.println("Insita o tamanho da fila (Inteiro)");
                            tam= ler.nextInt();
                            qtd = 1;
                            manual(tam,qtd,1);
                            break;
                        case 2:
                            x=menuProteção();
                            if(x == 0){
                                menu();
                            }
                            System.out.println("Insita o tamanho da fila (Inteiro)");
                            tam= ler.nextInt();
                            System.out.println("Insira a quantidade de threads");
                            qtd= ler.nextInt();
                            manual(tam,qtd,x);
                        case 0: break;
                        default: System.out.println("Opção incorreta");
                    }

                case 0: break;
                default: System.out.println("Opção incorreta");
            }
        }

    }

    public static void main(String[] args) throws IOException {
       menu();
    }
}

