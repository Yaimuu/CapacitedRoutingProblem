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

    private Course courseUsed;

    private List<TruckView> truckViews;
    private List<ClientView> clientViews;

    public CourseView()
    {
        this.truckViews = new ArrayList<>();
        this.clientViews = new ArrayList<>();
        try {
//            this.courseUsed = (Course) Course.getInstance().clone();
//            Settings.currentCourse = (Course) Course.getInstance().clone();
            Settings.updateCourse((Course) Course.getInstance().clone());
        }catch (Exception e) {}

        updateTrucks();

        for (Client c : Settings.currentCourse.getAllClients()) {
            this.clientViews.add(new ClientView(c));
        }

        System.out.println(Settings.currentCourse.computeFitness());
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
//        Course course = Course.getInstance();
        System.out.println(Settings.currentCourse.computeFitness());

        for (int i = 0; i < Settings.currentCourse.getTrucks().size(); i++) {
            TruckView tv = new TruckView(Settings.currentCourse.getTrucks().get(i));
            tv.setTruckColor(Settings.getPalette().get(i % Settings.getPalette().size()));
            this.truckViews.add(tv);
        }

//        this.updateView();
    }

    public void updateClients()
    {
        this.clientViews.clear();
        for (Client c : Settings.currentCourse.getAllClients()) {
            this.clientViews.add(new ClientView(c));
        }
    }

    public void updateView() {
        updateTrucks();
        updateClients();

        this.revalidate();
        this.repaint();
    }

    public List<TruckView> getTruckViews() {
        return truckViews;
    }

    public void setTruckViews(List<TruckView> truckViews) {
        this.truckViews = truckViews;
    }

    public Course getCourseUsed() {
        return courseUsed;
    }

    public void setCourseUsed(Course courseUsed) {
        this.courseUsed = courseUsed;
    }
}