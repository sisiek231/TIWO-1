package cdm;

import cosplay.CosplayEntity;
import cosplay.FranchiseEntity;
import cosplay.SessionGetter;
import cosplay.UsersEntity;
import crud.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.List;


/**
 * Klasa do manipulacji bazą danych o cosplayerach.
 *
 * @author Michał Jereczek
 * @date 20.11.15.
 */
public class CosplayDatabaseAPI {
    /**
     * Dodawanie użytkownika do bazy danych
     *
     * @param nick nick
     * @param age wiek
     * @throws DuplicateEntryException wyrzuca, gdy chcemy dodać isniejącego już użytkownika (nick się powtarza)
     * @throws EmptyStringException wyrzuca, gdy w argurmentach są puste Stringi
     * @throws AgeLowerThenOneException wyrzuca, gdy age jest <= 0
     */
    public static void addUser(String nick, Integer age)
            throws DuplicateEntryException, EmptyStringException, AgeLowerThenOneException {
        if(nick.isEmpty())
            throw new EmptyStringException();
        if(age <= 0)
            throw new AgeLowerThenOneException();

        Session session = SessionGetter.getSession();
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

    /**
     * Dodawanie uniwersum do bazy danych
     *
     * @param name
     * @param genre
     * @throws DuplicateEntryException wyrzuca, gdy chcemy dodać isniejące już uniwersum (name się powtarza)
     * @throws EmptyStringException wyrzuca, gdy w argumentach są puste Stringi
     */
    public static void addFranchise(String name, String genre) throws DuplicateEntryException, EmptyStringException {
        if(name.isEmpty() || genre.isEmpty())
            throw new EmptyStringException();

        Session session = SessionGetter.getSession();
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

    /**
     * Dodanie nowej informacji o cosplay'u.
     *
     * @param date
     * @param isFavourite
     * @param characterName
     * @param franchiseName
     * @param userNick
     * @throws CantFindTheUserException wyrzuca, gdy nie można znaleźć userNick w bazie użytkowników
     * @throws EmptyStringException wyrzuca, gdy w argurmentach są puste Stringi
     * @throws CantFindFranchiseException wyrzuca, gdy nie można znaleźć franchiseName w bazie uniwersów
     */
    public static void addCosplay(Timestamp date, Boolean isFavourite, String characterName, String franchiseName, String userNick)
            throws CantFindTheUserException, EmptyStringException, CantFindFranchiseException {
        if(characterName.isEmpty() || userNick.isEmpty() || franchiseName.isEmpty())
            throw new EmptyStringException();
        Session session = SessionGetter.getSession();

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

    /**
     * Usuwanie wszystkich danych użytkownika
     *
     * @param nick
     * @throws CantFindTheUserException wyrzuca, gdy nie można znaleźć userNick w bazie użytkowników
     */
    public static void deleteUserAndHisCosplayData(String nick) throws CantFindTheUserException {
        Session session = SessionGetter.getSession();
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

    /**
     * Zmiana wieku użytkownika
     *
     * @param nick
     * @param newAge
     * @throws EmptyStringException wyrzuca, gdy w argurmentach są puste Stringi
     * @throws AgeLowerThenOneException wyrzuca, gdy newAge jest <= 0
     * @throws CantFindTheUserException wyrzuca, gdy nie można znaleźć nick w bazie użytkowników
     */
    public static void changeUserAge(String nick, Integer newAge) throws EmptyStringException, AgeLowerThenOneException, CantFindTheUserException {
        if(nick.isEmpty())
            throw new EmptyStringException();
        if(newAge <= 0)
            throw new AgeLowerThenOneException();
        Session session = SessionGetter.getSession();
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

    /**
     * Zwracanie informacji o użytkowniku
     *
     * @param nick
     * @return zwraca UsersEntity
     * @throws EmptyStringException wyrzuca, gdy w argurmentach są puste Stringi
     * @throws CantFindTheUserException wyrzuca, gdy nie można znaleźć nick w bazie użytkowników
     */
    public static UsersEntity getUserData(String nick) throws EmptyStringException, CantFindTheUserException {
        if(nick.isEmpty())
            throw new EmptyStringException();
        Session session = SessionGetter.getSession();
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

    public static class DuplicateEntryException extends Exception {
    }
}
