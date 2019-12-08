package grafica;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edd.Arbol;

public class PanelFormularioArbol extends JPanel {

    private JTextField tfNombre = new JTextField(2);
    private JTextField tfId = new JTextField(2);
    private JTextField tfIdPadre = new JTextField(2);
    
    private final JLabel lbNombre = new JLabel("Nombre:");
    private final JLabel lbId = new JLabel("Id:");
    private final JLabel lbIdPadre = new JLabel("Id Padre:");
    
    private final JPanel panelSuperior = new JPanel();
    private final JPanel panelCentral = new JPanel();
    private final JPanel panelInferior = new JPanel();
    
    public PanelFormularioArbol() {
        init();
    }
    
    private void init() {
        setLayout(new BorderLayout());
        panelSuperior.setLayout(new BorderLayout());
        panelCentral.setLayout(new BorderLayout());
        panelInferior.setLayout(new BorderLayout());
        
        panelSuperior.add(lbNombre, BorderLayout.NORTH);
        panelSuperior.add(tfNombre, BorderLayout.CENTER);
        panelSuperior.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
        
        panelCentral.add(lbId, BorderLayout.NORTH);
        panelCentral.add(tfId, BorderLayout.CENTER);
        panelCentral.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
        
        panelInferior.add(lbIdPadre, BorderLayout.CENTER);
        panelInferior.add(tfIdPadre, BorderLayout.SOUTH);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Comprueba que los campos de texto no esten en blanco, para añadir un nodo
     * al arbol
     * @param elArbol El arbol
     * @param elPanel El panel que contiene el arbol
     */
    public void comprobarDatos(Arbol elArbol, PanelArbol elPanel) {
        String nombre = tfNombre.getText().trim();
        String id = tfId.getText().trim();
        String idPadre = tfIdPadre.getText().trim();
        
        if (nombre.length() == 0 || id.length() == 0 || idPadre.length() == 0) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        } else {
            try {
                elArbol.agregar(id, nombre, idPadre);
            } catch (Exception ex) {
                //TODO-CODE HERE
            }
        }
    }
}