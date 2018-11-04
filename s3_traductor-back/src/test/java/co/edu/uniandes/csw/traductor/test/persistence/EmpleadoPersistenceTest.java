/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity.TipoEmpleado;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
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
 * Puebas para Empleado Persistance
 * @author Alvaro Ayte
 */
@RunWith(Arquillian.class)
public class EmpleadoPersistenceTest {
    /**
     * Inyeccion de dependencias de la persistencia para las pruebas
     */
    @Inject
    private EmpleadoPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<EmpleadoEntity> data = new ArrayList<EmpleadoEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpleadoEntity.class.getPackage())
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
     * Prueba para crear un Empleado
     */
    @Test
    public void createEmpleadoTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        EmpleadoEntity newEntity = factory.manufacturePojo(EmpleadoEntity.class);
        EmpleadoEntity result = persistence.create(newEntity);
        
        Assert.assertNotNull(result);

        EmpleadoEntity entity = em.find(EmpleadoEntity.class, result.getId());

        assertEquals(newEntity.getNombreEmpleado(), entity.getNombreEmpleado());
        assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        assertEquals(newEntity.getNombreUsuario(), entity.getNombreUsuario());
        
        Assert.assertNotNull(result.getId());
        
    }
    
    /**
     * Prueba para consultar la lista de Empleados.
     */
    @Test
    public void getEmpleadosTest() {
        List<EmpleadoEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EmpleadoEntity ent : list) {
            boolean found = false;
            for (EmpleadoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un Empleados.
     */
    @Test
    public void getEmpleadoTest() {
        EmpleadoEntity entity = data.get(0);
        EmpleadoEntity newEntity = persistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        
        assertEquals(entity.getNombreEmpleado(), newEntity.getNombreEmpleado());
        assertEquals(entity.getCorreoElectronico(), newEntity.getCorreoElectronico());
        assertEquals(entity.getNombreUsuario(), newEntity.getNombreUsuario());
    }
    
    /**
     * Prueba para eliminar un empleado.
     */
    @Test
    public void deleteEmpleadoTest() {
        EmpleadoEntity entity = data.get(0);
        persistence.delete(entity.getId());
        EmpleadoEntity deleted = em.find(EmpleadoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un empleado.
     */
    @Test
    public void updateEmpleadoTest() {
        EmpleadoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EmpleadoEntity newEntity = factory.manufacturePojo(EmpleadoEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        EmpleadoEntity resp = em.find(EmpleadoEntity.class, entity.getId());

        assertEquals(newEntity.getNombreEmpleado(), resp.getNombreEmpleado());
        assertEquals(newEntity.getCorreoElectronico(), resp.getCorreoElectronico());
    }
    
    
    /**
     * Prueba para consultar un empleado por nombre de usuario.
     */
    @Test
    public void findByNombreUsuarioTest() {
        EmpleadoEntity entity = data.get(0);
        EmpleadoEntity newEntity = persistence.findByNombreUsuario(entity.getNombreUsuario());
        Assert.assertNotNull(newEntity);
        assertEquals(entity.getNombreUsuario(), newEntity.getNombreUsuario());

        newEntity = persistence.findByNombreUsuario(null);
        Assert.assertNull(newEntity);
    }
    
    /**
     * Prueba para consultar un cliente por correo electrónico.
     */
    @Test
    public void findByCorreoTest() {
        EmpleadoEntity entity = data.get(0);
        EmpleadoEntity newEntity = persistence.findByCorreo(entity.getCorreoElectronico());
        Assert.assertNotNull(newEntity);
        assertEquals(entity.getCorreoElectronico(), newEntity.getCorreoElectronico());

        newEntity = persistence.findByCorreo(null);
        Assert.assertNull(newEntity);
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from EmpleadoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);

            entity.setTipoEmpleado(TipoEmpleado.TRADUCTOR);
            em.persist(entity);

            data.add(entity);
        }
    }
}