import modelo.Movimiento;
import modelo.POOzzle;
import vista.VistaGrafica;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        POOzzle juego= new POOzzle(4);
        juego.realizarMovimiento(Movimiento.ABAJO);
        juego.moverFicha("2,2");
        VistaGrafica vista = new VistaGrafica(juego);
    }
}