package View;

import Model.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView  extends JPanel {


    JButton nextStep;

    public SettingsView() {
        Course course = Course.getInstance();

        this.nextStep = new JButton("Next");
        this.nextStep.setBounds(90,100,100,40);

        this.nextStep.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                course.nextStep();
            }
        });

        this.add(this.nextStep);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

//        this.nextStep.paint(g);
    }
}
