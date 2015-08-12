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
public class Fechas {
    public static String convertirFecha(String fecha) {
        String fechaAux = fecha.substring(0, 10);
        String[] arreglo = fechaAux.split("-");
        String mes = buscarMes(arreglo[1]);
        String fechaFinal = arreglo[2] + "-" + mes + "-" + arreglo[0];
        return fechaFinal;
    }
     public static String buscarMes(String cual) {
        String mes = "";
        switch (cual) {
            case "01":
                mes = "Enero";
                break;
            case "02":
                mes = "Febrero";
                break;
            case "03":
                mes = "Marzo";
                break;
            case "04":
                mes = "Abril";
                break;
            case "05":
                mes = "Mayo";
                break;
            case "06":
                mes = "Junio";
                break;
            case "07":
                mes = "Julio";
                break;
            case "08":
                mes = "Agosto";
                break;
            case "09":
                mes = "Septiembre";
                break;
            case "10":
                mes = "Octubre";
                break;
            case "11":
                mes = "Noviembre";
                break;
            case "12":
                mes = "Diciembre";
                break;
        }
        return mes;
    }
}
