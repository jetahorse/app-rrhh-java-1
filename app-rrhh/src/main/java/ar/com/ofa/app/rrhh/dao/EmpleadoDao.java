/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.ofa.app.rrhh.dao;

import ar.com.ofa.app.rrhh.Empleado;
import java.util.List;

/**
 *
 * @author federicoaugustotschopp
 */
public interface EmpleadoDao {
    public void crear(Empleado e);
    public void actualizar(Empleado e);
    public void eliminar(Empleado e);
    public Empleado buscarPorId(Integer num);
    public List<Empleado> buscarTodos();
    
    
}
