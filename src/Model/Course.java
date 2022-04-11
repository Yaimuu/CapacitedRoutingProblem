package Model;

import java.util.ArrayList;
import java.util.List;

public class Course {

    List<Truck> trucks;

    public Course()
    {
        this.trucks = new ArrayList<>();
    }

    public Course generateCourse()
    {
        Course course =new Course();

        return course;
    }

    public Course generateCourse(List<Client> clients)
    {
        Course course = new Course();

        int idTruck = 0;
        Truck newTruck = new Truck(idTruck);
        addTruck(newTruck);

        for (Client client : clients) {
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

        return course;
    }

    public List<Client> getAllClients()
    {
        List<Client> allClient = new ArrayList<>();

        for (Truck truck : this.trucks) {
            allClient.addAll(truck.getClients());
        }

        return allClient;
    }

    public float computeFitness()
    {
        float fitness = 0;
        for (Truck truck : this.trucks) {
            for (int i = 1; i < truck.getClients().size()-1; i++) {
                fitness += truck.getClients().get(i).getDistance(truck.getClients().get(i-1));
            }
        }
        return fitness;
    }

    public void recuitSimule()
    {

    }

    public void tabou()
    {

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
