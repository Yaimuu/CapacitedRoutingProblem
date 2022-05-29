package Model.MetaHeuristcs;

import Model.Course;
import Model.Neighborhood.Neighborhood;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RecuitSimule extends MetaHeuristic{


    public RecuitSimule(Course course) {
        super(course);
    }

    public Course run(float t0) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Random rand = new Random();
        int i = 0;
        Course x0 = null, xMin = null, xi = null;
        try{
            x0 = (Course) this.solution.clone();
            xMin = (Course) x0.clone();
            xi = (Course) x0.clone();

            float fMin = x0.computeFitness();
            Neighborhood n;
            float mu = rand.nextFloat(0,0.999f), tk = t0;
            for(int k=0; k < t0; k++)
            {
                for (int l=1; l < tk; l++)
                {
                    n = new Neighborhood(xi);
                    n.useMethod();
                    float deltaFitness = n.getFitness() - xi.computeFitness();

                    if(deltaFitness < 0)
                    {
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
                            xMin = (Course) xi.clone();
                        }
                    }
                }
                tk = tk * mu;
            }

        }catch (Exception e) {

        }

        return xMin;
    }
}
