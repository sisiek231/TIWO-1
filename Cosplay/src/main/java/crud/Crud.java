package crud;

import cdm.CosplayDatabaseAPI;
import cosplay.SessionGetter;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.metadata.ClassMetadata;

import java.util.List;
import java.util.Map;

/**
 * Created by jereczem on 19.11.15.
 */
public class Crud {
    public static void create(Object entity)
            throws UnknownEntityException, CosplayDatabaseAPI.DuplicateEntryException, NotCompletedEntityDataException {
        final Session session = SessionGetter.getSession();
        try{
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }catch (MappingException e){
            throw new UnknownEntityException();
        }catch (ConstraintViolationException e){
            throw new CosplayDatabaseAPI.DuplicateEntryException();
        }catch (PropertyValueException e){
            throw new NotCompletedEntityDataException();
        }
    }

    public static List read(Class entityClass) throws UnknownEntityException {
        final Session session = SessionGetter.getSession();
        final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
        for (Object key : metadataMap.keySet()) {
            final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
            final String entityName = classMetadata.getEntityName();
            if(entityClass.getName().equals(entityName)){
                Transaction tx = session.beginTransaction();
                List list = session.createCriteria(entityClass).list();
                tx.commit();
                return list;
            }
        }
        throw new UnknownEntityException();
    }

    public static void update(Object entity) throws UnknownEntityException, CosplayDatabaseAPI.DuplicateEntryException, NotCompletedEntityDataException, NotDeletedReferencesToOtherEntities {
        final Session session = SessionGetter.getSession();
        try{
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch (MappingException e){
            throw new UnknownEntityException();
        }catch (ConstraintViolationException e){
            throw new CosplayDatabaseAPI.DuplicateEntryException();
        }catch (PropertyValueException e){
            throw new NotCompletedEntityDataException();
        }catch (HibernateException e){
            throw new NotDeletedReferencesToOtherEntities();
        }
    }

    public static void delete(Object entity) throws UnknownEntityException, NotDeletedReferencesToOtherEntities {
        Boolean isEntityFound = false;
        //try{
        final Session session = SessionGetter.getSession();
        final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
        for (Object key : metadataMap.keySet()) {
            final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
            final String entityName = classMetadata.getEntityName();
            if(entity.getClass().getName().equals(entityName)) {
                Transaction tx = session.beginTransaction();
                session.delete(entity);
                tx.commit();
                isEntityFound=true;
            }
        }
        //} /*catch (HibernateException e){
        //    throw new NotDeletedReferencesToOtherEntities();
        //}*/
        if(!isEntityFound)
            throw new UnknownEntityException();
    }

    public static void main(final String[] args){

    }
}
