package observer;

import controlador.Controlador;

import javax.naming.ldap.Control;

public interface IObservable {
    void update();
    void agregarObservador(IObservador observador);
}
