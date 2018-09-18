/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.persistence.AreaConocimientoPersistence;
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
 * Metodos para probar que todas las operaciones de AreaConocimientoPersistence se realicen a cabalidad.
 * @author Geovanny Andres Gonzalez
 */

@RunWith(Arquillian.class)
public class AreaConocimientoPersistenceTest 
{
	/**
     * Inyección de la dependencia a la clase AreaConocimientoPersistence cuyos métodos
     * se van a probar.
     */
    
	@Inject
    private AreaConocimientoPersistence areaPersistence;

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
	
    private List<AreaConocimientoEntity> data = new ArrayList<AreaConocimientoEntity>();
	
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
                .addPackage(AreaConocimientoEntity.class.getPackage())
                .addPackage(AreaConocimientoPersistence.class.getPackage())
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
        em.createQuery("delete from AreaConocimientoEntity").executeUpdate();
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
            AreaConocimientoEntity entity = factory.manufacturePojo(AreaConocimientoEntity.class);			
            em.persist(entity); //Hace el insert en la base de datos de las tablas.
            data.add(entity); //Agrega a la lista la entidad insertada para que se realicen validaciones luego.
        }
    }
	
	/**
     * Prueba para crear una area de conocimiento.     
     */
	
    @Test
    public void createAreaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        AreaConocimientoEntity newEntity = factory.manufacturePojo(AreaConocimientoEntity.class);
        AreaConocimientoEntity result = areaPersistence.create(newEntity);
        Assert.assertNotNull(result);
        AreaConocimientoEntity entity = em.find(AreaConocimientoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getArea(), entity.getArea());
    }	
	
	/**
	 * Permite hacer las pruebas de busqueda de una area de conocimiento
	 */
	@Test
	public void findAreaTest()
	{
		AreaConocimientoEntity busqueda = areaPersistence.find(Long.MIN_VALUE); //Intenta buscar un area que no existe.
		Assert.assertNull("El area de conocimiento no deberia existir en la base de datos pues está vacia", busqueda);
		AreaConocimientoEntity invitacion = data.get(0); //Toma un area de la lista.
		AreaConocimientoEntity result = areaPersistence.create(invitacion); //Guarda la nueva area en la base de datos para ser buscada.		
		AreaConocimientoEntity busqueda2 = areaPersistence.find(invitacion.getId());
		Assert.assertNotNull("El area de conocimiento deberia existir en la base de datos", busqueda2);
		Assert.assertEquals("El area deberia ser la misma", busqueda2.getArea(), invitacion.getArea());
	}
	
	/**
	 * Permite encontrar un area de conocimiento por medio de su area.
	 */
	
	@Test
	public void findByArea()
	{
		PodamFactory podam = new PodamFactoryImpl();
		AreaConocimientoEntity entidad = podam.manufacturePojo(AreaConocimientoEntity.class);
		entidad.setArea("Matematicas");
		areaPersistence.create(entidad); //Guardar en la base de datos.
		AreaConocimientoEntity buscado = areaPersistence.findByArea("Matematicas");
		Assert.assertNotNull("El area de conocimiento no deberia ser nulo", buscado);
		Assert.assertEquals("El nombre del area de conocimiento no concuerda", "Matematicas", buscado.getArea());
	}
	
	
	/**
     * Prueba para actualizar un area de conocimiento.
     */
	
    @Test
    public void updateAreaTest() {
        AreaConocimientoEntity entity = data.get(0); //Trae la primera area de la lista de Podam
        PodamFactory factory = new PodamFactoryImpl();
        AreaConocimientoEntity newEntity = factory.manufacturePojo(AreaConocimientoEntity.class); //Crea una nueva entidad para el reemplazo.
        newEntity.setId(entity.getId()); //Reemplaza la PK para que se pueda hacer el reemplazo correctamente.
        areaPersistence.update(newEntity); //Actualiza con la nueva entidad los datos.
        AreaConocimientoEntity resp = em.find(AreaConocimientoEntity.class, entity.getId()); //Busca la entidad que se cambio		
        Assert.assertEquals("El area de conocimiento deberia ser igual", newEntity.getArea(), resp.getArea());
    }
	
	/**
     * Prueba para eliminar un area de conocimiento.
     */
	
    @Test
    public void deleteAreaTest() {
        AreaConocimientoEntity entity = data.get(0); //Recordar que por inicializacion de @Before la entidad ya existe en la BD
        areaPersistence.delete(entity.getId());
        AreaConocimientoEntity deleted = em.find(AreaConocimientoEntity.class, entity.getId());
        Assert.assertNull("El area de conocimiento se deberia haber borrado satisfactoriamente",deleted);
    }

	/**
	 * Permite consultar todos los elementos en la tabla en la base de datos
	 */
	
	@Test
	public void getAllTest()
	{
		List<AreaConocimientoEntity> entidades = areaPersistence.getAll();
		Assert.assertEquals("El numero de elementos en la tabla no es el correcto",3 ,entidades.size());
		PodamFactory podam = new PodamFactoryImpl(); //Crear una nueva entidad.
		AreaConocimientoEntity nuevaEntidad = podam.manufacturePojo(AreaConocimientoEntity.class);
		areaPersistence.create(nuevaEntidad);
		entidades = areaPersistence.getAll(); //Volver a realizar el llamado para obtener el nuevo elemento
		Assert.assertEquals("El numero de elementos en la tabla no es el correcto", 4, entidades.size());
	}	
}
