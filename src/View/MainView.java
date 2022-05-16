package View;

import Model.Client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class MainView extends JFrame {

    CourseView courseView;

    public MainView(List<Client> clients) {
        super("Capacited Routing Problem");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };
        addWindowListener(l);

        JPanel mainPanel = new JPanel();

        this.courseView = new CourseView(clients);
        mainPanel.add(this.courseView);

        setContentPane(this.courseView);
        setSize(550,550);
        setVisible(true);

    }

}
