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
public class TransaccionesMiembro {
    private int idMiembro;
    private String movimiento;
    private double monto;
    private String fecha;
    private int idCorte;

    public TransaccionesMiembro(int idMiembro, String movimiento, double monto, String fecha, int idCorte) {
        this.idMiembro = idMiembro;
        this.movimiento = movimiento;
        this.monto = monto;
        this.fecha = fecha;
        this.idCorte = idCorte;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdCorte(int idCorte) {
        this.idCorte = idCorte;
    }
    
    public int getIdMiembro() {
        return idMiembro;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public double getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }

    public int getIdCorte() {
        return idCorte;
    }
    
    
}
