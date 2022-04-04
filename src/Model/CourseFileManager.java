package Model;

import View.CourseView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class CourseFileManager {

    public static List<Client> readFile(String file)
    {
        List<Client> clientList = null;
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String elements[] = myReader.nextLine().split(";");
                float [] position = new float[2];
                position[0] = Float.parseFloat(elements[1]);
                position[1] = Float.parseFloat(elements[2]);
                Client cl = new Client(Integer.parseInt(elements[0]), Integer.parseInt(elements[3]), position);
                clientList.add(cl);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return clientList;
    }

    public static boolean generateFile(Course course)
    {
        return true;
    }
}
