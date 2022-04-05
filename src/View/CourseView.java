package View;

import Model.Client;
import Model.Course;
import Model.Truck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CourseView extends JPanel {

    Course course;

    List<TruckView> truckViews;

    public CourseView(List<Client> clients)
    {
        this.truckViews = new ArrayList<>();
        this.course = new Course();
        this.course.generateCourse(clients);

        for (Truck truck : this.course.getTrucks()) {
            this.truckViews.add(new TruckView(truck));
        }
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

}
