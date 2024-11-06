package vista;

import modelo.Movimiento;
import modelo.POOzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VistaGrafica {
    private final POOzzle juego;
    private JPanel panel1;
    private JLabel lTitulo;
    private JLabel lCantidadMovimientos;
    private JLabel lEstado;
    private JPanel panelSur;
    private JPanel panelCentro;

    public VistaGrafica(POOzzle juego)//iria controlador
    {
         this.juego= juego;
        JFrame ventana = new JFrame("POOzzle");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(panel1);
        int [][] tabla = juego.getTabla();
        int size= tabla.length;
        panelCentro.setLayout(new GridLayout(size,size));
        ventana.setSize(400,400);
        for (int fila = 0; fila < size; fila++) {
            for (int columna = 0; columna < size; columna++) {

                JButton boton = new JButton();
                if( tabla[fila][columna] != -1) {
                    ImageIcon image = new ImageIcon(getClass().getResource("imagenes/" + tabla[fila][columna] + ".png"));
                    image.setImage(image.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
                    boton.setIcon(image);
                    boton.setSize(100,100);
                }
                else{
                    boton.setBackground(new Color(0,0,0));
                }
                boton.setActionCommand(fila + "," + columna);
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        juego.moverFicha(e.getActionCommand());
                    }
                });

                panelCentro.add(boton);

            }
        }




        ventana.pack();
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setVisible(true);
        panel1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                Movimiento m = Movimiento.ERROR;
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP -> m= Movimiento.ARRIBA;
                    case KeyEvent.VK_DOWN -> m= Movimiento.ABAJO;
                    case KeyEvent.VK_LEFT -> m= Movimiento.IZQUIERDA;
                    case KeyEvent.VK_RIGHT -> m= Movimiento.DERECHA;
                }
                if (m != Movimiento.ERROR){
                    juego.realizarMovimiento(m);
                }
            }
        });
    }




}
