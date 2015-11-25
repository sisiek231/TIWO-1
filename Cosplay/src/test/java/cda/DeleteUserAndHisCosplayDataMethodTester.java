package cda;

import cosplay.CosplayEntity;
import cosplay.UsersEntity;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class DeleteUserAndHisCosplayDataMethodTester extends Tester {
    /*
    deleteUserAndHisCosplayData(String nick) throws CantFindTheUserException, StringLongerThan45Exception, EmptyStringException
     */

    @Test
    public void correctData() throws Exception{

        Date da= new Date();
        Timestamp data = new Timestamp(da.getTime());
        Boolean isFavourite = true;
        String nick ="testuser";
        //dodanie urzydkownika - nie wymaga testu
        CosplayDatabaseAPI.addUser("testuser", 6 );
        CosplayDatabaseAPI.addFranchise("testf", "testgen");
        CosplayDatabaseAPI.addCosplay(data, isFavourite, "testchara", "testf", "testuser");

        //usuniecie urzydkowinka przy urzyciu testowanej metody
        CosplayDatabaseAPI.deleteUserAndHisCosplayData(nick);

        //sprawdzenie czy funkcja wykonała zadanie
        CosplayDatabaseData cdd = new CosplayDatabaseData();
        ArrayList<CosplayEntity> cosplay = cdd.getCosplayList();
        ArrayList<UsersEntity> users = cdd.getUsersList();

        // baza i lista uzytkownikow były puste przed dodaniem uzydkownika wiec powinna byc taka i po uzyciu testowanej funkcji
        Assert.assertEquals(users.isEmpty(), true);
        Assert.assertEquals(cosplay.isEmpty(), true);

    }

    @Test(expected = CosplayDatabaseAPI.CantFindTheUserException.class) //wyrzucony wyjatek?
    public void wrongDataCantFindTheUserException() throws Exception {

    //pusta baza wiec nie bedzie tego uzytkownika
        String nick ="hakunamatata";
        CosplayDatabaseAPI.deleteUserAndHisCosplayData(nick);
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class) //wyrzucony wyjątek?
    public void wrongDataEmptyStringException() throws Exception{

        String nick ="";
        CosplayDatabaseAPI.deleteUserAndHisCosplayData(nick);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class) // wyrzucony wyjątek?
    public void wrongDataStringLongerThan45Exception() throws Exception{

        String nick ="testusereetestusereetestusereetestusereetestusereetestuseree";
        CosplayDatabaseAPI.deleteUserAndHisCosplayData(nick);

    }
}
