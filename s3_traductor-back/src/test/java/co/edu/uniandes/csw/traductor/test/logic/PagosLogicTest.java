/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.ClienteLogic;
import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.ejb.PagosLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.PagosPersistence;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
import java.util.ArrayList;
import java.util.Date;
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

public class PagosLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PagosLogic pagosLogic;
    
     @Inject
    private ClienteLogic clienteLogic;
     
     @Inject
    private PropuestaLogic propuestaLogic;
	 
	 @Inject
	private PropuestaPersistence propuestaPersistence; //Lo forzo debido a que se hicieron cambios de ultima hora. Geovanny.

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PagosEntity> data = new ArrayList<>();

    private List<ClienteEntity> clienteData = new ArrayList<>();
    

   @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagosEntity.class.getPackage())
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(PropuestaEntity.class.getPackage())
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
        em.createQuery("delete from PagosEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PagosEntity entity = factory.manufacturePojo(PagosEntity.class);
            ClienteEntity clienteEntity = factory.manufacturePojo(ClienteEntity.class);
            PropuestaEntity propuestaEntity = factory.manufacturePojo(PropuestaEntity.class);
            em.persist(propuestaEntity);
            em.persist(clienteEntity);
            entity.setCliente(clienteEntity);
            em.persist(entity);
            data.add(entity);
            clienteData.add(clienteEntity);
        }
      
    }

    /**
     * Prueba para crear un Pago.
     */
    @Test
    public void createPago()  {
        
        try{
            PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        ClienteEntity newClienteEntity = factory.manufacturePojo(ClienteEntity.class);
        PropuestaEntity newPropuestaEntity = factory.manufacturePojo(PropuestaEntity.class);
		newPropuestaEntity.setCosto(Math.abs(newPropuestaEntity.getCosto()));
        newPropuestaEntity = propuestaPersistence.create(newPropuestaEntity);
        newPropuestaEntity.setCosto(Math.abs(newPropuestaEntity.getCosto()));
        newClienteEntity = clienteLogic.createCliente(newClienteEntity);
        newEntity.setCliente(newClienteEntity);
        PagosEntity result = pagosLogic.createPago(newClienteEntity.getId(), newPropuestaEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        PagosEntity entity = em.find(PagosEntity.class, result.getId());
       Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getCliente(), newEntity.getCliente());
        }catch(BusinessLogicException b){
            Assert.fail();
        }
    }

    /**
     * Prueba para crear un Pago con cliente y propuesta nula
     */
    @Test
    public void createPagoConClienteYPropuestaInvalida()  {
        
        try{
            PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        newEntity.setCliente(null);
        pagosLogic.createPago(Long.MIN_VALUE, Long.MIN_VALUE, newEntity);
        }catch(BusinessLogicException b){
            Assert.fail();
        }
    }

    /**
     * Prueba para crear un Pago con una organizacion que no existe.
     */
    @Test
    public void createPagoConClienteYPropuestaInvalida2() {
        
        try{
            PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        ClienteEntity cliente = new ClienteEntity();
        PropuestaEntity propuesta=new PropuestaEntity();
        cliente.setId(Long.MIN_VALUE);
        newEntity.setCliente(cliente);
        pagosLogic.createPago(cliente.getId(), propuesta.getId(), newEntity);
        }catch(BusinessLogicException b){
            Assert.fail();
        }
    }

    /**
     * Prueba para crear un Pago con una propuesta nula.
     */
    @Test
    public void createPaogConPropuestaInvalida3Test()  {
        
        try{
            PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        newEntity.setCliente(clienteData.get(0));
        pagosLogic.createPago(clienteData.get(0).getId(), Long.MIN_VALUE, newEntity);
        }catch(BusinessLogicException b){
            Assert.fail();
        }
    }

    /**
     * Prueba para consultar la lista de Pagos.
     */
    @Test
    public void getPagosTest() {
        List<PagosEntity> list = pagosLogic.getPagos(clienteData.get(0).getId()); 
        for (PagosEntity entity : list) {
            boolean found = false;
            for (PagosEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Pago.
     */
    @Test
    public void getPagoTest() {
        PagosEntity entity = data.get(0);
        PagosEntity resultEntity = pagosLogic.getPago(clienteData.get(0).getId(),entity.getId());
        Assert.assertNotNull(resultEntity);
       Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCliente(), resultEntity.getCliente());
    }

    /**
     * Prueba para actualizar un Pago.
     */
    @Test
    public void updatePagoTest() {
        PagosEntity entity = data.get(0);
        PagosEntity pojoEntity = factory.manufacturePojo(PagosEntity.class);

        pojoEntity.setId(entity.getId());

        pagosLogic.updatePago(pojoEntity.getId(), pojoEntity);

        PagosEntity resp = em.find(PagosEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCliente(), resp.getCliente());
    }

    /**
     * Prueba para eliminar un Prize.
    */
    
    public void deletePagoTest()  {
        
        try{
            PagosEntity entity = data.get(0);
        pagosLogic.deletePago(clienteData.get(0).getId(),entity.getId());
        }catch(BusinessLogicException b){
            Assert.fail();
        }
    }
}