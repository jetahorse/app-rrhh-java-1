/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.ofa.app.rrhh.dao;

import ar.com.ofa.app.rrhh.Contratado;
import ar.com.ofa.app.rrhh.Efectivo;
import ar.com.ofa.app.rrhh.Empleado;
//import ar.com.ofa.app.rrhh.dao.ConexionJDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author federicoaugustotschopp
 */
public class EmpleadoDaoJDBC implements EmpleadoDao {
    
    private final String INSERT_EMPLEADO= "INSERT INTO EMPLEADOS (NOMBRE,CORREO,CUIL,"
                                          +"FECHA_INGRESO,HS_TRABAJADAS,SUELDO_BASICO,"
                                          +"COMISIONES, HS_MINIMAS,COSTO_HORA, TIPO_EMPLEADO) "
                                          +"VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final String ACTUALIZAR_EMPLEADO= "UPDATE EMPLEADOS SET NOMBRE=?,CORREO=?,CUIL=?,"
                                             +"FECHA_INGRESO=?,HS_TRABAJADAS=?,SUELDO_BASICO=?,"
                                             +"COMISIONES=?,HS_MINIMAS=?,COSTO_HORA=?,"
                                             +"TIPO_EMPLEADO=? WHERE ID=?";
    
    private final String ELIMINAR_EMPLEADO= "DELETE FROM EMPLEADOS WHERE ID=?";
    private final String BUSCAR_EMPLEADO= "SELECT * FROM EMPLEADOS WHERE ID=?";
    private final String BUSCAR_TODOS_LOS_EMPLEADOS="SELECT * FROM EMPLEADOS";
    
    @Override
    public void crear(Empleado e) {
        Connection conn=ConexionJDBC.getConexion();
        try (PreparedStatement ps=conn.prepareStatement(INSERT_EMPLEADO)){
            //ps.setInt(11, e.getId());
            ps.setString(1,e.getNombre());
            ps.setString(2,e.getCorreo());
            ps.setString(3,e.getCuil());
            ps.setDate(4,new Date(e.getFechaDeIngreso().getTime()));
            ps.setInt(5,e.getHorasTrabajadas());
            if (e.esEfectivo()){
                Efectivo empEf= (Efectivo)e;
                ps.setDouble(6, empEf.getSueldoBasico());
                ps.setDouble(7, empEf.getComision());
                ps.setInt(8, empEf.getHorasMinObligatorias());
                ps.setInt(9, 0);
                ps.setInt(10,1);
            }
            if (e.esContratado()){
               Contratado empCon= (Contratado) e;
               ps.setDouble(6,0.0);
               ps.setDouble(7,0.0);
               ps.setInt(8, 0);
               ps.setInt(9, empCon.getMontoHoraTrabajada());
               ps.setInt(10,2);
               
            }
            int filasInsertadas=ps.executeUpdate();
            
        } catch (SQLException ex){ 
            ex.printStackTrace();
        }
        finally{
        ConexionJDBC.liberarConexion();
        }
    }

