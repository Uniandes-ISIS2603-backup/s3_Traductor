package co.edu.uniandes.csw.traductor.test.persistence;


import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.persistence.PagosPersistence;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class PagoPersistenceTest {
    
    	   	/**
     * Inyección de la dependencia a la clase PagoPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private PagosPersistence pagosPersistence;

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
	
    private List<PagosEntity> data = new ArrayList<PagosEntity>();
	
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
                .addPackage(PagosEntity.class.getPackage())
                .addPackage(PagosPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
	
	/**
     * Configuración inicial de la prueba.
	 * El siguiente metodo, siguiendo el pago dada, deja limpia la tabla para que cada test se realice en limpio y solo con sus
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
            PagosEntity entity = factory.manufacturePojo(PagosEntity.class);			
            em.persist(entity); //Hace el insert en la base de datos de las tablas.
            data.add(entity); //Agrega a la lista la entidad insertada para que se realicen validaciones luego.
        }
    }
	
	/**
     * Prueba para crear un pago.     
     */
	
    @Test
    public void createPagoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PagosEntity newEntity = factory.manufacturePojo(PagosEntity.class);
        PagosEntity result = pagosPersistence.create(newEntity);
        Assert.assertNotNull(result);
        PagosEntity entity = em.find(PagosEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }	
	
	/**
	 * Permite hacer las pruebas de busqueda de un pago
	 */
	@Test
	public void searchPagoTest()
	{
		PagosEntity busqueda = pagosPersistence.find(Long.MIN_VALUE); //Intenta buscar un pago que no existe.
		Assert.assertNull("El pago no deberia existir en la base de datos pues está vacia", busqueda);
		PagosEntity pagoEntity = data.get(0); //Toma un pago de la lista.
		PagosEntity result = pagosPersistence.create(pagoEntity); //Guarda el nuevo pago en la base de datos para ser buscada.		
		PagosEntity busqueda2 = pagosPersistence.find(pagoEntity.getId());
		Assert.assertNotNull("El pago deberia existir en la base de datos", busqueda2);
		Assert.assertEquals("El numero deberia ser el mismo", busqueda2.getId(), pagoEntity.getId());
	}
	
	
	/**
     * Prueba para eliminar un pago.
     */
	
    @Test
    public void deletePagoTest() {
        PagosEntity entity = data.get(0); //Recordar que por inicializacion de @Before la entidad ya existe en la BD
        pagosPersistence.delete(entity.getId());
        PagosEntity deleted = em.find(PagosEntity.class, entity.getId());
        Assert.assertNull("El pago se deberia haber borrado satisfactoriamente",deleted);
    }
	
}
