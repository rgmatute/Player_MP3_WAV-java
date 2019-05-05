package Model;

import DB.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Ronny matute
 */
public class DAOMusica {

    private Connection con;
    private Statement sent;
    private DBManager dbm;
    private ResultSet res;
    private PreparedStatement sentPrep;

    public ArrayList<clsMusica> buscarTodosMusica(String musica) {
        ArrayList<clsMusica> lista = null;

        // obtener una conexion
        dbm = new DBManager();
        con = dbm.getConection();
        if (con == null) {
            return null;
        }
        //consultar sobre la base de datos
        String sql = "";
        if (musica.equals("all")) {
            sql = "SELECT m.M_Codigo , m.M_Nombre FROM MUSICA.CANCIONES m Order by m.M_Nombre";
        } else if (musica.length() == 0) {
            sql = "SELECT m.M_Codigo , m.M_Nombre FROM MUSICA.CANCIONES m WHERE m.M_Nombre = '-1'";
        } else {
            sql = "SELECT m.M_Codigo , m.M_Nombre FROM MUSICA.CANCIONES m WHERE LOWER(m.M_Nombre) LIKE'%" + musica + "%'";
        }
        try {
            sent = con.createStatement();
            res = sent.executeQuery(sql);
            if (res != null) {
                lista = new ArrayList();
                //Mapear resgistros de la tabla tipo a objeto
                clsMusica cM;
                while (res.next()) {
                    //Musicas
                    cM = new clsMusica();
                    cM.setCodigo(res.getString("M_Codigo"));
                    cM.setNombre(res.getString("M_Nombre"));
                    //cM.setMusica(res.getBytes("M_Musica"));
                    lista.add(cM);
                }
            }
        } catch (SQLException sqle) {
            sqle.getStackTrace();
            JOptionPane.showMessageDialog(null, "Error algo malo sucedio :(  \n" + sqle.getMessage());
        } finally {
            //cerrar conexion
            try {
                con.close();
                System.out.println("Desconectado..");
            } catch (SQLException sqle) {
                sqle.getStackTrace();
                JOptionPane.showMessageDialog(null, "Error algo malo sucedio2 :(  \n" + sqle.getMessage());
            }
        }
        return lista;
    }

    public ArrayList<clsMusica> buscarMusica(String musica) {
        ArrayList<clsMusica> lista = null;

        // obtener una conexion
        dbm = new DBManager();
        con = dbm.getConection();
        if (con == null) {
            return null;
        }
        //consultar sobre la base de datos
        String sql = "";

        sql = "SELECT * FROM MUSICA.CANCIONES m WHERE m.M_Nombre = '" + musica + "'";

        try {
            sent = con.createStatement();
            res = sent.executeQuery(sql);
            if (res != null) {
                lista = new ArrayList();
                //Mapear resgistros de la tabla tipo a objeto
                clsMusica cM;
                while (res.next()) {
                    //Musicas
                    cM = new clsMusica();
                    //cM.setCodigo(res.getString("M_Codigo"));
                    cM.setNombre(res.getString("M_Nombre"));
                    cM.setMusica(res.getBytes("M_Musica"));
                    lista.add(cM);
                }
            }
        } catch (SQLException sqle) {
            sqle.getStackTrace();
            JOptionPane.showMessageDialog(null, "Error algo malo sucedio :(  \n" + sqle.getMessage());
        } finally {
            //cerrar conexion
            try {
                con.close();
                System.out.println("Desconectado..");
            } catch (SQLException sqle) {
                sqle.getStackTrace();
                JOptionPane.showMessageDialog(null, "Error algo malo sucedio2 :(  \n" + sqle.getMessage());
            }
        }
        return lista;
    }

    public boolean registrarMusica(clsMusica cM) {
        dbm = new DBManager();
        con = dbm.getConection();
        if (con == null) {
            return false;
        }
        boolean resultado = false;
        String sql = "CALL insertMusicas(?,?,?)";

        try {
            sentPrep = con.prepareStatement(sql);
            sentPrep.setString(1, cM.getCodigo());
            sentPrep.setString(2, cM.getNombre());
            sentPrep.setBytes(3, cM.getMusica());

            int NumFilasAfectadas = sentPrep.executeUpdate();
            if (NumFilasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro con exito. .");
                System.out.println("Registro con exito..");
            }
            System.out.println("NFA: " + NumFilasAfectadas);
            resultado = true;
        } catch (SQLException sqle) {
            sqle.getStackTrace();
            JOptionPane.showMessageDialog(null, "Error algo malo sucedio :(  \n" + sqle.getMessage());
        } finally {
            try {
                con.close();
                System.out.println("Desconectado..");
            } catch (Exception sqle) {
                sqle.getStackTrace();
            }
        }
        return resultado;
    }

    public boolean modificarMusica(clsMusica cM) {
        dbm = new DBManager();
        con = dbm.getConection();
        if (con == null) {
            return false;
        }
        boolean resultado = false;
        String sql = "Update  cine.Empleado emp SET emp.ENombres=?, emp.ETelefono=?, emp.EUsuario=?, emp.EContraseña=? , emp.EFoto=?, emp.Eid_TipoEmpleado=? WHERE emp.ECedula=?";

        try {
            sentPrep = con.prepareStatement(sql);
            sentPrep.setString(1, cM.getNombre());
            sentPrep.setBytes(2, cM.getMusica());
            sentPrep.setString(3, cM.getCodigo());
            int NumFilasAfectadas = sentPrep.executeUpdate();
            if (NumFilasAfectadas > 0) {
                resultado = true;
                JOptionPane.showMessageDialog(null, "Modificado con exito. .");
                System.out.println("Modificado con exito..");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error algo malo sucedio :(  \n" + sqle.getMessage());
        } finally {
            try {
                con.close();
                System.out.println("Desconectado..");
            } catch (Exception sqle) {
                sqle.getStackTrace();
            }
        }
        return resultado;
    }

    public boolean eliminarMusica(String codigo) {
        dbm = new DBManager();
        con = dbm.getConection();
        if (con == null) {
            return false;
        }
        boolean resultado = false;
        String sql = "DELETE FROM cine.Empleado emp WHERE emp.ECedula=?";

        try {
            sentPrep = con.prepareStatement(sql);
            sentPrep.setString(1, codigo);
            int NumFilasAfectadas = sentPrep.executeUpdate();
            if (NumFilasAfectadas > 0) {
                resultado = true;
                JOptionPane.showMessageDialog(null, "Eliminado con exito. .");
                System.out.println("Eliminado con exito..");
            }
        } catch (SQLException sqle) {
            sqle.getStackTrace();
            JOptionPane.showMessageDialog(null, "Error algo malo sucedio :(  \n" + sqle.getMessage());
        } finally {
            try {
                con.close();
                System.out.println("Desconectado..");
            } catch (Exception sqle) {
                sqle.getStackTrace();
            }
        }
        return resultado;
    }
}
