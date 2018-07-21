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
public class EliminarTest {
    
    private EmpleadoDao empDao;
    private Connection conn;
    private static final String DATE_PATTERN="yyyy/MM/dd";
    private static final String TRUNCAR_TABLA="TRUNCATE TABLE EMPLEADOS";
    
    
    public EliminarTest() {
    }
    /*private static void assertEqualDates(Date expected, Date value){
        DateFormat formatter=new SimpleDateFormat(DATE_PATTERN);
        String strExpected=formatter.format(expected);
        String strValue=formatter.format(value);
        assertEquals(strExpected,strValue);
        
    }*/
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
    public void testMetodoEliminar(){
        Contratado e1=new Contratado();
        e1.setNombre("Maximiliano Tschopp");
        e1.setCorreo("mtschopp@gmail.com");
        e1.setCuil("20-27483833-2");
        e1.setFechaDeIngreso(new Date());
        e1.setHorasTrabajadas(10);
        e1.setMontoHoraTrabajada(71);
        empDao.crear(e1);
        Empleado emp=empDao.buscarPorId(1);
        empDao.eliminar(emp);
        assertNull(empDao.buscarPorId(1));
    }
}
