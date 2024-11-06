package modelo;

import controlador.Controlador;
import observer.IObservable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

public class POOzzle implements IObservable {
    private int[][] tabla ;
    private int size;
    private int cantidadFichas;
    private int[] fichaVacia =new int[2];
    private int cantidadMovimientos = 0;
    private Controlador controlador;

    public POOzzle(int size) {
        this.size = size;
        this.inicializarTabla();
        System.out.println(this.toString());
    }

    private void inicializarTabla() {
        this.cantidadFichas = this.size * this.size - 1;

        //es un ArrayList para poder usar el shuffle de Collections
        ArrayList<Integer> fichas = new ArrayList<Integer>();
        Random rnd = new Random();
        for (int i = 0; i < cantidadFichas; i++) {
            fichas.add(i);
        }
        Collections.shuffle(fichas);
        this.fichaVacia[0] = rnd.nextInt(this.size);
        this.fichaVacia[1] = rnd.nextInt(this.size);


        this.tabla = new int[this.size][this.size];
        int f = 0;
        for (int ficha = 0; ficha < this.size; ficha++) {
            for (int columna = 0; columna < this.size; columna++) {
                this.tabla[ficha][columna] = ((ficha != this.fichaVacia[0]) || (columna != this.fichaVacia[1]))? fichas.get(f++) : -1;
            }
        }
    }

    public boolean isSort() {
        boolean ordenado = false;
        if (this.tabla[0][0] == 0)
            ordenado = true;
            for (int fila = 0; fila < this.size; fila++) {
                for (int columna = 0; columna < this.size; columna++) {
                    int ficha = fila * this.size + columna;
                    if (!((this.tabla[fila][columna] == ficha) || (ficha == this.cantidadFichas))) {
                        ordenado =  false;
                    }
                }
            }
        return ordenado;
    }

    public boolean realizarMovimiento(Movimiento direccion) {
        if (isMovimientoValido(direccion)) {
            int[] fichaOrigen = fichaOrigen(direccion);
            cambiarFichas(fichaOrigen, this.fichaVacia);
            this.fichaVacia = fichaOrigen;
            this.cantidadMovimientos++;
            System.out.println(this.toString());
            return true;
        }
        return false;
    }

    private void cambiarFichas(int[] origen, int[] destino) {
        int auxiliar = this.tabla[destino[0]][destino[1]];
        this.tabla[destino[0]][destino[1]] =this.tabla[origen[0]][origen[1]];
        this.tabla[origen[0]][origen[1]] = auxiliar;
    }

    private int[] fichaOrigen(Movimiento direccion) {

        int[] origen = this.fichaVacia.clone();
        switch (direccion) {
            case ABAJO -> {
                origen[0]-=1;
            }
            case ARRIBA -> {
                origen[0]+=1;
            }
            case IZQUIERDA -> {
                origen[1]-=1;
            }
            case DERECHA -> {
                origen[1]+=1;
            }
        }
        return origen;
    }

    private boolean isMovimientoValido(Movimiento direccion) {
        switch (direccion){
            case ABAJO -> {
                return this.fichaVacia[0] > 0;
            }
            case ARRIBA -> {
                return this.fichaVacia[0] < this.size-1;
            }
            case IZQUIERDA -> {
                return this.fichaVacia[1] > 0;
            }
            case DERECHA -> {
                return this.fichaVacia[1] < this.size-1;
            }
        }
        return false;
    }

    public int[][] getTabla() {
        return this.tabla.clone();
    }

    @Override
    public String toString() {
        StringBuffer cadena = new StringBuffer();
        cadena.append("POOzzle:\n");
        for (int fila = 0; fila < this.size; fila++) {
            for (int columna = 0; columna < this.size; columna++) {
                cadena.append(" " + this.tabla[fila][columna] + " ");
                cadena.append((columna < this.size - 1)? "|": "");
                }
            cadena.append((fila < this.size - 1)? "\n": "");
        }

        return cadena.toString();
    }

    public void moverFicha(String string) {
        String[] origenString = string.split(",");
        int[] origen = new int[2];
        for (int i = 0; i < origenString.length; i++) {
            origen[i] = Integer.parseInt(origenString[i]);
        }
        System.out.println("Fila:" + origen[0] + "| Columna:" + origen[1]);
        Movimiento m = this.cualMovimientoEs(origen);
        if (m != Movimiento.ERROR) {
            realizarMovimiento(m);
        }

    }

    private Movimiento cualMovimientoEs(int[] origen) {
        int deltaFila = origen[0] - this.fichaVacia[0];
        int deltaColumna = origen[1] - this.fichaVacia[1];
        Movimiento m = Movimiento.ERROR;
        if ((deltaFila == -1) && (deltaColumna == 0)){
            m = Movimiento.ABAJO;
        }
        if ((deltaFila == 1) && (deltaColumna == 0)){
            m = Movimiento.ARRIBA;
        }
        if ((deltaFila == 0) && (deltaColumna == -1)){
            m = Movimiento.IZQUIERDA;
        }
        if ((deltaFila == 0) && (deltaColumna == 1)){
            m = Movimiento.DERECHA;
        }
        return m;
    }

    @Override
    public void agregarObservador(Controlador controlador){
        this.controlador=controlador;
    }
}


