package cda;

import cosplay.CosplayEntity;
import cosplay.FranchiseEntity;
import cosplay.SessionGetter;
import cosplay.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zwracająca listy: użytkowników, cosplayów i uniwersów z bazy danych.
 * Klasa NIE PODLEGA testowaniu - jest wykorzystywana jako obiekt do porównania w wynikach testów - zakładamy,
 * że działa bezbłędnie.
 *
 * @author Michał Jereczek
 * @date 20.11.15.
 */
public class CosplayDatabaseData {

    ArrayList<UsersEntity> usersList = receiveUsersList();
    ArrayList<FranchiseEntity> franchisesList = receiveFranchisesList();
    ArrayList<CosplayEntity> cosplayList = receiveCosplaysList();

    public ArrayList<UsersEntity> getUsersList() {
        return usersList;
    }

    public ArrayList<FranchiseEntity> getFranchisesList() {
        return franchisesList;
    }

    public ArrayList<CosplayEntity> getCosplayList() {
        return cosplayList;
    }

    private ArrayList<UsersEntity> receiveUsersList(){
        Session session = SessionGetter.getSession();
        Transaction tx = session.beginTransaction();
        List list = session.createCriteria(UsersEntity.class).list();
        tx.commit();
        return (ArrayList<UsersEntity>)list;
    }

    private ArrayList<FranchiseEntity> receiveFranchisesList(){
        Session session = SessionGetter.getSession();
        Transaction tx = session.beginTransaction();
        List list = session.createCriteria(FranchiseEntity.class).list();
        tx.commit();
        return (ArrayList<FranchiseEntity>)list;
    }

    private ArrayList<CosplayEntity> receiveCosplaysList(){
        Session session = SessionGetter.getSession();
        Transaction tx = session.beginTransaction();
        List list = session.createCriteria(CosplayEntity.class).list();
        tx.commit();
        return (ArrayList<CosplayEntity>)list;
    }
}
