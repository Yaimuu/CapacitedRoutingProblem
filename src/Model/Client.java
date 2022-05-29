package Model;

import java.util.Arrays;

public class Client implements Cloneable {

    private float[] position;

    int numClient, quantite;

    public Client (int numClient, int quantite, float[] position)
    {
        this.numClient = numClient;
        this.quantite = quantite;
        this.position = position;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
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

    public int getNumClient() {
        return numClient;
    }

    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Client{" +
                "position=" + Arrays.toString(position) +
                ", numClient=" + numClient +
                ", quantité=" + quantite +
                '}';
    }
}
