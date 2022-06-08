

package Model.Neighborhood;

import Model.*;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

enum NeighborhoodTabouType {
    MERGE_CLIENTS_BEST("mergeTrucksBest"),
//    INVERT_CLIENTS_1TRUCK_BEST("exchangeClientsBest"),
//    MOVE_CLIENT_BEST("addClientToOtherTruckBest"),
    INVERT_CLIENTS_2TRUCKS_BEST("switchClientsFromTwoTrucksBest"),
    ;

    private final String text;

    /**
     * @param text
     */
    NeighborhoodTabouType(final String text) {
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

public class NeighborhoodTabou extends Neighborhood {

    public NeighborhoodTabou() {
        super();
    }

    public NeighborhoodTabou(Course course) {
        super(course);
    }

    public Update useMethod(String methodName, List<Update> tabouList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(Settings.DEBUG)
            System.out.println("Method used : " + methodName);

        Method method = this.getClass().getMethod(methodName, tabouList.getClass());
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

    public void mergeTrucks(Update updateToApply)
    {
//        System.out.println("mergeTrucks Tabou");
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
     * Retourne le meilleur intra-échange de clients
     */
    public Update exchangeClientsBest(ArrayList<Update> updates)
    {
        float resultFitness = Float.MAX_VALUE;
        Truck bestTruck = null;
        Client bestClient1 = null;
        Client bestClient2 = null;

        for(Truck truck : this.getTrucks())
        {
            for(Client client1 : truck.getClients())
            {
                for(Client client2 : truck.getClients())
                {
                    if(client1 == client2)
                    {
                        continue;
                    }
                    else {
                        for (Update update : updates) {
                            if (update.getTrucks().get(0) == truck && update.getClients().get(1) == client1 && update.getClients().get(0) == client2)
                                continue;
                        }

                        truck.switchClient(client1, client2);
                        float newFitness = this.getFitness();
                        if (newFitness < resultFitness) {
                            resultFitness = newFitness;
                            bestClient1 = client1;
                            bestClient2 = client2;
                            bestTruck = truck;
                        }
                        truck.switchClient(client2, client1);
                    }
                }
            }
        }
        ArrayList<Truck> trucks = new ArrayList<>();
        trucks.add(bestTruck);
        ArrayList<Client> clients = new ArrayList<>();
        clients.add(bestClient1);
        clients.add(bestClient2);
        Update modification = new Update(this, "exchangeClients", trucks, clients, resultFitness);
        return modification;
    }

    /***
     *
     * Méthode pour faire l'échange de clients intra à partir d'une modification
     */
    public void exchangeClients(Update update)
    {
        Truck truck = update.getTrucks().get(0);
        Client client1 = update.getClients().get(0);
        Client client2 = update.getClients().get(1);
        truck.switchClient(client1, client2);
    }

    /**
     * Inversion entre 1 client de 2 tournées différentes
     * @param updates
     * @return
     */
    public Update switchClientsFromTwoTrucksBest(ArrayList<Update> updates)
    {
        float resultFitness = Float.MAX_VALUE;
        Truck bestTruck1 = null;
        Truck bestTruck2 = null;
        Client bestClient1 = null;
        Client bestClient2 = null;
        for (Truck truck1 : this.getTrucks())
        {
            for (Truck truck2 : this.getTrucks())
            {
                for (Client client1 : truck1.getClients())
                {
                    for (Client client2 : truck2.getClients())
                    {
                        if(truck1 == truck2 || client1 == client2)
                            continue;
                        for(Update update : updates)
                        {
                            if(update.getTrucks().get(0) == truck1 && update.getTrucks().get(1) == truck2 && update.getClients().get(0) == client2 && update.getClients().get(1) == client1)
                                continue;
                        }

                        if(truck1.getQuantite() - client1.getQuantite() + client2.getQuantite() <= truck1.getMaxCapacity() &&
                                truck2.getQuantite() - client2.getQuantite() + client1.getQuantite() <= truck2.getMaxCapacity())
                        {
                            truck1.updateClient(truck1.getClients().indexOf(client1), client2);
                            truck2.updateClient(truck2.getClients().indexOf(client2), client1);
                            float newFitness = this.getFitness();
                            if(newFitness < resultFitness)
                            {
                                bestClient1 = client1;
                                bestClient2 = client2;
                                bestTruck1 = truck1;
                                bestTruck2 = truck2;
                                resultFitness = newFitness;
                            }
                            truck1.updateClient(truck1.getClients().indexOf(client2), client1);
                            truck2.updateClient(truck2.getClients().indexOf(client1), client2);
                        }
                    }
                }
            }
        }
        ArrayList<Truck> trucks = new ArrayList<>();
        ArrayList<Client> clients = new ArrayList<>();
        trucks.add(bestTruck1);
        trucks.add(bestTruck2);
        clients.add(bestClient1);
        clients.add(bestClient2);
        Update modification = new Update(this, "switchClientsFromTwoTrucks", trucks, clients, resultFitness);
        return modification;
    }

    /**
     * Inversion entre 1 client de 2 tournées différentes
     */
    public void switchClientsFromTwoTrucks(Update update) {
        Truck truck1 = update.getTrucks().get(0);
        Truck truck2 = update.getTrucks().get(1);

        Client client1 = update.getClients().get(0);
        Client client2 = update.getClients().get(1);

        truck1.updateClient(truck1.getClients().indexOf(client1), client2);
        truck2.updateClient(truck2.getClients().indexOf(client2), client1);
    }

    /**
     *
     * @param updates
     * @return
     */
    public Update addClientToOtherTruckBest(ArrayList<Update> updates) {
        float resultFitness = Float.MAX_VALUE;
        Client bestClient = null;
        Truck bestTruck1 = null, bestTruck2 = null;
        for (Truck t1 : this.getTrucks())
        {
            for (Truck t2 : this.getTrucks())
            {
                for(Client c : t1.getClients())
                {
                    if(t1 == t2)
                        continue;
                    for (Update update : updates)
                    {
                        if(update.getTrucks().get(0) == t1 && update.getTrucks().get(1) == t2 && update.getClients().get(0) == c)
                            continue;
                    }

                    int idClient = t1.getClients().indexOf(c);

                    if(t2.addClient(c))
                    {
                        t1.removeClient(c);
                    }
                    float newFitness = this.getFitness();
                    if (newFitness < resultFitness)
                    {
                        resultFitness = newFitness;
                        bestClient = c;
                        bestTruck1 = t1;
                        bestTruck2 = t1;
                    }
                    t2.removeClient(c);
                    t1.addClient(c);

//                    if(truck2.addClient(c1))
//                    {
//                        truck1.removeClient(c1);
//                        if(truck1.getClients().size() <= 2)
//                            this.getTrucks().remove(truck1);
//                    }
                }
            }
        }
        ArrayList<Truck> trucks = new ArrayList<>();
        trucks.add(bestTruck1);
        trucks.add(bestTruck2);
        ArrayList<Client> clients = new ArrayList<>();
        clients.add(bestClient);
        Update update = new Update(this, "addClientToOtherTruck", trucks, null, resultFitness);
        return update;
    }

    public void addClientToOtherTruck(Update updateToApply)
    {
        Truck truck1 = updateToApply.getTrucks().get(0);
        Truck truck2 = updateToApply.getTrucks().get(1);
        truck1.getTruckFitness();
        truck2.getTruckFitness();
        Client c1 = updateToApply.getClients().get(0);
        if(truck2.addClient(c1))
        {
            truck1.removeClient(c1);
            if(truck1.getClients().size() <= 2)
                this.getTrucks().remove(truck1);
        }
    }

    /**
     *
     * @return
     */
    public List<String> getAllTabouMethods() {
        return Arrays.stream(NeighborhoodTabouType.values()).map(type -> type.toString()).collect(Collectors.toList());
    }
}
