package View;

import Model.Client;
import Model.Course;
import Model.Truck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CourseView extends JPanel {

    private List<TruckView> truckViews;

    public CourseView(List<Client> clients)
    {
        this.truckViews = new ArrayList<>();
        Course course = Course.getInstance();
//        course.generateCourse(clients);

        for (Truck truck : course.getTrucks()) {
            this.truckViews.add(new TruckView(truck));
        }

        System.out.println(course.computeFitness());
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

    public void updateTrucks()
    {
//        this.truckViews = new ArrayList<>();
        Course course = Course.getInstance();
        System.out.println(course.computeFitness());
//        course.generateCourse(clients);

        for (int i = 0; i < course.getTrucks().size(); i++) {
            this.truckViews.get(i).setTruck(course.getTrucks().get(i));
        }

        this.revalidate();
        this.repaint();
    }

    public List<TruckView> getTruckViews() {
        return truckViews;
    }

    public void setTruckViews(List<TruckView> truckViews) {
        this.truckViews = truckViews;
    }
}
