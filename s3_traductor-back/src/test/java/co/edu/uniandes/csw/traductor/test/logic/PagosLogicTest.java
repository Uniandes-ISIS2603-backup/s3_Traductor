/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;


import co.edu.uniandes.csw.traductor.ejb.PagosLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.PagosPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
public class PagosLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PagosLogic pagoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PagosEntity> data = new ArrayList<PagosEntity>();

    private List<ClienteEntity> clienteData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagosEntity.class.getPackage())
                .addPackage(PagosLogic.class.getPackage())
                .addPackage(PagosPersistence.class.getPackage())
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
        em.createQuery("delete from BookEntity").executeUpdate();
        em.createQuery("delete from EditorialEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ClienteEntity editorial = factory.manufacturePojo(ClienteEntity.class);
            em.persist(editorial);
            clienteData.add(editorial);
        }
        for (int i = 0; i < 3; i++) {
            PagosEntity entity = factory.manufacturePojo(PagosEntity.class);
            entity.setCliente(clienteData.get(0));

            em.persist(entity);
            data.add(entity);
        }
        
    }

    /**
     * Prueba para crear un Book
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createPagoTest() throws BusinessLogicException {
        PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        newEntity.setCliente(clienteData.get(0));
        PagosEntity result = pagoLogic.createPago(newEntity);
        Assert.assertNotNull(result);
        PagosEntity entity = em.find(PagosEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getPagoAprobado(), entity.getPagoAprobado());
        Assert.assertEquals(newEntity.getPropuesta(), entity.getPropuesta());
        Assert.assertEquals(newEntity.getCliente(), entity.getCliente());
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoConAprobacionInvalida() throws BusinessLogicException {
        PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setPagoAprobado(null);
        pagoLogic.createPago(newEntity);
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoConAprobacionInvalida2() throws BusinessLogicException {
        PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setCliente(null);
        pagoLogic.createPago(newEntity);
    }
    

    

    /**
     * Prueba para crear un Book con una editorial que no existe.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createTarjetaTestConClienteInexistente() throws BusinessLogicException {
        PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(Long.MIN_VALUE);
        newEntity.setCliente(clienteEntity);
        pagoLogic.createPago(newEntity);
    }
 /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoConAprobacionInvalidaTest() throws BusinessLogicException {
        PagosEntity entity = data.get(0);
        PagosEntity pojoEntity = factory.manufacturePojo(PagosEntity.class);
        pojoEntity.setPagoAprobado(null);
        pojoEntity.setId(entity.getId());
        pagoLogic.updateTarjeta(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoConClienteInvalidoTest() throws BusinessLogicException {
        PagosEntity entity = data.get(0);
        PagosEntity pojoEntity = factory.manufacturePojo(PagosEntity.class);
        pojoEntity.setCliente(null);
        pojoEntity.setId(entity.getId());
        pagoLogic.updateTarjeta(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para eliminar un Book.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deletePagoTest() throws BusinessLogicException {
        PagosEntity entity = data.get(0);
        pagoLogic.deleteTarjeta(entity.getId());
        PagosEntity deleted = em.find(PagosEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Book.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteTarjetaDeCreditoTest2() throws BusinessLogicException {
        PagosEntity entity = data.get(1);
        pagoLogic.deleteTarjeta(entity.getId());
    }
   
}
