package View;

import Model.Course;
import Model.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView  extends JPanel {


    JButton nextStep;
    JButton run;
    JButton reset;
    JButton newRandomSolution;

    JComboBox filesCombo;
    JComboBox metaCombo;

    public SettingsView() {
        Course course = Course.getInstance();

        this.nextStep = new JButton("Next");
        this.run = new JButton("Run");
        this.reset = new JButton("Reset");

        this.filesCombo = new JComboBox(Settings.getAllMapFiles().toArray());
        this.metaCombo = new JComboBox(Settings.metaHeuristicMap.keySet().toArray());

        this.nextStep.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                course.nextStep();
            }
        });

        this.run.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                course.run();
            }
        });

        this.reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                course.reset();
            }
        });

        this.filesCombo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Settings.curentFile = (String) filesCombo.getSelectedItem();
                course.reset();
            }
        });

        this.metaCombo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Settings.updateCurrentHeuristic((String) metaCombo.getSelectedItem());
                course.reset();
            }
        });

        this.add(this.nextStep);
        this.add(this.run);
        this.add(this.reset);
        this.add(this.filesCombo);
        this.add(this.metaCombo);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

//        this.nextStep.paint(g);
    }
}
