/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.ofa.app.rrhh;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ar.com.ofa.app.rrhh.dao.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author federicoaugustotschopp
 */
public class BuscarTodosTest {
    private EmpleadoDao empDao;
    private Connection conn;
    private static final String DATE_PATTERN="yyyy/MM/dd";
    private static final String TRUNCAR_TABLA="TRUNCATE TABLE EMPLEADOS";
    
    private static void assertEqualDates(Date expected, Date value){
        DateFormat formatter=new SimpleDateFormat(DATE_PATTERN);
        String strExpected=formatter.format(expected);
        String strValue=formatter.format(value);
        assertEquals(strExpected,strValue);
        
    }
    
    public BuscarTodosTest() {
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
    public void testListarTodos(){
        
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
        Contratado e3=new Contratado();
        e3.setNombre("Luis Iturraspe");
        e3.setCorreo("luisiturraspe@gmail.com");
        e3.setCuil("20-27444444-2");
        e3.setFechaDeIngreso(new Date());
        e3.setHorasTrabajadas(20);
        e3.setMontoHoraTrabajada(100);
        Efectivo e4=new Efectivo();
        e4.setNombre("Francisco Games");
        e4.setCorreo("panchogames@hotmail.com");
        e4.setCuil("27-394839388-3");
        e4.setFechaDeIngreso(new Date());
        e4.setHorasMinObligatorias(45);
        e4.setHorasTrabajadas(40);
        e4.setSueldoBasico(70000);
        e4.setComisiones(25000);
        List<Empleado> lstEmpEsperados=new ArrayList<>();
        lstEmpEsperados.add(e1);
        lstEmpEsperados.add(e2);
        lstEmpEsperados.add(e3);
        lstEmpEsperados.add(e4);
        empDao.crear(e1);
        empDao.crear(e2);
        empDao.crear(e3);
        empDao.crear(e4);
        List<Empleado> lstEmpActuales=empDao.buscarTodos();
        int iter=0;
        while (iter<lstEmpEsperados.size()){
            for(Empleado e:lstEmpActuales){
                assertEquals(lstEmpEsperados.get(iter).getNombre(),e.getNombre());
                assertEquals(lstEmpEsperados.get(iter).getCorreo(),e.getCorreo());
                assertEquals(lstEmpEsperados.get(iter).getCuil(),e.getCuil());
                assertEquals(lstEmpEsperados.get(iter).getHorasTrabajadas(),e.getHorasTrabajadas());
                assertEqualDates(lstEmpEsperados.get(iter).getFechaDeIngreso(),e.getFechaDeIngreso());
                if(lstEmpEsperados.get(iter).esEfectivo() & e.esEfectivo()){
                    Efectivo empEsperado= (Efectivo) lstEmpEsperados.get(iter);
                    Efectivo empActual= (Efectivo) e;
                    assertEquals(empEsperado.getHorasMinObligatorias(),empActual.getHorasMinObligatorias());
                    assertEquals(empEsperado.getComision(),empActual.getComision(),1e-15);
                    assertEquals(empEsperado.getSueldoBasico(),empActual.getSueldoBasico(),1e-15);
                    
                    
                }
                if(lstEmpEsperados.get(iter).esContratado() & e.esContratado()){
                    Contratado empEsperado= (Contratado) lstEmpEsperados.get(iter);
                    Contratado empActual= (Contratado) e;
                    assertEquals(empEsperado.getMontoHoraTrabajada(),empActual.getMontoHoraTrabajada());
                    
                    
                }
            iter++;
            }
            

        }
    }
}
