package Model;

import Model.MetaHeuristcs.MetaHeuristic;
import View.CourseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Course {

    private static Course instance;

    List<Truck> trucks;
    MetaHeuristic metaHeuristic;

    CourseView courseView;

    public Course()
    {
        this.trucks = new ArrayList<>();
    }

    public static Course getInstance()
    {
        if(instance == null)
        {
            instance = new Course();
            instance.generateCourse();
        }
        return instance;
    }

    public Course generateCourse()
    {
        Course course = new Course();

        return generateCourse(CourseFileManager.readFile("./Ressources/Tests/A3205.txt"));
    }

    public Course generateCourse(List<Client> clients)
    {
        this.trucks.clear();
        List<Integer> savedClients = new ArrayList<>();
        int idTruck = 0;
        Truck newTruck = new Truck(idTruck);
        this.addTruck(newTruck);
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
                this.addTruck(newTruck);
            }

            newTruck.addClient(client);
        }
        newTruck.addClient(clients.get(0));

        System.out.println("Nb trucks : " + this.trucks.size());

        return this;
    }

    public void nextStep()
    {
//        mergeTrucks();
//        switchClientsFromTwoTrucks();
//        this.trucks.get(0).twoOpts();
        addClientToOtherTruck();
//        this.trucks.get(0).twoOpts();
//        this.generateCourse();
        this.courseView.updateTrucks();
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

    /**
     * Echange de clients entre 2 tournées
     */
    public void switchClientsFromTwoTrucks() {
        Random rand = new Random();
        Truck truck1 = this.trucks.get(rand.nextInt(this.trucks.size()));
        Truck truck2 = this.trucks.get(rand.nextInt(this.trucks.size()));

        Client c1 = truck1.getRandomClient(), c2 = truck2.getRandomClient();

        truck1.updateClient(truck1.getClients().indexOf(c1), c2);
        truck2.updateClient(truck2.getClients().indexOf(c2), c1);
    }

    /**
     * On prend un client d'une tournée et on le met dans une autre tournée
     */
    public void addClientToOtherTruck()
    {
        Random rand = new Random();
        Truck truck1 = this.trucks.get(rand.nextInt(this.trucks.size()));
        Truck truck2 = this.trucks.get(rand.nextInt(this.trucks.size()));
        Client c1 = truck1.getRandomClient();
        if(truck2.addClient(c1))
        {
            truck1.removeClient(c1);
        }
    }

    /**
     * Fusion de 2 tournées
     */
    public void mergeTrucks()
    {
        Random rand = new Random();
        Truck truck1 = this.trucks.get(rand.nextInt(this.trucks.size()));
        Truck truck2 = this.trucks.get(rand.nextInt(this.trucks.size()));

        if(truck1.getQuantite() + truck2.getQuantite() <= truck2.getMaxCapacity())
        {
            truck2.addClients(truck1.getClients().subList(1, truck1.getClients().size() - 2));
            this.getTrucks().remove(truck1);
        }

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

    public CourseView getCourseView() {
        return courseView;
    }

    public void setCourseView(CourseView courseView) {
        this.courseView = courseView;
    }
}
