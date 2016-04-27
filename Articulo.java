/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

/**
 *
 * @author luis-pc
 */
public class Articulo {
    private int idNota;
    private String servicio;
    private double precio;
    private int cantidad;

    public Articulo(int idNota, String servicio, double precio, int cantidad) {
        this.idNota = idNota;
        this.servicio = servicio;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdNota() {
        return idNota;
    }

    public String getServicio() {
        return servicio;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "Articulo{" + "idNota=" + idNota + ", servicio=" + servicio + ", precio=" + precio + ", cantidad=" + cantidad + '}';
    }
    
    
}
