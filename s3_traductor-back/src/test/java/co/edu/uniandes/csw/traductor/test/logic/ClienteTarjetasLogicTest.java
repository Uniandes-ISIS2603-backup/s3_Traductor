/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.ClienteTarjetasLogic;
import co.edu.uniandes.csw.traductor.ejb.TarjetaDeCreditoLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
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
 * Pruebas de logica de la relación entre Cliente - Tarjetas
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class ClienteTarjetasLogicTest 
{
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteTarjetasLogic clienteTarjetasLogic;

    @Inject
    private TarjetaDeCreditoLogic tarjetaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private ClienteEntity cliente = new ClienteEntity();

    private List<TarjetaDeCreditoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(TarjetaDeCreditoEntity.class.getPackage())
                .addPackage(ClienteTarjetasLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
        em.createQuery("delete from TarjetaDeCreditoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        cliente = factory.manufacturePojo(ClienteEntity.class);
        cliente.setId(1L);
        cliente.setTarjetas(new ArrayList<>());
        em.persist(cliente);

        for (int i = 0; i < 3; i++) {
            TarjetaDeCreditoEntity entity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
            entity.setCliente(cliente);
            em.persist(entity);
            data.add(entity);
            cliente.getTarjetas().add(entity);
        }
    }

    /**
     * Prueba para asociar un cliente a una tarjeta.
     *
     *
     * @throws BusinessLogicException
     */
    @Test
    public void addTarjetaTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity newTarjeta = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        newTarjeta.setNumeroTarjetaCredito(new Long(000000010000001));
        newTarjeta.setCcv(331);
        tarjetaLogic.createTarjeta(newTarjeta);
        TarjetaDeCreditoEntity tarjetaEntity = clienteTarjetasLogic.addTarjeta(cliente.getId(), newTarjeta.getId());
        Assert.assertNotNull(tarjetaEntity);

        Assert.assertEquals(tarjetaEntity.getId(), newTarjeta.getId());
        Assert.assertEquals(tarjetaEntity.getNombreTitular(), newTarjeta.getNombreTitular());
        Assert.assertEquals(tarjetaEntity.getNumeroTarjetaCredito(), newTarjeta.getNumeroTarjetaCredito());
        Assert.assertEquals(tarjetaEntity.getCcv(), newTarjeta.getCcv());
        Assert.assertEquals(tarjetaEntity.getRedBancaria(), newTarjeta.getRedBancaria());

        TarjetaDeCreditoEntity lastTarjeta = clienteTarjetasLogic.getTarjeta(cliente.getId(), newTarjeta.getId());

        Assert.assertEquals(lastTarjeta.getId(), newTarjeta.getId());
        Assert.assertEquals(lastTarjeta.getNombreTitular(), newTarjeta.getNombreTitular());
        Assert.assertEquals(lastTarjeta.getNumeroTarjetaCredito(), newTarjeta.getNumeroTarjetaCredito());
        Assert.assertEquals(lastTarjeta.getCcv(), newTarjeta.getCcv());
        Assert.assertEquals(lastTarjeta.getRedBancaria(), newTarjeta.getRedBancaria());
    }

    /**
     * Prueba para consultar la lista de Tarjetas de un cliente.
     */
    @Test
    public void getTarjetasTest() {
        List<TarjetaDeCreditoEntity> tarjetasEntities = clienteTarjetasLogic.getTarjetas(cliente.getId());

        Assert.assertEquals(data.size(), tarjetasEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(tarjetasEntities.contains(data.get(i)));
        }
    }

    /**
     * Prueba para consultar una tarjeta de un cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getTarjetaTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity tarjetaEntity = data.get(0);
        TarjetaDeCreditoEntity tarjeta = clienteTarjetasLogic.getTarjeta(cliente.getId(), tarjetaEntity.getId());
        Assert.assertNotNull(tarjeta);

        Assert.assertEquals(tarjetaEntity.getId(), tarjeta.getId());
        Assert.assertEquals(tarjetaEntity.getNombreTitular(), tarjeta.getNombreTitular());
        Assert.assertEquals(tarjetaEntity.getNumeroTarjetaCredito(), tarjeta.getNumeroTarjetaCredito());
        Assert.assertEquals(tarjetaEntity.getCcv(), tarjeta.getCcv());
        Assert.assertEquals(tarjetaEntity.getRedBancaria(), tarjeta.getRedBancaria());
    }

    /**
     * Prueba para actualizar las tarjetas de un autor.
     *
     * @throws BusinessLogicException
     */
//    @Test
//    public void replaceBooksTest() throws BusinessLogicException {
//        List<BookEntity> nuevaLista = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            BookEntity entity = factory.manufacturePojo(BookEntity.class);
//            entity.setAuthors(new ArrayList<>());
//            entity.getAuthors().add(cliente);
//            entity.setEditorial(editorial);
//            bookLogic.createBook(entity);
//            nuevaLista.add(entity);
//        }
//        authorBookLogic.replaceBooks(cliente.getId(), nuevaLista);
//        List<BookEntity> bookEntities = authorBookLogic.getBooks(cliente.getId());
//        for (BookEntity aNuevaLista : nuevaLista) {
//            Assert.assertTrue(bookEntities.contains(aNuevaLista));
//        }
//    }

    /**
     * Prueba desasociar una tarjeta con un cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void removeTarjetaTest() throws BusinessLogicException {
        for (TarjetaDeCreditoEntity tarjeta : data) {
            clienteTarjetasLogic.removeTarjeta(tarjeta.getId());
        }
        Assert.assertTrue(clienteTarjetasLogic.getTarjetas(cliente.getId()).isEmpty());
    }

//    /**
//     * Prueba para desasociar una Tarjeta existente de un Cliente existente.
//     *
//     * @throws BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void removeTarjetaSinClienteTest() throws BusinessLogicException {
//        TarjetaDeCreditoEntity t = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
//        em.persist(t);
//        data.add(t);
//        
//        clienteTarjetasLogic.removeTarjeta(t.getId());
//    }
}
