package Model;

import Model.MetaHeuristcs.MetaHeuristic;
import Model.MetaHeuristcs.RecuitSimule;
import Model.Neighborhood.Neighborhood;
import View.CourseView;

import java.util.*;

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
            System.out.print(t);
        }

        return this;
    }

    public void nextStep()
    {
//        Neighborhood test = new Neighborhood(this);
//        test.relocate();
//        test.switchClientsFromTwoTrucks();
//        test.twoOpts();

//        test.addClientToOtherTruck();
//        test.mergeTrucks();
//        test.exchangePartsOfTrucks();

//        this.generateCourse();

        List<List<String[]>> resultsGeneral = new ArrayList<>();


        try {
            for (Map.Entry<String, MetaHeuristic> heuristicEntry : Settings.metaHeuristicMap.entrySet())
            {
                MetaHeuristic heuristic = heuristicEntry.getValue();

                List<String[]> resultsHeuristic = new ArrayList<>();
                String[] head = new String[] { "Fichier", "Fitness de départ", "Fitness finale", "Nb Trucks", "Temps d'exécution (en ms)", "Nb clients" };
                resultsHeuristic.add(head);

                for (String file : CourseFileManager.getAllFiles())
                {
                    Course course = this.generateCourse(file);
                    heuristic.setSolution(course);

                    float meanFitness = 0, meanTrucks = 0, meanExecTime = 0;
                    int nbExec = 10;

                    System.out.println("Run file : " + file + " " + nbExec + " times !");

                    for(int i = 0; i < nbExec; i++)
                    {
                        long startTime = System.nanoTime();
                        Course result = heuristic.run();
                        long elapsedTime = System.nanoTime() - startTime;

                        meanFitness += result.computeFitness();
                        meanTrucks += result.getTrucks().size();
                        meanExecTime += (float) elapsedTime/1000000f;
                    }
                    meanFitness = meanFitness / nbExec;
                    meanTrucks = meanTrucks / nbExec;
                    meanExecTime = meanExecTime / nbExec;

                    String[] resultLine = new String[] { file,
                            Float.toString(course.computeFitness()),
                            Float.toString(meanFitness),
                            Float.toString(meanTrucks),
                            Float.toString(meanExecTime),
                            String.valueOf(course.getAllClients().size())};
                    resultsHeuristic.add(resultLine);

                    System.out.println(Arrays.toString(resultLine));

                }
                Settings.exportStatsResults(resultsHeuristic, heuristic.getName().toString() + "_2");
                resultsGeneral.add(resultsHeuristic);
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        this.updateView();
    }

    public void run() {
        reset();
//        RecuitSimule recuit = new RecuitSimule(this);
        try {
            Course metaSolution = Settings.currentHeuristic.run();
//            this.courseView.setCourseUsed((Course) metaSolution.clone());
            Settings.updateCourse((Course) metaSolution.clone());
            System.out.println(metaSolution);
//            System.out.println(metaSolution.computeFitness());
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
            allClient.remove(truck.getClients().get(0));
            allClient.remove(truck.getClients().get(truck.getClients().size()-1));
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