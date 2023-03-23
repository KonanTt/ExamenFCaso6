/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloSocio;
import Modelo.Socios;
import Vista.VistaSocio;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import modelo.ModeloPersona;
import modelo.Persona;

/**
 *
 * @author Asus
 */
public class ControladorSocio {

    private ModeloSocio modelo;
    private VistaSocio vista;
    private JFileChooser jfc;

    public ControladorSocio(ModeloSocio modelo, VistaSocio vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
        cargaSocioTabla();
    }

    private void cargaSocioTabla() {
        vista.getTbSocio().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
        vista.getTbSocio().setRowHeight(100);

        DefaultTableModel tblModel;
        tblModel = (DefaultTableModel) vista.getTbSocio().getModel();
        tblModel.setNumRows(0);//limpio filas de la tabla.

        List<Socios> listap = modelo.listaSociosTabla();
        Holder<Integer> i = new Holder<>(0);

        listap.stream().forEach(pe -> {

            tblModel.addRow(new Object[8]);//Creo una fila vacia
            vista.getTbSocio().setValueAt(pe.getPer_cedula(), i.value, 0);
            vista.getTbSocio().setValueAt(pe.getNombre(), i.value, 1);
            vista.getTbSocio().setValueAt(pe.getDireccion(), i.value, 2);
            vista.getTbSocio().setValueAt(pe.getTelefono(), i.value, 3);
            vista.getTbSocio().setValueAt(pe.getDiscoTaxi(), i.value, 5);
            vista.getTbSocio().setValueAt(pe.getPlacaTaxi(), i.value, 6);
            vista.getTbSocio().setValueAt(pe.getMarcaTaxi(), i.value, 7);

            Image foto = pe.getImagen();
            if (foto != null) {

                Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon icono = new ImageIcon(nimg);
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setIcon(icono);
                vista.getTbSocio().setValueAt(new JLabel(icono), i.value, 4);

            } else {
                vista.getTbSocio().setValueAt(null, i.value, 4);
            }

            i.value++;
        });
    }
    

    public void iniciarControl() {
        vista.getBtnActualizar().addActionListener(l -> cargaSocioTabla());
        vista.getBtnCrear().addActionListener(l -> abrirDialogCrear());
        vista.getBtnModificar().addActionListener(l -> abrirYCargarDatosEnElDialog());
        vista.getBtnGuardar().addActionListener(l -> crearEditar());
        vista.getBtnEliminar().addActionListener(l -> eliminar());
        vista.getBtnExaminar().addActionListener(l -> seleccionarFoto());
        // vista.getBtnImprimir().addActionListener(l -> abrirJDlgImprimir());
        //vista.getBtngeneraReporte().addActionListener(l -> imprimirPersona());
        buscarSocio();
    }

    private void abrirDialogCrear() {
        vista.getjSocio().setName("Crear nuevo");
        vista.getjSocio().setLocationRelativeTo(vista);
        vista.getjSocio().setSize(1100, 500);
        vista.getjSocio().setTitle("Crear nuevo");
        vista.getjSocio().setVisible(true);

        //Quitar visibilidad del codigo del instructor
        LimpiarTablas();
    }

    private void LimpiarTablas() {
        vista.getTxtCedula().setText("");
        vista.getTxtNombre().setText(" ");
        vista.getTxtDireccion().setText(" ");
        vista.getTxtTelefono().setText(" ");
        vista.getTxtDisco().setText(" ");
        vista.getTxtDisco().setText(" ");
        vista.getTxtPlaca().setText(" ");
        vista.getTxtMarca().setText(" ");

    }

    private void abrirYCargarDatosEnElDialog() {

        int seleccion = vista.getTbSocio().getSelectedRow();

        if (seleccion == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            String cedula = vista.getTbSocio().getValueAt(seleccion, 0).toString();
            modelo.listaSociosTabla().forEach((pe) -> {
                if (pe.getPer_cedula().equals(cedula)) {

                    //Abre el jDialog y carga los datos en el jDialog
                    vista.getjSocio().setName("Editar");
                    vista.getjSocio().setLocationRelativeTo(vista);
                    vista.getjSocio().setSize(1100, 500);
                    vista.getjSocio().setTitle("Editar");
                    vista.getjSocio().setVisible(true);

                    vista.getTxtCedula().setText(pe.getPer_cedula());
                    vista.getTxtNombre().setText(pe.getNombre());
                    vista.getTxtDireccion().setText(pe.getDireccion());
                    vista.getTxtTelefono().setText(pe.getTelefono());
                    vista.getTxtDisco().setText(pe.getDiscoTaxi());
                    vista.getTxtPlaca().setText(pe.getPlacaTaxi());
                    vista.getTxtPlaca().setText(pe.getMarcaTaxi());
                    vista.getLabelFoto().setIcon(modelo.ConsultarFoto(cedula)); 
                }
            });
        }

    }

