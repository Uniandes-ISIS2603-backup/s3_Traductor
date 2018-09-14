/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.AreaConocimientoLogic;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.AreaConocimientoPersistence;
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
 * Pruebas unitarias de la clase AreaConocimientoLogic
 * @author Geovanny Andres Gonzalez
 */

@RunWith(Arquillian.class)
public class AreaConocimientoLogicTest 
{
	//Objeto podam para llenar los objetos de informacion.
	private PodamFactory podam = new PodamFactoryImpl();
	
	//Inyecciones de clases requeridas.
	
	@Inject 
	private AreaConocimientoLogic areaLogic;
	
	@PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
	
	//Listado de objetos areaConocimiento.
	private List<AreaConocimientoEntity> data = new ArrayList<>();

     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AreaConocimientoEntity.class.getPackage())
                .addPackage(AreaConocimientoLogic.class.getPackage())
                .addPackage(AreaConocimientoPersistence.class.getPackage())
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
	
    private void clearData() 
	{
        em.createQuery("delete from AreaConocimientoEntity").executeUpdate();        
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
	
    private void insertData()
	{
        for (int i = 0; i < 3; i++) {
            AreaConocimientoEntity areasConocimiento = podam.manufacturePojo(AreaConocimientoEntity.class);
            em.persist(areasConocimiento);
            data.add(areasConocimiento);
        }        
    }
	
	/**
	 * Prueba para comprobar el funcionamiento de createArea()
	*/
	
	@Test
	public void createAreaTest()
	{
		AreaConocimientoEntity entidad = podam.manufacturePojo(AreaConocimientoEntity.class);
		//Casos de prueba.
		
		//#1 - Algunos datos de entrada son nulos.
		entidad.setArea(null);
		
		try
		{
			areaLogic.createArea(entidad); 			
			Assert.fail("El metodo deberia fallar debido a que el id del empleado no existe");
			
		}		
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}		
		
		//#2 - La areaConocimiento se crea satisfactoriamente.
		String nuevaArea = "Matematicas";
		entidad.setArea(nuevaArea);
		
		try
		{
			areaLogic.createArea(entidad); //Se manda a crear la entidad.
			Assert.assertNotNull("La entidad debio haberse creado y debe existir", areaLogic.getArea(entidad.getId()));
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#3 - Intentar crear un area ya existente			
		try
		{
			areaLogic.createArea(data.get(0)); //Se manda a crear la entidad.
			Assert.fail("El metodo deberia fallar debido a que el area ya existe");					
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
	}

	/**
	 * Prueba para comprobar el funcionamiento de updateArea()
	 */
	
	@Test
	public void updateAreaTest()
	{
		//Casos de prueba
		AreaConocimientoEntity entidad = data.get(0); //Se trae la primera entidad de los datos.
		
		//#1 - Buscar un objeto inexistente. 
		try
		{
			areaLogic.updateArea(Long.MAX_VALUE, entidad);			
			Assert.fail("La areaConocimiento no deberia existir");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#2 - Actualizar satisfactoriamente.
		String descripcion = "Papitas"; //Un nuevo estado a actualizar.
		entidad.setArea(descripcion);
		try	
		{
			areaLogic.updateArea(entidad.getId(), entidad);
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		try
		{
			entidad = areaLogic.getArea(entidad.getId());
		}
		
		catch(BusinessLogicException e){System.out.println("ATENCION: NO DEBERIA MORIR AQUI"); e.printStackTrace();}
		Assert.assertEquals("La area despues de actualizar no es la deseada", descripcion, entidad.getArea());	
	}
	
	/**
	 * Prueba para comprobar el funcionamiento de getArea()
	 */
	
	@Test
	public void getAreaTest()
	{
		//Casos de prueba.
		
		//#1 - Buscar una areaConocimiento inexistente.
		try
		{
			areaLogic.getArea(Long.MAX_VALUE);
			Assert.fail("La areaConocimiento no deberia existir");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#2 - Buscar una areaConocimiento existente en la base de datos.
		AreaConocimientoEntity buscado = null; //Vamos a traer la ultima de la lista data.
		try
		{
			buscado = areaLogic.getArea(data.get(2).getId());			
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		Assert.assertNotNull("La areaConocimiento buscada no es nula pues sí existe", buscado);
		Assert.assertEquals("El area del areaConocimiento no es la esperada", buscado.getArea(), data.get(2).getArea());
	}
	
	/**
	 * Prueba para comprobar el funcionamiento de getAllAreas()
	 */
	
	@Test
	public void getAllAreasTest()
	{
		//Caso unico: El numero de areas de conocimiento obtenido debe ser igual al de los creados.
		List<AreaConocimientoEntity> entidades = areaLogic.getAllAreas();
		Assert.assertEquals("El numero de elementos no es el deseado", 3, entidades.size());
		for (short g = 0; g < entidades.size(); g++)
		{
			Assert.assertTrue("La entidades no esta en la respuesta", data.contains(entidades.get(g)));
		}
	}
	
	@Test
	public void deleteAreaTest()
	{
		//Casos de prueba
				
		//#1 - La areaConocimiento se puede borrar
		AreaConocimientoEntity entidad2 = data.get(1);
		
		try
		{			
			areaLogic.deleteArea(entidad2.getId());			
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		try
		{
			
			entidad2 = areaLogic.getArea(entidad2.getId());
			Assert.assertNull("La entidad ya debio haberse borrado", entidad2);
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
	}
}
