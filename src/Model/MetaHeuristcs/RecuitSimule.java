package Model.MetaHeuristcs;

import Model.Course;
import Model.Neighborhood.Neighborhood;
import Model.Settings;
import Model.Solution.Solution;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RecuitSimule extends MetaHeuristic{

    public RecuitSimule() {
        super();
        this.name = MetaheuristicName.RECUIT;
    }

    public RecuitSimule(Course course) {
        super(course);
        this.name = MetaheuristicName.RECUIT;
    }

    public Course run() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println("Run " + this.name);
        System.out.println(Settings.getRecuitSettings());
        return this.run(Settings.getRecuitSettings().get("T0").get(0),
                Settings.getRecuitSettings().get("N1").get(0).intValue(),
                Settings.getRecuitSettings().get("N2").get(0).intValue(),
                Settings.getRecuitSettings().get("Mu").get(0));
    }

    public Course run(float t0, int n1, int n2, float mu) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Random rand = new Random();
        int i = 0;
        Course x0 = null, xMin = null, xi = null;
        try{
            x0 = (Course) this.solution.clone();
            xMin = (Course) x0.clone();
            xi = (Course) x0.clone();

            float fMin = x0.computeFitness();
            Neighborhood n;
            float tk = t0;
            for(int k=0; k < n1; k++)
            {
                for (int l=1; l < n2; l++)
                {
                    n = new Neighborhood(xi);
                    n.useMethod();
//                    System.out.println("Nouvelle fitness : " + n.getFitness() + " - Ancienne fitness :" + xi.computeFitness());
                    float deltaFitness = n.getFitness() - xi.computeFitness();
                    if(deltaFitness < 0)
                    {
//                        System.out.println("Solution meilleure ! : " + n.getFitness());
                        xi = (Course) n.getSolution().clone();
                        if(xi.computeFitness() < fMin) {
                            xMin = (Course) xi.clone();
                            fMin = xi.computeFitness();
                        }
                    }
                    else {
                        float p = rand.nextFloat(0,1);
                        if(p <= Math.exp(deltaFitness / tk))
                        {
//                            System.out.println("solution pas bonne ... mais ...: " + xi.computeFitness());
                            xMin = (Course) xi.clone();
                        }
//                        else
//                            System.out.println("pas entrée");
                    }
                }
                tk = tk * mu;
            }
        }catch (Exception e) {

        }
        return xMin;
    }

    public void nextStep()
    {

    }
}
