package Model.MetaHeuristcs;

import Model.Course;

public abstract class MetaHeuristic {

    protected Course solution;

    public MetaHeuristic(Course course) {
        try {
            this.solution = (Course) course.clone();
        } catch (Exception e) {

        }
    }

}
