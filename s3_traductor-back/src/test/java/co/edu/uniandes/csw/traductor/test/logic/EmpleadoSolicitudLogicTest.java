/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoSolicitudLogic;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author Juan Parra
 */
@RunWith(Arquillian.class)
public class EmpleadoSolicitudLogicTest {
     private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private EmpleadoSolicitudLogic empleadoSolicitudLogic;
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<EmpleadoEntity> data=new ArrayList<>();
    private List<SolicitudEntity> sData=new ArrayList<>();
   
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
        em.createQuery("delete from SolicitudEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SolicitudEntity solicitudes = factory.manufacturePojo(SolicitudEntity.class);
            em.persist(solicitudes);
            sData.add(solicitudes);
        }
        for (int i = 0; i < 3; i++) {
            EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                sData.get(i).setEmpleado(entity);
            }
        }
    }
    @Test
    public void addInvitacionTest(){
        EmpleadoEntity entity=null;
            for (EmpleadoEntity empleadoEntity : data) {
                entity=empleadoEntity;
                break;
            }
            SolicitudEntity sol=null;
            int cont=0;
            for (SolicitudEntity solicitudEntity : sData) {
                
                if(cont==0){
                    sol=solicitudEntity;
                    cont++;
                }
                else{
                    break;
                }
                
                
            }
        SolicitudEntity response = empleadoSolicitudLogic.addSolicitud(sol.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(sol.getId(), response.getId());
    }
    
    @Test
    public void getInvitacionesTest(){
        EmpleadoEntity entity=null;
            for (EmpleadoEntity empleadoEntity : data) {
                entity=empleadoEntity;
                break;
            }
        if(entity!=null){List<SolicitudEntity> list = empleadoSolicitudLogic.getSolicitudes(entity.getId());
        Assert.assertEquals(1, list.size());}
    }
    
    @Test
    public void getInvitacionTest(){
       try{
            EmpleadoEntity entity=null;
            for (EmpleadoEntity empleadoEntity : data) {
                entity=empleadoEntity;
                break;
            }
            SolicitudEntity solicitudEntity=null;
            for (SolicitudEntity solicitudEntity2 : sData) {
                
                    solicitudEntity=solicitudEntity2;
                 
                    break;
            }
        SolicitudEntity response = empleadoSolicitudLogic.getSolicitud(entity.getId(), solicitudEntity.getId());

        Assert.assertEquals(solicitudEntity.getId(), response.getId());
        Assert.assertEquals(solicitudEntity.getDescripcion(), response.getDescripcion());
       }catch(BusinessLogicException | NullPointerException b){
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
            SolicitudEntity solicitud=null;
            int cont=0;
            for (SolicitudEntity solicitudEntity : sData) {
                
                if(cont==0){
                    solicitud=solicitudEntity;
                    cont++;
                }
                else{
                    break;
                }
                
                
            }
            empleadoSolicitudLogic.getSolicitud(entity.getId(), solicitud.getId());
            Assert.fail("No debe encontrar la invitacion");
        }
        catch(Exception b){
            Assert.assertTrue("No encontro la invitacion", true);
        }
    }
}
