package View;

import Model.Client;
import Model.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ClientView extends Canvas {

    private final Random rand = new Random();

    Client client;

    int clientSize;
    Color clientColor;

    public ClientView(Client client) {
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
        int scale = Settings.scale;
        int x = (int)client.getPosition()[0] * scale, y = (int)client.getPosition()[1] * scale;
        if(this.client.getNumClient() == 0)
        {
            g.fillOval(x, y, clientSize*2, clientSize*2);
            g.setColor(Color.WHITE);
            g.drawOval(x, y, clientSize*2, clientSize*2);
        }
        else
        {
            g.fillOval(x, y, clientSize, clientSize);
            g.setColor(this.clientColor);
            g.drawOval(x, y, clientSize, clientSize);
        }

        g.drawRect(0,0, this.getWidth(), this.getHeight());
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
