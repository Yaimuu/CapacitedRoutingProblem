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
        this.quantite = computeQuantite();
        return this.fitness;
    }

    public int computeQuantite()
    {
        int _quantite = 0;

        for (Client c : this.clients) {
            _quantite += c.quantite;
        }

        return _quantite;
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
        int id = this.clients.size() != 0 && client.getNumClient() != 0 ? this.clients.size()-1 : 0;
        return addClient(id, client);
    }

    public boolean addClient(int id, Client client) {
        if(this.quantite + client.getQuantite() <= this.maxCapacity)
        {
            this.clients.add(id, client);
//            this.quantite += client.getQuantite();
            this.quantite = this.computeQuantite();
            this.fitness = computeFitness();
            return true;
        }
        return false;
    }

    public boolean addClients(List<Client> clients)
    {
        int id = this.clients.size() - 1;

        int quantityToAdd = 0;
        for (Client c : clients) {
            quantityToAdd += c.getQuantite();
        }

        if(this.quantite + quantityToAdd <= this.maxCapacity)
        {
            this.clients.addAll(id, clients);
//            this.quantite += quantityToAdd;
            this.quantite = this.computeQuantite();
//        this.fitness += computeFitness(id, clients.size());
            this.fitness = computeFitness();
            return true;
        }
        return false;
    }

    public void removeClient(int index)
    {
        removeClient(this.clients.get(index));
    }

    public void removeClient(Client client) {
        if(this.clients.contains(client))
        {
//            this.fitness -= computeFitness(client, this.clients.get(this.clients.size()));
            this.fitness = computeFitness();
            this.quantite = this.computeQuantite();
//            this.quantite -= client.getQuantite();
            this.clients.remove(client);
        }
    }

    public void removeClients(List<Client> clients)
    {
        if(Collections.indexOfSubList(this.clients , clients) != -1)
        {
            this.clients.removeAll(clients);
            this.quantite = this.computeQuantite();
//            for (Client c : clients) {
//                this.quantite -= c.getQuantite();
//            }

//        this.fitness -= computeFitness(id, clients.size());
            this.fitness = computeFitness();
        }

    }

    /**
     * Echange de 2 clients voulu au sein d'une m??me tourn??e
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

    public void updateQuantite() {
        this.quantite = this.computeQuantite();
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
        String res = quantite > maxCapacity ? Settings.ANSI_RED : "";
        res += "Truck{" +
                "maxCapacity=" + maxCapacity +
                ", clients=" + clients +
                ", truckNum=" + truckNum +
                ", quantite=" + quantite +
                ", fitness=" + fitness +
                "}\n" + Settings.ANSI_RESET;
        return res;
    }
}
