package cna;

import cda.CosplayDatabaseAPI;
import cna.interfaces.FranchiseInfo;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by jereczem on 08.12.15.
 */
public class GetCharacterNamesFromFranchiseTester extends Tester{

    /**
     *  Scenariusz gdy wszystko gra
     */
    @Test
    public void testScenario1() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick = "Shafear";
        final String pokemon1 = "Pikachu";
        final String pokemon2 = "Charizard";
        final String pokemon3 = "Pichu";

        FranchiseInfo franchiseInfo = EasyMock.createMock(FranchiseInfo.class);
        EasyMock.expect(franchiseInfo.getFranchiseName()).andReturn(franchiseName);
        EasyMock.replay(franchiseInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick, 21);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon1, franchiseName, userNick);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon2, franchiseName, userNick);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon3, franchiseName, userNick);

        ArrayList<String> characterNames = CosplayNamesAPI.getCharacterNamesFrom(franchiseInfo);
        Assert.assertEquals(3, characterNames.size());
        Assert.assertTrue(characterNames.contains(pokemon1));
        Assert.assertTrue(characterNames.contains(pokemon2));
        Assert.assertTrue(characterNames.contains(pokemon3));
    }

    /**
     *  Scenariusz gdy Charizard się powtarza
     */
    @Test
    public void testScenario2() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick = "Shafear";
        final String pokemon1 = "Pikachu";
        final String pokemon2 = "Charizard";
        final String pokemon3 = "Pichu";

        FranchiseInfo franchiseInfo = EasyMock.createMock(FranchiseInfo.class);
        EasyMock.expect(franchiseInfo.getFranchiseName()).andReturn(franchiseName);
        EasyMock.replay(franchiseInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick, 21);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon1, franchiseName, userNick);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon2, franchiseName, userNick);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon2, franchiseName, userNick); //powtorka
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon3, franchiseName, userNick);

        ArrayList<String> characterNames = CosplayNamesAPI.getCharacterNamesFrom(franchiseInfo);
        Assert.assertEquals(characterNames.size(), 3); //powinny być 3!
        Assert.assertTrue(characterNames.contains(pokemon1));
        Assert.assertTrue(characterNames.contains(pokemon2));
        Assert.assertTrue(characterNames.contains(pokemon3));
    }

    /**
     *  Scenariusz gdy pusto
     */
    @Test
    public void testScenario3() throws Exception {
        final String franchiseName = "Pokemon";

        FranchiseInfo franchiseInfo = EasyMock.createMock(FranchiseInfo.class);
        EasyMock.expect(franchiseInfo.getFranchiseName()).andReturn(franchiseName);
        EasyMock.replay(franchiseInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");

        ArrayList<String> characterNames = CosplayNamesAPI.getCharacterNamesFrom(franchiseInfo);
        Assert.assertEquals(characterNames.size(), 0); //pusto!
    }
}
