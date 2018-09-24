/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.InvitacionLogic;
import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
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
 * Pruebas de logica de Invitacion Se tomo como ejemplo la dada por Review Se hicieron cambios de ultima hora
 *
 * @author Geovanny Andres Gonzalez
 */
@RunWith(Arquillian.class)
public class PropuestaLogicTest {

	private PodamFactory factory = new PodamFactoryImpl();

	@Inject
	private PropuestaLogic reviewLogic;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	private List<PropuestaEntity> data = new ArrayList<PropuestaEntity>();

	private List<EmpleadoEntity> dataBook = new ArrayList<EmpleadoEntity>();

	/**
	 * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido. El jar contiene las clases, el descriptor de la base de datos y el archivo beans.xml para resolver la inyección de dependencias.
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
	private void clearData() {
		em.createQuery("delete from PropuestaEntity").executeUpdate();
		em.createQuery("delete from EmpleadoEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);
			em.persist(entity);
			dataBook.add(entity);
		}

		for (int i = 0; i < 3; i++) {
			PropuestaEntity entity = factory.manufacturePojo(PropuestaEntity.class);
			entity.setCosto(1234);
			entity.setEmpleado(dataBook.get(1));
			em.persist(entity);
			data.add(entity);
		}
	}

	/**
	 * Prueba para crear una invitacion.
	 * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
	*/
	
	@Test
	public void createPropuestaTest() throws BusinessLogicException {
		PropuestaEntity newEntity = factory.manufacturePojo(PropuestaEntity.class);
                newEntity.setCosto(102340);
		newEntity.setEmpleado(dataBook.get(1));
		PropuestaEntity result = reviewLogic.createPropuesta(dataBook.get(1).getId(), newEntity);
		Assert.assertNotNull(result);
		PropuestaEntity entity = em.find(PropuestaEntity.class, result.getId());
		Assert.assertEquals(newEntity.getId(), entity.getId());
		Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
	}

	
	
	/**
	 * Prueba para consultar la lista de invitaciones.
	 *
	 *
	 * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
	 */
	@Test
	public void getAllPropuestasTest() throws BusinessLogicException {
		List<PropuestaEntity> list = reviewLogic.getAllPropuestas(dataBook.get(1).getId());
		Assert.assertEquals(data.size(), list.size());
		for (PropuestaEntity entity : list) {
			boolean found = false;
			for (PropuestaEntity storedEntity : data) {
				if (entity.getId().equals(storedEntity.getId())) {
					found = true;
				}
			}
			Assert.assertTrue(found);
		}
	}

	/**
	 * Prueba para consultar una invitacion.
	 */
	@Test
	public void getPropuestaTest() {
		PropuestaEntity entity = data.get(0);
		PropuestaEntity resultEntity = reviewLogic.getPropuesta(dataBook.get(1).getId(), entity.getId());
		Assert.assertNotNull(resultEntity);
		Assert.assertEquals(entity.getId(), resultEntity.getId());
		Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
	}

	/**
	 * Prueba para actualizar un Review.
	 */
	@Test
	public void updatePropuestaTest() {
		PropuestaEntity entity = data.get(0);
		PropuestaEntity pojoEntity = factory.manufacturePojo(PropuestaEntity.class);

		pojoEntity.setId(entity.getId());

		reviewLogic.updatePropuesta(dataBook.get(1).getId(), pojoEntity);

		PropuestaEntity resp = em.find(PropuestaEntity.class, entity.getId());

		Assert.assertEquals(pojoEntity.getId(), resp.getId());
		Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
	}

	/**
	 * Prueba para eliminar un Review.	 *
	 * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
	*/
	@Test
	public void deletePropuestaTest() throws BusinessLogicException {
		PropuestaEntity entity = data.get(0);
		reviewLogic.deletePropuesta(dataBook.get(1).getId(), entity.getId());
		PropuestaEntity deleted = em.find(PropuestaEntity.class, entity.getId());
		Assert.assertNull(deleted);
	}
	
	
	/**
	 * Prueba para eliminarle un review a un book del cual no pertenece.	 *
	 * @throws co.edu.uniandes.csw.traducto.exceptions.BusinessLogicException
	 */
	@Test(expected = BusinessLogicException.class)
	public void deletePropuestaConClienteNoAsociadoTest() throws BusinessLogicException {
		PropuestaEntity entity = data.get(0);
		reviewLogic.deletePropuesta(dataBook.get(0).getId(), entity.getId());
	}
}