    private void crearEditar() {
        if ("Crear nuevo SOCIO".equals(vista.getjSocio().getName())) {
            //INSERTAR
            String cedula = vista.getTxtCedula().getText();
            String nombres = vista.getTxtNombre().getText();
            String direccion = vista.getTxtDireccion().getText();
            String telefono = vista.getTxtTelefono().getText();
            String disco = vista.getTxtDisco().getText();
            String placa = vista.getTxtPlaca().getText();
            String marca = vista.getTxtMarca().getText();
            ModeloSocio so = new ModeloSocio();
            so.setPer_cedula(cedula);
            so.setNombre(nombres);
            so.setDireccion(direccion);
            so.setTelefono(telefono);
            so.setDiscoTaxi(disco);
            so.setPlacaTaxi(placa);
            so.setMarcaTaxi(marca);

            if (vista.getLabelFoto().getIcon() == null) { //Verifico si el label esta vacio o no

                if (so.crearSocioNoFto()) {
                    vista.getjSocio().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Creada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear");
                }

            } else {

                //Foto
                try {

                    FileInputStream foto = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();

                    so.setFoto(foto);
                    so.setLongitud(longitud);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorSocio.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (so.crearPersonaFoto()) {
                    vista.getjSocio().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Creada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear ");
                }
            }

        } else {

            String cedula = vista.getTxtCedula().getText();
            String nombres = vista.getTxtNombre().getText();
            String direccion = vista.getTxtDireccion().getText();
            String telefono = vista.getTxtTelefono().getText();
            String disco = vista.getTxtDisco().getText();
            String placa = vista.getTxtPlaca().getText();
            String marca = vista.getTxtMarca().getText();

            ModeloSocio so = new ModeloSocio();
            so.setPer_cedula(cedula);
            so.setNombre(nombres);
            so.setDireccion(direccion);
            so.setTelefono(telefono);
            so.setDiscoTaxi(disco);
            so.setPlacaTaxi(placa);
            so.setMarcaTaxi(marca);

            if (vista.getLabelFoto().getIcon() == null) {
                if (so.modificarSocioF()) {

                    vista.getTbSocio().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Modificada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar");
                }
            } else {

                //Foto
                try {

                    FileInputStream img = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();
                    so.setFoto(img);
                    so.setLongitud(longitud);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorSocio.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (so.modificarSocioF()) {

                    vista.getjSocio().setVisible(false);
                    JOptionPane.showMessageDialog(vista, " Modificada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar");
                }
            }
        }

        cargaSocioTabla();
    }

    private void eliminar() {
        int fila = vista.getTbSocio().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            int response = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea eliminar la información?", "Confirme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {

                String cedula;
                cedula = vista.getTbSocio().getValueAt(fila, 0).toString();

                if (modelo.eliminarSocioNoCreada(cedula)) {
                    JOptionPane.showMessageDialog(null, "El socio fue eliminada exitosamente");
                    cargaSocioTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "El socio no se pudo eliminar");
                }
            }
        }

    }

    private void seleccionarFoto() {
        vista.getLabelFoto().setIcon(null);
        jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = jfc.showOpenDialog(null);

        if (estado == JFileChooser.APPROVE_OPTION) {
            try {
                Image imagen = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(vista.getLabelFoto().getWidth(), vista.getLabelFoto().getHeight(), Image.SCALE_DEFAULT);
                vista.getLabelFoto().setIcon(new ImageIcon(imagen));
                vista.getLabelFoto().updateUI();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex);
            }
        }
    }

    private void buscarSocio() {
        KeyListener eventoTeclado = new KeyListener() {//Crear un objeto de tipo keyListener(Es una interface) por lo tanto se debe implementar sus metodos abstractos

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {

                vista.getTbSocio().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
                vista.getTbSocio().setRowHeight(100);

                //Enlazar el modelo de tabla con mi controlador.
                DefaultTableModel tblModel;
                tblModel = (DefaultTableModel) vista.getTbSocio().getModel();
                tblModel.setNumRows(0);//limpio filas de la tabla.

                List<Socios> listap = modelo.buscarSocio(vista.getTxtBuscar().getText());//Enlazo al Modelo y obtengo los datos
                Holder<Integer> i = new Holder<>(0);//contador para el no. fila
                listap.stream().forEach(pe -> {

                    tblModel.addRow(new Object[9]);//Creo una fila vacia/
                    vista.getTbSocio().setValueAt(pe.getPer_cedula(), i.value, 0);
                    vista.getTbSocio().setValueAt(pe.getNombre(), i.value, 1);
                    vista.getTbSocio().setValueAt(pe.getDireccion(), i.value, 2);
                    vista.getTbSocio().setValueAt(pe.getTelefono(), i.value, 3);
                    vista.getTbSocio().setValueAt(pe.getFoto(), i.value, 4);
                    vista.getTbSocio().setValueAt(pe.getDiscoTaxi(), i.value, 5);
                    vista.getTbSocio().setValueAt(pe.getPlacaTaxi(), i.value, 6);
                    vista.getTbSocio().setValueAt(pe.getMarcaTaxi(), i.value, 7);

                    Image foto = pe.getImagen();
                    if (foto != null) {

                        Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon icono = new ImageIcon(nimg);
                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                        renderer.setIcon(icono);
                        vista.getTbSocio().setValueAt(new JLabel(icono), i.value, 9);

                    } else {
                        vista.getTbSocio().setValueAt(null, i.value, 9);
                    }

                    i.value++;
                });
            }
        };

        vista.getTxtBuscar().addKeyListener(eventoTeclado); //
    }

    public void cancelar() {
        vista.getjSocio().setVisible(false);
    }

}
