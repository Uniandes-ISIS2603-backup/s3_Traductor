/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistance;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.persistance.InvitacionPersistence;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Metodos para probar que todas las operaciones de InvitacionPersistence se realicen a cabalidad.
 * @author Geovanny Andres Gonzalez
 */

@RunWith(Arquillian.class)
public class InvitacionPersistenceTest 
{
	/**
     * Inyección de la dependencia a la clase InvitacionPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private InvitacionPersistence invitacionPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

	/**
     * Variable para marcar las transacciones del EntityManager anterior cuando se
     * crean/borran datos para las pruebas.
     */
	
    @Inject
    UserTransaction utx;
	
	/**
     * Lista que tiene los datos de prueba.
     */
	
    private List<InvitacionEntity> data = new ArrayList<InvitacionEntity>();
	
	/**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Editorial, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(InvitacionEntity.class.getPackage())
                .addPackage(InvitacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
	
	/**
     * Configuración inicial de la prueba.
	 * El siguiente metodo, siguiendo la propuesta dada, deja limpia la tabla para que cada test se realice en limpio y solo con sus
	 * datos
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
        em.createQuery("delete from InvitacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     * Para ello se apoya de la libreria Podam que inserta datos aleatorios correctos para 
     * realizar las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++)
		{
            InvitacionEntity entity = factory.manufacturePojo(InvitacionEntity.class);			
            em.persist(entity); //Hace el insert en la base de datos de las tablas.
            data.add(entity); //Agrega a la lista la entidad insertada para que se realicen validaciones luego.
        }
    }
	
	/**
     * Prueba para crear una invitacion.     
     */
	
    @Test
    public void createInvitacionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        InvitacionEntity newEntity = factory.manufacturePojo(InvitacionEntity.class);
        InvitacionEntity result = invitacionPersistence.create(newEntity);
        Assert.assertNotNull(result);
        InvitacionEntity entity = em.find(InvitacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getIdEmpleado(), entity.getIdEmpleado());
    }	
	
	/**
	 * Permite hacer las pruebas de busqueda de una invitacion
	 */
	@Test
	public void findInvitacionTest()
	{
		InvitacionEntity busqueda = invitacionPersistence.find(Long.MIN_VALUE); //Intenta buscar una invitacion que no existe.
		Assert.assertNull("La invitacion no deberia existir en la base de datos pues está vacia", busqueda);
		InvitacionEntity invitacion = data.get(0); //Toma una invitacion de la lista.
		InvitacionEntity result = invitacionPersistence.create(invitacion); //Guarda la nueva invitacion en la base de datos para ser buscada.		
		InvitacionEntity busqueda2 = invitacionPersistence.find(invitacion.getId());
		Assert.assertNotNull("La invitacion deberia existir en la base de datos", busqueda2);
		Assert.assertEquals("La descripcion deberia ser la misma", busqueda2.getDescripcion(), invitacion.getDescripcion());
	}
	
	/**
     * Prueba para actualizar una invitacion.
     */
	
    @Test
    public void updateInvitacionTest() {
        InvitacionEntity entity = data.get(0); //Trae la primera invitacion de la lista de Podam
        PodamFactory factory = new PodamFactoryImpl();
        InvitacionEntity newEntity = factory.manufacturePojo(InvitacionEntity.class); //Crea una nueva entidad para el reemplazo.
        newEntity.setId(entity.getId()); //Reemplaza la PK para que se pueda hacer el reemplazo correctamente.
        invitacionPersistence.update(newEntity); //Actualiza con la nueva entidad los datos.
        InvitacionEntity resp = em.find(InvitacionEntity.class, entity.getId()); //Busca la entidad que se cambio		
        Assert.assertEquals("El ID del empleado deberia ser igual", newEntity.getIdEmpleado(), resp.getIdEmpleado());
    }
	
	/**
     * Prueba para eliminar una invitacion.
     */
	
    @Test
    public void deleteInvitacionTest() {
        InvitacionEntity entity = data.get(0); //Recordar que por inicializacion de @Before la entidad ya existe en la BD
        invitacionPersistence.delete(entity.getId());
        InvitacionEntity deleted = em.find(InvitacionEntity.class, entity.getId());
        Assert.assertNull("La propuesta se deberia haber borrado satisfactoriamente",deleted);
    }	
}
