/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.ofa.app.rrhh;

/**
 *
 * @author federicoaugustotschopp
 */
public class Contratado extends Empleado{
    private Integer montoHoraTrabajada;
    @Override
    public double salario() {
        return(horasTrabajadas*montoHoraTrabajada);
    
    }
    
    public void setMontoHoraTrabajada(Integer montoHoraTrabajada) {
        this.montoHoraTrabajada = montoHoraTrabajada;
    }
    
    public Integer getMontoHoraTrabajada(){
        return this.montoHoraTrabajada;
    }

    @Override
    public boolean esEfectivo() {
        return false;
    }

    @Override
    public boolean esContratado() {
        return true;
    }
    
}
