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
        }catch (Exception e) {

        }
        float fMin = x0.computeFitness();
        Neighborhood n;
        float mu = rand.nextFloat(0,0.999f), tk = t0;
        for(int k=0; k < t0; k++)
        {
            for (int l=1; l < tk; l++)
            {
                n = new Neighborhood(x0);
                n.useMethod();
                float deltaFitness = n.getFitness() - xi.computeFitness();
                n.getSolution();
                if(deltaFitness < 0)
                {
                    xi = n.getSolution();
                    if(xi.computeFitness() < fMin) {
                        xMin = xi;
                        fMin = xi.computeFitness();
                    }
                }
                else {

                    float p = rand.nextFloat(0,1);
                    if(p <= Math.exp(deltaFitness / tk))
                    {
                        xMin = xi;
                    }
                }
            }
            tk = tk * mu;
        }

        return xMin;
    }
}
