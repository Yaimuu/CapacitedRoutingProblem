package Model.MetaHeuristcs;

import Model.Course;

public abstract class MetaHeuristic {

    protected Course solution;

    public MetaHeuristic(Course course) {
        this.solution = course;
    }

}
