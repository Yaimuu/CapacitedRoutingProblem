package View;

import Model.Client;
import Model.Course;
import Model.Settings;
import Model.Truck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CourseView extends View {

    private List<TruckView> truckViews;
    private List<ClientView> clientViews;

    public CourseView(List<Client> clients)
    {
        this.truckViews = new ArrayList<>();
        this.clientViews = new ArrayList<>();
        Course course = Course.getInstance();

        updateTrucks();

        for (Client c : clients) {
            this.clientViews.add(new ClientView(c));
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

        for (ClientView c:
                this.clientViews) {
            c.paint(g);
        }

        g.drawRect(0,0, this.getWidth(), this.getHeight());
    }

    public void updateTrucks()
    {
        this.truckViews.clear();
        Course course = Course.getInstance();
        System.out.println(course.computeFitness());

        for (int i = 0; i < course.getTrucks().size(); i++) {
            TruckView tv = new TruckView(course.getTrucks().get(i));
            tv.setTruckColor(Settings.getPalette().get(i % Settings.getPalette().size()));
            this.truckViews.add(tv);
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
