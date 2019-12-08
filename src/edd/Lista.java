package edd;

import java.util.Iterator;

/**
 * Una lista simple
 * @param <E> El tipo de dato
 */
public class Lista<E> implements Iterable<E> {
//public class Lista<E extends Comparable> implements Iterable<E> {

    protected Nodo<E> inicio;
    protected Nodo<E> fin;

    public Lista() {
        inicio = null;
        fin = null;
    }

    /**
     * Devuelve el elemento de la posicion especifica en esta lista
     * @param i Posicion del elemento a retornar
     * @return El elemento en la posicion especifica de esta lista
     */
    public E obtener(int i) {
        comprobarPosicionValida(i);
        Nodo<E> actual = inicio;
        int cont = 0;
        while (actual != null && cont < i) {
            actual = actual.getSiguiente();
            cont++;
        }
        return actual.getDato();
    }

    /**
     * Devuelve el ultimo dato de esta lista
     * @return El ultimo dato de esta lista
     */
    public E obtenerUltimo() {
        Nodo<E> actual = fin;
        return actual.getDato();
    }
    
    /**
     * Agrega el elemento al final de esta lista
     * @param o El elemento a ser agregado
     */
    public void agregar(E o) {
        Nodo<E> nuevo = new Nodo(o);
        if (inicio == null) {
            fin = nuevo;
            inicio = fin;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    /**
     * Inserta el elemento a la posicion especifica de esta lista. Mueve el
     * elemento de esa posicion (si lo hay) a la derecha
     * @param pos Posicion en la cual el elemento debe ser insertado
     * @param o El elemento a ser insertado
     */
    public void agregar(int pos, E o) {
        comprobarPosicionValida(pos);

        int cont = 0;
        Nodo<E> actual = inicio;
        while (cont < pos - 1) {
            actual = actual.getSiguiente();
            cont++;
        }
        Nodo<E> nuevo = new Nodo<>(o);
        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }
    
    /**
     * Inserta el elemento al comienzo de esta lista
     * @param o El elemento a ser insertado
     */
    public void insertar(E o) {
        Nodo<E> nuevo = new Nodo<>(o);
        nuevo.setSiguiente(inicio);
        inicio = nuevo;
    }
   
    /**
     * Elimina el elemento en la posicion especifica de esta lista.
     * @param i La posicion del elemento a ser eliminado
     * @return El elemento que fue eliminado de la lista
     */
    public E eliminar(int i) {
        comprobarPosicionValida(i);
        if (i == 0) {
            Nodo<E> aux = inicio;
            inicio = inicio.getSiguiente();
            return aux.getDato();
        }
        Nodo<E> aux = inicio;
        int contador = 0;
        while (contador != i - 1) {
            contador++;
            aux = aux.getSiguiente();
        }

        Nodo<E> temp = aux.getSiguiente();
        aux.setSiguiente(temp.getSiguiente());
        return temp.getDato();
    }        

    /**
     * Ordena esta lista
     */
    public void ordenar() {
        Nodo<E> pivote = inicio;
        
        while (pivote != null) {
            boolean seMovio = pivote.moverAPosicionCorrecta();
            if (seMovio)
                pivote = inicio; //System.out.println(this);
             else
                pivote = pivote.getSiguiente();
        }
    }
    
    /**
     * Devuelve el numero de elementos de esta lista
     * @return El numero de elementos de esta lista
     */
    public int tamano() {
        int t = 0;
        for (E obj : this) 
            t++;
        
        return t;
    }

    /**
     * Devuelve verdadero si esta lista no contiene elementos
     * @return Verdadero si esta lista no contiene elementos
     */
    public boolean estaVacia() {
        return inicio == null;
    }
    
    /**
     * Devuelve un vector con todos los elementos de esta lista. Util para utilizar
     * con un JList
     * @return Un vector con todos los elementos de esta lista
     */
    public String[] toArray(){
        String[] res = new String[tamano()];
        int cont = 0;
        Nodo<E> actual = inicio;
        while (actual != null) {
            res[cont] = actual.toString();
            actual = actual.siguiente;
            cont++;
        }
        return res;
    }
    
    /**
     * Revisa que la posicion dada esté dentro del rango de elementos
     * @param pos La posicion a revisar
     */
    private void comprobarPosicionValida(int pos) {
        if (pos >= tamano() || pos < 0)
            throw new IndexOutOfBoundsException("Tu posicion " + pos + " no es coherente");
    }
    
    /**
     * Devuelve un iterador sobre los elementos de esta lista
     * @return Un iterador sobre los elementos de esta lista
     */
    @Override
    public Iterador<E> iterator() {
        return new Iterador<>(inicio);
    }

    @Override
    public String toString() {
        String res = "";
        Nodo<E> actual = inicio;
        while (actual != null) {
            res += actual.toString();
            actual = actual.siguiente;
        }
        return res;
    }

    public class Nodo<E> {

        private E dato;
        private Nodo<E> siguiente;
        
        public boolean moverAPosicionCorrecta() {
            if (!(dato instanceof Comparable))
                return false;
            
            if (this.getSiguiente() == null) 
                return false;
            
            Comparable<E> obj = (Comparable<E>) dato;
            Nodo<E> nodoAComparar = this.getSiguiente();
            
            //Si solamente hay dos nodos
            if (nodoAComparar.getSiguiente() == null) {
                
                if (obj.compareTo(nodoAComparar.getDato()) > 0) {
                    E aux = dato;
                    dato = nodoAComparar.getDato();
                    nodoAComparar.setDato(aux);
                    
                    return true;
                }
                
                return false;
            }
            
            nodoAComparar = this;
            while (nodoAComparar.getSiguiente() != null && (obj.compareTo((E) nodoAComparar.getSiguiente().getDato()) >= 0)) {
                nodoAComparar = nodoAComparar.getSiguiente();
            }
            
            //Llegó al final
            if (nodoAComparar.getSiguiente() == null) {
                
                if (obj.compareTo(nodoAComparar.getDato()) >= 0) {
                    E aux = dato;
                    dato = nodoAComparar.getDato();
                    nodoAComparar.setDato(aux);
                    
                    return true;
                }
                
                return false;
            }
            
            if (this != nodoAComparar && obj.compareTo(nodoAComparar.getDato()) > 0) {
                E aux = dato;
                dato = nodoAComparar.getDato();
                nodoAComparar.setDato(aux);
                
                return true;
            }
            
            return false;
        }
        
        public Nodo(E contenido) { 
            this.dato = contenido; 
        }

        public E getDato() { return dato; }

        public void setDato(E contenido) { dato = contenido; }

        public Nodo getSiguiente() { return siguiente; }

        public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }
        
        @Override
        public String toString() {
            String resultado = "";
            resultado += dato.toString();
            resultado += "";
            return resultado;
        }
    }

    public class Iterador<E> implements Iterator<E> {

        private Nodo<E> actual;

        public Iterador(Nodo<E> r) {
            actual = r;
        }

        @Override
        public boolean hasNext() {
            return (actual != null);
        }

        @Override
        public E next() {
            E o = actual.getDato();
            actual = actual.getSiguiente();
            
            return o;
        }
    }
}
