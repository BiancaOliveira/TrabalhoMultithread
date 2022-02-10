package Classes;

public class Carro {
    int id; //identificação do carro;
    int tempo;//tempo que cada carro precisara do elevador

    public Carro(int id, int tempo) {
        this.id = id;
        this.tempo = tempo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

}
