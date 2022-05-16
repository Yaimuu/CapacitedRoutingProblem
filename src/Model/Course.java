package Model;

import Model.MetaHeuristcs.MetaHeuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Course {

    List<Truck> trucks;
    MetaHeuristic metaHeuristic;

    public Course()
    {
        this.trucks = new ArrayList<>();
    }

    public Course generateCourse()
    {
        Course course = new Course();

        return course;
    }

    public Course generateCourse(List<Client> clients)
    {
        Course course = new Course();
        List<Integer> savedClients = new ArrayList<>();
        int idTruck = 0;
        Truck newTruck = new Truck(idTruck);
        addTruck(newTruck);
        newTruck.addClient(clients.get(0));

        for (int i = 1; i < clients.size()-2; i++) {
            Random rand = new Random();
            int clientId = rand.nextInt(1, clients.size()-2);
            while (savedClients.contains(clientId))
            {
                clientId = rand.nextInt(1, clients.size()-2);
            }
            savedClients.add(clientId);
            Client client = clients.get(clientId);
            if( newTruck.getQuantite() > newTruck.getMaxCapacity()/2 )
            {
                idTruck++;
                newTruck.addClient(clients.get(0));
                newTruck = new Truck(idTruck);
                newTruck.addClient(clients.get(0));
                addTruck(newTruck);
            }

            newTruck.addClient(client);
        }
        newTruck.addClient(clients.get(0));

        System.out.println("Nb trucks : " + this.trucks.size());

        return course;
    }

    public float computeFitness()
    {
        float fitness = 0;
        for (Truck truck : this.trucks)
        {
            fitness += truck.getTruckFitness();
        }
        return fitness;
    }

    public void switchTwoTrucks() {
        Random rand = new Random();
        Truck truck1 = this.trucks.get(rand.nextInt(this.trucks.size()));
        Truck truck2 = this.trucks.get(rand.nextInt(this.trucks.size()));

        Client c1 = truck1.getRandomClient(), c2 = truck2.getRandomClient();

        truck1.updateClient(truck1.getClients().indexOf(c1), c2);
        truck2.updateClient(truck1.getClients().indexOf(c2), c1);
    }

    public List<Client> getAllClients()
    {
        List<Client> allClient = new ArrayList<>();

        for (Truck truck : this.trucks) {
            allClient.addAll(truck.getClients());
        }

        return allClient;
    }

    public void addTruck(Truck truck)
    {
        this.trucks.add(truck);
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }
}
