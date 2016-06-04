package cda;

import cosplay.UsersEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by jereczem on 22.11.15.
 * Edited by jadczakk on --.11.15.
 */
public class AddUserMethodTester extends Tester {
    /*
     * Każda klasa testująca powinna rozwijać klasę abstrakcyjną Tester,
     * dzięku czemu przed testowaniem każdej metody CAŁA baza danych jest usuwana, dlatego możemy przewidzieć dokładnie
     * jak powinna ona wyglądać po przeprowadzeniu operacji, które testujemy.
     */

    @Test
    public void correctData() throws Exception{
        //Używając testowanej metody dodaję nowego użytkownika
        String nick = "New User";
        int age = 18;
        CosplayDatabaseAPI.addUser(nick, age);

        //Pobieram dane o użytkownikach (klasa CosplayDatabaseData nie wymaga testowania, używamy jej do porównywania wyników)
        CosplayDatabaseData cDD = new CosplayDatabaseData();
        ArrayList<UsersEntity> users = cDD.getUsersList();

        // Wiem, że baza danych była pusta na początku, więc po dodaniu powinien być tylko jeden użytkownik
        // Sprawdzam, czy ten użytkownik zgadza się z tym, którego przed chwilą dodałem
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(nick, users.get(0).getNick());
        Assert.assertEquals(age, users.get(0).getAge());
    }


    @Test(expected = CosplayDatabaseAPI.DuplicateEntryException.class) //sprawdzam czy zostanie wyrzucony wyjątek
    public void wrongDataDuplicateEntryException() throws Exception{
        String nick = "Duplicate User";
        int age = 66;
        CosplayDatabaseAPI.addUser(nick, age);
        CosplayDatabaseAPI.addUser(nick, age); // ups, drugi raz chcę dać to samo!
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class) //sprawdzam czy zostanie wyrzucony wyjątek
    public void wrongDataEmptyStringException() throws Exception{
        String nick = "";
        int age = 7;
        CosplayDatabaseAPI.addUser(nick, age);// dodaję z pustym nickiem nowego
    }

    @Test(expected = CosplayDatabaseAPI.AgeLowerThenOneException.class) //sprawdzam czy wyrzuci wyjątek zle wpisanego wieku przy 0
    public void wrongDataAgeLowerThenOneException() throws Exception{
        String nick = "Nowy Typek";
        int age = 0; // wpisuje wiek 0
        CosplayDatabaseAPI.addUser(nick,age);
    }

    @Test(expected = CosplayDatabaseAPI.AgeLowerThenOneException.class) //sprawdzam czy wyrzuci wyjątek zle wpisanego wieku przy 0
    public void wrongDataAgeLowerThenZeroException() throws Exception{
        String nick = "Now Type";
        int age = -5; // wpisuje ujemny wiek
        CosplayDatabaseAPI.addUser(nick,age);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class)
    public void wrongDataStringLongerThan45Exception() throws Exception{
        String nick = "NewuserNewuserNewuserNewuserNewuserNewuser Newuser"; //wpisuje string majacy wiecej niz 45 znakow
        int age = 21;
        CosplayDatabaseAPI.addUser(nick,age);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Nick() throws Exception{ //jako string daje null
        String nick = null;
        int age = 16;
        CosplayDatabaseAPI.addUser(nick,age);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Age() throws Exception{ //jako age daje null
        String nick = "Mama dzwoni";
        CosplayDatabaseAPI.addUser(nick,null);
    }
}