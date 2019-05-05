package Controller;

import Model.DAOMusica;
import Model.MoverForm;
import Model.clsMusica;
import View.FormLista;
import static com.sun.awt.AWTUtilities.setWindowOpaque;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Ronny matute
 */
@SuppressWarnings("")
public class ControladorLista implements MouseListener {

    public FormLista fL;
    private static clsMusica cM;

    //ListaMusicas
    //Musica
    ArrayList<clsMusica> listaDeMusicas = new ArrayList<>();
    DAOMusica daoM;
    DefaultListModel List = null;
    boolean controlMusicaLocal = false;
    public String patchLocal;
    public String[] patchL = new String[100];
    public int contador;

    public ControladorLista(FormLista fL) {
        this.fL = fL;
        daoM = new DAOMusica();/////////////////////
        iniciar();
    }

    private void iniciar() {
        MyEventoBusqueda ev = new MyEventoBusqueda();
        //Eventos Mouse
        this.fL.lblCerrar.addMouseListener(this);
        this.fL.lblPC.addMouseListener(this);
        this.fL.lblDB.addMouseListener(this);
        this.fL.jListaMusicas.addMouseListener(this);

        //Lista
        this.fL.jListaMusicas.setEnabled(false);
        this.fL.jListaMusicas.addKeyListener(ev);

        //Txt
        this.fL.jTextField1.addKeyListener(ev);

        //Formulario
        //this.fL.setVisible(true);
        this.fL.setSize(420, 239);
        this.fL.setLocationRelativeTo(null);
        setWindowOpaque(fL, false);
        moverFromMouse();
    }

    private void moverFromMouse() {
        MoverForm mF = new MoverForm();
        mF.setObjec(this.fL.lblFondo, fL);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == fL.jListaMusicas & (e.getClickCount() == 2)) {
            prePlay();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == this.fL.lblCerrar) {
            this.fL.dispose();
        } else if (e.getSource() == this.fL.lblPC) {
            System.out.println("Abrir de PC");
            listaDeMusicas = null;
            ArrayList<File> musicass = new ArrayList();
            System.out.println("* * * * * MUSIC * * * * *");
            //musicass = getMusicas(new File("C:\\Users\\Telematico\\Music"));
            musicass = getMusicas(new File(System.getProperty("user.home")+"/Music"));
            getListaMusicaLocal(musicass);
            controlMusicaLocal = true;
        } else if (e.getSource() == this.fL.lblDB) {
            System.out.println("Abrir de Base de Dato");
            getListaMusicaDB();
            controlMusicaLocal = false;
        }
        this.fL.jListaMusicas.setEnabled(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void getListaMusicaDB() {
        List = new DefaultListModel();
        fL.jListaMusicas.setModel(List);
        new Thread() {
            @Override
            public void run() {
                listaDeMusicas = null;
                //contador = 0;
                listaDeMusicas = daoM.buscarTodosMusica("all");
                for (clsMusica items : listaDeMusicas) {
                    List.addElement(items.toString());
                    //contador++;
                    fL.lblTotalMusicas.setText("" + List.getSize() + " -> Audios");
                }
            }
        }.start();
    }

    private void getListaMusicaLocal(ArrayList<File> rootFiles) {
        List = new DefaultListModel();
        fL.jListaMusicas.setModel(List);
        new Thread() {
            @Override
            public void run() {
                listaDeMusicas = new ArrayList();
                contador = 0;
                for (File items : rootFiles) {
                    cM = new clsMusica();
                    cM.setMusica(ControladorReproductor.d1.getBytes(items + ""));
                    cM.setNombre(items.getName());
                    cM.setCodigo(Math.round(Math.random() * 999) + "");
                    List.addElement(items.getName());
                    listaDeMusicas.add(cM);
                    System.out.println("patch: " + items);
                    patchLocal = items.toString();
                    patchL[contador] = items.toString();
                    contador++;
                    fL.lblTotalMusicas.setText(" ");
                    fL.lblTotalMusicas.setText("" + List.getSize() + " -> Audios");
                }
            }
        }.start();
    }

    public ArrayList<File> getMusicas(File root) {
        ArrayList<File> canciones = new ArrayList();
        File[] archivos = root.listFiles();
        for (File patch : archivos) {
            if (patch.isDirectory() & !patch.isHidden()) {
                System.out.println("* * * * * " + patch.getName() + " * * * * *");
                canciones.addAll(getMusicas(patch));
            } else {
                if (patch.getName().endsWith(".mp3") || patch.getName().endsWith(".wav")) {
                    canciones.add(patch);
                    System.out.println("Nombre: " + patch.getName());
                }
            }
        }
        return canciones;
    }

    public void prePlay() {
        System.out.println(fL.jListaMusicas.getSelectedValue());
        new Thread() {
            @Override
            public void run() {
                if (!controlMusicaLocal) {
                    listaDeMusicas = null;
                    listaDeMusicas = daoM.buscarMusica(fL.jListaMusicas.getSelectedValue());
                }
                for (clsMusica items : listaDeMusicas) {
                    if (items.getNombre().equals(fL.jListaMusicas.getSelectedValue())) {
                        //ControladorReproductor.fR.jLabel1.setText(ControladorReproductor.d1.duracionMP3(patchL[fL.jListaMusicas.getSelectedIndex()]));
                        System.out.println(patchLocal);
                        ControladorReproductor.Play(items);
                        fL.dispose();
                    }
                }
            }
        }.start();
    }

    public class MyEventoBusqueda implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == fL.jTextField1) {
                listaDeMusicas = null;
                List = new DefaultListModel();
                fL.jListaMusicas.setModel(List);
                listaDeMusicas = daoM.buscarTodosMusica(fL.jTextField1.getText());
                for (clsMusica items : listaDeMusicas) {
                    List.addElement(items.toString());
                }
                fL.jListaMusicas.setEnabled(true);
            } else if (e.getSource() == fL.jListaMusicas) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    prePlay();
                }
            }
        }
    }
}
