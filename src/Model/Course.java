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

        Settings.generateRandomPalette(100);
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

        return generateCourse("A3205.txt");
    }

    public Course generateCourse(String filename)
    {
        return generateCourse(CourseFileManager.readFile("./Ressources/Tests/" + filename));
    }

    public Course generateCourse(List<Client> clients)
    {
        this.trucks.clear();
        List<Integer> savedClients = new ArrayList<>();
        int idTruck = 0;
        Truck newTruck = new Truck(idTruck);
        this.addTruck(newTruck);
        newTruck.addClient(clients.get(0));

        for (int i = 1; i < clients.size(); i++) {
            Random rand = new Random();
            int clientId = rand.nextInt(1, clients.size());
            while (savedClients.contains(clientId))
            {
                clientId = rand.nextInt(1, clients.size());
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

        for (Truck t:
             this.trucks) {
            System.out.println(t);
        }

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
        this.updateView();
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
            if(truck1.getClients().size() <= 2)
                this.trucks.remove(truck1);
        }

//        System.out.println("truck1");
//        System.out.println(truck1.clients.toString());
//        System.out.println("truck2");
//        System.out.println(truck2.clients.toString());
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

    /***
     * Echange de morceau de tournée entre 2 camions
     */
    public void exchangePartsOfTrucks() {
        Random rand = new Random();
        List<Truck> trucks = new ArrayList<>();
        trucks.add(this.trucks.get(rand.nextInt(this.trucks.size())));
        trucks.add(this.trucks.get(rand.nextInt(this.trucks.size())));

        List<Integer> numberOfClients = new ArrayList<>();
        numberOfClients.add(rand.nextInt(trucks.get(0).getClients().size() - 2));
        numberOfClients.add(rand.nextInt(trucks.get(1).getClients().size() - 2));

        List<Integer> startIndex = new ArrayList<>();
        startIndex.add(rand.nextInt(trucks.get(0).getClients().size() - 2));
        startIndex.add(rand.nextInt(trucks.get(1).getClients().size() - 2));

        while (numberOfClients.get(0) > startIndex.get(0) || numberOfClients.get(1) > startIndex.get(1)) {
            startIndex.set(0, rand.nextInt(trucks.get(0).getClients().size() - 2));
            startIndex.set(1, rand.nextInt(trucks.get(1).getClients().size() - 2));
        }

        List<Client> clients1 = new ArrayList<>();
        List<Client> clients2 = new ArrayList<>();
        int capacite1 = 0, capacite2 = 0;

        for (int i = 0; i < 2; i++) {
            for (int k = startIndex.get(i); k < numberOfClients.get(i); k++) {
                if (i == 0) {
                    clients1.add(trucks.get(i).clients.get(k));
                    capacite1 += trucks.get(i).clients.get(k).getQuantite();
                } else {
                    clients2.add(trucks.get(i).clients.get(k));
                    capacite2 += trucks.get(i).clients.get(k).getQuantite();
                }
            }


            if (trucks.get(0).getQuantite() + capacite1 < trucks.get(0).getMaxCapacity() && trucks.get(1).getQuantite() + capacite2 < trucks.get(1).getMaxCapacity()) {
                trucks.get(0).clients.removeAll(clients1);
                trucks.get(0).clients.addAll(clients1);

                trucks.get(1).clients.removeAll(clients2);
                trucks.get(1).clients.addAll(clients2);
            }
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

    public void updateView()
    {
        this.courseView.updateTrucks();
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
