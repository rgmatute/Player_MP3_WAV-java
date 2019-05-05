package Main;

import Controller.ControladorLista;
import Controller.ControladorReproductor;
import View.FormLista;
import View.FormReproductor;

/**
 *
 * @author Ronny matute
 */
public class main {

    public static void main(String[] args) {
        FormReproductor fR = new FormReproductor();
        FormLista fL = new FormLista();
        ControladorLista cL = new ControladorLista(fL);
        ControladorReproductor cR = new ControladorReproductor(fR, fL);
    }

}
