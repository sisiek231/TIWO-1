package cda;

import cosplay.UsersEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by jereczem on 22.11.15.
 * Edited by jadczakk on --.11.15.
 */

public class ChangeUserAgeMethodTester extends Tester {
    /*
     * Każda klasa testująca powinna rozwijać klasę abstrakcyjną Tester,
     * dzięku czemu przed testowaniem każdej metody CAŁA baza danych jest usuwana, dlatego możemy przewidzieć dokładnie
     * jak powinna ona wyglądać po przeprowadzeniu operacji, które testujemy.
     */

    @Test
    public void correctData() throws Exception{
        //dodaję nowego użytkownika
        String nick = "New User";
        int age = 18;
        int newAge = 21;
        CosplayDatabaseAPI.addUser(nick, age);

        //Pobieram dane o użytkownikach (klasa CosplayDatabaseData nie wymaga testowania, używamy jej do porównywania wyników)
        CosplayDatabaseData cDD = new CosplayDatabaseData();
        ArrayList<UsersEntity> users = cDD.getUsersList();

        // Wiem, że baza danych była pusta na początku, więc po dodaniu powinien być tylko jeden użytkownik
        // Sprawdzam, czy wiek użytkownika zgadza się z tym, jaki wpisałem w age
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(age, users.get(0).getAge());

        // Sprawdzam, czy wiek użytkownika zgadza się z tym, jaki zmieniłem w newAge
        CosplayDatabaseAPI.changeUserAge(nick, newAge);
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(age, users.get(0).getAge());
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class) //sprawdzam czy zostanie wyrzucony wyjątek
    public void wrongDataEmptyStringException() throws Exception{
        String nick = "";
        int age = 7;
        CosplayDatabaseAPI.changeUserAge(nick, age);// dodaję z pustym nickiem nowego
    }

    @Test(expected = CosplayDatabaseAPI.AgeLowerThenOneException.class) //sprawdzam czy wyrzuci wyjątek zle wpisanego wieku przy 0
    public void wrongDataAgeEqualsZeroException() throws Exception{
        String nick = "Now Type";
        int age = 5;
        CosplayDatabaseAPI.addUser(nick,age);
        int newAge= 0; // wpisuje nowy zerowy wiek
        CosplayDatabaseAPI.changeUserAge(nick,newAge);
    }

    @Test(expected = CosplayDatabaseAPI.AgeLowerThenOneException.class) //sprawdzam czy wyrzuci wyjątek zle wpisanego wieku przy 0
    public void wrongDataAgeLowerThenOneException() throws Exception{
        String nick = "Now Type";
        int age = 5;
        CosplayDatabaseAPI.addUser(nick,age);
        int newAge= -5; // wpisuje nowy ujemny wiek
        CosplayDatabaseAPI.changeUserAge(nick,newAge);
    }
    @Test(expected = CosplayDatabaseAPI.CantFindTheUserException.class) //sprawdzam czy wyrzuci wyjątek zle wpisanego wieku przy 0
    public void wrongDataCantFindTheUserException() throws Exception{
        CosplayDatabaseAPI.changeUserAge("Nie Mamnie", 18);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class)
    public void wrongDataStringLongerThan45Exception() throws Exception{
        CosplayDatabaseAPI.addUser("New User", 18);
        String nick = "NewuserNewuserNewuserNewuserNewuserNewuser Newuser"; //wpisuje string majacy wiecej niz 45 znakow
        int newAge = 21;
        CosplayDatabaseAPI.changeUserAge(nick, newAge);

    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Nick() throws Exception{ //jako string daje null
        String nick = null;
        int age = 16;
        CosplayDatabaseAPI.changeUserAge(nick,age);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Age() throws Exception{ //jako age daje null
        String nick = "Mama dzwoni";
        CosplayDatabaseAPI.changeUserAge(nick,null);
    }
}
