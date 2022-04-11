package View;

import Model.Client;
import Model.Course;
import Model.Truck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CourseView extends JPanel {

    private Course course;

    private List<TruckView> truckViews;

    public CourseView(List<Client> clients)
    {
        this.truckViews = new ArrayList<>();
        this.course = new Course();
        this.course.generateCourse(clients);

        for (Truck truck : this.course.getTrucks()) {
            this.truckViews.add(new TruckView(truck));
        }

        System.out.println(this.course.computeFitness());
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for (TruckView truck:
             this.truckViews) {
            truck.paint(g);
        }

        g.drawRect(0,0, this.getWidth(), this.getHeight());
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<TruckView> getTruckViews() {
        return truckViews;
    }

    public void setTruckViews(List<TruckView> truckViews) {
        this.truckViews = truckViews;
    }
}
