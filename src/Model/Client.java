package Model;

import java.util.Arrays;

public class Client {

    private float[] position;

    Client next;
    Client previous;

    int numClient, quantité;

    public Client (int numClient, int quantité, float[] position){
        this.numClient = numClient;
        this.quantité = quantité;
        this.position = position;
    }

    public int getDistance(Client client)
    {
        return (int)Math.sqrt(Math.pow(this.position[0] - client.getPosition()[0], 2 ) + Math.pow(this.position[1] - client.getPosition()[1], 2));
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

    @Override
    public String toString() {
        return "Client{" +
                "position=" + Arrays.toString(position) +
//                ", next=" + next == null ? null : next.numClient +
//                ", previous=" + previous == null ? null : previous.numClient +
                ", numClient=" + numClient +
                ", quantité=" + quantité +
                '}';
    }
}
