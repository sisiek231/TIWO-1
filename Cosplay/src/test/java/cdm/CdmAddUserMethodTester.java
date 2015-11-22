package cdm;

import cosplay.UsersEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by jereczem on 22.11.15.
 */
public class CdmAddUserMethodTester extends CdmTester{
    /*
     * Każda klasa testująca powinna rozwijać klasę abstrakcyjną CdmTester,
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
}
