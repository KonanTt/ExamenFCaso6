package Modelo;

import java.awt.Image;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.List;

public class Socios {

    private String per_cedula;
    private String nombre;
    private String telefono;
   private String direccion;
   private String discoTaxi;
      private String placaTaxi;
   private String marcaTaxi;

   
    private FileInputStream foto;
    private int longitud;
    private Image imagen;

    public Socios() {
    }

    public Socios(String per_cedula, String nombre, String telefono, String direccion, String discoTaxi, String placaTaxi, String marcaTaxi, FileInputStream foto, int longitud, Image imagen) {
        this.per_cedula = per_cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.discoTaxi = discoTaxi;
        this.placaTaxi = placaTaxi;
        this.marcaTaxi = marcaTaxi;
        this.foto = foto;
        this.longitud = longitud;
        this.imagen = imagen;
    }

    public String getPer_cedula() {
        return per_cedula;
    }

    public void setPer_cedula(String per_cedula) {
        this.per_cedula = per_cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDiscoTaxi() {
        return discoTaxi;
    }

    public void setDiscoTaxi(String discoTaxi) {
        this.discoTaxi = discoTaxi;
    }

    public String getPlacaTaxi() {
        return placaTaxi;
    }

    public void setPlacaTaxi(String placaTaxi) {
        this.placaTaxi = placaTaxi;
    }

    public String getMarcaTaxi() {
        return marcaTaxi;
    }

    public void setMarcaTaxi(String marcaTaxi) {
        this.marcaTaxi = marcaTaxi;
    }

    public FileInputStream getFoto() {
        return foto;
    }

    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

  

   
}
 