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
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.InvitacionPersistence;
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
public class InvitacionLogicTest {

	private PodamFactory factory = new PodamFactoryImpl();

	@Inject
	private InvitacionLogic reviewLogic;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	private List<InvitacionEntity> data = new ArrayList<InvitacionEntity>();

	private List<ClienteEntity> dataBook = new ArrayList<ClienteEntity>();

	/**
	 * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido. El jar contiene las clases, el descriptor de la base de datos y el archivo beans.xml para resolver la inyección de dependencias.
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
	private void clearData() {
		em.createQuery("delete from InvitacionEntity").executeUpdate();
		em.createQuery("delete from ClienteEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
			em.persist(entity);
			dataBook.add(entity);
		}

		for (int i = 0; i < 3; i++) {
			InvitacionEntity entity = factory.manufacturePojo(InvitacionEntity.class);
                       
                        entity.setCliente(dataBook.get(1));
			em.persist(entity);
			data.add(entity);
		}
	}

	/**
	 * Prueba para crear una invitacion.
	 * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
	 
	@Test
	public void createInvitacionTest() throws BusinessLogicException {
		InvitacionEntity newEntity = factory.manufacturePojo(InvitacionEntity.class);
		newEntity.setCliente(dataBook.get(1));
		InvitacionEntity result = reviewLogic.createInvitacion(dataBook.get(1).getId(), newEntity);
		Assert.assertNotNull(result);
		InvitacionEntity entity = em.find(InvitacionEntity.class, result.getId());
		Assert.assertEquals(newEntity.getId(), entity.getId());
		Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
	}

	*/
	
	/**
	 * Prueba para consultar la lista de invitaciones.
	 *
	 *
	 * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
	 */
	@Test
	public void getAllInvitacionesTest()  {
		List<InvitacionEntity> list = reviewLogic.getAllInvitaciones(dataBook.get(1).getId());
		Assert.assertEquals(data.size(), list.size());
		for (InvitacionEntity entity : list) {
			boolean found = false;
			for (InvitacionEntity storedEntity : data) {
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
	public void getInvitacionTest() {
		InvitacionEntity entity = data.get(0);
		InvitacionEntity resultEntity = reviewLogic.getInvitacion(dataBook.get(1).getId(), entity.getId());
		Assert.assertNotNull(resultEntity);
		Assert.assertEquals(entity.getId(), resultEntity.getId());
		Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
	}

	/**
	 * Prueba para actualizar un Review.
	 */
	@Test
	public void updateInvitacionTest() {
		InvitacionEntity entity = data.get(0);
		InvitacionEntity pojoEntity = factory.manufacturePojo(InvitacionEntity.class);

		pojoEntity.setId(entity.getId());

		reviewLogic.updateInvitacion(dataBook.get(1).getId(), pojoEntity);

		InvitacionEntity resp = em.find(InvitacionEntity.class, entity.getId());

		Assert.assertEquals(pojoEntity.getId(), resp.getId());
		Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
	}

	/**
	 * Prueba para eliminar un Review.	 *
	 * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
	
	@Test
	public void deleteInvitacionTest() throws BusinessLogicException {
		InvitacionEntity entity = data.get(0);
		reviewLogic.deleteInvitacion(dataBook.get(1).getId(), entity.getId());
		InvitacionEntity deleted = em.find(InvitacionEntity.class, entity.getId());
		Assert.assertNull(deleted);
	}
	 */
	
	/**
	 * Prueba para eliminarle un review a un book del cual no pertenece.	 *
	 * @throws BusinessLogicException
	 */
	@Test(expected = BusinessLogicException.class)
	public void deleteInvitacionConClienteNoAsociadoTest() throws BusinessLogicException {
		InvitacionEntity entity = data.get(0);
		reviewLogic.deleteInvitacion(dataBook.get(0).getId(), entity.getId());
	}
}
