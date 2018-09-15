/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.TarjetaDeCreditoLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.TarjetaDeCreditoPersistence;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author ANDRES
 */
@RunWith(Arquillian.class)
public class TarjetaDeCreditoLogicTest {
       private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TarjetaDeCreditoLogic tarjetaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<TarjetaDeCreditoEntity> data = new ArrayList<TarjetaDeCreditoEntity>();

    private List<ClienteEntity> clienteData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaDeCreditoEntity.class.getPackage())
                .addPackage(TarjetaDeCreditoLogic.class.getPackage())
                .addPackage(TarjetaDeCreditoPersistence.class.getPackage())
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
        em.createQuery("delete from TarjetaDeCreditoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
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
            TarjetaDeCreditoEntity entity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
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
    public void createTarjetaTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity newEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        newEntity.setCliente(clienteData.get(0));
        TarjetaDeCreditoEntity result = tarjetaLogic.createTarjeta(newEntity);
        Assert.assertNotNull(result);
        TarjetaDeCreditoEntity entity = em.find(TarjetaDeCreditoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getRedBancaria(), entity.getRedBancaria());
        Assert.assertEquals(newEntity.getNumeroTarjetaCredito(), entity.getNumeroTarjetaCredito());
        Assert.assertEquals(newEntity.getNombreTitular(), entity.getNombreTitular());
        Assert.assertEquals(newEntity.getFechaExpiracion(), entity.getFechaExpiracion());
         Assert.assertEquals(newEntity.getCcv(), entity.getCcv());
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createTarjetaTestConNumeroInvalido() throws BusinessLogicException {
        TarjetaDeCreditoEntity newEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setNumeroTarjetaCredito(Long.valueOf(1));
        tarjetaLogic.createTarjeta(newEntity);
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createTarjetaTestConNumeroInvalido2() throws BusinessLogicException {
        TarjetaDeCreditoEntity newEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setNumeroTarjetaCredito(null);
        tarjetaLogic.createTarjeta(newEntity);
    }
    

    /**
     * Prueba para crear un Book con ISBN existente.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createTarjetaConNumeroExistente() throws BusinessLogicException {
        TarjetaDeCreditoEntity newEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setNumeroTarjetaCredito(data.get(0).getNumeroTarjetaCredito());
        tarjetaLogic.createTarjeta(newEntity);
    }

    /**
     * Prueba para crear un Book con una editorial que no existe.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createTarjetaTestConClienteInexistente() throws BusinessLogicException {
        TarjetaDeCreditoEntity newEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(Long.MIN_VALUE);
        newEntity.setCliente(clienteEntity);
        tarjetaLogic.createTarjeta(newEntity);
    }
 /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateBookConNumeroTarjetaInvalidoTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entity = data.get(0);
        TarjetaDeCreditoEntity pojoEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        pojoEntity.setNumeroTarjetaCredito(Long.valueOf(1));
        pojoEntity.setId(entity.getId());
        tarjetaLogic.updateTarjeta(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateTarjetaConNumeroInvalidoTest2() throws BusinessLogicException {
        TarjetaDeCreditoEntity entity = data.get(0);
        TarjetaDeCreditoEntity pojoEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        pojoEntity.setNumeroTarjetaCredito(null);
        pojoEntity.setId(entity.getId());
        tarjetaLogic.updateTarjeta(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para eliminar un Book.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteTarjetaDeCreditoTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entity = data.get(0);
        tarjetaLogic.deleteTarjeta(entity.getId());
        TarjetaDeCreditoEntity deleted = em.find(TarjetaDeCreditoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Book.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteTarjetaDeCreditoTest2() throws BusinessLogicException {
        TarjetaDeCreditoEntity entity = data.get(1);
        tarjetaLogic.deleteTarjeta(entity.getId());
    }
    
   
}
