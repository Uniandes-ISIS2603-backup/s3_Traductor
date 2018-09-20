/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;

import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
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
 * @author Jhonattan Fonseca
 */
@RunWith(Arquillian.class)
public class SolicitudPersistenceTest {

    @Inject
    private SolicitudPersistence solicitudrPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<SolicitudEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudEntity.class.getPackage())
                .addPackage(SolicitudPersistence.class.getPackage())
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
            em.joinTransaction();
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
        em.createQuery("delete from AuthorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            SolicitudEntity entity = factory.manufacturePojo(SolicitudEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear una solicitud.
     */
    @Test
    public void createSolicitudTest() {
        PodamFactory factory = new PodamFactoryImpl();
        SolicitudEntity newEntity = factory.manufacturePojo(SolicitudEntity.class);
        SolicitudEntity result = solicitudrPersistence.create(newEntity);

        Assert.assertNotNull(result);

        SolicitudEntity entity = em.find(SolicitudEntity.class, result.getId());

        Assert.assertEquals(newEntity.getEstado(), entity.getEstado());
    }

    /**
     * Prueba para consultar la lista de Solicitudes.
     */
    @Test
    public void getSolicitudesTest() {
        List<SolicitudEntity> list = solicitudrPersistence.findAll();
        Assert.assertEquals(data.size(), 0);
        for (SolicitudEntity ent : list) {
            boolean found = false;
            for (SolicitudEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertFalse(found);
        }
    }

    /**
     * Prueba para consultar una Solicitud.
     */
    @Test
    public void getSolicitudTest() {
        SolicitudEntity newEntity = new SolicitudEntity();
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getEstado(), newEntity.getEstado());
        Assert.assertEquals(newEntity.getTipoSolicitud(), newEntity.getTipoSolicitud());
    }



}
