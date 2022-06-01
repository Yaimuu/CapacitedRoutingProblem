import Model.Client;
import Model.CourseFileManager;
import View.MainView;

import javax.swing.*;
import java.util.List;

public class Main {

    public static void main (String[] args){
        CourseFileManager cm = new CourseFileManager();

//        List<Client> clientList = CourseFileManager.readFile("./Ressources/Tests/A3205.txt");

        JFrame frame = new MainView();
    }
}