    @Override
    public void actualizar(Empleado e) {
        Connection conn=ConexionJDBC.getConexion();
        try (PreparedStatement ps=conn.prepareStatement(ACTUALIZAR_EMPLEADO)){
            ps.setInt(11, e.getId());
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getCorreo());
            ps.setString(3, e.getCuil());
            ps.setDate(4,new Date(e.getFechaDeIngreso().getTime()));
            ps.setInt(5,e.getHorasTrabajadas());
            
            if (e.esEfectivo()){
                Efectivo empEf= (Efectivo)e;
                ps.setDouble(6, empEf.getSueldoBasico());
                ps.setDouble(7, empEf.getComision());
                ps.setInt(8, empEf.getHorasMinObligatorias());
                ps.setInt(9, 0);
                ps.setInt(10,1);
                
            }
            
            if (e.esContratado()){
               Contratado empCon= (Contratado) e;
               ps.setDouble(6,0.0);
               ps.setDouble(7, 0.0);
               ps.setInt(8, 0);
               ps.setInt(9, empCon.getMontoHoraTrabajada());
               ps.setInt(10,2);
            }
            
            int filasActualizadas=ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConexionJDBC.liberarConexion();
        }
    }

    @Override
    public void eliminar(Empleado e) {
        Connection conn=ConexionJDBC.getConexion();
        try (PreparedStatement ps=conn.prepareStatement(ELIMINAR_EMPLEADO)){
            ps.setInt(1, e.getId());
            int filasEliminadas=ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConexionJDBC.liberarConexion();
        }
    }

    @Override
    public Empleado buscarPorId(Integer num) {
        Connection conn=ConexionJDBC.getConexion();
        Empleado e=null;
        try (PreparedStatement ps=conn.prepareStatement(BUSCAR_EMPLEADO)){
            ps.setInt(1,num);
            try(ResultSet rs=ps.executeQuery()){
                rs.next();
                Integer tipoEmpleado=rs.getInt("TIPO_EMPLEADO");
                if (tipoEmpleado==1){ //si es empleado efectivo
                    Efectivo empleadoEf=new Efectivo();
                    empleadoEf.setId(rs.getInt("ID"));
                    empleadoEf.setNombre(rs.getString("NOMBRE"));
                    empleadoEf.setCorreo(rs.getString("CORREO"));
                    empleadoEf.setCuil(rs.getString("CUIL"));
                    empleadoEf.setFechaDeIngreso(rs.getDate("FECHA_INGRESO"));
                    empleadoEf.setSueldoBasico(rs.getDouble("SUELDO_BASICO"));
                    empleadoEf.setComision(rs.getDouble("COMISIONES"));
                    empleadoEf.setHorasMinObligatorias(rs.getInt("HS_MINIMAS"));
                    empleadoEf.setHorasTrabajadas(rs.getInt("HS_TRABAJADAS"));
                    e=empleadoEf;
                        
                }
                if (tipoEmpleado==2){ //si es empleado contratado
                    Contratado empleadoCon=new Contratado();
                    empleadoCon.setId(rs.getInt("ID"));
                    empleadoCon.setNombre(rs.getString("NOMBRE"));
                    empleadoCon.setCorreo(rs.getString("CORREO"));
                    empleadoCon.setCuil(rs.getString("CUIL"));
                    empleadoCon.setFechaDeIngreso(rs.getDate("FECHA_INGRESO"));
                    empleadoCon.setHorasTrabajadas(rs.getInt("HS_TRABAJADAS"));
                    empleadoCon.setMontoHoraTrabajada(rs.getInt("COSTO_HORA"));
                    e=empleadoCon;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            ConexionJDBC.liberarConexion();
            return(e);
        }
        
    }

    @Override
    public List<Empleado> buscarTodos() {
        Connection conn=ConexionJDBC.getConexion();
        List<Empleado> unaLista=new ArrayList<>();
        
        try (Statement stmt=conn.createStatement()){
            ResultSet rs=stmt.executeQuery(BUSCAR_TODOS_LOS_EMPLEADOS);
            while (rs.next()){
                if(rs.getInt("TIPO_EMPLEADO")==1){
                    Efectivo unEfectivo=new Efectivo();
                    unEfectivo.setId(rs.getInt("ID"));
                    unEfectivo.setNombre(rs.getString("NOMBRE"));
                    unEfectivo.setCorreo(rs.getString("CORREO"));
                    unEfectivo.setCuil(rs.getString("CUIL"));
                    unEfectivo.setFechaDeIngreso(rs.getDate("FECHA_INGRESO"));
                    unEfectivo.setSueldoBasico(rs.getDouble("SUELDO_BASICO"));
                    unEfectivo.setComision(rs.getDouble("COMISIONES"));
                    unEfectivo.setHorasMinObligatorias(rs.getInt("HS_MINIMAS"));
                    unEfectivo.setHorasTrabajadas(rs.getInt("HS_TRABAJADAS"));
                    unaLista.add(unEfectivo);
                }
                if (rs.getInt("TIPO_EMPLEADO")==2){
                    Contratado unContratado=new Contratado();
                    unContratado.setId(rs.getInt("ID"));
                    unContratado.setNombre(rs.getString("NOMBRE"));
                    unContratado.setCorreo(rs.getString("CORREO"));
                    unContratado.setCuil(rs.getString("CUIL"));
                    unContratado.setFechaDeIngreso(rs.getDate("FECHA_INGRESO"));
                    unContratado.setHorasTrabajadas(rs.getInt("HS_TRABAJADAS"));
                    unContratado.setMontoHoraTrabajada(rs.getInt("COSTO_HORA"));
                    unaLista.add(unContratado);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            ConexionJDBC.liberarConexion();
            return (unaLista);
        }
    }
    
    
}

