/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.ofa.app.rrhh;

import ar.com.ofa.app.rrhh.dao.ConexionJDBC;
import ar.com.ofa.app.rrhh.dao.EmpleadoDao;
import ar.com.ofa.app.rrhh.dao.EmpleadoDaoJDBC;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author federicoaugustotschopp
 */
public class ActualizarTest {
    private EmpleadoDao empDao;
    private Connection conn;
    private static final String DATE_PATTERN="yyyy/MM/dd";
    private static final String TRUNCAR_TABLA="TRUNCATE TABLE EMPLEADOS";
    
    
    public ActualizarTest() {
    }
    
    private static void assertEqualDates(Date expected, Date value){
        DateFormat formatter=new SimpleDateFormat(DATE_PATTERN);
        String strExpected=formatter.format(expected);
        String strValue=formatter.format(value);
        assertEquals(strExpected,strValue);
        
    }
    
    @Before
    public void setUp() {
        empDao=new EmpleadoDaoJDBC();
    }
    
    @After
    public void tearDown() throws SQLException {
        conn=ConexionJDBC.getConexion();
        Statement stmt=conn.createStatement();
        stmt.execute(TRUNCAR_TABLA);
        conn.close();
    }
    @Test
    public void testMetodoActualizar(){
        Contratado e1=new Contratado();
        e1.setNombre("Maximiliano Tschopp");
        e1.setCorreo("mtschopp@gmail.com");
        e1.setCuil("20-27483833-2");
        e1.setFechaDeIngreso(new Date());
        e1.setHorasTrabajadas(10);
        e1.setMontoHoraTrabajada(71);
        Efectivo e2=new Efectivo();
        e2.setNombre("Leandro Pinto");
        e2.setCorreo("leopinto@hotmail.com");
        e2.setCuil("27-39838928-3");
        e2.setFechaDeIngreso(new Date());
        e2.setHorasMinObligatorias(40);
        e2.setHorasTrabajadas(16);
        e2.setSueldoBasico(40000);
        e2.setComisiones(7000);
        empDao.crear(e1);
        empDao.crear(e2);
        e1.setNombre("Pepe");
        e2.setNombre("Luis");
        e1.setId(1);
        e2.setId(2);
        empDao.actualizar(e1);
        empDao.actualizar(e2);
        Empleado e3=empDao.buscarPorId(1);
        Empleado e4=empDao.buscarPorId(2);
        assertEquals(e1.getNombre(),e3.getNombre());
        assertEquals(e2.getNombre(),e4.getNombre());
        assertEquals(e1.getCorreo(),e3.getCorreo());
        assertEquals(e2.getCorreo(),e4.getCorreo());
        assertEquals(e1.getCuil(),e3.getCuil());
        assertEqualDates(e1.getFechaDeIngreso(),e3.getFechaDeIngreso());
        assertEqualDates(e2.getFechaDeIngreso(),e4.getFechaDeIngreso());
        assertEquals(e1.getHorasTrabajadas(),e3.getHorasTrabajadas());
        assertEquals(e2.getHorasTrabajadas(),e4.getHorasTrabajadas());
        Contratado empCont= (Contratado) e3;
        assertEquals(e1.getMontoHoraTrabajada(),empCont.getMontoHoraTrabajada());
        Efectivo empEf= (Efectivo) e4;
        assertEquals(e2.getComision(),empEf.getComision(),1e-15);
        assertEquals(e2.getSueldoBasico(),empEf.getSueldoBasico(),1e-15);
        
        
        
    }
}
