package Model.MetaHeuristcs;

import Model.Course;

import java.lang.reflect.InvocationTargetException;

public abstract class MetaHeuristic {

    protected String name;
    protected Course solution;

    public MetaHeuristic() {
        this.name = "None";
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

    public Course run() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println("Run " + this.name);
        return this.solution;
    }


    public Course getSolution() {
        return solution;
    }

    public void setSolution(Course solution) {
        this.solution = solution;
    }
}
