package View;

import Model.Client;
import Model.Course;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CourseView extends JPanel {

    Course course;

    public CourseView(List<Client> clients)
    {
        this.course.generateCourse(clients);
    }

    public void update()
    {

    }

}
