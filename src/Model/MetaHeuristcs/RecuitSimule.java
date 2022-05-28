package Model.MetaHeuristcs;

import Model.Course;

public class RecuitSimule extends MetaHeuristic{


    RecuitSimule(Course course) {
        super(course);
    }

    public float run(Course x0, float t0) {
        int i = 0;
        float fMin = x0.computeFitness();
        Course xMin = x0;
        for(int k=0; k < t0; k++)
        {
            for (int l=1; l < k; l++)
            {

            }
        }


        return 0;
    }
}
