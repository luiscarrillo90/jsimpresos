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
public class CorteCaja {
    int idCorte;
    String fecha;
    ArrayList<Abono> abonos;
    ArrayList<TransaccionesMiembro> transaccionesMiembro;

    public CorteCaja(int idCorte, String fecha, ArrayList<Abono> abonos, ArrayList<TransaccionesMiembro> transaccionesMiembro) {
        this.idCorte = idCorte;
        this.fecha = fecha;
        this.abonos = abonos;
        this.transaccionesMiembro = transaccionesMiembro;
    }

    public int getIdCorte() {
        return idCorte;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<Abono> getAbonos() {
        return abonos;
    }

    public ArrayList<TransaccionesMiembro> getTransaccionesMiembro() {
        return transaccionesMiembro;
    }

    public void setIdCorte(int idCorte) {
        this.idCorte = idCorte;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setAbonos(ArrayList<Abono> abonos) {
        this.abonos = abonos;
    }

    public void setTransaccionesMiembro(ArrayList<TransaccionesMiembro> transaccionesMiembro) {
        this.transaccionesMiembro = transaccionesMiembro;
    }
    
}
