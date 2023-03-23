package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionPG {

    Connection con;

    String cadenaConexion = "jdbc:postgresql://localhost:5432/caso6";
    String usuarioPG = "postgres";
    String passPG = "1234";

    public ConexionPG() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            con = DriverManager.getConnection(cadenaConexion, usuarioPG, passPG);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet consulta(String sql) {

        try {
            Statement st = con.createStatement();
            return st.executeQuery(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean accion(String sql) {
        boolean correcto;

        try {
            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
            correcto = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionPG.class.getName()).log(Level.SEVERE, null, ex);//NO PONER EL LOGGER, CASO CONTRARIO SALE LA EXECEPCION EN LA CONSOLA
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            correcto = false;
        }

        return correcto;
    }

    public Connection getCon() {
        return con;
    }
}
