package cdm;

import cosplay.CosplayEntity;
import cosplay.FranchiseEntity;
import cosplay.SessionGetter;
import cosplay.UsersEntity;
import org.hibernate.Session;
import org.junit.Before;

import java.util.List;

/**
 * Created by jereczem on 22.11.15.
 */
public abstract class CdmTester {
    @Before
    public void setUp() throws Exception {
        removeAllInstances(CosplayEntity.class);
        removeAllInstances(UsersEntity.class);
        removeAllInstances(FranchiseEntity.class);
    }

    public static void removeAllInstances(final Class<?> clazz) {
        Session session = SessionGetter.getSession();
        session.beginTransaction();
        final List<?> instances = session.createCriteria(clazz).list();
        for (Object obj : instances) {
            session.delete(obj);
        }
        session.getTransaction().commit();
    }

    public static void main(String[] argv) throws Exception {
        CdmAddUserMethodTester cdmAddUserMethodTester = new CdmAddUserMethodTester();
        cdmAddUserMethodTester.setUp();
    }
}