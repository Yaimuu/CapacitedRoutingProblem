package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Truck {

    final int maxCapacity = 100;

    List<Client> clients;
    List<Client[]> roads;
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

    public float getTruckFitness() {
        float fitness = 0;

        for (int i = 1; i < this.clients.size(); i++) {
            Client c1 = this.clients.get(i-1), c2 = this.clients.get(i);
            float distance = c1.getDistance(c2);
            fitness += distance;
        }

        return fitness;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Client getRandomClient() {
        Random rand  = new Random();
        return this.clients.get(rand.nextInt(1, this.clients.size() - 1));
    }

    public Client getRandomClient(int min, int range) {
        Random rand  = new Random();
        return this.clients.get(rand.nextInt(min, min + range));
    }

    public List<Client> getRandomClients(int nbClients) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < nbClients; i++) {
            Client c = this.getRandomClient();

            if(nbClients < this.clients.size())
            {
                while(!clients.contains(c)) {
                    c = this.getRandomClient();
                }
            }

            clients.add(c);
        }
        return clients;
    }

    public List<Client> getRandomClients(int nbClients, int min, int range) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < nbClients; i++) {
            Client c = this.getRandomClient(min, range);

            if(nbClients < this.clients.size())
            {
                while(!clients.contains(c)) {
                    c = this.getRandomClient(min, range);
                }
            }

            clients.add(c);
        }
        return clients;
    }

    public void addClient(Client client) {
        this.clients.add(client);
        this.quantite += client.getQuantité();
    }

    public void switchClient(Client c1, Client c2)
    {
        int id1 = this.clients.indexOf(c1), id2 = this.clients.indexOf(c2);
        this.clients.set(id1, c2);
        this.clients.set(id2, c1);
    }

    public void exchangeClients()
    {
        Random rand  = new Random();
        Client client1 = this.clients.get(rand.nextInt(1, this.clients.size() - 1));
        int index1 = this.clients.indexOf(client1);
        int index2;
        Client client2;
        do {
            index2 = this.clients.size() - 1;
            client2 = this.clients.get(rand.nextInt(1, index2));
        } while (Math.abs(index1 - this.clients.indexOf(client1)) > 1);

        this.getClients().set(index1, client2);
        this.getClients().set(index2, client1);
    }

    public void twoOpts()
    {
        List<Client> randClients = this.getRandomClients(2, 1, this.clients.size() - 3);
        int indStart = this.clients.indexOf(randClients.get(0)), indEnd = this.clients.indexOf(randClients.get(1));
//        Client c12 = this.clients.get(index1 + 1), c22 = this.clients.get(index2 + 1);
//        Client[] road1 = {randClients.get(0), c12}, road2 = {randClients.get(1), c22};
        Collections.reverse(this.clients.subList(indStart, indEnd));
    }

    public void relocate() {
        Client client = this.getRandomClient();
        int indexCli = this.clients.indexOf(client);
        int indexNext = indexCli;
        while(indexNext == indexCli) {
            Client client2 = this.getRandomClient();
            indexNext = this.clients.indexOf(client2);
        }

        this.clients.remove(client);
        this.clients.add(indexNext, client);
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

    public List<Client> getSegment(int start, int size)
    {
        return this.clients.subList(start, start+size);
    }

    public void updateClient(int index, Client c)
    {
        this.clients.set(index, c);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
