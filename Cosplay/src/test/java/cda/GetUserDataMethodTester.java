package cda;

import cosplay.UsersEntity;
import org.junit.Test;

public class GetUserDataMethodTester extends Tester {
    /*
getUserData(String nick) throws EmptyStringException, CantFindTheUserException, StringLongerThan45Exception
     */

    @Test
    public void correctData() throws Exception{

        String nick = "usertest";
        int age = 6;
        String name = "testf";
        String genre = "testg";
        Date da = new Date();
        Timestamp data = new Timestamp(da.getTime());
        Boolean isFavourite = true;
        String characterName = "testch";
        CosplayDatabaseAPI.addUser(nick, age);
        CosplayDatabaseAPI.addFranchise(name, genre);
        CosplayDatabaseAPI.addCosplay(data, isFavourite, characterName, genre, nick);

        UsersEntity user = CosplayDatabaseAPI.getUserData(nick);

        Assert.assertEquals(user.getAge(), age );
        Assert.assertEquals(user.getNick(), nick);
        Assert.assertEquals(user.getIdUsers(), 0);
        //user.getCosplaysByIdUsers()

    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class) //wyrzucony wyjątek?
    public void wrongDataEmptyStringException() throws Exception{

        String nick ="";
        CosplayDatabaseAPI.getUserData(nick);
    }

    @Test(expected = CosplayDatabaseAPI.CantFindTheUserException.class) //wyrzucony wyjatek?
    public void wrongDataCantFindTheUserException() throws Exception {

        //pusta baza wiec nie bedzie tego uzytkownika
        String nick ="hakunamatata";
        CosplayDatabaseAPI.getUserData(nick);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class) // wyrzucony wyjątek?
    public void wrongDataStringLongerThan45Exception() throws Exception{

        String nick ="testusereetestusereetestusereetestusereetestusereetestuseree";
        CosplayDatabaseAPI.getUserData(nick);
    }


}
