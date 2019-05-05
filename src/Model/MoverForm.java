package Model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Ronny matute
 */
public class MoverForm implements MouseListener, MouseMotionListener {

    private JLabel obj1;
    private JFrame obj2;
    private int xMouse;
    private int yMouse;

    public MoverForm() {
    }

    public void setObjec(JLabel obj1, JFrame obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.obj1.addMouseListener(this);
        this.obj1.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.xMouse = e.getX();
        this.yMouse = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();
        this.obj2.setLocation(x - xMouse, y - yMouse);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
