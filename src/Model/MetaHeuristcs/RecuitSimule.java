package Model.MetaHeuristcs;

import Model.Course;

public class RecuitSimule extends MetaHeuristic{


    RecuitSimule(Course course) {
        super(course);
    }

    public float run(Course x0, float t0) {
        int i = 0;
        float xMin = this.solution.computeFitness();
        Course minSolution;
        for(int k=0; k < t0; k++)
        {
//            for (int l=1; l < )
        }


        return 0;
    }
}
