/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistance;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.persistance.PropuestaPersistence;
import java.util.ArrayList;
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
 *
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
     * Prueba para crear una propuesta.     
     */
	
    @Test
    public void createEditorialTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PropuestaEntity newEntity = factory.manufacturePojo(PropuestaEntity.class);
        PropuestaEntity result = propuestaPersistence.create(newEntity);
        Assert.assertNotNull(result);
        PropuestaEntity entity = em.find(PropuestaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getIdEmpleado(), entity.getIdEmpleado());
    }	
}
