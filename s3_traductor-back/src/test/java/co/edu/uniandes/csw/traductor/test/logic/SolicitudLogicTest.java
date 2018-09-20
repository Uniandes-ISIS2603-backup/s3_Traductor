
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.SolicitudLogic;
import co.edu.uniandes.csw.traductor.entities.DocumentoEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.SolicitudPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.corba.EncapsInputStreamFactory;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Jhonattan Fonseca
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

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudEntity.class.getPackage())
                .addPackage(SolicitudLogic.class.getPackage())
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

    }

    /**
     * Inserta los datos iniciale spara el correcto funcionamientoo de las
     * pruebas
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            SolicitudEntity soliEntity = factory.manufacturePojo(SolicitudEntity.class);
            em.persist(soliEntity);
            soliEntity.setDocumentos(new ArrayList<DocumentoEntity>());
            data.add(soliEntity);
        }
        SolicitudEntity solicitud = data.get(2);
        DocumentoEntity docu = factory.manufacturePojo(DocumentoEntity.class);
        solicitud.getDocumentos().add(docu);
        em.persist(docu);

    }

    /**
     * Prueba para crear una solicitud.
     *
     * @throws BusinessLogicException si algún dato para crear la solicitud es
     * inválido.
     */
    @Test(expected = BusinessLogicException.class)
    public void createSolicitudTest() throws BusinessLogicException {
        SolicitudEntity newSoli = factory.manufacturePojo(SolicitudEntity.class);
        SolicitudEntity result = solicitudLogic.createSolicitud(newSoli);
        Assert.assertNotNull(result);
        SolicitudEntity soli = em.find(SolicitudEntity.class, result.getId());
        Assert.assertEquals(newSoli.getId(), soli.getId());
        Assert.assertEquals(newSoli.getEstado(), soli.getEstado());
        Assert.assertEquals(newSoli.getTipoSolicitud(), soli.getTipoSolicitud());

    }

    /**
     * Prueba para consultar la lista de solicitudes.
     */
    @Test
    public void getSolicitudesTest() {
        List<SolicitudEntity> list = solicitudLogic.getSolicitudes();
        Assert.assertEquals(data.size(), 3);
        for (SolicitudEntity entity : list) {
            boolean found = false;
            for (SolicitudEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Solicitud.
     */
    @Test
    public void getSolicitudTest() {
        SolicitudEntity entity = data.get(0);
        SolicitudEntity resultEntity = solicitudLogic.getSolicitud(entity.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getId(), entity.getId());
        Assert.assertEquals(entity.getEstado(), entity.getEstado());
        Assert.assertEquals(entity.getTipoSolicitud(), entity.getTipoSolicitud());
    }

    /**
     * Prueba para eliminar una Solicitud.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteAuthorTest() throws BusinessLogicException {
        SolicitudEntity entity = data.get(0);
        SolicitudEntity deleted = em.find(SolicitudEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}

