package Model.MetaHeuristcs;

import Model.Course;

public abstract class MetaHeuristic {

    Course solution;

    MetaHeuristic(Course course) {
        this.solution = course;
    }

}
