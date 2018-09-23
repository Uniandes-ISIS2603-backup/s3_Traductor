/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity.TipoCliente;
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
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Puebas para Cliente Persistance
 * @author Santiago Salazar
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest {
    /**
     * Inyeccion de dependencias de la persistencia para las pruebas
     */
    @Inject
    private ClientePersistence clientePersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
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
     * Prueba para crear un cliente
     */
    @Test
    public void createClienteTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clientePersistence.create(newEntity);
        
        Assert.assertNotNull(result);

        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());

        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        assertEquals(newEntity.getIdentificacion(), entity.getIdentificacion());
        assertEquals(newEntity.getNombreUsuario(), entity.getNombreUsuario());
        
        Assert.assertNotNull(result.getId());
        
    }
    
    /**
     * Prueba para consultar la lista de clientes.
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = clientePersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity ent : list) {
            boolean found = false;
            for (ClienteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar una lista de clientes 
     * segun un tipo de cliente en particular.
     */
    @Test
    public void getClientesByTipoTest()
    {
        List<ClienteEntity> list = clientePersistence.findAllByTipo(TipoCliente.PERSONA_NATURAL);
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity ent : list) {
            boolean found = false;
            for (ClienteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
        list = clientePersistence.findAllByTipo(TipoCliente.EMPRESA);
        Assert.assertEquals(0, list.size());
    }
    
    /**
     * Prueba para consultar un cliente.
     */
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        
        assertEquals(entity.getNombre(), newEntity.getNombre());
        assertEquals(entity.getCorreoElectronico(), newEntity.getCorreoElectronico());
        assertEquals(entity.getIdentificacion(), newEntity.getIdentificacion());
        assertEquals(entity.getNombreUsuario(), newEntity.getNombreUsuario());
    }
    
    /**
     * Prueba para eliminar un cliente.
     */
    @Test
    public void deleteClienteTest() {
        ClienteEntity entity = data.get(0);
        clientePersistence.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un cliente.
     */
    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setId(entity.getId());

        clientePersistence.update(newEntity);

        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());

        assertEquals(newEntity.getNombre(), resp.getNombre());
        assertEquals(newEntity.getIdentificacion(), resp.getIdentificacion());
    }
    
    /**
     * Prueba para consultar un cliente por identificación.
     */
    @Test
    public void findByIdentificacionTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.findByIdentificacion(entity.getIdentificacion());
        Assert.assertNotNull(newEntity);
        assertEquals(entity.getIdentificacion(), newEntity.getIdentificacion());

        newEntity = clientePersistence.findByIdentificacion(null);
        Assert.assertNull(newEntity);
    }
    
    /**
     * Prueba para consultar un cliente por nombre de usuario.
     */
    @Test
    public void findByNombreUsuarioTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.findByNombreUsuario(entity.getNombreUsuario());
        Assert.assertNotNull(newEntity);
        assertEquals(entity.getNombreUsuario(), newEntity.getNombreUsuario());

        newEntity = clientePersistence.findByNombreUsuario(null);
        Assert.assertNull(newEntity);
    }
    
    /**
     * Prueba para consultar un cliente por correo electrónico.
     */
    @Test
    public void findByCorreoTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.findByCorreo(entity.getCorreoElectronico());
        Assert.assertNotNull(newEntity);
        assertEquals(entity.getCorreoElectronico(), newEntity.getCorreoElectronico());

        newEntity = clientePersistence.findByCorreo(null);
        Assert.assertNull(newEntity);
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);

            entity.setTipoCliente(TipoCliente.PERSONA_NATURAL);
            em.persist(entity);

            data.add(entity);
        }
    }
}