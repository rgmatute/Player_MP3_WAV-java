package Controller;

import Model.DAOMusica;
import Model.clsMusica;
import TelematicoTools.Platillos.DiscoOne;
import TelematicoTools.FormMouse.formRoot;
import View.FormLista;
import View.FormReproductor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static com.sun.awt.AWTUtilities.setWindowOpaque;
import java.util.ArrayList;

/**
 *
 * @author Ronny matute
 */
@SuppressWarnings("")
public class ControladorReproductor implements MouseListener {

    //Formulario Lista
    public static FormLista fL;

    //Musica
    ArrayList<clsMusica> listaDeMusicas = new ArrayList<>();
    DAOMusica daoM;
    private static clsMusica cM;
    private static String nombreMusica;

    public static DiscoOne d1 = new DiscoOne();
    formRoot mouseMove = new formRoot();
    public static int repetir;
    /////////////////////

    public static FormReproductor fR;

    public ControladorReproductor(FormReproductor fR, FormLista fL) {
        this.fR = fR;
        this.fL = fL;
        daoM = new DAOMusica();
        inicializar();
    }

    private void inicializar() {
        //Eventos LabelsButton
        this.fR.lblCerrar.addMouseListener(this);
        this.fR.lblMinimizar.addMouseListener(this);
        this.fR.lblPlay.addMouseListener(this);
        this.fR.lblPausa.addMouseListener(this);
        this.fR.lblStop.addMouseListener(this);
        this.fR.lblRepetir.addMouseListener(this);
        this.fR.lblMenu.addMouseListener(this);
        this.fR.lblAdd.addMouseListener(this);

        //FONDO
        mouseMove.MoverFrame(this.fR, this.fR.lblFondo);
        //Progress & JSlider
        mouseMove.ControlProgress(this.fR.jProgressBar1, this.fR.jSliderVolumen, d1);
        mouseMove.getValueSlider(this.fR.lblVolumenPorcentaje);
        d1.getTimeRun(fR.jProgressBar1);
        d1.getProgressLabel(this.fR.jLabel2);
        //Equializador
        d1.equalizador(this.fR.lblEquializador, 1);
        //Formulario Reproductor
        this.fR.setVisible(true);
        this.fR.setSize(420, 170);
        //this.fR.setSize(620, 170);
        this.fR.setLocationRelativeTo(null);
        setWindowOpaque(fR, false);
        //listaDeMusicas = daoM.buscarTodosMusica();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(this.fR.lblCerrar)) {
            System.exit(0);
        } else if (e.getSource().equals(this.fR.lblMinimizar)) {
            //String name = System.getProperty("os.name");
            this.fR.setExtendedState(1);
        } else if (e.getSource().equals(this.fR.lblPlay)) {
            this.fR.lblTituloMusica.setText("PLAY");
            d1.continuar();
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        fR.lblTituloMusica.setText(nombreMusica);
                    } catch (InterruptedException ex) {
                        ex.getStackTrace();
                    }
                }
            }.start();
        } else if (e.getSource().equals(this.fR.lblPausa)) {
            this.fR.lblTituloMusica.setText("PAUSE");
            d1.pause();
        } else if (e.getSource().equals(this.fR.lblStop)) {
            this.fR.lblTituloMusica.setText("STOP");
            d1.stop();
        } else if (e.getSource().equals(this.fR.lblRepetir)) {
            //d1.getMetaDatos("C:\\Users\\Telematico\\Music\\MusicasMias\\Si Tu La Ves - Nicky Jam_ Wisin [320kbps_MP3].mp3");
            //d1.saveLocal(cM.getMusica(), "C:\\Users\\Telematico\\Music\\" + cM.getNombre() + ".mp3");
            String patch = "C:\\Users\\Telematico\\Music\\aTobu - Candyland.mp3";
            String g = d1.getMetaDatos("C:\\Users\\Telematico\\Music\\aTobu - CandylandConGenero.mp3");
            //d1.duracionAudio(cM.getMusica());
            System.out.println(d1.duracionMP3(patch));
            System.out.println("Genero: " + g);
            switch (repetir) {
                case 0:
                    repetir = 1;
                    d1.repetir(1);
                    this.fR.lblRepetir.setText("ON");
                    this.fR.lblEquializador2.setVisible(true);
                    //System.out.println("1");
                    break;
                case 1:
                    repetir = 0;
                    d1.repetir(0);
                    this.fR.lblRepetir.setText("OF");
                    this.fR.lblEquializador2.setVisible(false);
                    //System.out.println("0");
                    break;
            }
        } else if (e.getSource().equals(this.fR.lblMenu)) {
            this.fL.setVisible(true);
        } else if (e.getSource().equals(this.fR.lblAdd)) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println("ADD+");
                    if (daoM.registrarMusica(cM)) {
                        System.out.println("Registrado..");
                    } else {
                        System.out.println("No Registrado..");
                    }
                }
            }.start();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void Play(clsMusica items) {
        fR.lblTituloMusica.setText(items.getNombre());
        cM = items;
        nombreMusica = items.getNombre();
        if (items.getNombre().endsWith(".mp3")) {
            d1.stop();
            d1.PlayMP3(items.getMusica());
        } else if (items.getNombre().endsWith(".wav")) {
            d1.stop();
            d1.PlayWAV(items.getMusica());
        }
    }
}
