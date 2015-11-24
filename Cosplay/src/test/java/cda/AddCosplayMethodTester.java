package cda;

import cosplay.CosplayEntity;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jereczem on 22.11.15.
 */
public class AddCosplayMethodTester extends Tester {
    /*
     * Każda klasa testująca powinna rozwijać klasę abstrakcyjną Tester,
     * dzięku czemu przed testowaniem każdej metody CAŁA baza danych jest usuwana, dlatego możemy przewidzieć dokładnie
     * jak powinna ona wyglądać po przeprowadzeniu operacji, które testujemy.
     */

    @Test
    public void correctData() throws Exception{
        //addCosplay(Timestamp date, Boolean isFavourite, String characterName, String franchiseName, String userNick)
        //Używając testowanej metody dodaję nowy cosplay
        String nick = "TestUser";
        int age = 69;
        CosplayDatabaseAPI.addUser(nick, age);

        String name = "TestFranchise";
        String genre = "Mahou Shoujo";
        CosplayDatabaseAPI.addFranchise(name, genre);

        Date d = new Date();

        Timestamp date = new Timestamp(d.getTime());
        Boolean isFavourite = true;
        String characterName = "TestCharacter";
        String franchiseName = "TestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);

        //Pobieram dane o cosplayach (klasa CosplayDatabaseData nie wymaga testowania, używamy jej do porównywania wyników)
        CosplayDatabaseData cDD = new CosplayDatabaseData();
        ArrayList<CosplayEntity> cosplay = cDD.getCosplayList();

        // Wiem, że baza danych była pusta na początku, więc po dodaniu powinien być tylko jeden użytkownik
        // Sprawdzam, czy ten użytkownik zgadza się z tym, którego przed chwilą dodałem
        Assert.assertEquals(cosplay.size(), 1);
        Assert.assertEquals(date, cosplay.get(0).getDate());
        Assert.assertEquals(isFavourite, cosplay.get(0).isFavourite());
        Assert.assertEquals(characterName, cosplay.get(0).getCharacterName());
        Assert.assertEquals(franchiseName, cosplay.get(0).getFranchiseByFranchiseId().getName());
        Assert.assertEquals(userNick, cosplay.get(0).getUsersByUsersId().getNick());
    }
}
