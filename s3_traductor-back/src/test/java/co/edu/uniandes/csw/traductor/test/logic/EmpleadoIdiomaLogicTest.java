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

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoIdiomaLogic;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
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
import org.junit.Assert;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan Parra
 */
@RunWith(Arquillian.class)
public class EmpleadoIdiomaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    
	
    @Inject
    private EmpleadoIdiomaLogic empleadoIdiomaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EmpleadoEntity> data = new ArrayList<>();

    private List<IdiomaEntity> iData = new ArrayList<>();

    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpleadoEntity.class.getPackage())
                .addPackage(EmpleadoLogicTest.class.getPackage())
                .addPackage(EmpleadoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
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
    private void clearData() {
        em.createQuery("delete from EmpleadoEntity").executeUpdate();
        em.createQuery("delete from IdiomaEntity").executeUpdate();
    }
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            IdiomaEntity propuestas = factory.manufacturePojo(IdiomaEntity.class);
            em.persist(propuestas);
            iData.add(propuestas);
        }
        for (int i = 0; i < 3; i++) {
            EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                iData.get(i).addEmpleado(entity);
            }
        }
    }
	
    @Test
    public void addPropuestasTest() {
        EmpleadoEntity entity = data.get(0);
        IdiomaEntity iEntity = iData.get(1);
        IdiomaEntity response = empleadoIdiomaLogic.addIdioma(entity.getId(), iEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(iEntity.getId(), response.getId());
    }
	
    @Test
    public void getBooksTest() {
        List<IdiomaEntity> list = empleadoIdiomaLogic.getIdiomas(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getBookTest()  {
        try{
        EmpleadoEntity entity = data.get(0);
        IdiomaEntity iEntity = iData.get(0);
        IdiomaEntity response = empleadoIdiomaLogic.getIdioma(entity.getId(), iEntity.getId());

        Assert.assertEquals(iEntity.getId(), response.getId());
        Assert.assertEquals(iEntity.getEmpleados().size(), response.getEmpleados().size());
        Assert.assertEquals(iEntity.getIdioma(), response.getIdioma());
        Assert.assertEquals(iEntity.getSolicitud().getId(), response.getSolicitud().getId()); 
       }catch(BusinessLogicException b){
           Assert.fail();
       }      
    }

    @Test
    public void getPropuestaNoAsociadaTest()  {
        try{
            EmpleadoEntity entity = data.get(0);
        IdiomaEntity propuestaEntity = iData.get(1);
        empleadoIdiomaLogic.getIdioma(entity.getId(), propuestaEntity.getId());
        Assert.fail();
        }catch(BusinessLogicException b){
            Assert.assertTrue(true);
        }
    }   
}
