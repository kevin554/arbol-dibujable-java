package edd;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;

public class Arbol<E> extends Observable{

    private Nodo<E> raiz;
    private HashMap<String, Object> nodos;

    public Arbol() {
        raiz = null;
        nodos = new HashMap<>();
    }

    /**
     * Agrega un nodo a este arbol
     * @param id El id del nodo
     * @param o El dato
     * @param idPadre El id del nodo padre
     * @throws Exception 
     */
    public void agregar(String id, E o, String idPadre) throws Exception {
        if (id == null) 
            throw new Exception("El id no puede ser nulo");
        
        if (idPadre == null && raiz != null) 
            throw new Exception("Ya existe una raiz");
        
        if (idPadre == null && raiz == null) {
            this.raiz = new Nodo<>(id, o);
            nodos.put(raiz.getId(), raiz);
            return;
        }
        
        raiz.agregarHijo(o, id, idPadre, this);
    }

    public E get() {
        return null;
    }
    
    /**
     * Devuelve verdadero si este arbol no contiene elementos
     * @return Verdadero si este arbol no contiene elementos
     */
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Dibuja el arbol
     * @param g 
     */
    public void graficar(Graphics g) {
        if (raiz == null) {
            g.setColor(Color.BLACK);
            g.drawString("Arbol Vacio", 20, 20);
            return;
        }
        graficar(0, 0, g);
    }

    /**
     * Dibuja el arbol en la posicion superior izquierda (x, y) especifica
     * @param x La posicion superior izquierda x 
     * @param y La posicion superior izquierda y
     * @param g 
     */
    private void graficar(int x, int y, Graphics g) {
        raiz.graficar(x, y, g);
    }
    
    @Override
    public String toString() {
        if (raiz.getHijos().tamano() == 0) 
            return raiz.getDato().toString();
         else 
            return raiz.toString();
    }

    //CLASE NODO
    class Nodo<E> {

        public final int MARGEN_NODO = 10;
        public final int TAMANO_NODO = 50;
        public final int ESPACIO_VERTICAL = 50;
        public final int ESPACIO_HORIZONTAL = 30;

        private Lista<Nodo<E>>  hijos;
        
        private String id;
        private E dato;

        public Nodo(String id, E contenido) {
            hijos = new Lista<>();
            this.id = id;
            this.dato = contenido;
        }

        public Nodo() {
            hijos = new Lista<>();
        }

        /**
         * Agrega un nodo al arbol de manera recursiva
         * @param o El dato
         * @param id El id del dato
         * @param idPadre El id del padre
         * @param objArbol El arbol
         * @throws Exception 
         */
        public void agregarHijo(E o, String id, String idPadre, Arbol<E> objArbol) throws Exception {
            if (this.id.equals(idPadre)) {
                if (this.id.equals(id)) 
                    throw new Exception("El id ya existe");
                
                Nodo<E> nn = new Nodo(id, o);
                objArbol.nodos.put(id, nn);
                
                setChanged();
                notifyObservers();
                this.hijos.insertar(nn);
                return;
            }
            
            //Recorremos el arbol de manera recursiva
            Iterator<Nodo<E>> it = hijos.iterator();
            while (it.hasNext()) {
                Nodo<E> c = it.next();
                c.agregarHijo(o, id, idPadre, objArbol);
            }
        }

        /**
         * Metodo que hace todos los calculos necesarios para devolver el arbol
         * dibujado
         * @param x La posicion superior izquierda x
         * @param y La posicion superior izquierda y
         * @param g 
         */
        public void graficar(int x, int y, Graphics g) {
            int ancho = calcularAncho();
            int cx = (ancho / 2) + x;
            int xH = x;
            int yH = y + TAMANO_NODO + ESPACIO_VERTICAL;
            
            if (hijos.tamano() > 0) 
                for (Nodo<E> h : hijos) {
                    int anchoHijo = h.calcularAncho() +  h.getAnchoContenido(g) ;
                    g.setColor(Color.BLACK);
                    g.drawLine(cx, y + TAMANO_NODO / 2, anchoHijo / 2 - MARGEN_NODO + xH, yH + TAMANO_NODO / 2);
//                    g.drawLine(cx, y + TAMANO_NODO / 2, anchoHijo / 2 + xH, yH + TAMANO_NODO / 2);
                    h.graficar(xH, yH, g);
                    xH += (anchoHijo + ESPACIO_HORIZONTAL);
                }
            
            g.setColor(Color.WHITE);
            g.fillOval(cx - TAMANO_NODO / 2, y, getAnchoContenido(g) + (MARGEN_NODO * 2), TAMANO_NODO);
            g.setColor(Color.BLACK);
            g.drawOval(cx - TAMANO_NODO / 2, y, getAnchoContenido(g) + (MARGEN_NODO * 2), TAMANO_NODO);
            g.drawString(dato.toString(), cx - (MARGEN_NODO * 2) + (MARGEN_NODO / 2), y + (TAMANO_NODO / 2) + (MARGEN_NODO / 2));
        }

        /**
         * Calcula el ancho que tendra el arbol
         * @return 
         */
        public int calcularAncho() {
            if (hijos.tamano() == 0) 
                return TAMANO_NODO;
            
            int ancho = 0;
            for (Nodo<E> h : hijos) {
                ancho += (h.calcularAncho() + ESPACIO_HORIZONTAL);
            }
            ancho -= (ESPACIO_HORIZONTAL);
            
            return ancho;
        }
        
        /**
         * Devuelve el ancho del contenido del nodo
         * @param g
         * @return El ancho del contenido del nodo
         */
        public int getAnchoContenido(Graphics g) {
            FontMetrics mesurer = g.getFontMetrics();
            int ancho = mesurer.stringWidth(dato.toString());
            return ancho;
        }

        public String getId() { return id; }

        public void setId(String id) { this.id = id; }

        public E getDato() { return dato; }

        public void setDato(E contenido) { dato = contenido; }

        public Lista<Nodo<E>> getHijos() { return hijos; }

        @Override
        public String toString() {
            StringBuilder r = new StringBuilder();
            if (hijos.tamano() == 0) 
                return "";
            
            r.append(dato.toString());
            r.append(" padre \n");
            
            for (Nodo<E> hijo : hijos) {
                r.append(hijo.getDato().toString());
                r.append(" hijo \n");
            }
            
            for (Nodo<E> hijo : hijos) {
                r.append(hijo.toString());
            }

            return r.toString();
        }
    }
}
