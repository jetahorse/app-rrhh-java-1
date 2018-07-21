/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.ofa.app.rrhh;

import java.util.Date;

/**
 *
 * @author federicoaugustotschopp
 */
public abstract class Empleado {
    protected Integer id;
    protected String nombre;
    protected String correoElectronico;
    protected String cuil;
    protected Date fechaDeIngreso;
    protected Integer horasTrabajadas;
    
    public abstract double salario();
    public void setNombre(String nom){
        this.nombre=nom;
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getCorreo(){
        return this.correoElectronico;
        
    }
    
    public void setCorreo(String c){
        this.correoElectronico=c;
    }
    
    public String getCuil(){
        return this.cuil;
    }
    
    public void setCuil(String s){
        this.cuil=s;
    }
    public Date getFechaDeIngreso(){
        return this.fechaDeIngreso;
    }
    
    public void setFechaDeIngreso(Date d){
        this.fechaDeIngreso=d;
    }
    public Integer getHorasTrabajadas(){
        return this.horasTrabajadas;
    }
    
    public void setHorasTrabajadas(Integer i){
        this.horasTrabajadas=i;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public abstract boolean esEfectivo();
    public abstract boolean esContratado();
}
