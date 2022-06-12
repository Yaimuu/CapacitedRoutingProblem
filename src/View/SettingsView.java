package View;

import Model.Course;
import Model.Settings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class SettingsView  extends JPanel {


    JButton nextStep;
    JButton run;
    JButton reset;
    JButton newRandomSolution;

    JComboBox filesCombo;
    JComboBox metaCombo;

    JPanel metaHeuristicSettings;

    public SettingsView() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        this.metaHeuristicSettings = new JPanel();

        updateParameters();

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
                updateParameters();
            }
        });

//        this.add(this.nextStep);
        this.add(this.run);
        this.add(this.reset);
        this.add(this.filesCombo);
        this.add(this.metaCombo);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(this.metaHeuristicSettings);

        this.setVisible(true);
    }

    public void updateParameters() {
//        this.contains(this.metaHeuristicSettings)
//            this.remove(this.metaHeuristicSettings);
        if(this.metaHeuristicSettings != null)
        {
            this.metaHeuristicSettings.removeAll();

//            System.out.println(Settings.currentHeuristicSettings);

            for (Map.Entry<String, List<Float>> entry : Settings.currentHeuristicSettings.entrySet()) {

                JLabel label = new JLabel(entry.getKey());
                JSlider slider = new JSlider(entry.getValue().get(1).intValue(),
                        entry.getValue().get(2).intValue(),
                        entry.getValue().get(0).intValue());

                slider.addChangeListener(new ChangeListener(){
                    public void stateChanged(ChangeEvent e){
                        entry.getValue().set(0, (float) slider.getValue());
                        label.setText(entry.getKey() + " " + slider.getValue());
//                        System.out.println(slider.getValue());
                    }
                });

                this.metaHeuristicSettings.add(label);
                this.metaHeuristicSettings.add(slider);

//            this.add(this.metaHeuristicSettings);
            }

        }
        this.repaint();
        this.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

//        this.nextStep.paint(g);
    }
}
