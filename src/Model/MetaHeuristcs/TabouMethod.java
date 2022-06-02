package Model.MetaHeuristcs;

import Model.Course;

import java.lang.reflect.InvocationTargetException;

public class TabouMethod extends MetaHeuristic {

    public TabouMethod() {
        super();
        this.name = "Tabou";
    }

    public TabouMethod(Course course) {
        super(course);
        this.name = "Tabou";
    }

    public Course run() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println("Run " + this.name);
        return this.solution;
    }


}
