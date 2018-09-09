/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;

import co.edu.uniandes.csw.traductor.persistence.CalificacionPersistence;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import javax.inject.Inject;
import co.edu.uniandes.csw.traductor.entities.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan Felipe Parra Camargo
 */
@RunWith(Arquillian.class)
public class CalificacionPersistenceTest {
    @Inject
    private CalificacionPersistence calificacionPersistance;
    
    @Inject
    UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
        private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();

     @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
     @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    @Test
    public void createCalificacionTest(){
        PodamFactory factory= new PodamFactoryImpl();
        CalificacionEntity newEntity=new CalificacionEntity();
        CalificacionEntity result=calificacionPersistance.create(newEntity);
        
        Assert.assertNotNull(result);
        
        CalificacionEntity entity=em.find(CalificacionEntity.class,result.getId());
        
        Assert.assertEquals(newEntity.getComentario(),entity.getComentario());
        
    }
   
}
