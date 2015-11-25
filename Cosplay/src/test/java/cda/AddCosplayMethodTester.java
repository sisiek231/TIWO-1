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

        // Wiem, że baza danych była pusta na początku, więc po dodaniu powinien być tylko jeden cosplay
        // Sprawdzam, czy ten cosplay zgadza się z tym, którego przed chwilą dodałem
        Assert.assertEquals(cosplay.size(), 1);
        Assert.assertEquals(date, cosplay.get(0).getDate());
        Assert.assertEquals(isFavourite, cosplay.get(0).isFavourite());
        Assert.assertEquals(characterName, cosplay.get(0).getCharacterName());
        Assert.assertEquals(franchiseName, cosplay.get(0).getFranchiseByFranchiseId().getName());
        Assert.assertEquals(userNick, cosplay.get(0).getUsersByUsersId().getNick());
    }

    //Poniżej wszystkie możliwe wyjątki
    @Test(expected = CosplayDatabaseAPI.CantFindTheUserException.class)
    public void wrongDataCantFindTheUserException_NoUser() throws Exception{
        //String nick = "TestUser";
        //int age = 69;
        //CosplayDatabaseAPI.addUser(nick, age);

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
    }

    @Test(expected = CosplayDatabaseAPI.CantFindTheUserException.class)
    public void wrongDataCantFindTheUserException_WrongUserNick() throws Exception{
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
        String userNick = "TestUser99";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class)
    public void wrongDataEmptyStringException_CharacterName() throws Exception{
        String nick = "TestUser";
        int age = 69;
        CosplayDatabaseAPI.addUser(nick, age);

        String name = "TestFranchise";
        String genre = "Mahou Shoujo";
        CosplayDatabaseAPI.addFranchise(name, genre);

        Date d = new Date();

        Timestamp date = new Timestamp(d.getTime());
        Boolean isFavourite = true;
        String characterName = "";
        String franchiseName = "TestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class)
    public void wrongDataEmptyStringException_FranchiseName() throws Exception{
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
        String franchiseName = "";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class)
    public void wrongDataEmptyStringException_UserNick() throws Exception{
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
        String userNick = "";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.CantFindFranchiseException.class)
    public void wrongDataCantFindFranchiseException_NoFranchise() throws Exception{
        String nick = "TestUser";
        int age = 69;
        CosplayDatabaseAPI.addUser(nick, age);

        //String name = "TestFranchise";
        //String genre = "Mahou Shoujo";
        //CosplayDatabaseAPI.addFranchise(name, genre);

        Date d = new Date();

        Timestamp date = new Timestamp(d.getTime());
        Boolean isFavourite = true;
        String characterName = "TestCharacter";
        String franchiseName = "TestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.CantFindFranchiseException.class)
    public void wrongDataCantFindFranchiseException_WrongFranchiseName() throws Exception{
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
        String franchiseName = "TestFranchise99";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class)
    public void wrongDataStringLongerThan45Exception_CharacterName() throws Exception{
        String nick = "TestUser";
        int age = 69;
        CosplayDatabaseAPI.addUser(nick, age);

        String name = "TestFranchise";
        String genre = "Mahou Shoujo";
        CosplayDatabaseAPI.addFranchise(name, genre);

        Date d = new Date();

        Timestamp date = new Timestamp(d.getTime());
        Boolean isFavourite = true;
        String characterName = "TestCharacterTestCharacterTestCharacterTestCharacter";
        String franchiseName = "TestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class)
    public void wrongDataStringLongerThan45Exception_FranchiseName() throws Exception{
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
        String franchiseName = "TestFranchiseTestFranchiseTestFranchiseTestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class)
    public void wrongDataStringLongerThan45Exception_UserNick() throws Exception{
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
        String userNick = "TestUserTestUserTestUserTestUserTestUserTestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Date() throws Exception{
        String nick = "TestUser";
        int age = 69;
        CosplayDatabaseAPI.addUser(nick, age);

        String name = "TestFranchise";
        String genre = "Mahou Shoujo";
        CosplayDatabaseAPI.addFranchise(name, genre);

        Date d = new Date();

        Timestamp date = null;
        Boolean isFavourite = true;
        String characterName = "TestCharacter";
        String franchiseName = "TestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Favourite() throws Exception{
        String nick = "TestUser";
        int age = 69;
        CosplayDatabaseAPI.addUser(nick, age);

        String name = "TestFranchise";
        String genre = "Mahou Shoujo";
        CosplayDatabaseAPI.addFranchise(name, genre);

        Date d = new Date();

        Timestamp date = new Timestamp(d.getTime());
        Boolean isFavourite = null;
        String characterName = "TestCharacter";
        String franchiseName = "TestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_CharacterName() throws Exception{
        String nick = "TestUser";
        int age = 69;
        CosplayDatabaseAPI.addUser(nick, age);

        String name = "TestFranchise";
        String genre = "Mahou Shoujo";
        CosplayDatabaseAPI.addFranchise(name, genre);

        Date d = new Date();

        Timestamp date = new Timestamp(d.getTime());
        Boolean isFavourite = true;
        String characterName = null;
        String franchiseName = "TestFranchise";
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_FranchiseName() throws Exception{
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
        String franchiseName = null;
        String userNick = "TestUser";

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_UserNick() throws Exception{
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
        String userNick = null;

        CosplayDatabaseAPI.addCosplay(date, isFavourite, characterName, franchiseName, userNick);
    }
}
