package Model;

import View.TruckView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Truck implements Cloneable {

    final int maxCapacity = 100;

    private List<Client> clients;
    private List<Double> distances;

    int truckNum, quantite;

    float fitness = 0f;

    public Truck(int num)
    {
        this.clients = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.truckNum = num;
        this.quantite = 0;
        this.fitness = computeFitness();
    }

    public Truck(int num, List<Client> clients)
    {
        this(num);
        this.clients = clients;

        this.fitness = computeFitness();
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Truck clone = (Truck) super.clone();
        List<Client> cloneClients = new ArrayList<>();
        for (Client c : this.clients) {
            cloneClients.add( (Client) c.clone() );
        }
        clone.setClients(cloneClients);
        return clone;
    }

    public float getTruckFitness() {
//        float fitness = 0;
//
//        for (int i = 1; i < this.clients.size(); i++) {
//            Client c1 = this.clients.get(i-1), c2 = this.clients.get(i);
//            float distance = c1.getDistance(c2);
//            fitness += distance;
//        }
        this.fitness = computeFitness();
        return this.fitness;
    }

    public float computeFitness()
    {
        float localFitness = 0;

        for (int i = 1; i < this.clients.size(); i++) {
            Client c1 = this.clients.get(i-1), c2 = this.clients.get(i);
            float distance = c1.getDistance(c2);
            localFitness += distance;
        }

        return localFitness;
    }

    public float computeFitness(Client start, Client end)
    {
        float localFitness;
        int i1 = this.clients.indexOf(start), i2 = this.clients.indexOf(end);

        localFitness = computeFitness(i1, i2);

        return localFitness;
    }

    public float computeFitness(int start, int end)
    {
        float localFitness = 0;

        if(Math.abs(start-end) == 1 && end != this.clients.size())
        {
            Client c1 = this.clients.get(start), c2 = this.clients.get(end);
            return c1.getDistance(c2);
        }
        for (int i = start; i < end-1; i++) {
            Client c1 = this.clients.get(i), c2 = this.clients.get(i+1);
            float distance = c1.getDistance(c2);
            localFitness += distance;
        }

        return localFitness;
    }

    public void updateView()
    {
        Course.getInstance().updateView();
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
                while(clients.contains(c)) {
                    c = this.getRandomClient(min, range);
                }
            }

            clients.add(c);
        }
        return clients;
    }

    public boolean addClient(Client client) {
        if(this.quantite + client.getQuantite() <= this.maxCapacity)
        {
            int id = this.clients.size() != 0 && client.getNumClient() != 0 ? this.clients.size()-1 : 0;
            this.clients.add(id, client);
            this.quantite += client.getQuantite();

//            this.fitness += computeFitness(id, id+1);
            return true;
        }
        return false;
    }

    public void addClients(List<Client> clients)
    {
        int id = this.clients.size() - 1;

        this.clients.addAll(id, clients);
        for (Client c : clients) {
            this.quantite += c.getQuantite();
        }

        this.fitness += computeFitness(id, clients.size());
    }

    public void removeClient(Client client) {
        if(this.clients.contains(client))
        {
//            this.fitness -= computeFitness(client, this.clients.get(this.clients.size()));
            this.quantite -= client.getQuantite();
            this.clients.remove(client);
        }
    }

    /**
     * Echange de 2 clients voulu au sein d'une même tournée
     * @param c1
     * @param c2
     */
    public void switchClient(Client c1, Client c2)
    {
        int id1 = this.clients.indexOf(c1), id2 = this.clients.indexOf(c2);
        this.clients.set(id1, c2);
        this.clients.set(id2, c1);
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

    public void updateFitness()
    {
        this.fitness = computeFitness();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "maxCapacity=" + maxCapacity +
                ", clients=" + clients +
                ", truckNum=" + truckNum +
                ", quantite=" + quantite +
                ", fitness=" + fitness +
                "}\n";
    }
}
