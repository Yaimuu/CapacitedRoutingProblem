package Model.MetaHeuristcs;

import Model.Course;
import Model.Neighborhood.Neighborhood;

public class RecuitSimule extends MetaHeuristic{


    RecuitSimule(Course course) {
        super(course);
    }

    public float run(Course x0, float t0) {
        int i = 0;
        float fMin = x0.computeFitness();
        Course xMin = x0;
        Neighborhood n;
        for(int k=0; k < t0; k++)
        {
            for (int l=1; l < k; l++)
            {
                n = new Neighborhood(x0);


            }
        }


        return 0;
    }
}
