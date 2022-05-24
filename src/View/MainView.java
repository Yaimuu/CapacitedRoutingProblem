package View;

import Model.Client;
import Model.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class MainView extends JFrame {

    CourseView courseView;
    SettingsView settingsView;

    public MainView(List<Client> clients) {
        super("Capacited Routing Problem");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };
        addWindowListener(l);

        JPanel mainPanel = new JPanel();

//        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
        GridLayout gridLayout = new GridLayout(2, 1);

        this.courseView = new CourseView(clients);
        this.settingsView = new SettingsView();

        this.courseView.setBorder(BorderFactory.createEtchedBorder());
        this.settingsView.setBorder(BorderFactory.createEtchedBorder());

        mainPanel.setSize(550,700);
        mainPanel.setLayout(gridLayout);

        Course.getInstance().setCourseView(this.courseView);
        mainPanel.add(this.courseView);
        mainPanel.add(this.settingsView);

        this.setContentPane(mainPanel);

        this.pack();
        setSize(550,700);
        setVisible(true);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    @Override
    public void repaint() {
        super.repaint();

        this.courseView.revalidate();
        this.courseView.repaint();
        this.settingsView.revalidate();
        this.settingsView.repaint();
    }

    public void updateCourse()
    {
        this.courseView.updateTrucks();
        this.repaint();
    }
}
