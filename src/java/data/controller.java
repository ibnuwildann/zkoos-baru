package data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.zkoss.zul.Window;

/**
 *
 * @author PERSONAL
 */
public class controller extends Window {
 
    List customers = new ArrayList();
 
    public controller() {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("data");
        EntityManager em = emf.createEntityManager();
        javax.persistence.Query q = em.createQuery("select c from Mahasiswa as c");
        customers = q.getResultList();
        em.close();
    }
 
    public List getCustomers() {
        return customers;
    }
    
}