package Model;

import java.util.ArrayList;
import java.util.List;

public class Truck {

    final int maxCapacity = 100;

    List<Client> clients;
    List<Double> distances;

    int truckNum, quantite;

    public Truck(int num)
    {
        this.clients = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.truckNum = num;
        this.quantite = 0;
    }

    public Truck(int num, List<Client> clients)
    {
        this(num);
        this.clients = clients;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        this.clients.add(client);
        this.quantite += client.getQuantité();
    }

    public List<Double> getDistances() {
        return distances;
    }

    public void setDistances(List<Double> distances) {
        this.distances = distances;
    }

    public int getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(int truckNum) {
        this.truckNum = truckNum;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
