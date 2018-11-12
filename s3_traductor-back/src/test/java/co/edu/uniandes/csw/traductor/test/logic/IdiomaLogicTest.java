/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.IdiomaLogic;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.IdiomaPersistence;
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
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 *
 * @author Juan Felipe Parra
 */
@RunWith(Arquillian.class)
public class IdiomaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IdiomaLogic idiomaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<IdiomaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IdiomaEntity.class.getPackage())
                .addPackage(IdiomaLogic.class.getPackage())
                .addPackage(IdiomaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuracion incial del test
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
     * Dejar limpia la tabla implicada en la pruba
     */
    private void clearData() {
        em.createQuery("delete from IdiomaEntity").executeUpdate();
    }

    /**
     * Inserta datos para llevar a cabo las pruebas
     */
    private void insertData() {
        for (int i = 0; i <= 2; i++) {
            IdiomaEntity idiom = factory.manufacturePojo(IdiomaEntity.class);
            em.persist(idiom);
            data.add(idiom);
        }
    }

    @Test
    public void createCalificacionTest() {
        IdiomaEntity entity = factory.manufacturePojo(IdiomaEntity.class);
        // creacion satisfactoria
        entity.setIdioma("ingles");
        entity.setId(Long.MAX_VALUE);
        try {
            idiomaLogic.createIdioma(entity);
            Assert.assertNotNull("El idioma debió persistir", idiomaLogic.getIdioma(entity.getId()));
        } catch (BusinessLogicException e) {
            Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);
        }
        //crecion de ya existente
        try {
            idiomaLogic.createIdioma(data.get(0));
            Assert.fail("El metodo no deberia llegar, ya que existe esa entidad previamente");

        } catch (BusinessLogicException e) {
            Assert.assertTrue("Deberia haber un mensaje de excepcion", e.getMessage().length() != 0);
        }

    }

    /**
     * verificar la obtencion de una calificacion
     */
    @Test
    public void getIdiomaTest() {
        IdiomaEntity i = idiomaLogic.getIdioma(Long.MAX_VALUE);
        Assert.assertNull("El idioma no debería existir", i);

        IdiomaEntity a = idiomaLogic.getIdioma(data.get(1).getId());

        Assert.assertNotNull("El idioma buscada no es nula, pues si existe", a);
        Assert.assertEquals("El idioma no es la esperada", a.getIdioma(), data.get(1).getIdioma());
    }

    /**
     * verificar la obtencion de todas las calificaciones
     */
    @Test
    public void getIdiomasTest() {
        List<IdiomaEntity> c = idiomaLogic.getIdiomas();
        Assert.assertEquals("El numero de elementos no coincide", 3, c.size());
        for (IdiomaEntity ce : c) {
            Assert.assertTrue("Hay elementos no concistentes", data.contains(ce));
        }
    }

    /**
     * verificar la elimiancion de un idioma
     */
    public void deleteIdiomaTest() {
        IdiomaEntity i = idiomaLogic.getIdioma(data.get(2).getId());
        idiomaLogic.deleteIdioma(i.getId());
        Assert.assertNull("El idioma deberia ser nulo", idiomaLogic.getIdioma(data.get(2).getId()));
    }
}
