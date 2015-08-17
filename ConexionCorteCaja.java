/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class ConexionCorteCaja extends Conexion {

    public void hacerCorte() {
        String query1 = "insert into cortesdecaja values(default, default)";
        try {
            PreparedStatement st1 = this.getConexion().prepareStatement(query1);
            st1.execute();
            st1 = this.getConexion().prepareStatement("select last_insert_id()");
            ResultSet rs = st1.executeQuery();
            int clave = 0;
            while (rs.next()) {
                clave = rs.getInt("last_insert_id()");
            }
            String query2 = "update detalleabono set idcorte=? where idcorte=1";
            PreparedStatement st2 = this.getConexion().prepareStatement(query2);
            st2.setInt(1, clave);
            st2.executeUpdate();
            String query3 = "update transaccionesmiembro set idcorte=? where idcorte=1";
            PreparedStatement st3 = this.getConexion().prepareStatement(query3);
            st3.setInt(1, clave);
            st3.executeUpdate();
            crearPdf(clave);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }

    }
    public ArrayList<CorteCaja> obtenerCortes(){
        ArrayList <CorteCaja> cortes = new ArrayList();
        String query = "select * from cortesdecaja where idCorte <>1";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                cortes.add(new CorteCaja(rs.getInt(1), Fechas.convertirFecha(rs.getString(2)),null, null));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
        return cortes;
    }
    public CorteCaja obtenerCortePorId(int idCorte){
        ArrayList <Abono> abonos = new ArrayList();
        ArrayList <TransaccionesMiembro> transacciones = new ArrayList();
        String query1 = "select * from detalleabono where idcorte=?";
        CorteCaja corte=null;
        try {
            PreparedStatement st1 = this.getConexion().prepareStatement(query1);
            st1.setInt(1, idCorte);
            ResultSet rs1 = st1.executeQuery();
            while(rs1.next()){
                abonos.add(new Abono(rs1.getInt(1),rs1.getDouble(2), rs1.getString(3), rs1.getString(4), Fechas.convertirFecha(rs1.getString(5)), rs1.getInt(6)));
            }
            String query2 = "select * from transaccionesmiembro where idcorte = ?";
            PreparedStatement st2 = this.getConexion().prepareStatement(query2);
            st2.setInt(1, idCorte);
            ResultSet rs2 = st2.executeQuery();
            while(rs2.next()){
                transacciones.add(new TransaccionesMiembro(rs2.getInt(1), rs2.getString(2),rs2.getDouble(3), Fechas.convertirFecha(rs2.getString(4)), rs2.getInt(5)));
            }
            String query3 = "select * from cortesdecaja where idcorte =?";
            PreparedStatement st3 = this.getConexion().prepareStatement(query3);
            st3.setInt(1, idCorte);
            ResultSet rs3 = st3.executeQuery();
            while(rs3.next()){
                corte = new CorteCaja(rs3.getInt(1), Fechas.convertirFecha(rs3.getString(2)), abonos, transacciones);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
        
        return corte;
    }
    public void crearPdf(int idCorte) {
        try {
            String query1 = "select * from detalleabono where idcorte=?";
            PreparedStatement st = this.getConexion().prepareStatement(query1);
            st.setInt(1, idCorte);
            ResultSet rs = st.executeQuery();
            ArrayList<Abono> abonos = new ArrayList();
            while (rs.next()) {
                abonos.add(new Abono(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            String query2 = "select * from transaccionesmiembro where idcorte=?";
            PreparedStatement st2 = this.getConexion().prepareStatement(query2);
            st2.setInt(1, idCorte);
            ResultSet rs2 = st2.executeQuery();
            ArrayList<TransaccionesMiembro> transaccionesMiembro = new ArrayList();
            while (rs2.next()) {
                transaccionesMiembro.add(new TransaccionesMiembro(rs2.getInt(1), rs2.getString(2), rs2.getDouble(3), rs2.getString(4), rs2.getInt(5)));
            }
            String query3 = "select * from cortesdecaja where idcorte =?";
            PreparedStatement st3 = this.getConexion().prepareStatement(query3);
            st3.setInt(1, idCorte);
            ResultSet rs3 = st3.executeQuery();
            String fecha = "";
            while (rs3.next()) {
                fecha = Fechas.convertirFecha(rs3.getString(2));
            }
            ArrayList<Abono> abonosEfectivo = new ArrayList();
            ArrayList<Abono> abonosTarjeta = new ArrayList();
            ArrayList<TransaccionesMiembro> recargasSaldo = new ArrayList();
            ArrayList<TransaccionesMiembro> transaccionesPaypal = new ArrayList();
            ArrayList<TransaccionesMiembro> registrosCliente = new ArrayList();
            for (int i = 0; i < abonos.size(); i++) {
                String tipo = abonos.get(i).getTipoPago();
                switch (tipo) {
                    case "Efectivo":
                        abonosEfectivo.add(abonos.get(i));
                        break;
                    case "Tarjeta":
                        abonosTarjeta.add(abonos.get(i));
                        break;
                }

            }
            for (int i = 0; i < transaccionesMiembro.size(); i++) {
                String tipo = transaccionesMiembro.get(i).getMovimiento();
                switch (tipo) {
                    case "Recarga":
                        recargasSaldo.add(transaccionesMiembro.get(i));
                        break;
                    case "Paypal":
                        transaccionesPaypal.add(transaccionesMiembro.get(i));
                        break;
                    case "Registro":
                        registrosCliente.add(transaccionesMiembro.get(i));
                        break;
                }

            }
            int mayorEfectivo;
            if (abonosEfectivo.size() >= recargasSaldo.size() && abonosEfectivo.size() >= registrosCliente.size()) {
                mayorEfectivo = abonosEfectivo.size();
            } else {
                if (recargasSaldo.size() >= registrosCliente.size()) {
                    mayorEfectivo = recargasSaldo.size();
                } else {
                    mayorEfectivo = registrosCliente.size();
                }
            }
            int mayorOtros;
            if (abonosTarjeta.size() >= transaccionesPaypal.size()) {
                mayorOtros = abonosTarjeta.size();
            } else {
                mayorOtros = transaccionesPaypal.size();
            }
            Document documento = new Document();
            FileOutputStream ficheroPdf;
            try {
                ficheroPdf = new FileOutputStream("c:/corte.pdf");
                PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
                documento.setMargins(0, 0, 0, 0);
                documento.open();
                PdfPTable tabla = new PdfPTable(8);
                tabla.setWidths(new float[]{20, 10, 10, 20, 10, 10, 20, 10});
                tabla.setWidthPercentage(100);
                PdfPCell celdaVacia = new PdfPCell(new Paragraph(" "));
                celdaVacia.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                PdfPCell celda1 = new PdfPCell(new Paragraph("Corte de caja del día " + fecha, FontFactory.getFont("Arial", 12, Font.BOLD)));
                celda1.setColspan(3);
                celda1.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celda1);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                PdfPCell celda2 = new PdfPCell(new Paragraph("Transacciones en efectivo: ", FontFactory.getFont("Arial", 11, Font.BOLD)));
                celda2.setColspan(3);
                celda2.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celda2);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                PdfPCell celda3 = new PdfPCell(new Paragraph("Abonos en efectivo", FontFactory.getFont("Arial", 10, Font.BOLD)));
                tabla.addCell(celda3);
                PdfPCell celda4 = new PdfPCell(new Paragraph("Monto", FontFactory.getFont("Arial", 10, Font.BOLD)));
                tabla.addCell(celda4);
                tabla.addCell(celdaVacia);
                PdfPCell celda5 = new PdfPCell(new Paragraph("Recargas de saldo", FontFactory.getFont("Arial", 10, Font.BOLD)));
                tabla.addCell(celda5);
                tabla.addCell(celda4);
                tabla.addCell(celdaVacia);
                PdfPCell celda6 = new PdfPCell(new Paragraph("Registro de clientes", FontFactory.getFont("Arial", 10, Font.BOLD)));
                tabla.addCell(celda6);
                tabla.addCell(celda4);
                double totalAbonosEfectivo = 0;
                double totalRecargasSaldo = 0;
                double totalRegistrosCliente = 0;
                for (int i = 0; i <= mayorEfectivo; i++) {
                    if (abonosEfectivo.size() > i) {
                        PdfPCell celda7 = new PdfPCell(new Paragraph("Abono a la nota " + abonosEfectivo.get(i).getId(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda7);
                        PdfPCell celda8 = new PdfPCell(new Paragraph("$" + abonosEfectivo.get(i).getMonto(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda8);
                        totalAbonosEfectivo += abonosEfectivo.get(i).getMonto();
                    } else {
                        if (abonosEfectivo.size() == i) {
                            PdfPCell celda7 = new PdfPCell(new Paragraph("Total", FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda7);
                            PdfPCell celda8 = new PdfPCell(new Paragraph("$" + totalAbonosEfectivo, FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda8);
                        } else {
                            tabla.addCell(celdaVacia);
                            tabla.addCell(celdaVacia);
                        }
                    }
                    tabla.addCell(celdaVacia);
                    if (recargasSaldo.size() > i) {
                        PdfPCell celda7 = new PdfPCell(new Paragraph("Recarga al cliente " + recargasSaldo.get(i).getIdMiembro(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda7);
                        PdfPCell celda8 = new PdfPCell(new Paragraph("$" + recargasSaldo.get(i).getMonto(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda8);
                        totalRecargasSaldo += recargasSaldo.get(i).getMonto();
                    } else {
                        if (recargasSaldo.size() == i) {
                            PdfPCell celda7 = new PdfPCell(new Paragraph("Total", FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda7);
                            PdfPCell celda8 = new PdfPCell(new Paragraph("$" + totalRecargasSaldo, FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda8);
                        } else {
                            tabla.addCell(celdaVacia);
                            tabla.addCell(celdaVacia);
                        }
                    }
                    tabla.addCell(celdaVacia);
                    if (registrosCliente.size() > i) {
                        PdfPCell celda7 = new PdfPCell(new Paragraph("Registro del cliente " + registrosCliente.get(i).getIdMiembro(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda7);
                        PdfPCell celda8 = new PdfPCell(new Paragraph("$" + registrosCliente.get(i).getMonto(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda8);
                        totalRegistrosCliente += registrosCliente.get(i).getMonto();
                    } else {
                        if (recargasSaldo.size() == i) {
                            PdfPCell celda7 = new PdfPCell(new Paragraph("Total", FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda7);
                            PdfPCell celda8 = new PdfPCell(new Paragraph("$" + totalRegistrosCliente, FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda8);
                        } else {
                            tabla.addCell(celdaVacia);
                            tabla.addCell(celdaVacia);
                        }
                    }
                }
                PdfPCell celda9 = new PdfPCell(new Paragraph("Total de pagos en efectivo: $"+(totalAbonosEfectivo+totalRecargasSaldo+totalRegistrosCliente), FontFactory.getFont("Arial", 9, Font.BOLD)));
                celda9.setBorder(Rectangle.NO_BORDER);
                celda9.setColspan(8);
                tabla.addCell(celda9);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                PdfPCell celda10 = new PdfPCell(new Paragraph("Otras transacciones: ", FontFactory.getFont("Arial", 11, Font.BOLD)));
                celda10.setColspan(3);
                celda10.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celda10);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                PdfPCell celda11 = new PdfPCell(new Paragraph("Abonos con tarjeta", FontFactory.getFont("Arial", 10, Font.BOLD)));
                tabla.addCell(celda11);
                PdfPCell celda12 = new PdfPCell(new Paragraph("Monto", FontFactory.getFont("Arial", 10, Font.BOLD)));
                tabla.addCell(celda12);
                tabla.addCell(celdaVacia);
                PdfPCell celda13 = new PdfPCell(new Paragraph("Paypal", FontFactory.getFont("Arial", 10, Font.BOLD)));
                tabla.addCell(celda13);
                tabla.addCell(celda12);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                tabla.addCell(celdaVacia);
                double totalAbonosTarjeta=0;
                double totalPaypal =0;
                for (int i = 0; i <= mayorOtros; i++) {
                    if (abonosTarjeta.size() > i) {
                        PdfPCell celda7 = new PdfPCell(new Paragraph("Abono a la nota " + abonosTarjeta.get(i).getId(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda7);
                        PdfPCell celda8 = new PdfPCell(new Paragraph("$" + abonosTarjeta.get(i).getMonto(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda8);
                        totalAbonosTarjeta += abonosTarjeta.get(i).getMonto();
                    } else {
                        if (abonosTarjeta.size() == i) {
                            PdfPCell celda7 = new PdfPCell(new Paragraph("Total", FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda7);
                            PdfPCell celda8 = new PdfPCell(new Paragraph("$" + totalAbonosTarjeta, FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda8);
                        } else {
                            tabla.addCell(celdaVacia);
                            tabla.addCell(celdaVacia);
                        }
                    }
                    tabla.addCell(celdaVacia);
                    if (transaccionesPaypal.size() > i) {
                        PdfPCell celda7 = new PdfPCell(new Paragraph("Recarga con Paypal al cliente " + transaccionesPaypal.get(i).getIdMiembro(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda7);
                        PdfPCell celda8 = new PdfPCell(new Paragraph("$" + transaccionesPaypal.get(i).getMonto(), FontFactory.getFont("Arial", 9)));
                        tabla.addCell(celda8);
                        totalPaypal += transaccionesPaypal.get(i).getMonto();
                    } else {
                        if (transaccionesPaypal.size() == i) {
                            PdfPCell celda7 = new PdfPCell(new Paragraph("Total", FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda7);
                            PdfPCell celda8 = new PdfPCell(new Paragraph("$" + totalPaypal, FontFactory.getFont("Arial", 9)));
                            tabla.addCell(celda8);
                        } else {
                            tabla.addCell(celdaVacia);
                            tabla.addCell(celdaVacia);
                        }
                    }
                    tabla.addCell(celdaVacia);
                    tabla.addCell(celdaVacia);
                    tabla.addCell(celdaVacia);
                    
                }
                documento.add(tabla);
                documento.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error al generar el pdf");
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar pdf");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
    }
}
