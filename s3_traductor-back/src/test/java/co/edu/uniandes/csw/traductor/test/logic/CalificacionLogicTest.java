/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.CalificacionLogic;
import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.junit.Assert;

/**
 *
 * @author Juan Felipe Parra
 */
@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    private PodamFactory factory =new PodamFactoryImpl();
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CalificacionEntity> data=new ArrayList<>();
    private List<EmpleadoEntity> eData=new ArrayList<>();
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Configuracion incial del test
     */
    @Before
    public void configTest(){
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try{
                utx.rollback();
            }
            catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
    /**
     * Dejar limpia la tabla implicada en la pruba
     */
    private void clearData(){
        
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from EmpleadoEntity").executeUpdate();
    }
    /**
     * Inserta datos para llevar a cabo las pruebas
     */
    private void insertData(){
        for (int i = 0; i < 3; i++) {
            EmpleadoEntity empleados = factory.manufacturePojo(EmpleadoEntity.class);
            em.persist(empleados);
            eData.add(empleados);
        }
        for(int i=0;i<=2;i++){
            CalificacionEntity calificacion=factory.manufacturePojo(CalificacionEntity.class);
            int tmp = calificacion.getValorCalificacion();
            calificacion.setValorCalificacion(Math.abs(tmp));
            em.persist(calificacion);
            data.add(calificacion);
        }
        eData.get(0).setCalificaciones(data);
    }
    @Test
    public void createCalificacionTest(){
        try {
            CalificacionEntity cal=data.get(1);
            EmpleadoEntity emp=eData.get(0);
            Assert.assertNotNull(calificacionLogic.createCalificacion(emp.getId(), cal));
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getMessage());
        }
        
    }
    /**
     * verificar la obtencion de una calificacion
     */
    @Test
    public void getCalificacionTest(){
        CalificacionEntity cal=data.get(0);
        EmpleadoEntity emp=eData.get(0);
        Assert.assertNull(calificacionLogic.getCalificacion(emp.getId(), cal.getId()));
    }
    /**
     * verificar la obtencion de todas las calificaciones
     */
    @Test
    public void getCalificacionesTest()
    { 
        EmpleadoEntity emp=eData.get(1);
        Assert.assertEquals(0, calificacionLogic.getAllCalificaciones(emp.getId()).size());
         emp=eData.get(0);
        Assert.assertEquals(0,calificacionLogic.getAllCalificaciones(emp.getId()).size());
       
    }
    @Test
    public void  updateCalificacionTest(){
        EmpleadoEntity emp=eData.get(1);
            CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setId(entity.getId());
        calificacionLogic.updateCalificacion(emp.getId(), pojoEntity);
        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(pojoEntity.getValorCalificacion(), resp.getValorCalificacion());
        Assert.assertEquals(pojoEntity.getNombreCalificador(), resp.getNombreCalificador());
            
      
    }
}
