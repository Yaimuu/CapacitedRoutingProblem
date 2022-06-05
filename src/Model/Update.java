package Model;

import Model.Neighborhood.Neighborhood;

import java.util.ArrayList;

public class Update {

    private Neighborhood neighborhood;

    private String neighborhoodType;

    private ArrayList<Truck> trucks;

    private ArrayList<Client> clients;

    private float fitness;

    public Update(Neighborhood n_, String nt_, ArrayList<Truck> trucks, ArrayList<Client> clients, float fitness)
    {
        this.setClients(clients);
        this.setTrucks(trucks);
        this.setNeighborhood(n_);
        this.setNeighborhoodType(nt_);
        this.setFitness(fitness);
    }


    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(ArrayList<Truck> trucks) {
        this.trucks = trucks;
    }

    public String getNeighborhoodType() {
        return neighborhoodType;
    }

    public void setNeighborhoodType(String neighborhoodType) {
        this.neighborhoodType = neighborhoodType;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }
}
