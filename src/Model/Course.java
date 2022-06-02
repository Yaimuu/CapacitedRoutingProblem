package Model;

import Model.MetaHeuristcs.MetaHeuristic;
import Model.MetaHeuristcs.RecuitSimule;
import Model.Neighborhood.Neighborhood;
import View.CourseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Course implements Cloneable {

    private static Course instance;

    List<Truck> trucks;
    MetaHeuristic metaHeuristic;

    CourseView courseView;

    public Course()
    {
        this.trucks = new ArrayList<>();

        Settings.generateRandomPalette(150);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Course clone = (Course) super.clone();
        List<Truck> cloneTrucks = new ArrayList<>();
        for (Truck t : this.trucks) {
            cloneTrucks.add( (Truck) t.clone() );
        }
        clone.setTrucks(cloneTrucks);
        return clone;
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

        return generateCourse(Settings.curentFile);
    }

    public Course generateCourse(String filename)
    {
        return generateCourse(CourseFileManager.readFile(Settings.MAP_DIRECTORY + filename));
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
            if( newTruck.getQuantite() > newTruck.getMaxCapacity()/8 )
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
        Neighborhood test = new Neighborhood(this);
//        test.relocate();
//        test.switchClientsFromTwoTrucks();
//        test.twoOpts();

//        test.addClientToOtherTruck();
//        test.mergeTrucks();
//        test.exchangePartsOfTrucks();

//        this.generateCourse();

        try {
//            this.trucks = ((Course) test.getSolution().clone()).getTrucks();
//
//            this.courseView.setCourseUsed((Course) test.getSolution().clone());
//            System.out.println(test.getSolution());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        this.updateView();
    }

    public void run() {
//        RecuitSimule recuit = new RecuitSimule(this);
        try {
            Course metaSolution = Settings.currentHeuristic.run();
//            this.courseView.setCourseUsed((Course) metaSolution.clone());
            Settings.updateCourse((Course) metaSolution.clone());
            System.out.println(metaSolution);
            System.out.println(metaSolution.computeFitness());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        this.updateView();
    }

    public void reset() {
        this.generateCourse();

        try {
//            this.courseView.setCourseUsed((Course) this.clone());
            Settings.updateCourse((Course) this.clone());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

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
        this.courseView.updateView();
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

    public Truck getRandomTruck() {
        Random rand  = new Random();
        return this.trucks.get(rand.nextInt(1, this.trucks.size() - 1));
    }

    public Truck getRandomTruck(int min, int range) {
        Random rand  = new Random();
        return this.trucks.get(rand.nextInt(min, min + range));
    }

    public List<Truck> getRandomTrucks(int nb, int min, int range) {
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            Truck t = this.getRandomTruck(min, range);

            if(nb < this.trucks.size())
            {
                while(trucks.contains(t)) {
                    t = this.getRandomTruck(min, range);
                }
            }

            trucks.add(t);
        }
        return trucks;
    }

    public CourseView getCourseView() {
        return courseView;
    }

    public void setCourseView(CourseView courseView) {
        this.courseView = courseView;
    }

    @Override
    public String toString() {
        return "Course{" +
                "trucks=" + trucks +
                ", Fitness=" + this.computeFitness() +
                ", NbTrucks=" + this.trucks.size() +
                '}';
    }
}