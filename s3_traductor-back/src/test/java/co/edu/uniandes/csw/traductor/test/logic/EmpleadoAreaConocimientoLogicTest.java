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
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoAreaConocimientoLogic;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
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
 * @author Alvaro ayte
 */
@RunWith(Arquillian.class)
public class EmpleadoAreaConocimientoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    
	
    @Inject
    private EmpleadoAreaConocimientoLogic empleadoAreaConocimientoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EmpleadoEntity> data = new ArrayList<>();

    private List<AreaConocimientoEntity> iData = new ArrayList<>();
    List<AreaConocimientoEntity> listTmp=new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpleadoEntity.class.getPackage())
                .addPackage(EmpleadoLogic.class.getPackage())
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
        em.createQuery("delete from AreaConocimientoEntity").executeUpdate();
        em.createQuery("delete from EmpleadoEntity").executeUpdate();

    }
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);
            em.persist(entity);
            data.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            AreaConocimientoEntity areasConocimiento = factory.manufacturePojo(AreaConocimientoEntity.class);
            
            areasConocimiento.setEmpleados(data);
            em.persist(areasConocimiento);
            iData.add(areasConocimiento);
            listTmp.add(areasConocimiento);
           
        }

        data.get(0).setAreasDeConocimiento(listTmp);
        EmpleadoEntity entity2 = factory.manufacturePojo(EmpleadoEntity.class);
        em.persist(entity2);
        data.add(entity2);
        AreaConocimientoEntity areasConocimiento2 = factory.manufacturePojo(AreaConocimientoEntity.class);
        em.persist(areasConocimiento2);
        iData.add(areasConocimiento2);
    }
	
    @Test
    public void addAreaConocimientoTest() {
        EmpleadoEntity entity = data.get(3);
        AreaConocimientoEntity iEntity = iData.get(3);
        AreaConocimientoEntity response = empleadoAreaConocimientoLogic.addArea(entity.getId(), iEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(iEntity.getId(), response.getId());
    }
	
    @Test
    public void getAreasConocimientoTest() {
        List<AreaConocimientoEntity> list = empleadoAreaConocimientoLogic.getAreasConocimiento(data.get(0).getId());
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void getAreaConocimientoTest()  {
        try{
        EmpleadoEntity entity = data.get(0);
        AreaConocimientoEntity iEntity = listTmp.get(0);
        AreaConocimientoEntity response = empleadoAreaConocimientoLogic.getAreaConocmiento(entity.getId(), iEntity.getId());

        Assert.assertEquals(iEntity.getId(), response.getId());
        Assert.assertEquals(iEntity.getArea(), response.getArea());
       }catch(BusinessLogicException b){
           Assert.fail();
       }      
    }

    @Test
    public void getAreaConocimientoNoAsociadoTest()  {
        try{
            EmpleadoEntity entity = data.get(3);
        AreaConocimientoEntity areaConocimientoEntity = iData.get(3);
        empleadoAreaConocimientoLogic.getAreaConocmiento(entity.getId(), areaConocimientoEntity.getId());
        Assert.fail();
        }catch(BusinessLogicException b){
            Assert.assertTrue(true);
        }
    }   
}
