package cna;

import cda.CosplayDatabaseAPI;
import cna.interfaces.CharacterInfo;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static org.easymock.EasyMock.replay;

/**
 * Created by jereczem on 08.12.15.
 */
public class GetUserNamesCosplayedForCharacterTester extends Tester{
    /**
     *  Scenariusz gdy wszystko gra
     */
    @Test
    public void testScenario1() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick1 = "Shafear";
        final String userNick2 = "ThreeXe";
        final String characterName = "Pikachu";

        CharacterInfo characterInfo = EasyMock.createMock(CharacterInfo.class);
        EasyMock.expect(characterInfo.getCharacterName()).andReturn(characterName);
        EasyMock.expect(characterInfo.getFranchiseName()).andReturn(franchiseName);
        replay(characterInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick1, 21);
        CosplayDatabaseAPI.addUser(userNick2, 21);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseName, userNick1);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseName, userNick2);

        ArrayList<String> userNames = CosplayNamesAPI.getUserNamesCosplayedFor(characterInfo);
        Assert.assertEquals(2, userNames.size());
        Assert.assertTrue(userNames.contains(userNick1));
        Assert.assertTrue(userNames.contains(userNick2));
    }

    /**
     *  Scenariusz gdy Shafear się powtarza
     */
    @Test
    public void testScenario2() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick1 = "Shafear";
        final String userNick2 = "ThreeXe";
        final String characterName = "Pikachu";

        CharacterInfo characterInfo = EasyMock.createMock(CharacterInfo.class);
        EasyMock.expect(characterInfo.getCharacterName()).andReturn(characterName);
        EasyMock.expect(characterInfo.getFranchiseName()).andReturn(franchiseName);
        replay(characterInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick1, 21);
        CosplayDatabaseAPI.addUser(userNick2, 21);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseName, userNick1);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseName, userNick1); //tutaj się powtarza :)
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseName, userNick2);

        EasyMock.replay(characterInfo);
        ArrayList<String> userNames = CosplayNamesAPI.getUserNamesCosplayedFor(characterInfo);
        Assert.assertEquals(2, userNames.size());
        Assert.assertTrue(userNames.contains(userNick1));
        Assert.assertTrue(userNames.contains(userNick2));
    }

    /**
     *  Scenariusz gdy pusto
     */
    @Test
    public void testScenario3() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick1 = "Shafear";
        final String userNick2 = "ThreeXe";
        final String characterName = "Pikachu";

        CharacterInfo characterInfo = EasyMock.createMock(CharacterInfo.class);
        EasyMock.expect(characterInfo.getCharacterName()).andReturn(characterName);
        EasyMock.expect(characterInfo.getFranchiseName()).andReturn(franchiseName);
        replay(characterInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick1, 21);
        CosplayDatabaseAPI.addUser(userNick2, 21);

        EasyMock.replay(characterInfo);
        ArrayList<String> userNames = CosplayNamesAPI.getUserNamesCosplayedFor(characterInfo);
        Assert.assertEquals(0, userNames.size()); //pusto!
    }

    /**
     *  Scenariusz gdy Pikachu z innej franchyzy się pojawia (go nie liczymy)
     */
    @Test
    public void testScenario4() throws Exception {
        final String franchiseName = "Pokemon";
        final String franchiseNameFake = "Digimon"; //fake
        final String userNick1 = "Shafear";
        final String userNick2 = "ThreeXe";
        final String userNickFake = "ShafearFake"; //fake, nie powinien byc zwrocony
        final String characterName = "Pikachu";

        CharacterInfo characterInfo = EasyMock.createMock(CharacterInfo.class);
        EasyMock.expect(characterInfo.getCharacterName()).andReturn(characterName);
        EasyMock.expect(characterInfo.getFranchiseName()).andReturn(franchiseName);
        replay(characterInfo);


        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addFranchise(franchiseNameFake, "ComedyFake"); //Dodajemy Digimony
        CosplayDatabaseAPI.addUser(userNick1, 21);
        CosplayDatabaseAPI.addUser(userNick2, 21);
        CosplayDatabaseAPI.addUser(userNickFake, 21);

        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseName, userNick1);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseNameFake, userNick1); //ups, tutaj doszedl pikaczu, ale z Digimonow!
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseNameFake, userNickFake); //ups, tutaj doszedl pikaczu, ale z Digimonow i do tego do FakeNicku!
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, characterName, franchiseName, userNick2);

        EasyMock.replay(characterInfo);
        ArrayList<String> userNames = CosplayNamesAPI.getUserNamesCosplayedFor(characterInfo);
        Assert.assertEquals(2, userNames.size());
        Assert.assertTrue(userNames.contains(userNick1));
        Assert.assertTrue(userNames.contains(userNick2));
    }
}
