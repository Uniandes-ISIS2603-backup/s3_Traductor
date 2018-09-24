/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
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
 * Metodos para probar que todas las operaciones de PropuestaPersistence se realicen a cabalidad.
 * @author Geovanny Andres Gonzalez
 */

@RunWith(Arquillian.class)
public class PropuestaPersistenceTest 
{
	/**
     * Inyección de la dependencia a la clase EditorialPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private PropuestaPersistence propuestaPersistence;

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
	
    private List<PropuestaEntity> data = new ArrayList<PropuestaEntity>();
	
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
                .addPackage(PropuestaEntity.class.getPackage())
                .addPackage(PropuestaPersistence.class.getPackage())
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
        em.createQuery("delete from PropuestaEntity").executeUpdate();
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
            PropuestaEntity entity = factory.manufacturePojo(PropuestaEntity.class);			
            em.persist(entity); //Hace el insert en la base de datos de las tablas.
            data.add(entity); //Agrega a la lista la entidad insertada para que se realicen validaciones luego.
        }
    }
	
	/**
     * Prueba para crear una propuesta.     
     */
	
    @Test
    public void createPropuestaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PropuestaEntity newEntity = factory.manufacturePojo(PropuestaEntity.class);
        PropuestaEntity result = propuestaPersistence.create(newEntity);
        Assert.assertNotNull(result);
        PropuestaEntity entity = em.find(PropuestaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getIdEmpleado(), entity.getIdEmpleado());
    }	
	
	/**
	 * Permite hacer las pruebas de busqueda de una propuesta
	 
	@Test
	public void findPropuestaTest()
	{
		PropuestaEntity busqueda = propuestaPersistence.find(Long.MIN_VALUE); //Intenta buscar una propuesta que no existe.
		Assert.assertNull("La propuesta no deberia existir en la base de datos pues está vacia", busqueda);
		PropuestaEntity propuestaLista = data.get(0); //Toma una propuesta de la lista.
		PropuestaEntity result = propuestaPersistence.create(propuestaLista); //Guarda la nueva propuesta en la base de datos para ser buscada.		
		PropuestaEntity busqueda2 = propuestaPersistence.find(propuestaLista.getId());
		Assert.assertNotNull("La propuesta deberia existir en la base de datos", busqueda2);
		Assert.assertEquals("La descripcion deberia ser la misma", busqueda2.getDescripcion(), propuestaLista.getDescripcion());
	}
	*/
	
	/**
     * Prueba para actualizar una propuesta.
     */
	
    @Test
    public void updatePropuestaTest() {
        PropuestaEntity entity = data.get(0); //Trae la primera propuesta de la lista de Podam
        PodamFactory factory = new PodamFactoryImpl();
        PropuestaEntity newEntity = factory.manufacturePojo(PropuestaEntity.class); //Crea una nueva entidad para el reemplazo.
        newEntity.setId(entity.getId()); //Reemplaza la PK para que se pueda hacer el reemplazo correctamente.
        propuestaPersistence.update(newEntity); //Actualiza con la nueva entidad los datos.
        PropuestaEntity resp = em.find(PropuestaEntity.class, entity.getId()); //Busca la entidad que se cambio		
        Assert.assertEquals("El estado de las propuestas deberia ser igual", newEntity.getEstado(), resp.getEstado());
    }
	
	/**
     * Prueba para eliminar una propuesta.
     */
	
    @Test
    public void deletePropuestaTest() {
        PropuestaEntity entity = data.get(0); //Recordar que por inicializacion de @Before la entidad ya existe en la BD
        propuestaPersistence.delete(entity.getId());
        PropuestaEntity deleted = em.find(PropuestaEntity.class, entity.getId());
        Assert.assertNull("La propuesta se deberia haber borrado satisfactoriamente",deleted);
    }
	
	/**
	 * Test para consultar propuestas por costo en la base de datos
	 */
	
	@Test
	public void findAllByCostoTest()
	{
		PodamFactory factory = new PodamFactoryImpl(); //Instancia de Podam para crear los datos aleatorios.
		//Crear con Podam los nuevos casos de prueba.
		List<PropuestaEntity> cola = new LinkedList<>();
		for (short i = 0; i < 4; i++)
		{
			PropuestaEntity nuevaProp = factory.manufacturePojo(PropuestaEntity.class); //Crea una nueva entidad para el reemplazo.
			nuevaProp.setCosto(1); //Pone el costo en un pesito :)
			propuestaPersistence.create(nuevaProp); //Pone en la base de datos los Entities.
		}
		
		List<PropuestaEntity> respuesta = propuestaPersistence.findAllByCosto(1); //Le pide a la BD que le retorne todos las propuestas con costo 1.
		Assert.assertEquals("Deberia haber 4 propuestas con el costo de 1 pesito", respuesta.size(), 4);
	}
	
	/**
	 * Permite consultar todos los elementos en la tabla en la base de datos
	 */
	
	@Test
	public void getAllTest()
	{
		List<PropuestaEntity> entidades = propuestaPersistence.getAll();
		Assert.assertEquals("El numero de elementos en la tabla no es el correcto",3 ,entidades.size());
		PodamFactory podam = new PodamFactoryImpl(); //Crear una nueva entidad.
		PropuestaEntity nuevaEntidad = podam.manufacturePojo(PropuestaEntity.class);
		propuestaPersistence.create(nuevaEntidad);
		entidades = propuestaPersistence.getAll(); //Volver a realizar el llamado para obtener el nuevo elemento
		Assert.assertEquals("El numero de elementos en la tabla no es el correcto", 4, entidades.size());
	}
}
