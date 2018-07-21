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
public class Efectivo extends Empleado{
    private double sueldoBasico;
    private double comision;
    private Integer horasMinObligatorias;
    @Override
    public double salario() {
        double montoHorasExtras=0;
        if (horasTrabajadas > horasMinObligatorias){
           montoHorasExtras = ((horasTrabajadas-horasMinObligatorias)*(sueldoBasico/20));
        }
        
        return (sueldoBasico+comision+montoHorasExtras);
    }
    public void setSueldoBasico(double monto){
        this.sueldoBasico=monto;
    }
    
    public double getSueldoBasico(){
        return this.sueldoBasico;
    }
    public void setComisiones(double monto){
        this.comision=monto;
        
    }
    
    public void setHorasMinObligatorias(Integer horas){
        this.horasMinObligatorias=horas;
    }
    
    public Integer getHorasMinObligatorias(){
        return this.horasMinObligatorias;
    }
    
    public void setHorasTrabajadas(Integer horas){
        horasTrabajadas=horas;
    }
    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    @Override
    public boolean esEfectivo() {
        return true;
    }

    @Override
    public boolean esContratado() {
        return false;
    }
}
