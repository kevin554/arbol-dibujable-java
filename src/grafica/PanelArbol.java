package grafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import edd.Arbol;
import java.util.Observable;
import java.util.Observer;

public class PanelArbol extends JPanel implements Observer {

    private Arbol elArbol;
    
    public PanelArbol(Arbol objArbol) {
        elArbol = objArbol;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if (elArbol != null) 
            elArbol.graficar(g);
    }        

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}