package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBManager {

    private Connection cn;
    //Parametros de conexion
    final String cadenaConexion = "jdbc:oracle:thin:@192.168.56.1:1521:XE";
    //final String DB = "XE";
    final String User = "musica";
    final String password = "admin";

    public DBManager() {
        this.cn = null;
    }

    public Connection getConection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection(cadenaConexion, User, password);
            System.out.println("Conectado..");
        } catch (ClassNotFoundException ex) {
            ex.getStackTrace();
            System.out.println("Error1: " + ex.getMessage());
        } catch (SQLException ex) {
            String msn = ex.getMessage();
            if (msn.contains("logon denied")) {
                msn = "Usuarios Invalidos :(";
            } else if (msn.contains("connection")) {
                msn = "No tiene Base de dato :(\n o la cadena de conexion esta mal\n correcto es: jdbc:oracle:thin:@localhost:1521:XE";
            }
            ex.getStackTrace();
            System.out.println("Error2: " + ex.getMessage() + ":" + ex.getSQLState());
            JOptionPane.showMessageDialog(null, msn);
        }
        return cn;
    }
}
