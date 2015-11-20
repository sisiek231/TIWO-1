import cosplay.CosplayEntity;
import cosplay.FranchiseEntity;
import cosplay.Main;
import cosplay.UsersEntity;
import crud.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by jereczem on 20.11.15.
 */
public class CosplayDatabaseManipulator {
    public static void addUser(String nick, Integer age)
            throws DuplicateEntryException, EmptyStringException, AgeLowerThenOneException {
        if(nick.isEmpty())
            throw new EmptyStringException();
        if(age <= 0)
            throw new AgeLowerThenOneException();

        Session session = Main.getSession();
        Query getUserQuery = session.createQuery("SELECT U FROM UsersEntity U WHERE U.nick=" + "'"+ nick + "'");
        List users = getUserQuery.list();
        if(!users.isEmpty())
            throw new DuplicateEntryException();

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setNick(nick);
        usersEntity.setAge(age);
        try {
            Crud.create(usersEntity);
        } catch (UnknownEntityException e) {
            System.out.println(e.getClass().getName());
        } catch (NotCompletedEntityDataException e) {
            System.out.println(e.getClass().getName());
        }
    }

    public static void addFranchise(String name, String genre) throws DuplicateEntryException, EmptyStringException {
        if(name.isEmpty() || genre.isEmpty())
            throw new EmptyStringException();

        Session session = Main.getSession();
        Query getFranchiseQuery = session.createQuery("SELECT F FROM FranchiseEntity F WHERE F.name=" + "'"+ name + "'");
        List franchises = getFranchiseQuery.list();
        if(!franchises.isEmpty())
            throw new DuplicateEntryException();

        FranchiseEntity franchiseEntity = new FranchiseEntity();
        franchiseEntity.setName(name);
        franchiseEntity.setGenre(genre);
        try {
            Crud.create(franchiseEntity);
        } catch (UnknownEntityException e) {
            System.out.println(e.getClass().getName());
        } catch (NotCompletedEntityDataException e) {
            System.out.println(e.getClass().getName());
        }
    }

    public static void addCosplay(Timestamp date, Boolean isFavourite, String characterName, String franchiseName, String userNick)
            throws CantFindTheUserException, EmptyStringException, CantFindFranchiseException {
        if(characterName.isEmpty() || userNick.isEmpty() || franchiseName.isEmpty())
            throw new EmptyStringException();
        Session session = Main.getSession();

        Query getUserQuery = session.createQuery("SELECT U FROM UsersEntity U WHERE U.nick=" + "'"+ userNick + "'");
        List users = getUserQuery.list();
        if(users.isEmpty())
            throw new CantFindTheUserException();
        UsersEntity usersEntity = (UsersEntity) users.get(0);

        Query getFranchiseQuery = session.createQuery("SELECT F FROM FranchiseEntity F WHERE F.name=" + "'"+ franchiseName + "'");
        List franchises = getFranchiseQuery.list();
        if(franchises.isEmpty())
            throw new CantFindFranchiseException();
        FranchiseEntity franchiseEntity = (FranchiseEntity) franchises.get(0);

        CosplayEntity cosplayEntity = new CosplayEntity();
        cosplayEntity.setDate(date);
        cosplayEntity.setFavourite(isFavourite);
        cosplayEntity.setCharacterName(characterName);
        cosplayEntity.setFranchiseByFranchiseId(franchiseEntity);
        cosplayEntity.setUsersByUsersId(usersEntity);

        try {
            Crud.create(cosplayEntity);
        } catch (UnknownEntityException e) {
            System.out.println(e.getClass().getName());
        } catch (DuplicateEntryException e) {
            System.out.println(e.getClass().getName());
        } catch (NotCompletedEntityDataException e) {
            System.out.println(e.getClass().getName());
        }
    }

    public static void deleteUserAndHisCosplayData(String nick) throws CantFindTheUserException {
        Session session = Main.getSession();
        Query getUserQuery = session.createQuery("SELECT U FROM UsersEntity U WHERE U.nick=" + "'"+ nick + "'");
        List users = getUserQuery.list();
        if(users.isEmpty())
            throw new CantFindTheUserException();
        UsersEntity usersEntity = (UsersEntity) users.get(0);
        try {
            for(CosplayEntity cosplayEntity : usersEntity.getCosplaysByIdUsers()) {
                Crud.delete(cosplayEntity);
            }
            usersEntity.setCosplaysByIdUsers(null);
            Crud.delete(usersEntity);
        } catch (UnknownEntityException e) {
            e.printStackTrace();
        } catch (NotDeletedReferencesToOtherEntities notDeletedReferencesToOtherEntities) {
            notDeletedReferencesToOtherEntities.printStackTrace();
        }
    }

    public static void changeUserAge(String nick, Integer newAge) throws EmptyStringException, AgeLowerThenOneException, CantFindTheUserException {
        if(nick.isEmpty())
            throw new EmptyStringException();
        if(newAge <= 0)
            throw new AgeLowerThenOneException();
        Session session = Main.getSession();
        Query getUserQuery = session.createQuery("SELECT U FROM UsersEntity U WHERE U.nick=" + "'" + nick + "'");
        List users = getUserQuery.list();
        if(users.isEmpty())
            throw new CantFindTheUserException();
        UsersEntity usersEntity = (UsersEntity) users.get(0);
        usersEntity.setCosplaysByIdUsers(null);
        usersEntity.setAge(newAge);
        try {
            Crud.update(usersEntity);
        } catch (UnknownEntityException e) {
            e.printStackTrace();
        } catch (DuplicateEntryException e) {
            e.printStackTrace();
        } catch (NotCompletedEntityDataException e) {
            e.printStackTrace();
        } catch (NotDeletedReferencesToOtherEntities notDeletedReferencesToOtherEntities) {
            notDeletedReferencesToOtherEntities.printStackTrace();
        }
    }

    public static UsersEntity getUserData(String nick) throws EmptyStringException, CantFindTheUserException {
        if(nick.isEmpty())
            throw new EmptyStringException();
        Session session = Main.getSession();
        Query getUserQuery = session.createQuery("SELECT U FROM UsersEntity U WHERE U.nick=" + "'"+ nick + "'");
        List users = getUserQuery.list();
        if(users.isEmpty())
            throw new CantFindTheUserException();
        return (UsersEntity) users.get(0);
    }

    public static class EmptyStringException extends Exception {
    }

    public static class AgeLowerThenOneException extends Exception {
    }

    public static class CantFindTheUserException extends Exception {
    }

    public static class CantFindFranchiseException extends Exception {
    }

    public static void main(String[] argv)
            throws AgeLowerThenOneException, EmptyStringException, CantFindTheUserException, CantFindFranchiseException {

        try {
            addUser("Shafear", 18);
        } catch (DuplicateEntryException e) {
            System.out.println(e.getClass().getName());
        }
        addCosplay(new Timestamp(new Date().getTime()), true, "Ginny", "Harry Potter", "Shafear");
        addCosplay(new Timestamp(new Date().getTime()), true, "Hermione", "Harry Potter", "Shafear");
        try {
            addFranchise("Harry Potter", "Comedy");
        }catch (DuplicateEntryException e){
            System.out.println(e.getClass().getName());
        }
        changeUserAge("Shafear", 10);
        UsersEntity shafear = getUserData("Shafear");
        System.out.println(shafear.toString());
        deleteUserAndHisCosplayData("Shafear");
    }


}
