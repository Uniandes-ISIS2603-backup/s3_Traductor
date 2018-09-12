/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;

import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.traductor.persistance.TarjetaDeCreditoPersistence;
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
public class TarjetaDeCreditoPersistenceTest {
    
    	/**
     * Inyección de la dependencia a la clase TarjetaDeCreditoPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private TarjetaDeCreditoPersistence tarjetaDeCreditoPersistence;

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
	
    private List<TarjetaDeCreditoEntity> data = new ArrayList<TarjetaDeCreditoEntity>();
	
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
                .addPackage(TarjetaDeCreditoEntity.class.getPackage())
                .addPackage(TarjetaDeCreditoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
	
	/**
     * Configuración inicial de la prueba.
	 * El siguiente metodo, siguiendo la tarjeta dada, deja limpia la tabla para que cada test se realice en limpio y solo con sus
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
            TarjetaDeCreditoEntity entity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);			
            em.persist(entity); //Hace el insert en la base de datos de las tablas.
            data.add(entity); //Agrega a la lista la entidad insertada para que se realicen validaciones luego.
        }
    }
	
	/**
     * Prueba para crear una tarjeta.     
     */
	
    @Test
    public void createTarjetaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaDeCreditoEntity newEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        TarjetaDeCreditoEntity result = tarjetaDeCreditoPersistence.create(newEntity);
        Assert.assertNotNull(result);
        TarjetaDeCreditoEntity entity = em.find(TarjetaDeCreditoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getNumeroTarjetaCredito(), entity.getNumeroTarjetaCredito());
    }	
	
	/**
	 * Permite hacer las pruebas de busqueda de una tarjeta
	 */
	@Test
	public void searchTarjetaTest()
	{
		TarjetaDeCreditoEntity busqueda = tarjetaDeCreditoPersistence.find(Long.MIN_VALUE); //Intenta buscar una tarjeta que no existe.
		Assert.assertNull("La tarjeta no deberia existir en la base de datos pues está vacia", busqueda);
		TarjetaDeCreditoEntity tarjetaEntity = data.get(0); //Toma una tarjeta de la lista.
		TarjetaDeCreditoEntity result = tarjetaDeCreditoPersistence.create(tarjetaEntity); //Guarda la nueva tarjeta en la base de datos para ser buscada.		
		TarjetaDeCreditoEntity busqueda2 = tarjetaDeCreditoPersistence.find(tarjetaEntity.getId());
		Assert.assertNotNull("La tarjeta deberia existir en la base de datos", busqueda2);
		Assert.assertEquals("El numero deberia ser el mismo", busqueda2.getNumeroTarjetaCredito(), tarjetaEntity.getNumeroTarjetaCredito());
	}
	
	/**
     * Prueba para actualizar una tarjeta.
     */
	
    @Test
    public void updateTarjetaTest() {
        TarjetaDeCreditoEntity entity = data.get(0); //Trae la primera tarjeta de la lista de Podam
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaDeCreditoEntity newEntity = factory.manufacturePojo(TarjetaDeCreditoEntity.class); //Crea una nueva entidad para el reemplazo.
        newEntity.setId(entity.getId()); //Reemplaza la PK para que se pueda hacer el reemplazo correctamente.
        tarjetaDeCreditoPersistence.update(newEntity); //Actualiza con la nueva entidad los datos.
        TarjetaDeCreditoEntity resp = em.find(TarjetaDeCreditoEntity.class, entity.getId()); //Busca la entidad que se cambio		
        Assert.assertEquals("El numero de las tarjetas deberia ser igual", newEntity.getNumeroTarjetaCredito(), resp.getNumeroTarjetaCredito());
    }
	
	/**
     * Prueba para eliminar una tarjeta.
     */
	
    @Test
    public void deleteTarjetaTest() {
        TarjetaDeCreditoEntity entity = data.get(0); //Recordar que por inicializacion de @Before la entidad ya existe en la BD
        tarjetaDeCreditoPersistence.delete(entity.getId());
        TarjetaDeCreditoEntity deleted = em.find(TarjetaDeCreditoEntity.class, entity.getId());
        Assert.assertNull("La tarjeta se deberia haber borrado satisfactoriamente",deleted);
    }
	
	
}