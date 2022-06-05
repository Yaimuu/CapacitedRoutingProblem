package Model.MetaHeuristcs;

import Model.Course;
import Model.Neighborhood.Neighborhood;
import Model.Update;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TabouMethod extends MetaHeuristic {

    public TabouMethod() {
        super();
        this.name = "Tabou";
    }

    public TabouMethod(Course course) {
        super(course);
        this.name = "Tabou";
    }

    public Course run(int maxIter) throws CloneNotSupportedException {
        Course x0 = null, xMin = null, xi = null;
        try{
            x0 = (Course) this.solution.clone();
            xMin = (Course) x0.clone();
            xi = (Course) x0.clone();
            float fMin = xMin.computeFitness();

            ArrayList<Update> tabouList = new ArrayList<>();

            for(int i = 0; i < maxIter; i++)
            {
                // Initialisation du voisinage
                Neighborhood neighborhood = new Neighborhood(xi);
                ArrayList<Update> neighborList = new ArrayList<>();

                // Récupération des meilleurs voisinages par méthodes
                neighborList = getBestNeighborhood(xi);

                // Récupérer un voisinage
                // Récupérer le meilleur de tous les voisinnage

                float fitness = Float.MAX_VALUE;
                Update updateToApply = null;
                for(Update neighbor : neighborList)
                {
                    if(neighbor.getFitness() < fitness)
                    {
                        updateToApply = neighbor;
                    }
                }

                // Comparer la fitness min actuelle avec la fitness nouvelle
                // Si la diff est > 0 alors on ajoute la modif a la liste tabou
                float deltaFitness = fMin - fitness;
                if(deltaFitness > 0)
                {
                    tabouList.add(updateToApply);
                }

                // Faire la modification avec le voisinage
                //TODO
                //neighborhood.useMethod(updateToApply.getNeighborhoodType());

                // Si la nouvelle solution devient la meilleure solution alors on modifie les variables
                if(fitness < fMin)
                {
                    fMin = neighborhood.getFitness();
                    xMin = (Course) neighborhood.getSolution().clone();
                }
            }
        }catch (Exception e) {

        }
        return xMin;
    }

    private ArrayList<Update> getBestNeighborhood(Course course) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //Liste des méthodes à utiliser
        ArrayList<String> methodsBest = new ArrayList<>();
        methodsBest.add("mergeTrucksBest");

        Neighborhood neighbor = new Neighborhood(course);

        ArrayList<Update> modifications = new ArrayList<>();
        // Parcours de toutes les méthodes et récupération des meilleurs voisinages. On les récupère sous forme de modifications.
        for (String method : methodsBest)
        {
           // result = neighbor.useMethod(method);
        }

        return modifications;
    }
}
