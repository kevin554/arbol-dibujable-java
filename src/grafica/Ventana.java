package grafica;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import edd.Arbol;

public class Ventana extends JFrame {

    private PanelArbol elPanel;
    private PanelFormularioArbol elPanelFormulario;
    private Arbol<String> elArbol;
    
    public Ventana() {
        init();
    }

    private void init() {
        JMenuBar barra = new JMenuBar();
        JMenu mnArchivo = new JMenu("Archivo");
        JMenuItem imAgregarNodo = new JMenuItem("Agregar Nodo");
        JMenuItem imSalir = new JMenuItem("Salir");
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setJMenuBar(barra);
        barra.add(mnArchivo);
        mnArchivo.add(imAgregarNodo);
        mnArchivo.addSeparator();
        mnArchivo.add(imSalir);

        imAgregarNodo.addActionListener(this::cmdAgregarNodo);
        imSalir.addActionListener(this::cmdSalir);

        try {
            precargarArbol();
        } catch (Exception e) {
            //TODO-CODE HERE
        }

        elPanel = new PanelArbol(elArbol);
        elArbol.addObserver(elPanel);
        add(new JScrollPane(elPanel));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void precargarArbol() throws Exception {
        elArbol = new Arbol();
        elArbol.agregar("Paco", "Paco", null);
        elArbol.agregar("Pedro", "Pedro", "Paco");
        elArbol.agregar("Lucas", "Lucas", "Paco");
        elArbol.agregar("Donald", "Donald", "Pedro");
        elArbol.agregar("Mickey", "Mickey", "Pedro");
        elArbol.agregar("Minni", "Minnie", "Lucas");
        elArbol.agregar("Goofie", "Goofie", "Lucas");
    }
    
    /**
     * Despliega una ventana con un formulario para agregar un nodo
     * @param e
     */
    public void cmdAgregarNodo(ActionEvent e) {
        elPanelFormulario = new PanelFormularioArbol();
        int result = JOptionPane.showConfirmDialog(null, elPanelFormulario,
                "Datos de la persona", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
            elPanelFormulario.comprobarDatos(elArbol, elPanel);
    }
    
    public void cmdSalir(ActionEvent e) {
        System.exit(0);
    }
}
