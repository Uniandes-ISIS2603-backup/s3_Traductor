/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.InvitacionLogic;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.InvitacionPersistence;
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
 * Pruebas unitarias de la clase InvitacionLogic
 * @author Geovanny Andres Gonzalez
 */

@RunWith(Arquillian.class)
public class InvitacionLogicTest 
{
	//Objeto podam para llenar los objetos de informacion.
	private PodamFactory podam = new PodamFactoryImpl();
	
	//Inyecciones de clases requeridas.
	
	@Inject 
	private InvitacionLogic invitacionLogic;
	
	@PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
	
	//Listado de objetos invitacion.
	private List<InvitacionEntity> data = new ArrayList<>();

     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(InvitacionEntity.class.getPackage())
                .addPackage(InvitacionLogic.class.getPackage())
                .addPackage(InvitacionPersistence.class.getPackage())
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
        em.createQuery("delete from InvitacionEntity").executeUpdate();        
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
	
    private void insertData()
	{
        for (int i = 0; i < 3; i++) {
            InvitacionEntity invitaciones = podam.manufacturePojo(InvitacionEntity.class);
            em.persist(invitaciones);
            data.add(invitaciones);
        }        
    }
	
	/**
	 * Prueba para comprobar el funcionamiento de createInvitacion()
	*/
	
	@Test
	public void createInvitacionTest()
	{
		InvitacionEntity entidad = podam.manufacturePojo(InvitacionEntity.class);
		//Casos de prueba.
		
		//#1 - Algunos datos de entrada son nulos.
		entidad.setIdEmpleado(null);
		
		try
		{
			invitacionLogic.createInvitacion(entidad); 			
			Assert.fail("El metodo deberia fallar debido a que el id del empleado no existe");
			
		}		
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}		
		
		//#2 - La invitacion se crea satisfactoriamente.
		Long nuevaId = (long) 43211213;
		entidad.setIdEmpleado(nuevaId);
		
		try
		{
			invitacionLogic.createInvitacion(entidad); //Se manda a crear la entidad.
			Assert.assertNotNull("La entidad debio haberse creado y debe existir", invitacionLogic.getInvitacion(entidad.getId()));
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#3 - La descripcion es una cadena vacia		
		entidad.setDescripcion("");
		try
		{
			invitacionLogic.createInvitacion(entidad); //Se manda a crear la entidad.
			Assert.fail("El metodo deberia fallar debido a que la descripcion no puede ser vacia");					
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
	}

	/**
	 * Prueba para comprobar el funcionamiento de updateInvitacion()
	 */
	
	@Test
	public void updateInvitacionTest()
	{
		//Casos de prueba
		InvitacionEntity entidad = data.get(0); //Se trae la primera entidad de los datos.
		
		//#1 - Buscar un objeto inexistente. 
		try
		{
			invitacionLogic.updateInvitacion(Long.MAX_VALUE, entidad);			
			Assert.fail("La invitacion no deberia existir");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#2 - Actualizar satisfactoriamente.
		String descripcion = "Papitas"; //Un nuevo estado a actualizar.
		entidad.setDescripcion(descripcion);
		try	
		{
			invitacionLogic.updateInvitacion(entidad.getId(), entidad);
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		try
		{
			entidad = invitacionLogic.getInvitacion(entidad.getId());
		}
		
		catch(BusinessLogicException e){System.out.println("ATENCION: NO DEBERIA MORIR AQUI"); e.printStackTrace();}
		Assert.assertEquals("La descripcion despues de actualizar no es la deseada", descripcion, entidad.getDescripcion());	
	}
	
	/**
	 * Prueba para comprobar el funcionamiento de getInvitacion()
	 */
	
	@Test
	public void getInvitacionTest()
	{
		//Casos de prueba.
		
		//#1 - Buscar una invitacion inexistente.
		try
		{
			invitacionLogic.getInvitacion(Long.MAX_VALUE);
			Assert.fail("La invitacion no deberia existir");
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		//#2 - Buscar una invitacion existente en la base de datos.
		InvitacionEntity buscado = null; //Vamos a traer la ultima de la lista data.
		try
		{
			buscado = invitacionLogic.getInvitacion(data.get(2).getId());			
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		Assert.assertNotNull("La invitacion buscada no es nula pues sí existe", buscado);
		Assert.assertEquals("La descripcion de la invitacion no es la esperada", buscado.getDescripcion(), data.get(2).getDescripcion());
	}
	
	/**
	 * Prueba para comprobar el funcionamiento de getAllInvitaciones()
	 */
	
	@Test
	public void getAllInvitacionesTest()
	{
		//Caso unico: El numero de invitaciones obtenido debe ser igual al de los creados.
		List<InvitacionEntity> entidades = invitacionLogic.getAllInvitaciones();
		Assert.assertEquals("El numero de elementos no es el deseado", 3, entidades.size());
		for (short g = 0; g < entidades.size(); g++)
		{
			Assert.assertTrue("La entidades no esta en la respuesta", data.contains(entidades.get(g)));
		}
	}
	
	@Test
	public void deleteInvitacionTest()
	{
		//Casos de prueba
				
		//#1 - La invitacion se puede borrar
		InvitacionEntity entidad2 = data.get(1);
		
		try
		{			
			invitacionLogic.deleteInvitacion(entidad2.getId());			
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
		
		try
		{
			
			entidad2 = invitacionLogic.getInvitacion(entidad2.getId());
			Assert.assertNull("La entidad ya debio haberse borrado", entidad2);
		}
		
		catch(BusinessLogicException e){Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);}
	}
}
