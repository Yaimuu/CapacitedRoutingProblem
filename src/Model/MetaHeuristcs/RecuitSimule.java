package Model.MetaHeuristcs;

import Model.Course;
import Model.Neighborhood.Neighborhood;
import Model.Solution.Solution;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RecuitSimule extends MetaHeuristic{

    public RecuitSimule() {
        super();
        this.name = "Recuit Simulé";
    }

    public RecuitSimule(Course course) {
        super(course);
        this.name = "Recuit Simulé";
    }

    public Course run() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println("Run " + this.name);
        return this.run(200, 100, 100);
    }

    public Course run(float t0, int n1, int n2) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Random rand = new Random();
        int i = 0;
        Course x0 = null, xMin = null, xi = null;
        try{
            x0 = (Course) this.solution.clone();
            xMin = (Course) x0.clone();
            xi = (Course) x0.clone();

            float fMin = x0.computeFitness();
            Neighborhood n;
            float mu = (float) 0.99, tk = t0;
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
