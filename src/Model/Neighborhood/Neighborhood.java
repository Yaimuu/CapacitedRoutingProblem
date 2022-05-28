package Model.Neighborhood;

import Model.Client;
import Model.Course;
import Model.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

enum NeighborhoodType {

}

public class Neighborhood {

    private Course course;


    Neighborhood() {

    }

    Neighborhood(Course course) {
        this.course = course;
    }

    /**
     * Echange de clients entre 2 tournées
     */
    public void switchClientsFromTwoTrucks() {
        Random rand = new Random();
        Truck truck1 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));
        Truck truck2 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));

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
        Truck truck1 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));
        Truck truck2 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));
        Client c1 = truck1.getRandomClient();
        if(truck2.addClient(c1))
        {
            truck1.removeClient(c1);
            if(truck1.getClients().size() <= 2)
                this.getTrucks().remove(truck1);
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
        Truck truck1 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));
        Truck truck2 = this.getTrucks().get(rand.nextInt(this.getTrucks().size()));

        System.out.println(truck1.toString());
        System.out.println(truck2.toString());

        if(truck1.getQuantite() + truck2.getQuantite() <= truck2.getMaxCapacity())
        {
            truck2.addClients(truck1.getClients().subList(1, truck1.getClients().size() - 2));
            this.getTrucks().remove(truck1);
        }

        System.out.println(truck2.toString());
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
            System.out.println("Avant : ");
            for (int i = 0; i < 2; i++)
            {
                System.out.println("Numéro de camion : " +trucks.get(i).getTruckNum() + " - liste clients : " + trucks.get(i).getClients());
            }
            Client c1 = trucks.get(0).getClients().get(trucks.get(0).getClients().size()-1);
            trucks.get(0).getClients().removeAll(clients1);
            trucks.get(0).getClients().remove(trucks.get(0).getClients().size()-1);
            trucks.get(0).getClients().addAll(clients2);
            trucks.get(0).getClients().add(c1);

            Client c2 = trucks.get(1).getClients().get(trucks.get(1).getClients().size()-1);
            trucks.get(1).getClients().removeAll(clients2);
            trucks.get(1).getClients().remove(trucks.get(1).getClients().size()-1);
            trucks.get(1).getClients().addAll(clients1);
            trucks.get(1).getClients().add(c2);

            System.out.println("Après : ");
            for (int j = 0; j < 2; j++)
            {
                System.out.println("Numéro de camion : " +trucks.get(j).getTruckNum() + " - liste clients : " + trucks.get(j).getClients());
            }
        }
    }

    /**
     * Echange de 2 clients voulu au sein d'une même tournée
     * @param c1
     * @param c2
     */
    public void switchClient(Client c1, Client c2, Truck truck)
    {
        int id1 = truck.getClients().indexOf(c1), id2 = truck.getClients().indexOf(c2);
        truck.getClients().set(id1, c2);
        truck.getClients().set(id2, c1);
    }

    /**
     * Echange de 2 clients aléatoire au sein d'une même tournée
     */
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

        switchClient(client1, client2, truck);
    }

    public void twoOpts(Truck truck)
    {
        Random rand = new Random();
        int indStart = rand.nextInt(1, truck.getClients().size() - 3), indEnd = rand.nextInt(indStart + 2, truck.getClients().size() - 1);

        System.out.println("start : " + indStart + " - end : " + indEnd);
        Collections.reverse(truck.getClients().subList(indStart, indEnd));
    }

    /**
     * Changement de l'ordre de passage d'un client dans une tournée
     */
    public void relocate(Truck truck) {
        Client client = truck.getRandomClient();
        int indexCli = truck.getClients().indexOf(client);
        int indexNext = indexCli;
        while(indexNext == indexCli) {
            Client client2 = truck.getRandomClient();
            indexNext = truck.getClients().indexOf(client2);
        }

        truck.getClients().remove(client);
        truck.getClients().add(indexNext, client);
    }

    public List<Truck> getTrucks()
    {
        return this.course.getTrucks();
    }
}