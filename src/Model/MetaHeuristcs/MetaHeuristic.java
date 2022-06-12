package Model.MetaHeuristcs;

import Model.Course;

import java.lang.reflect.InvocationTargetException;

public abstract class MetaHeuristic {

    protected MetaheuristicName name;
    protected Course solution;

    public MetaHeuristic() {
        this.name = MetaheuristicName.NONE;
        try {
            this.solution = (Course) Course.getInstance().clone();
        } catch (Exception e) {

        }
    }

    public MetaHeuristic(Course course) {
        this();
        try {
            this.solution = (Course) course.clone();
        } catch (Exception e) {

        }
    }

    public abstract Course run() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException ;

    public Course getSolution() {
        return solution;
    }

    public void setSolution(Course solution) {
        this.solution = solution;
    }

    public MetaheuristicName getName() {
        return name;
    }
}
