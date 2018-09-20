/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.ClientePagosLogic;
import co.edu.uniandes.csw.traductor.ejb.PagosLogic;
import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
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
 * Pruebas de logica de la relación entre Cliente - Pagos
 * @author Santiago Salazar
 */
@RunWith(Arquillian.class)
public class ClientePagosLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClientePagosLogic clientePagosLogic;

    @Inject
    private PagosLogic pagosLogic;
    
    @Inject
    private PropuestaLogic propuestaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private ClienteEntity cliente = new ClienteEntity();

    private List<PagosEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(PagosEntity.class.getPackage())
                .addPackage(ClientePagosLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addPackage(PropuestaLogic.class.getPackage())
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
        em.createQuery("delete from PagosEntity").executeUpdate();
        em.createQuery("delete from PropuestaEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

//        for (int i = 0; i < 3; i++) {
//            PropuestaEntity propuestas = factory.manufacturePojo(PropuestaEntity.class);
//            em.persist(propuestas);
//            data.add(propuestas);
//        } 
        
        cliente = factory.manufacturePojo(ClienteEntity.class);
        cliente.setId(1L);
        cliente.setNombre("Andres");
        cliente.setPagos(new ArrayList<>());
        em.persist(cliente);

        PagosEntity p1 = factory.manufacturePojo(PagosEntity.class);
        p1.setPagoAprobado(false);
        p1.setCliente(cliente);
        em.persist(p1);
        data.add(p1);
        cliente.getPagos().add(p1);
        
        PagosEntity p2 = factory.manufacturePojo(PagosEntity.class);
        p2.setPagoAprobado(false);
        p2.setCliente(cliente);
        em.persist(p2);
        data.add(p2);
        cliente.getPagos().add(p2);
        
        PagosEntity p3 = factory.manufacturePojo(PagosEntity.class);
        p3.setPagoAprobado(false);
        p3.setCliente(cliente);
        em.persist(p3);
        data.add(p3);
        cliente.getPagos().add(p3);
    }

    /**
     * Prueba para asociar un cliente a un pago.
     *
     *
     * @throws BusinessLogicException
     */
    @Test
    public void addPagoTest() throws BusinessLogicException {
        PagosEntity newPago = factory.manufacturePojo(PagosEntity.class);
        PropuestaEntity propuesta = factory.manufacturePojo(PropuestaEntity.class);
        propuesta.setCosto(100000);
        propuestaLogic.createPropuesta(propuesta);
        newPago.setCliente(cliente);
        newPago.setPagoAprobado(false);
        newPago.setPropuesta(propuesta);
//        em.persist(newTarjeta);
        
        pagosLogic.createPago(newPago);
        PagosEntity pagoEntity = clientePagosLogic.addPago(cliente.getId(), newPago.getId());
        Assert.assertNotNull(pagoEntity);

        Assert.assertEquals(pagoEntity.getId(), newPago.getId());
        Assert.assertEquals(pagoEntity.getPagoAprobado(), newPago.getPagoAprobado());
        Assert.assertEquals(pagoEntity.getCliente(), newPago.getCliente());

        PagosEntity lastPago = clientePagosLogic.getPago(cliente.getId(), newPago.getId());

        Assert.assertEquals(lastPago.getId(), newPago.getId());
        Assert.assertEquals(lastPago.getPagoAprobado(), newPago.getPagoAprobado());
        Assert.assertEquals(lastPago.getCliente(), newPago.getCliente());
    }

    /**
     * Prueba para consultar la lista de Pagos de un cliente.
     */
    @Test
    public void getPagosTest() {
        List<PagosEntity> pagosEntities = clientePagosLogic.getPagos(cliente.getId());

        Assert.assertEquals(data.size(), pagosEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(pagosEntities.contains(data.get(i)));
        }
    }

    /**
     * Prueba para consultar un pago de un cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getPagoTest() throws BusinessLogicException {
        PagosEntity pagoEntity = data.get(0);
        PagosEntity pago = clientePagosLogic.getPago(cliente.getId(), pagoEntity.getId());
        Assert.assertNotNull(pago);

        Assert.assertEquals(pagoEntity.getId(), pago.getId());
        Assert.assertEquals(pagoEntity.getPagoAprobado(), pago.getPagoAprobado());
        Assert.assertEquals(pagoEntity.getCliente(), pago.getCliente());
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
     * Prueba desasociar un pago con un cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void removePagoTest() throws BusinessLogicException {
        for (PagosEntity tarjeta : data) {
            clientePagosLogic.removePago(tarjeta.getId());
        }
        Assert.assertTrue(clientePagosLogic.getPagos(cliente.getId()).isEmpty());
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
