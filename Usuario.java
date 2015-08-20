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
public class Usuario {
   private final int id;
   private String nombres;
   private String apPaterno;
   private String apMaterno;
   private String nombreUsuario;
   private String password;
   private String tipoUsuario;
   
   public Usuario(int id, String nombres, String apPaterno, String apMaterno, String nombreUsuario, String password, String tipoUsuario){
       this.id = id;
       this.nombres = nombres;
       this.apPaterno = apPaterno;
       this.apMaterno = apMaterno;
       this.nombreUsuario = nombreUsuario;
       this.password = password;
       this.tipoUsuario = tipoUsuario;
   }
   
   public int getId(){
       return id;
   }
   public String getNombres(){
       return nombres;
   } 
   public String getApPaterno(){
       return apPaterno;
   }
   public String getApMaterno(){
       return apMaterno;
   }
   public String getNombreUsuario(){
       return nombreUsuario;
   }
   public String getPassword(){
       return password;
   }
   public String getTipoUsuario(){
       return tipoUsuario;
   }
   public void setNombres(String nombres){
       this.nombres = nombres;
   }
   public void setApPaterno(String apPaterno){
       this.apPaterno = apPaterno;
   }
   public void setApMaterno(String apMaterno){
       this.apMaterno = apMaterno;
   }
   public void setNombreUsuario(String nombreUsuario){
       this.nombreUsuario = nombreUsuario;
   }
   public void setPassword(String password){
       this.password = password;
   }
   public void setTipoUsuario(String tipoUsuario){
       this.tipoUsuario = tipoUsuario;
   }
}
