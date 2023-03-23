/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author Asus
 */
public class ModeloSocio extends Socios {

    ConexionPG conpg = new ConexionPG();

    public ModeloSocio() {
    }

    public ModeloSocio(String per_cedula, String nombre, String telefono, String direccion, String discoTaxi, String placaTaxi, String marcaTaxi, FileInputStream foto, int longitud, Image imagen) {
        super(per_cedula, nombre, telefono, direccion, discoTaxi, placaTaxi, marcaTaxi, foto, longitud, imagen);
    }

    

    public List<Socios> listaSociosTabla() {
        try {
            List<Socios> lista = new ArrayList<>();

            String sql = "select * from socio";

            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"
            byte[] bytea;

            while (rs.next()) {
                //Crear las instancias de las personas
                Socios socioo = new Socios();

                socioo.setPer_cedula(rs.getString("so_cedula"));
                socioo.setNombre(rs.getString("so_nombre"));
                socioo.setTelefono(rs.getString("so_telefono"));
                socioo.setDireccion(rs.getString("so_direcion"));
                socioo.setDiscoTaxi(rs.getString("so_discoTaxi"));
                socioo.setPlacaTaxi(rs.getString("so_placaTaxi"));
                socioo.setMarcaTaxi(rs.getString("so_marcaTaxi"));
                bytea = rs.getBytes("so_foto");

                if (bytea != null) {

                    try {
                        socioo.setImagen(obtenerImagen(bytea));
                    } catch (IOException ex) {
                        Logger.getLogger(modelo.ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                lista.add(socioo); //Agrego los datos a la lista
            }

            rs.close();
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(Modelo.ModeloSocio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean crearSocioNoFto() {

        String sql = "INSERT INTO socio(so_cedula,so_nombre, so_telefono, so_direccion,so_foto, so_discoTaxi, so_placaTaxi, so_marcaTaxi) VALUES ('" + getPer_cedula() + "', '" + getNombre() + "', '" + getTelefono() + "', '" + getDireccion() + "', '" + ", null,'" + "'," + getDiscoTaxi() + ", " + getPlacaTaxi() + getMarcaTaxi() + "');";

        return conpg.accion(sql);
    }

    public boolean modificarSocioF() {
        try {
            String sql;

            sql = "UPDATE socio SET so_nombre=?,so_direccion=?,so_telefono=?,so_foto=?,so_discoTaxi=?,so_placaTaxi=?,marcaTaxi=? Where so_cedula=?";
            PreparedStatement ps = conpg.getCon().prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getDireccion());
            ps.setString(3, getTelefono());
            ps.setBinaryStream(4, getFoto(), getLongitud());
            ps.setString(5, getDiscoTaxi());
            ps.setString(6, getPlacaTaxi());
            ps.setString(7, getMarcaTaxi());
            ps.setString(8, getPer_cedula());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Modelo.ModeloSocio.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminarSocioNoCreada(String cedula) {
        String sql = "DELETE FROM persona where so_cedula = '" + cedula + "';";

        return conpg.accion(sql);
    }

    public boolean crearPersonaFoto() {
        try {
            String sql;

            sql = "INSERT INTO socio(so_cedula,so_nombre, so_telefono, so_direccion, so_foto,so_discoTaxi, so_placaTaxi, so_marcaTaxi)";
            sql += "VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conpg.getCon().prepareStatement(sql);
            ps.setString(1, getPer_cedula());
            ps.setString(2, getNombre());
            ps.setString(3, getDireccion());
            ps.setString(4, getTelefono());
            ps.setBinaryStream(5, getFoto(), getLongitud());
            ps.setString(6, getDiscoTaxi());
            ps.setString(7, getPlacaTaxi());
            ps.setString(8, getMarcaTaxi());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(modelo.ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Image obtenerImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator it = ImageIO.getImageReadersByFormatName("png");
        ImageReader reader = (ImageReader) it.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceSubsampling(1, 1, 0, 0);
        return reader.read(0, param);
    }

    public ImageIcon ConsultarFoto(String cedula) {
        conpg.getCon();
        String sql = "select foto from \"socio\" where so_cedula = '" + cedula + "';";
        ImageIcon foto;
        InputStream is;

        try {
            ResultSet rs = conpg.consulta(sql);
            while (rs.next()) {

                is = rs.getBinaryStream(1);

                BufferedImage bi = ImageIO.read(is);
                foto = new ImageIcon(bi);

                Image img = foto.getImage();
                Image newimg = img.getScaledInstance(118, 139, java.awt.Image.SCALE_SMOOTH);

                ImageIcon newicon = new ImageIcon(newimg);
                return newicon;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public List<Socios> buscarSocio(String cedula) {
        try {
            List<Socios> lista = new ArrayList<>();

            String sql = "Select * from socio where so_cedula like '" + cedula + "%'";

            ResultSet rs = conpg.consulta(sql); 
            byte[] bytea;

            while (rs.next()) {
                Socios socioss = new Socios();

                socioss.setPer_cedula(rs.getString("so_cedula"));
                socioss.setNombre(rs.getString("so_nombre"));
                socioss.setDireccion(rs.getString("so_direccion"));
                socioss.setTelefono(rs.getString("so_telefono"));
                bytea = rs.getBytes("so_foto");
                socioss.setDiscoTaxi(rs.getString("so_dicoTaxi"));
                socioss.setPlacaTaxi(rs.getString("so_placaTaxi"));
                socioss.setMarcaTaxi(rs.getString("so_marcaTaxi"));

                if (bytea != null) {

                    try {
                        socioss.setImagen(obtenerImagen(bytea));
                    } catch (IOException ex) {
                        Logger.getLogger(modelo.ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                lista.add(socioss); 
            }

            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Modelo.ModeloSocio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
