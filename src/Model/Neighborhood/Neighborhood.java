package Model.Neighborhood;

import Model.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

enum NeighborhoodType {
    INVERT_CLIENTS_2TRUCKS("switchClientsFromTwoTrucks"),
    MOVE_CLIENT("addClientToOtherTruck"),
    MERGE_CLIENTS("mergeTrucks"),
    EXCHANGE_PART_TRUCKS("exchangePartsOfTrucks"),
    INVERT_CLIENTS_1TRUCK("exchangeClients"),
    TWO_OPTS("twoOpts"),
    RELOCATE("relocate")
    ;

    private final String text;

    /**
     * @param text
     */
    NeighborhoodType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}

public class Neighborhood {

    private Course course;
    private NeighborhoodType currentMethod;

    public Neighborhood() {
        setRandomMethod();
    }

    public Neighborhood(Course course) {
        this();
        try {
            this.course = (Course) course.clone();
        } catch (Exception e) {

        }
    }

    public void setRandomMethod() {
        int pick = new Random().nextInt(NeighborhoodType.values().length);
        this.currentMethod = NeighborhoodType.values()[pick];
    }

    public void useMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(Settings.DEBUG)
            System.out.println("Method used : " + currentMethod);
        Method method = this.getClass().getMethod(currentMethod.toString());
        method.invoke(this);
    }

    public Update useMethod(String methodName, List<Update> tabouList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(Settings.DEBUG)
            System.out.println("Method used : " + methodName);
        Method method = this.getClass().getMethod(methodName, tabouList.getClass());
        System.out.println(method);
        Update update = (Update) method.invoke(this, tabouList);
        return update;
    }

    public void useMethod(Update updateToApply) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(updateToApply != null)
        {
            if(Settings.DEBUG)
                System.out.println("Method used : " + updateToApply.getNeighborhoodType());
            Method method = this.getClass().getMethod(updateToApply.getNeighborhoodType(), updateToApply.getClass());
            method.invoke(this, updateToApply);
        }
    }

    /**
     * Inversion entre 1 client de 2 tournées différentes
     */
    public void switchClientsFromTwoTrucks() {
        Random rand = new Random();
        Truck truck1 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));
        Truck truck2 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));

        Client c1 = truck1.getRandomClient(), c2 = truck2.getRandomClient();

        if(truck1.getQuantite() - c1.getQuantite() + c2.getQuantite() <= truck1.getMaxCapacity() &&
                truck2.getQuantite() - c2.getQuantite() + c1.getQuantite() <= truck2.getMaxCapacity())
        {
            truck1.updateClient(truck1.getClients().indexOf(c1), c2);
            truck2.updateClient(truck2.getClients().indexOf(c2), c1);
        }
    }

    /**
     * On prend un client d'une tournée et on le met dans une autre tournée
     */
    public void addClientToOtherTruck()
    {
        Random rand = new Random();
        Truck truck1 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));
        Truck truck2 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));
        truck1.getTruckFitness();
        truck2.getTruckFitness();
        Client c1 = truck1.getRandomClient();
        if(truck2.addClient(c1))
        {
            truck1.removeClient(c1);
            if(truck1.getClients().size() <= 2)
                this.getTrucks().remove(truck1);
        }

        if(Settings.DEBUG)
        {
//            System.out.println("truck1");
//            System.out.println(truck1.clients.toString());
//            System.out.println("truck2");
//            System.out.println(truck2.clients.toString());
        }
    }

    /**
     * Fusion de 2 tournées
     */
    public void mergeTrucks()
    {
        Random rand = new Random();
        List<Truck> trucks = this.course.getRandomTrucks(2,0, this.getTrucks().size());
        Truck truck1 = trucks.get(0);
        Truck truck2 = trucks.get(1);
        truck1.getTruckFitness();
        truck2.getTruckFitness();
        if(truck1.getQuantite() + truck2.getQuantite() <= truck2.getMaxCapacity())
        {
            if(Settings.DEBUG)
            {
                System.out.println(truck1);
                System.out.println(truck2);
            }


            if(truck2.addClients(truck1.getClients().subList(1, truck1.getClients().size() - 1)))
                this.getTrucks().remove(truck1);

            if(Settings.DEBUG)
                System.out.println(truck2);
        }
    }

    public void mergeTrucks(Update updateToApply)
    {
        System.out.println("mergeTrucks Tabou");
        int id1 = this.getTrucks().indexOf(updateToApply.getTrucks().get(0)), id2 = this.getTrucks().indexOf(updateToApply.getTrucks().get(1));
        Truck t1 = null;
        Truck t2 = null;
        for(Truck t : this.getTrucks())
        {
            if(t.getTruckNum() == updateToApply.getTrucks().get(0).getTruckNum())
                t1 = t;
            if(t.getTruckNum() == updateToApply.getTrucks().get(1).getTruckNum())
                t2 = t;
        }

        t2.addClients(t1.getClients().subList(1, t1.getClients().size() - 1));
        this.getTrucks().remove(t1);
    }

    /***
     * Retourne la meilleure fusion de 2 tournées
     */
    public Update mergeTrucksBest(ArrayList<Update> updates)
    {
        float resultFitness = Float.MAX_VALUE;
        Truck bestTruck1 = null;
        Truck bestTruck2 = null;
        for(Truck truck1 : this.getTrucks())
        {
            for(Truck truck2 : this.getTrucks())
            {
                // Si la configuration fait partie de la liste tabou, alors on passe à la configuration suivante
                for (Update update : updates)
                {
                    if(update.getTrucks().get(0) == truck1 && update.getTrucks().get(1) == truck2 && update.getNeighborhoodType() == "mergeTrucksBest")
                        continue;
                }

                if (truck1 == truck2)
                    continue;
                else
                {
                    if(truck1.getQuantite() + truck2.getQuantite() <= truck2.getMaxCapacity())
                    {
                        if(Settings.DEBUG)
                            System.out.println("Résultat avant changement : " + this.getFitness());
                        truck2.addClients(truck1.getClients().subList(1, truck1.getClients().size() - 1));
                        float newFitness = this.getFitness() - truck1.getTruckFitness();
                        if (resultFitness > newFitness)
                        {
                            resultFitness = newFitness;
                            bestTruck1 = truck1;
                            bestTruck2 = truck2;
                        }
                        truck2.getClients().removeAll(truck1.getClients().subList(1, truck1.getClients().size() - 1));
                        if(Settings.DEBUG)
                            System.out.println("Résultat après changement : " + resultFitness);
                    }
                }
            }
        }
        ArrayList<Truck> trucks = new ArrayList<>();
        trucks.add(bestTruck1);
        trucks.add(bestTruck2);
        Update update = new Update(this, "mergeTrucks", trucks, null, resultFitness);
        return update;
    }


    /***
     * Echange de morceau de tournée entre 2 camions
     */
    public void exchangePartsOfTrucks() {
        Random rand = new Random();
        List<Truck> trucks = new ArrayList<>();
        trucks.add(this.getTrucks().get(rand.nextInt(this.getTrucks().size())));
        trucks.add(this.getTrucks().get(rand.nextInt(this.getTrucks().size())));

        while(trucks.get(0) == trucks.get(1))
        {
            trucks.set(1, this.getTrucks().get(rand.nextInt(this.getTrucks().size())));
        }

        List<Integer> endIndex = new ArrayList<>();
        endIndex.add(rand.nextInt(1,trucks.get(0).getClients().size() - 1));
        endIndex.add(rand.nextInt(1,trucks.get(1).getClients().size() - 1));

        List<Integer> startIndex = new ArrayList<>();
        startIndex.add(rand.nextInt(1,trucks.get(0).getClients().size() - 1));
        startIndex.add(rand.nextInt(1,trucks.get(1).getClients().size() - 1));

        while(startIndex.get(0) > endIndex.get(0) || startIndex.get(1) > endIndex.get(1))
        {
            endIndex.set(0, rand.nextInt(1,trucks.get(0).getClients().size() - 1));
            endIndex.set(1, rand.nextInt(1,trucks.get(1).getClients().size() - 1));
        }

        List<Client> clients1 = new ArrayList<>();
        List<Client> clients2 = new ArrayList<>();
        int capacite1 = 0, capacite2 = 0;

        for (int i = 0; i < 2; i++) {
            for (int k = startIndex.get(i); k <= endIndex.get(i); k++) {
                if (i == 0) {
                    clients1.add(trucks.get(i).getClients().get(k));
                    capacite1 += trucks.get(i).getClients().get(k).getQuantite();
                } else {
                    clients2.add(trucks.get(i).getClients().get(k));
                    capacite2 += trucks.get(i).getClients().get(k).getQuantite();
                }
            }
        }

        if (trucks.get(0).getQuantite() - capacite1 + capacite2 < trucks.get(0).getMaxCapacity() && trucks.get(1).getQuantite() - capacite2 + capacite1 < trucks.get(1).getMaxCapacity()) {
            if(Settings.DEBUG)
            {
                System.out.println("Avant : ");
                for (int i = 0; i < 2; i++)
                {
                    System.out.println("Numéro de camion : " +trucks.get(i).getTruckNum() + " - liste clients : " + trucks.get(i).getClients());
                }
            }

            Client c1 = trucks.get(0).getClients().get(trucks.get(0).getClients().size()-1);
            trucks.get(0).removeClients(clients1);
            trucks.get(0).getClients().remove(trucks.get(0).getClients().size()-1);
            trucks.get(0).getClients().addAll(clients2);
            trucks.get(0).getClients().add(c1);

            Client c2 = trucks.get(1).getClients().get(trucks.get(1).getClients().size()-1);
            trucks.get(1).removeClients(clients2);
            trucks.get(1).getClients().remove(trucks.get(1).getClients().size()-1);
            trucks.get(1).getClients().addAll(clients1);
            trucks.get(1).getClients().add(c2);

            if(Settings.DEBUG)
            {
                System.out.println("Après : ");
                for (int j = 0; j < 2; j++)
                {
                    System.out.println("Numéro de camion : " +trucks.get(j).getTruckNum() + " - liste clients : " + trucks.get(j).getClients());
                }
            }
        }
    }

    /**
     * Echange de 2 clients aléatoire au sein d'une même tournée
     */
    public void exchangeClients() {
        Truck truck = getRandomTruck();
        exchangeClients(truck);
    }

    public void exchangeClients(Truck truck)
    {
        Random rand  = new Random();

        Client client1 = truck.getClients().get(rand.nextInt(1, truck.getClients().size() - 1));
        int index1 = truck.getClients().indexOf(client1);
        int index2;
        Client client2;
        do {
            index2 = truck.getClients().size() - 1;
            client2 = truck.getClients().get(rand.nextInt(1, index2));
        } while (Math.abs(index1 - truck.getClients().indexOf(client1)) > 1);

        truck.switchClient(client1, client2);
    }

    /**
     *
     */
    public void twoOpts()
    {
        Truck truck = getRandomTruck();
        twoOpts(truck);
    }

    public void twoOpts(Truck truck)
    {
        Random rand = new Random();
        if(truck.getClients().size() >= 5)
        {
            int indStart = rand.nextInt(1, truck.getClients().size() - 3), indEnd = rand.nextInt(indStart + 2, truck.getClients().size() - 1);

            if(Settings.DEBUG)
                System.out.println("start : " + indStart + " - end : " + indEnd);

            Collections.reverse(truck.getClients().subList(indStart, indEnd));
        }
    }

    /**
     * Changement de l'ordre de passage d'un client dans une tournée
     */
    public void relocate() {
        Truck truck = getRandomTruck();
        relocate(truck);
    }

    public void relocate(Truck truck) {
        Client client = truck.getRandomClient();

        if(Settings.DEBUG)
            System.out.println(truck.getClients());

        int indexCli = truck.getClients().indexOf(client);
        int indexNext = indexCli;
        while(indexNext == indexCli) {
            if (truck.getClients().size() == 3)
                break;
            else
            {
                Client client2 = truck.getRandomClient();
                indexNext = truck.getClients().indexOf(client2);
            }
        }

        truck.getClients().remove(client);
//        truck.addClient(indexNext, client);
        truck.getClients().add(indexNext, client);

        if(Settings.DEBUG)
            System.out.println(truck.getClients());
    }

    public List<Truck> getTrucks()
    {
        return this.course.getTrucks();
    }

    public Truck getRandomTruck()
    {
        Random rand  = new Random();
        return this.course.getTrucks().get(rand.nextInt(this.course.getTrucks().size()));
    }

    public NeighborhoodType getCurrentMethod() {
        return currentMethod;
    }

    public float getFitness()
    {
        return this.course.computeFitness();
    }

    public Course getSolution()
    {
        return this.course;
    }

    public void setSolution(Course course)
    {
        this.course = course;
    }
}
