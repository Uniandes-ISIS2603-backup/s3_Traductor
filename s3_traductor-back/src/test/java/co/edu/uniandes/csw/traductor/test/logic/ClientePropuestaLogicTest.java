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

import co.edu.uniandes.csw.traductor.ejb.ClienteLogic;
import co.edu.uniandes.csw.traductor.ejb.ClientePropuestaLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
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
 * Pruebas de logica de la relacion Cliente - Propuesta
 * Prueba sobre el subrecurso.
 * @author Geovanny Andres Gonzalez
 */
@RunWith(Arquillian.class)
public class ClientePropuestaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteLogic clienteLogic;
	
    @Inject
    private ClientePropuestaLogic clientePropuestaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<>();

    private List<PropuestaEntity> propuestasData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PropuestaEntity propuestas = factory.manufacturePojo(PropuestaEntity.class);
            em.persist(propuestas);
            propuestasData.add(propuestas);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                propuestasData.get(i).setCliente(entity);
            }
        }
    }

    /**
     * Prueba para asociar una propuesta existente a un Cliente.
     */
	
    @Test
    public void addPropuestasTest() {
        ClienteEntity entity = data.get(0);
        PropuestaEntity propuestaEntity = propuestasData.get(1);
        PropuestaEntity response = clientePropuestaLogic.addPropuesta(entity.getId(), propuestaEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(propuestaEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Propuesta asociadas a una
     * instancia Cliente.
     */
	
    @Test
    public void getBooksTest() {
        List<PropuestaEntity> list = clientePropuestaLogic.getPropuestas(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Propuesta asociada a una instancia
     * Cliente.
     * @throw BussinessLogicException si se incumplen las precondiciones de la logica.
     */
    @Test
    public void getBookTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        PropuestaEntity propuestaEntity = propuestasData.get(0);
        PropuestaEntity response = clientePropuestaLogic.getPropuesta(entity.getId(), propuestaEntity.getId());

        Assert.assertEquals(propuestaEntity.getId(), response.getId());
        Assert.assertEquals(propuestaEntity.getCosto(), response.getCosto());
        Assert.assertEquals(propuestaEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(propuestaEntity.getEstado(), response.getEstado());        
    }

    /**
     * Prueba para obtener una instancia de Propuesta asociada a una instancia
     * Cliente que no le pertenece.
     *
     * @throw BussinessLogicException si se incumplen las precondiciones de la logica.
     */
    @Test(expected = BusinessLogicException.class)
    public void getPropuestaNoAsociadaTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        PropuestaEntity propuestaEntity = propuestasData.get(1);
        clientePropuestaLogic.getPropuesta(entity.getId(), propuestaEntity.getId());
    }   
}
