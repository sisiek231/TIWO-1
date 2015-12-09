package cna;

import cda.CosplayDatabaseAPI;
import cna.interfaces.FranchiseInfo;
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
public class GetUserNamesCosplayedForSomeoneFromFranchiseTester extends Tester{
    /**
     *  Scenariusz gdy wszystko gra
     */
    @Test
    public void testScenario1() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick1 = "Shafear";
        final String userNick2 = "ThreeXe";
        final String pokemon = "Pikachu";

        FranchiseInfo franchiseInfo = EasyMock.createMock(FranchiseInfo.class);
        EasyMock.expect(franchiseInfo.getFranchiseName()).andReturn(franchiseName);
        replay(franchiseInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick1, 21);
        CosplayDatabaseAPI.addUser(userNick2, 21);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon, franchiseName, userNick1);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon, franchiseName, userNick2);


        ArrayList<String> userNames = CosplayNamesAPI.getUserNamesCosplayedForSomeoneFrom(franchiseInfo);
        Assert.assertEquals(2, userNames.size());
        Assert.assertTrue(userNames.contains(userNick1));
        Assert.assertTrue(userNames.contains(userNick1));
    }

    /**
     *  Scenariusz gdy Shafear się powtarza
     */
    @Test
    public void testScenario2() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick1 = "Shafear";
        final String userNick2 = "ThreeXe";
        final String pokemon = "Pikachu";

        FranchiseInfo franchiseInfo = EasyMock.createMock(FranchiseInfo.class);
        EasyMock.expect(franchiseInfo.getFranchiseName()).andReturn(franchiseName);
        replay(franchiseInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick1, 21);
        CosplayDatabaseAPI.addUser(userNick2, 21);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon, franchiseName, userNick1);
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon, franchiseName, userNick1);  //Shafear przebierał się 2 razy za pikachu, ale to nie znaczy ze ma byc dwa razy wymieniony w liscie!
        CosplayDatabaseAPI.addCosplay(new Timestamp(new Date().getTime()), true, pokemon, franchiseName, userNick2);


        ArrayList<String> userNames = CosplayNamesAPI.getUserNamesCosplayedForSomeoneFrom(franchiseInfo);
        Assert.assertEquals(userNames.size(), 2); //Shafear nie moze sie dublowac, proste!
        Assert.assertTrue(userNames.contains(userNick1));
        Assert.assertTrue(userNames.contains(userNick1));
    }

    /**
     *  Scenariusz gdy pusto
     */
    @Test
    public void testScenario3() throws Exception {
        final String franchiseName = "Pokemon";
        final String userNick1 = "Shafear";
        final String userNick2 = "ThreeXe";

        FranchiseInfo franchiseInfo = EasyMock.createMock(FranchiseInfo.class);
        EasyMock.expect(franchiseInfo.getFranchiseName()).andReturn(franchiseName);
        replay(franchiseInfo);

        CosplayDatabaseAPI.addFranchise(franchiseName, "Comedy");
        CosplayDatabaseAPI.addUser(userNick1, 21);
        CosplayDatabaseAPI.addUser(userNick2, 21);


        ArrayList<String> userNames = CosplayNamesAPI.getUserNamesCosplayedForSomeoneFrom(franchiseInfo);
        Assert.assertEquals(userNames.size(), 0); //pusto
    }
}
