package View;

import Model.Client;
import Model.Settings;
import Model.Truck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TruckView extends Canvas {

    private final Random rand = new Random();

    private Truck truck;

    private List<ClientView> clientViews;

    private Color truckColor;

    public TruckView(Truck truck)
    {
        this.truck = truck;
        this.clientViews = new ArrayList<>();

        for (Client client : this.truck.getClients()) {
            this.clientViews.add(new ClientView(client));
        }

        this.truckColor = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
    }

    public TruckView(Truck truck, List<ClientView> cv)
    {
        this.truck = truck;
        this.clientViews = cv;
        this.truckColor = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        int scale = Settings.scale;

        int i = 0;
        ClientView lastClient = null;

        for (ClientView cv : this.clientViews) {
//            cv.paint(g);

            if(i > 0)
            {
                int clientSize = lastClient.getClientSize()/2;
                int x1 = (int)lastClient.getClient().getPosition()[0] * scale + clientSize, y1 = (int)lastClient.getClient().getPosition()[1] * scale + clientSize;
                int x2 = (int)cv.getClient().getPosition()[0] * scale + clientSize, y2 = (int)cv.getClient().getPosition()[1] * scale + clientSize;
                g2.setColor(this.truckColor);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(x1, y1, x2, y2);
                g2.setColor(Color.WHITE);
            }
            lastClient = cv;
            i++;
        }

        g.drawRect(0,0, this.getWidth(), this.getHeight());
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;

        for (int i = 0; i < this.truck.getClients().size(); i++) {
            this.clientViews.get(i).setClient(this.truck.getClients().get(i));
        }
    }

    public void udpateClients(Truck newTruck)
    {
        this.truck = truck;
        this.clientViews = new ArrayList<>();

        for (Client client : this.truck.getClients()) {
            this.clientViews.add(new ClientView(client));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckView truckView = (TruckView) o;
        return truck.equals(truckView.truck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(truck);
    }

    public Color getTruckColor() {
        return truckColor;
    }

    public void setTruckColor(Color truckColor) {
        this.truckColor = truckColor;
    }
}
