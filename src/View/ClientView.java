package View;

import Model.Client;
import Model.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ClientView extends Canvas {

    private Client client;

    private int clientSize;
    private Color clientColor;

    public ClientView(Client client) {
        Random rand = new Random();
        this.client = client;
        this.clientSize = 10;
        this.clientColor = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
    }

    public ClientView(Client client, int clientSize) {
        this(client);
        this.client = client;
        this.clientSize = clientSize;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        int scale = Settings.scale;
        int x = (int)client.getPosition()[0] * scale, y = (int)client.getPosition()[1] * scale;
        if(this.client.getNumClient() == 0)
        {
            g2.setColor(Color.BLACK);
            g2.fillOval(x, y, clientSize*2, clientSize*2);
            g2.drawOval(x, y, clientSize*2, clientSize*2);
        }
        else
        {
            g2.setColor(this.clientColor);
            g2.fillOval(x, y, clientSize, clientSize);
            g2.drawOval(x, y, clientSize, clientSize);
        }
        g2.drawString(client.getNumClient() + "", x, y);
        g2.drawRect(0,0, this.getWidth(), this.getHeight());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getClientSize() {
        return clientSize;
    }

    public void setClientSize(int clientSize) {
        this.clientSize = clientSize;
    }
}
