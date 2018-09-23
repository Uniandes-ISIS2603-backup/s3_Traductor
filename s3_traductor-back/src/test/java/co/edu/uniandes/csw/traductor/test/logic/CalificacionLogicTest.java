/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.CalificacionLogic;
import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
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
    }
    /**
     * Inserta datos para llevar a cabo las pruebas
     */
    private void insertData(){
        for(int i=0;i<=2;i++){
            CalificacionEntity calificacion=factory.manufacturePojo(CalificacionEntity.class);
            em.persist(calificacion);
            data.add(calificacion);
        }
    }
    @Test
    public void createCalificacionTest(){
        CalificacionEntity entity=factory.manufacturePojo(CalificacionEntity.class);
        entity.setIdEmpleado(null);
       
        // creacion satisfactoria
        entity.setIdEmpleado(Long.parseLong("21451451"));
        try{
            calificacionLogic.createCalificacion(entity);
            Assert.assertNotNull("La calificacion debió persistir", calificacionLogic.getCalificacion(entity.getId()));
        }
        catch(BusinessLogicException e){
            Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);
        }
        //crecion de ya existente
        try{
            calificacionLogic.createCalificacion(data.get(0));
            Assert.fail("El metodo no deberia llegar, ya que existe esa entidad previamente");
            
        }
        catch(BusinessLogicException e){
            Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);
        }
        
    }
    /**
     * verificar la obtencion de una calificacion
     */
    @Test
    public void getCalificacionTest(){
        try{
            calificacionLogic.getCalificacion(Long.MAX_VALUE);
            Assert.fail("La calificacion no debería existir");
        }
        catch(BusinessLogicException e){
            Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);
        }
        CalificacionEntity a=null;
        try{
            a=calificacionLogic.getCalificacion(data.get(1).getId());
        }
        catch(BusinessLogicException e){
            Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);
        }
        Assert.assertNotNull("La calififcacion buscada no es nula, pues si existe",a);
        Assert.assertEquals("La calificacion no es la esperada",a.getComentario(),data.get(1).getComentario());
    }
    /**
     * verificar la obtencion de todas las calificaciones
     */
    @Test
    public void getCalificacionesTest()
    {
        List<CalificacionEntity> c=calificacionLogic.getCalificaciones();
        Assert.assertEquals("El numero de elementos no coincide", 3, c.size());
        for(CalificacionEntity ce:c){
            Assert.assertTrue("Hay elementos no concistentes",data.contains(ce));
        }
    }
}
