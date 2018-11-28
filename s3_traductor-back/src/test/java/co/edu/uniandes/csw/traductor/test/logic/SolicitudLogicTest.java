/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import co.edu.uniandes.csw.traductor.ejb.SolicitudLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
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
 * @author Juan Parra
 */
@RunWith(Arquillian.class)
public class SolicitudLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SolicitudLogic solicitudLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<SolicitudEntity> data = new ArrayList<>();
    private List<ClienteEntity> eData=new ArrayList<>();
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudEntity.class.getPackage())
                .addPackage(SolicitudLogic.class.getPackage())
                .addPackage(SolicitudPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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

    private void clearData() {
        em.createQuery("delete from SolicitudEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            ClienteEntity clientes = factory.manufacturePojo(ClienteEntity.class);
            em.persist(clientes);
            eData.add(clientes);
        }
        for (int i = 0; i < 3; i++) {
            SolicitudEntity solicitudes = factory.manufacturePojo(SolicitudEntity.class);
            em.persist(solicitudes);
            data.add(solicitudes);
            if (i == 0) {
                eData.get(i).setSolicitudes(data);
            }
        }
    }
    @Test
    public void getSolicitudesTest() {
        List<SolicitudEntity> list = solicitudLogic.getAllSolicitudes(eData.get(0).getId());
        Assert.assertTrue(list.size()>0);
    }
    @Test
    public void getSolicitudTest(){
        SolicitudEntity solicitud=data.get(0);
        ClienteEntity cliente=eData.get(0);
        SolicitudEntity response=solicitudLogic.getSolicitud(cliente.getId(), solicitud.getId());
        if(response==null)Assert.fail();
        else{
            Assert.assertEquals(solicitud.getId(), response.getId());
            Assert.assertEquals(solicitud.getArchivo(), response.getArchivo());
            Assert.assertEquals(solicitud.getCliente().getId(), response.getCliente().getId());
            Assert.assertEquals(solicitud.getDescripcion(),response.getDescripcion());
            Assert.assertEquals(solicitud.getEmpleado().getId(),response.getEmpleado().getId());
            Assert.assertTrue(solicitud.getEstado() == response.getEstado());
            Assert.assertEquals(solicitud.getFechaEntrega(), response.getFechaEntrega());
            Assert.assertEquals(solicitud.getFechaInicio(),response.getFechaInicio());
            Assert.assertEquals(solicitud.getIdiomaEntrada(),response.getIdiomaEntrada());
            Assert.assertEquals(solicitud.getIdiomaSalida(),response.getIdiomaSalida());
            Assert.assertTrue(solicitud.getLongitud()== response.getLongitud());
            Assert.assertEquals(solicitud.getTipoSolicitud(),response.getTipoSolicitud());
        }
    }
    @Test
    public void getSolicitudIDTest(){
        SolicitudEntity solicitud=data.get(0);
        SolicitudEntity response= solicitudLogic.getSolicitudSoloId(solicitud.getId());
        Assert.assertEquals(solicitud.getArchivo(), response.getArchivo());
        Assert.assertEquals(solicitud.getIdiomaEntrada(), response.getFechaEntrega());
        Assert.assertEquals(solicitud.getFechaInicio(), solicitud.getFechaInicio());
    }
    @Test
    public void deleteSolicitudTest(){
        SolicitudEntity solicitud=data.get(0);
        try {
            if(solicitudLogic!= null && solicitud.getCliente()!=null && solicitudLogic.getSolicitud(solicitud.getCliente().getId(), solicitud.getId())!=null){
                 solicitudLogic.deleteSolicitud(solicitud.getCliente().getId(), solicitud.getId());
            Assert.assertNull(solicitudLogic.getSolicitudSoloId(solicitud.getId()));
            }
           
        } catch (BusinessLogicException ex) {
            Assert.fail();
        }
    }
}
