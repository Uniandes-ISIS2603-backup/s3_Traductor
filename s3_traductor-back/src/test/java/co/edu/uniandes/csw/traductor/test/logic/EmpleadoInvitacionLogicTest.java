/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.EmpleadoInvitacionLogic;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan Felipe Parra
 */
@RunWith(Arquillian.class)
public class EmpleadoInvitacionLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private EmpleadoInvitacionLogic empleadoInvitacionLogic;
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<EmpleadoEntity> data=new ArrayList<>();
    private List<InvitacionEntity> invitacionData=new ArrayList<>();
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpleadoEntity.class.getPackage())
                .addPackage(EmpleadoLogic.class.getPackage())
                .addPackage(EmpleadoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from EmpleadoEntity").executeUpdate();
        em.createQuery("delete from InvitacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            InvitacionEntity invitaciones = factory.manufacturePojo(InvitacionEntity.class);
            em.persist(invitaciones);
            invitacionData.add(invitaciones);
        }
        for (int i = 0; i < 3; i++) {
            EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                invitacionData.get(i).setEmpleado(entity);
            }
        }
    }
    @Test
    public void addInvitacionTest(){
        EmpleadoEntity entity = data.get(0);
        InvitacionEntity invitacionEntity = invitacionData.get(1);
        InvitacionEntity response = empleadoInvitacionLogic.addInvitacion(entity.getId(), invitacionEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(invitacionEntity.getId(), response.getId());
    }
    
    @Test
    public void getInvitacionesTest(){
         EmpleadoEntity entity=null;
            for (EmpleadoEntity empleadoEntity : data) {
                entity=empleadoEntity;
                break;
            }
        if(entity!=null){
            List<InvitacionEntity> list = empleadoInvitacionLogic.getInvitaciones(entity.getId());
        
        Assert.assertEquals(1, list.size());
        }
    }
    
    @Test
    public void getInvitacionTest(){
       try{
           EmpleadoEntity entity=null;
            for (EmpleadoEntity empleadoEntity : data) {
                entity=empleadoEntity;
                break;
            }
            InvitacionEntity invitacionEntity=null;
            for (InvitacionEntity i : invitacionData) {
                invitacionEntity=i;
                break;
            }
        if(entity!=null && invitacionEntity!=null){
            InvitacionEntity response = empleadoInvitacionLogic.getInvitacion(entity.getId(), invitacionEntity.getId());
        

        Assert.assertEquals(invitacionEntity.getId(), response.getId());
        Assert.assertEquals(invitacionEntity.getIdEmpleado(), response.getIdEmpleado());
        Assert.assertEquals(invitacionEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(invitacionEntity.getSolicitudId(), response.getSolicitudId()); 
        }
       }catch(BusinessLogicException b){
           Assert.fail();
       }
    }
    @Test
    public void getInvitacionNoAsociada(){
        try{
            
            EmpleadoEntity entity=null;
            for (EmpleadoEntity empleadoEntity : data) {
                entity=empleadoEntity;
                break;
            }
            InvitacionEntity invitacion=null;
            for (InvitacionEntity i : invitacionData) {
                invitacion=i;
                break;
            }
            if(entity!=null && invitacion!=null){empleadoInvitacionLogic.getInvitacion(entity.getId(), invitacion.getId());
            Assert.fail("No debe encontrar la invitacion");}
        }
        catch(BusinessLogicException b){
            Assert.assertTrue("No encontro la invitacion", true);
        }
    }
}
