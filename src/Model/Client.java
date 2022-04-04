package Model;

public class Client {

    private float[] position = new float[2];

    Client next;
    Client previous;

    int numClient, quantité;

    public Client (int numClient, int quantité, float[] position){
        this.numClient = numClient;
        this.quantité = quantité;
        this.position = position;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public Client getNext() {
        return next;
    }

    public void setNext(Client next) {
        this.next = next;
    }

    public Client getPrevious() {
        return previous;
    }

    public void setPrevious(Client previous) {
        this.previous = previous;
    }

    public int getNumClient() {
        return numClient;
    }

    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }
}
