/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import java.util.ArrayList;

/**
 *
 * @author luis-pc
 */
public class Nota {
    private int idNota;
    private String nombres;
    private Miembro cliente;
    private String apPaterno;
    private String apMaterno;
    private String telefono;
    private String fecha;
    private String hora;
    private String fechaEntrega;
    private String observaciones;
    private String estado;
    private String domicilio;
    private int descuento;
    private int usuario;
    ArrayList<Articulo> articulos;
    ArrayList<Abono> abonos;
    
    public Nota(int idNota, String nombres, Miembro cliente, String apPaterno, String apMaterno, String telefono, String fecha,String fechaEntrega, String observaciones, ArrayList<Articulo> articulos, ArrayList<Abono> abonos, String estado, String domicilio, int descuento, int usuario) {
        this.idNota = idNota;
        this.nombres = nombres;
        this.cliente = cliente;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.telefono = telefono;
        this.fecha = fecha;
        this.fechaEntrega = fechaEntrega;
        this.observaciones = observaciones;
        this.articulos = articulos;
        this.abonos = abonos;
        this.estado= estado;
        this.domicilio = domicilio;
        this.descuento = descuento;
        this.usuario = usuario;
    }
    
    public int getDescuento(){
        return descuento;
    }
    
    public int getUsuario(){
        return usuario;
    }
    
    public String getEstado(){
        return estado;
    }
    public int getIdNota() {
        return idNota;
    }

    public String getNombres() {
        return nombres;
    }

    public Miembro getCliente() {
        return cliente;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public String getObservaciones() {
        return observaciones;
    }
    
    public String getDomicilio(){
        return domicilio;
    }

    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }

    public ArrayList<Abono> getAbonos() {
        return abonos;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setCliente(Miembro cliente) {
        this.cliente = cliente;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }

    public void setAbonos(ArrayList<Abono> abonos) {
        this.abonos = abonos;
    }
    
    public void setDomicilio(String domicilio){
        this.domicilio = domicilio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
    
//    public Usuario generarUsuarioResponsable(){
//        new ConexionUsuario.
//        return Usuario;
//    }
}
