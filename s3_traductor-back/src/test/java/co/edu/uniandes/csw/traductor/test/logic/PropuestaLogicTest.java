/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
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
 * Pruebas unitarias de la clase PropuestaLogic
 * @author Geovanny Andres Gonzalez
 */

@RunWith(Arquillian.class)
public class PropuestaLogicTest 
{
	//Objeto podam para llenar los objetos de informacion.
	private PodamFactory podam = new PodamFactoryImpl();
	
	//Inyecciones de clases requeridas.
	
	@Inject 
	private PropuestaLogic propuestaLogic;
	
	@PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
	
	//Listado de objetos propuesta.
	private List<PropuestaEntity> data = new ArrayList<>();

     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PropuestaEntity.class.getPackage())
                .addPackage(PropuestaLogic.class.getPackage())
                .addPackage(PropuestaPersistence.class.getPackage())
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
        em.createQuery("delete from PropuestaEntity").executeUpdate();        
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
	
    private void insertData()
	{
        for (int i = 0; i < 3; i++) {
            PropuestaEntity propuestas = podam.manufacturePojo(PropuestaEntity.class);
            em.persist(propuestas);
            data.add(propuestas);
        }        
    }
	
	/**
	 * Prueba para comprobar el funcionamiento de createPropuesta()
	*/
	
	@Test
	public void createPropuestaTest()
	{
		PropuestaEntity entidad = podam.manufacturePojo(PropuestaEntity.class);
		//Casos de prueba.
		
		//#1 - Algunos datos de entrada son nulos.
		entidad.setIdEmpleado(null);
		
		try
		{
			propuestaLogic.createPropuesta(entidad); 			
			Assert.fail("El metodo deberia fallar debido a que el id del empleado no existe");
			
		}		
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}		
		
		//#2 - La propuesta se crea satisfactoriamente.
		Long nuevaId = (long) 43211213;
		entidad.setIdEmpleado(nuevaId);
		
		try
		{
			propuestaLogic.createPropuesta(entidad); //Se manda a crear la entidad.
			Assert.assertNotNull("La entidad debio haberse creado y debe existir", propuestaLogic.getPropuesta(entidad.getId()));
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#3 - El costo es un valor negativo.
		entidad.setCosto(-23);
		try
		{
			propuestaLogic.createPropuesta(entidad); //Se manda a crear la entidad.			
			Assert.fail("El metodo deberia fallar debido a que el costo no puede ser un valor negativo");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//4 - La descripcion es una cadena vacia
		entidad.setCosto(23);
		entidad.setDescripcion("");
		try
		{
			propuestaLogic.createPropuesta(entidad); //Se manda a crear la entidad.
			Assert.fail("El metodo deberia fallar debido a que la descripcion no puede ser vacia");					
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
	}

	/**
	 * Prueba para comprobar el funcionamiento de updatePropuesta()
	 */
	
	@Test
	public void updatePropuestaTest()
	{
		//Casos de prueba
		PropuestaEntity entidad = data.get(0); //Se trae la primera entidad de los datos.
		
		//#1 - Buscar un objeto inexistente. 
		try
		{
			propuestaLogic.updatePropuesta(Long.MAX_VALUE, entidad);			
			Assert.fail("La propuesta no deberia existir");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#2 - Actualizar satisfactoriamente.
		String descripcion = "Papitas"; //Un nuevo estado a actualizar.
		entidad.setDescripcion(descripcion);
		try	
		{
			propuestaLogic.updatePropuesta(entidad.getId(), entidad);
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		try
		{
			entidad = propuestaLogic.getPropuesta(entidad.getId());
		}
		
		catch(BusinessLogicException e){System.out.println("ATENCION: NO DEBERIA MORIR AQUI"); e.printStackTrace();}
		Assert.assertEquals("La descripcion despues de actualizar no es la deseada", descripcion, entidad.getDescripcion());	
	}
	
	/**
	 * Prueba para comprobar el funcionamiento de getPropuesta()
	 */
	@Test
	public void getPropuestaTest()
	{
		//Casos de prueba.
		
		//#1 - Buscar una propuesta inexistente.
		try
		{
			propuestaLogic.getPropuesta(Long.MAX_VALUE);
			Assert.fail("La propuesta no deberia existir");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#2 - Buscar una propuesta existente en la base de datos.
		PropuestaEntity buscado = null; //Vamos a traer la ultima de la lista data.
		try
		{
			buscado = propuestaLogic.getPropuesta(data.get(2).getId());			
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		Assert.assertNotNull("La propuesta buscada no es nula pues sí existe", buscado);
		Assert.assertEquals("La descripcion de la propuesta no es la esperada", buscado.getDescripcion(), data.get(2).getDescripcion());
	}
	
	/**
	 * Prueba para comprobar el funcionamiento de getAllPropuestas()
	 */
	
	@Test
	public void getAllPropuestasTest()
	{
		//Caso unico: El numero de propuestas obtenidas debe ser igual al de los creados.
		List<PropuestaEntity> entidades = propuestaLogic.getAllPropuestas();
		Assert.assertEquals("El numero de elementos no es el deseado", 3, entidades.size());
		for (short g = 0; g < entidades.size(); g++)
		{
			Assert.assertTrue("La entidades no esta en la respuesta", data.contains(entidades.get(g)));
		}
	}
	
	@Test
	public void deletePropuestaTest()
	{
		//Casos de prueba
		
		//#1 - La propuesta no se puede borrar debido a que tiene una invitacion.
		/*
		PropuestaEntity entidad1 = data.get(0);
		InvitacionEntity nuevaEntidad = podam.manufacturePojo(InvitacionEntity.class);
		entidad1.setInvitacion(nuevaEntidad);
		
		try
		{
			entidad1 = propuestaLogic.updatePropuesta(entidad1.getId(), entidad1); //Meter el objeto Invitacion.
			InvitacionEntity invitacionBuscada = propuestaLogic.getPropuesta(entidad1.getId()).getInvitacion();
 			Assert.assertNotNull("La invitacion no debe de ser nula" , entidad1.getInvitacion());
			propuestaLogic.deletePropuesta(entidad1.getId());
			Assert.fail("Deberia haber fallado pues la invitacion no es nula y por ello no se puede borrar");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		*/
		
		//#2 - La propuesta se puede borrar
		PropuestaEntity entidad2 = data.get(1);
		
		try
		{			
			propuestaLogic.deletePropuesta(entidad2.getId());			
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		try
		{
			
			entidad2 = propuestaLogic.getPropuesta(entidad2.getId());
			Assert.assertNull("La entidad ya debio haberse borrado", entidad2);
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
	}
}
