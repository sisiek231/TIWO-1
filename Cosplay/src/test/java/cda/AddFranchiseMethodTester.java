package cda;

import cosplay.FranchiseEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by jereczem on 22.11.15.
 */
public class AddFranchiseMethodTester extends Tester {
    /*
     * Każda klasa testująca powinna rozwijać klasę abstrakcyjną Tester,
     * dzięku czemu przed testowaniem każdej metody CAŁA baza danych jest usuwana, dlatego możemy przewidzieć dokładnie
     * jak powinna ona wyglądać po przeprowadzeniu operacji, które testujemy.
     */

    @Test
    public void correctData() throws Exception{
        //addFranchise(String name, String genre)
        //Używając testowanej metody dodaję nowy franchise
        String name = "TestFranchise";
        String genre = "TestGenre";

        CosplayDatabaseAPI.addFranchise(name, genre);

        //Pobieram dane o franchise'ach (klasa CosplayDatabaseData nie wymaga testowania, używamy jej do porównywania wyników)
        CosplayDatabaseData cDD = new CosplayDatabaseData();
        ArrayList<FranchiseEntity> franchise = cDD.getFranchisesList();

        // Wiem, że baza danych była pusta na początku, więc po dodaniu powinien być tylko jeden franchise
        // Sprawdzam, czy ten franchise zgadza się z tym, którego przed chwilą dodałem
        Assert.assertEquals(franchise.size(), 1);
        Assert.assertEquals(name, franchise.get(0).getName());
        Assert.assertEquals(genre, franchise.get(0).getGenre());
    }

    //Poniżej wszystkie możliwe wyjątki
    @Test(expected = CosplayDatabaseAPI.DuplicateEntryException.class)
    public void wrongDataDuplicateEntryException() throws Exception{
        String name = "TestFranchise";
        String genre = "TestGenre";

        CosplayDatabaseAPI.addFranchise(name, genre);
        CosplayDatabaseAPI.addFranchise(name, genre);
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class)
    public void wrongDataEmptyStringException_Name() throws Exception{
        String name = "";
        String genre = "TestGenre";

        CosplayDatabaseAPI.addFranchise(name, genre);
    }

    @Test(expected = CosplayDatabaseAPI.EmptyStringException.class)
    public void wrongDataEmptyStringException_Genre() throws Exception{
        String name = "TestFranchise";
        String genre = "";

        CosplayDatabaseAPI.addFranchise(name, genre);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class)
    public void wrongDataStringLongerThan45Exception_Name() throws Exception{
        String name = "TestFranchiseTestFranchiseTestFranchiseTestFranchise";
        String genre = "TestGenre";

        CosplayDatabaseAPI.addFranchise(name, genre);
    }

    @Test(expected = CosplayDatabaseAPI.StringLongerThan45Exception.class)
    public void wrongDataStringLongerThan45Exception_Genre() throws Exception{
        String name = "TestFranchise";
        String genre = "TestGenreTestGenreTestGenreTestGenreTestGenreTestGenre";

        CosplayDatabaseAPI.addFranchise(name, genre);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Name() throws Exception{
        String name = null;
        String genre = "TestGenre";

        CosplayDatabaseAPI.addFranchise(name, genre);
    }

    @Test(expected = NullPointerException.class)
    public void wrongDataNullPointerException_Genre() throws Exception{
        String name = "TestFranchise";
        String genre = null;

        CosplayDatabaseAPI.addFranchise(name, genre);
    }
}
