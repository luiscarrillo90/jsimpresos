/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author luis-pc
 */
public class ConexionNotas extends Conexion {

     public void guardarNota(String nombres, String apPaterno, String apMaterno, String telefono, String fechaEntrega, Abono anticipo, int tipoAbono, String observaciones, int idMiembro, ArrayList<Articulo> articulos, String domicilio) {
        boolean guardar = true;
        double resto = 0;
        String tipoPago = "Efectivo";
        try {
            if (tipoAbono == 1) {
                tipoPago = "Tarjeta";
                String consulta = "select * from miembros where idMiembro =?";
                PreparedStatement st1;
                st1 = this.getConexion().prepareStatement(consulta);
                st1.setInt(1, idMiembro);
                ResultSet rs = st1.executeQuery();
                while (rs.next()) {
                    if (anticipo.getMonto() > rs.getDouble(8)) {
                        JOptionPane.showMessageDialog(null, "La tarjeta tiene $" + rs.getDouble(8) + " de saldo e intentas pagar $" + anticipo.getMonto() + " realiza el pago en efectivo o recarga la tarjeta");
                        guardar = false;
                    } else {
                        resto = rs.getDouble(8) - anticipo.getMonto();
                        String querySaldo = "update miembros set saldo = ? where idMiembro=?";
                        PreparedStatement stSaldo = this.getConexion().prepareStatement(querySaldo);
                        stSaldo.setDouble(1, resto);
                        stSaldo.setInt(2, idMiembro);
                        stSaldo.executeUpdate();
                    }
                }

            }
            if (guardar) {
                String query = "insert into notas values(default,?,?,?,?, default, ?, ?, 1,?,'no', ?)";
                PreparedStatement st;
                int clave = 0;
                st = this.getConexion().prepareStatement(query);
                if (idMiembro == 1) {
                    st.setString(1, nombres);
                    st.setString(2, apPaterno);
                    st.setString(3, apMaterno);
                    st.setString(4, telefono);
                    st.setString(5, fechaEntrega);
                    st.setString(6, observaciones);
                    st.setInt(7, idMiembro);
                    st.setString(8, domicilio);
                    st.executeUpdate();
                    st = this.getConexion().prepareStatement("select last_insert_id()");
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        clave = rs.getInt("last_insert_id()");
                    }

                } else {
                    st.setString(1, "no");
                    st.setString(2, "no");
                    st.setString(3, "no");
                    st.setString(4, telefono);
                    st.setString(5, fechaEntrega);
                    st.setString(6, observaciones);
                    st.setInt(7, idMiembro);
                    st.setString(8, domicilio);
                    st.executeUpdate();
                    st = this.getConexion().prepareStatement("select last_insert_id()");
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        clave = rs.getInt("last_insert_id()");
                    }
                }
                for (int i = 0; i < articulos.size(); i++) {
                    String queryAux = "insert into detalleservicios values(?,?,?,?)";
                    PreparedStatement stAux;
                    stAux = this.getConexion().prepareStatement(queryAux);
                    stAux.setInt(1, clave);
                    stAux.setString(2, articulos.get(i).getServicio());
                    stAux.setDouble(3, articulos.get(i).getPrecio());
                    stAux.setInt(4, articulos.get(i).getCantidad());
                    stAux.executeUpdate();
                }
                String queryAbono = "insert into detalleabono values(?,?,?,default,default, default)";
                PreparedStatement stAbono = this.getConexion().prepareStatement(queryAbono);
                stAbono.setInt(1, clave);
                stAbono.setDouble(2, anticipo.getMonto());
                stAbono.setString(3, tipoPago);
                stAbono.executeUpdate();
                this.generarPdf(clave);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar al servidor");
            System.out.println(ex.getMessage());
        }
    }
    public Nota getNotaPorId(int id) {
        Nota nota = null;
        String query = "select * from notas where idnota = ?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                nota = new Nota(rs.getInt(1), rs.getString(2), null, rs.getString(3), rs.getString(4), rs.getString(5), Fechas.convertirFecha(rs.getString(6)), rs.getString(7), rs.getString(8), null, null, rs.getString(11), rs.getString(12));
                if (rs.getInt(10) != 1) {
                    query = "select * from miembros where idmiembro = ?";
                    PreparedStatement auxst = this.getConexion().prepareStatement(query);
                    auxst.setInt(1, rs.getInt(10));
                    ResultSet aux = auxst.executeQuery();
                    while (aux.next()) {
                        nota.setCliente(new Miembro(aux.getInt(1), aux.getString(2), aux.getString(3), aux.getString(4), aux.getString(5), aux.getString(6), aux.getString(7), aux.getDouble(8), aux.getString(9),aux.getString(10), aux.getString(11)));
                    }
                }

            }

            query = "select * from detalleservicios where idnota=?";
            st = this.getConexion().prepareStatement(query);
            st.setInt(1, id);
            ArrayList<Articulo> articulos = new ArrayList();
            rs = st.executeQuery();
            
            while (rs.next()) {
                articulos.add(new Articulo(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4)));
            }
            nota.setArticulos(articulos);
            query = "select * from detalleabono where idnota=?";
            st = this.getConexion().prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            ArrayList<Abono> abonos = new ArrayList();
            while (rs.next()) {
                abonos.add(new Abono(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), Fechas.convertirFecha(rs.getString(5)),rs.getInt(6)));
            }
            nota.setAbonos(abonos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }

        return nota;
    }
    public ArrayList <Nota> obtenerVistaNotas(){
        ArrayList <Nota> notas= new ArrayList();
        String query = "select * from notas;";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                if(rs.getInt(10)==1){
                    notas.add(new Nota(rs.getInt(1), rs.getString(2), null,rs.getString(3), rs.getString(4), rs.getString(5), Fechas.convertirFecha(rs.getString(6)), rs.getString(7), rs.getString(8), null, null, rs.getString(11),rs.getString(12)));
                }else{
                    String query2 = "select * from miembros where idmiembro=?";
                    PreparedStatement st2 = this.getConexion().prepareStatement(query2);
                    st2.setInt(1, rs.getInt(10));
                    ResultSet rs2 = st2.executeQuery();
                    while(rs2.next()){
                        notas.add(new Nota(rs.getInt(1), rs2.getString(3), null,rs2.getString(4), rs2.getString(5), rs.getString(5), Fechas.convertirFecha(rs.getString(6)), rs.getString(7), rs.getString(8), null, null, rs.getString(11),rs.getString(12)));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
        return notas;
    }
    public boolean guardarAbono(Abono abono){
        String query = "insert into detalleabono values(?,?,?,?,default, default)";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setInt(1, abono.getId());
            st.setDouble(2, abono.getMonto());
            st.setString(3, abono.getTipoPago());
            st.setString(4, "no");
            st.executeUpdate();
            if(abono.getTipoPago().equals("Tarjeta")){
             String query2 = "update miembros set saldo = saldo-? where(select idmiembro from notas where idnota=?)";
             PreparedStatement st2 = this.getConexion().prepareStatement(query2);
             st2.setDouble(1, abono.getMonto());
             st2.setInt(2, abono.getId());
             st2.executeUpdate();
             generarPdf(abono.getId());
            }
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
            return false;
        }
    }
    public ArrayList<Abono> obtenerAbonosPorId(int idNota){
        ArrayList<Abono> abonos = new ArrayList();
        String query = "select * from detalleabono where idnota=?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setInt(1, idNota);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                abonos.add(new Abono(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), Fechas.convertirFecha(rs.getString(5)),rs.getInt(6)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los pagos");
        }
        
        return abonos;
    }
    public boolean terminarNota(Nota nota){
        String query = "update notas set terminado = ? where idnota=?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setString(1, "si");
            st.setInt(2, nota.getIdNota());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
            return false;
        }
        
    }
    public void generarPdf(int idNota) {
        Document documento = new Document();
        Nota nota = this.getNotaPorId(idNota);
        FileOutputStream ficheroPdf;
        try {
            ficheroPdf = new FileOutputStream("c:/fichero.pdf");
            PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
            documento.setMargins(0, 0, 0, 0);
            documento.open();
            Image foto = Image.getInstance("logo.JPG");
            foto.scaleToFit(80, 90);
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(new float[]{13, 12, 25, 25, 25});
            tabla.setWidthPercentage(100);
            PdfPCell celda = new PdfPCell();
            celda.addElement(foto);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setRowspan(6);
            tabla.addCell(celda);
            PdfPCell celda2 = new PdfPCell(new Paragraph("NOTA DE PEDIDO", FontFactory.getFont("Arial", 15, Font.BOLD)));
            celda2.setColspan(3);
            celda2.setUseAscender(true);
            celda2.setBorder(Rectangle.NO_BORDER);
            celda2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(celda2);
            PdfPCell celdaFecha = new PdfPCell(new Paragraph("Fecha: " + nota.getFecha()));
            celdaFecha.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(celdaFecha);
            PdfPCell celda3 = new PdfPCell(new Phrase("Calle del Rayo y 2 de Abril 31a. Col. centro, Parral, Chih.", FontFactory.getFont("Arial", 7, Font.BOLD, BaseColor.RED)));
            celda3.setColspan(2);
            celda3.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(celda3);
            PdfPCell celda4 = new PdfPCell(new Phrase("Tel. 5230073 y 6271034745", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.RED)));
            celda4.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(celda4);
            Paragraph p = new Paragraph("No. " + nota.getIdNota(), FontFactory.getFont("Arial", 13, Font.BOLD, BaseColor.RED));
            PdfPCell celda5 = new PdfPCell();
            celda5.setUseAscender(true);
            celda5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda5.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            celda5.addElement(p);
            tabla.addCell(celda5);
            PdfPCell celdanueva = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celdanueva.setBorder(Rectangle.NO_BORDER);
            celdanueva.setColspan(4);
            tabla.addCell(celdanueva);
            PdfPCell celdanueva1 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celdanueva1.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(celdanueva1);
            PdfPCell celdanueva2 = new PdfPCell(new Phrase("Datos del cliente", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celdanueva2.setBorder(Rectangle.NO_BORDER);
            celdanueva2.setColspan(3);
            tabla.addCell(celdanueva2);
            PdfPCell celdanueva3 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celdanueva3.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(celdanueva3);
            if (nota.getCliente() == null) {
                PdfPCell celda6 = new PdfPCell(new Phrase("Cliente:        " + nota.getNombres() + " " + nota.getApPaterno(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda6.setBorder(Rectangle.NO_BORDER);
                celda6.setColspan(3);
                tabla.addCell(celda6);
                PdfPCell celdanueva4 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celdanueva4.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celdanueva4);
                PdfPCell celda7 = new PdfPCell(new Phrase("Domicilio:       "+nota.getDomicilio(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda7.setBorder(Rectangle.NO_BORDER);
                celda7.setColspan(2);
                tabla.addCell(celda7);
                PdfPCell celda8 = new PdfPCell(new Phrase("Teléfono: " + nota.getTelefono(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda8.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celda8);

            } else {
                PdfPCell celda6 = new PdfPCell(new Phrase("Cliente:        " + nota.getCliente().getNombres() + " " + nota.getCliente().getApPaterno(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda6.setBorder(Rectangle.NO_BORDER);
                celda6.setColspan(3);
                tabla.addCell(celda6);
                PdfPCell celdanueva4 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celdanueva4.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celdanueva4);
                PdfPCell celda7 = new PdfPCell(new Phrase("Domicilio:     "+nota.getCliente().getDomicilio(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda7.setBorder(Rectangle.NO_BORDER);
                celda7.setColspan(2);
                tabla.addCell(celda7);
                PdfPCell celda8 = new PdfPCell(new Phrase("Teléfono: " + nota.getCliente().getTelefono(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda8.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celda8);
            }
            PdfPTable tabla2 = new PdfPTable(4);
            tabla2.setWidths(new float[]{10, 70, 15, 15});
            tabla2.setWidthPercentage(99.5f);
            PdfPCell celda21 = new PdfPCell(new Phrase("Cantidad", FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.BLACK)));
            tabla2.addCell(celda21);
            PdfPCell celda22 = new PdfPCell(new Phrase("Artículo", FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.BLACK)));
            tabla2.addCell(celda22);
            PdfPCell celda23 = new PdfPCell(new Phrase("Precio unitario", FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.BLACK)));
            tabla2.addCell(celda23);
            PdfPCell celda24 = new PdfPCell(new Phrase("Total", FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.BLACK)));
            tabla2.addCell(celda24);
            double total = 0;
            for (int i = 0; i < 6; i++) {
                if (i < nota.getArticulos().size()) {
                    PdfPCell celda25 = new PdfPCell(new Phrase(nota.getArticulos().get(i).getCantidad() + "", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                    tabla2.addCell(celda25);
                    PdfPCell celda26 = new PdfPCell(new Phrase(nota.getArticulos().get(i).getServicio() + "", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                    tabla2.addCell(celda26);
                    PdfPCell celda27 = new PdfPCell(new Phrase(nota.getArticulos().get(i).getPrecio() + "", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                    tabla2.addCell(celda27);
                    PdfPCell celda28 = new PdfPCell(new Phrase((nota.getArticulos().get(i).getCantidad() * nota.getArticulos().get(i).getPrecio()) + "", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                    tabla2.addCell(celda28);
                    total += nota.getArticulos().get(i).getCantidad() * nota.getArticulos().get(i).getPrecio();
                } else {
                    PdfPCell celda29 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                    tabla2.addCell(celda29);
                    tabla2.addCell(celda29);
                    tabla2.addCell(celda29);
                    tabla2.addCell(celda29);
                }

            }
            PdfPTable tabla3 = new PdfPTable(4);
            tabla3.setWidths(new float[]{55, 25, 15, 15});
            tabla3.setWidthPercentage(99.5f);
            PdfPCell celda31 = new PdfPCell(new Phrase("Observaciones: " + nota.getObservaciones(), FontFactory.getFont("Arial", 9, BaseColor.BLACK)));
            celda31.setRowspan(4);
            tabla3.addCell(celda31);
            PdfPCell celda32 = new PdfPCell(new Phrase("Suma recibida de abono : " + nota.getAbonos().get(nota.getAbonos().size() - 1).getMonto(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celda32.setRowspan(4);
            tabla3.addCell(celda32);
            PdfPCell celda33 = new PdfPCell(new Phrase("Total", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda33);
            PdfPCell celda34 = new PdfPCell(new Phrase(total + "", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda34);
            PdfPCell celda35 = new PdfPCell(new Phrase("Total abonado", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda35);
            double totalAbonado = 0;
            for (int i = 0; i < nota.getAbonos().size(); i++) {
                totalAbonado += nota.getAbonos().get(i).getMonto();

            }
            PdfPCell celda36 = new PdfPCell(new Phrase(totalAbonado + "", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda36);
            PdfPCell celda37 = new PdfPCell(new Phrase("Saldo actual", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda37);
            PdfPCell celda38 = new PdfPCell(new Phrase((total - totalAbonado) + "", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda38);
            PdfPCell celda39 = new PdfPCell(new Phrase("Fecha entrega", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda39);
            PdfPCell celda40 = new PdfPCell(new Phrase(nota.getFechaEntrega(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            tabla3.addCell(celda40);
            PdfPTable tabla4 = new PdfPTable(3);
            tabla4.setWidths(new float[]{30, 30, 30});
            tabla4.setWidthPercentage(99.5f);
            PdfPCell celda41 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celda41.setBorder(Rectangle.NO_BORDER);
            celda41.setColspan(3);
            tabla4.addCell(celda41);
            PdfPCell celda42 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celda42.setBorder(Rectangle.NO_BORDER);
            tabla4.addCell(celda42);
            PdfPCell celda43 = new PdfPCell(new Phrase("Firma de conformidad del cliente: ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celda43.setBorder(Rectangle.NO_BORDER);
            tabla4.addCell(celda43);
            PdfPCell celda44 = new PdfPCell(new Phrase(" ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
            celda44.setBorder(Rectangle.NO_BORDER);
            tabla4.addCell(celda44);
            tabla4.addCell(celda41);
            tabla4.addCell(celda41);
            tabla4.addCell(celda41);
            documento.add(tabla);
            documento.add(tabla2);
            documento.add(tabla3);
            documento.add(tabla4);
            documento.add(tabla);
            documento.add(tabla2);
            documento.add(tabla3);
            documento.add(tabla4);

            documento.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error con el pdf");
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar pdf");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error con el pdf");
        }
    }

}


