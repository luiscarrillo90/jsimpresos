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
public class Abono {
    private int id;
    private double monto;
    private String tipoPago;
    private String contabilizado;
    private String fecha;
    private int idCorte;
    public Abono(int id, double monto, String tipoPago, String contabilizado, String fecha, int idCorte) {
        this.id = id;
        this.monto = monto;
        this.tipoPago = tipoPago;
        this.contabilizado = contabilizado;
        this.fecha = fecha;
        this.idCorte = idCorte;
    }
    public String getFecha(){
        return fecha;
    }
    public int getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public String getContabilizado() {
        return contabilizado;
    }
    
    public int getIdCorte(){
        return idCorte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public void setContabilizado(String contabilizado) {
        this.contabilizado = contabilizado;
    }
    public void setIdCorte(int idCorte){
        this.idCorte = idCorte;
    }
    
}
