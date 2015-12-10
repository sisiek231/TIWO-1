package cna;

import cda.CosplayDatabaseData;
import cna.interfaces.CharacterInfo;
import cna.interfaces.FranchiseInfo;
import cosplay.CosplayEntity;
import cosplay.FranchiseEntity;
import cosplay.UsersEntity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Do napisania funkcjonalności powinny wystarczyć metody z CosplayDatabaseData, po prostu trzeba przeszukac to co dostarczaja i zwrocic wynik.
 *
 * Created by jereczem on 08.12.15.
 */
public class CosplayNamesAPI {
    /**
     * Zwróć mi listę imion użytkoników którzy kiedykolwiek się przebierali za postać o danym imieniu (a co za tmy idzie do danej Franczyzy)
     * Uwaga! Imiona nie mogą się powtarzać.
     * Uwaga! Pamiętajcie, że samo imię postaci do za mało, musi się zgadzać jego franczyza!
     */
    public static ArrayList<String> getUserNamesCosplayedFor(CharacterInfo cInfo){

        String franchiseName = cInfo.getFranchiseName();
        String characterName = cInfo.getCharacterName();

        boolean duplicated;
        ArrayList<String> names = new ArrayList<String>();

        CosplayDatabaseData c = new CosplayDatabaseData();
        ArrayList<CosplayEntity> chr = c.getCosplayList();

        //sprawdzamy cosplayów pod względem nazw postaci
        for ( CosplayEntity cospaly : chr)
        {
            //jak jest taka jak szukamy...
            if(cospaly.getCharacterName().equals(characterName)){

                Collection<FranchiseEntity> fr = cospaly.getFranchiseByFranchiseId();
            //to sprawdzamy jej frenchisy
                for (FranchiseEntity frenchise : fr)
                {
                    //jak są zgodne to..
                    if(frenchise.getName().equals(franchiseName)){
                        duplicated = false;
                        UsersEntity users = cosplay.getUsersByUsersId();
                        String uname = users.getNick();
                        //sprawdzamy czy się nie zduplikują
                        for (String nu : names)
                        {
                            if(nu.equals(uname)){
                                //jest nie dodajemy
                                duplicated = true;
                                break;
                            }
                            if(duplicated == false){
                                //nie bylo -> dodaj
                                names.add(uname);
                            }
                        }
                        break;
                    }

                }
                break;
            }

        }

        return names;
    }

    /**
     * Zwróć mi imiona użytkowników którzy kiedykolwiek się przebierali za coś z danej franczyzy
     * Uwaga! imiona nie mogą się powtarzać.
     */
    public static ArrayList<String> getUserNamesCosplayedForSomeoneFrom(FranchiseInfo fInfo){
        String franchiseName = fInfo.getFranchiseName(); //to musi być użyte, aby odszukać
        CosplayDatabaseData cdd = new CosplayDatabaseData();
        ArrayList<FranchiseEntity> franchises = cdd.getFranchisesList();
        ArrayList<String> users = new ArrayList<String>();
        boolean exists;

        //przeszukiwanie listy franchise'ów po kolei szukając z nazwą z argumentu
        for(FranchiseEntity franchise : franchises){
            //jeśli znaleziony
            if(franchise.getName().equals(franchiseName)){
                //to weź jego listę cosplay'ów
                Collection<CosplayEntity> cosplays = franchise.getCosplaysByIdFranchise();
                //dla każdego cosplay'u z listy szukamy user'ów, którzy się przebierali
                for(CosplayEntity cosplay : cosplays){
                    exists = false;
                    UsersEntity user = cosplay.getUsersByUsersId();
                    String nick = user.getNick();
                    //trzeba sprawdzić czy user się nie powtarza na liście
                    for(String userNick : users){
                        if(userNick.equals(nick)){
                            exists = true;
                            //jeśli już jest taki to break; nie opłaca się dalej szukać
                            break;
                        }
                    }
                    if(exists == false){
                        //jeśli nie było takiego użytkownika już na liście to go dodajemy
                        users.add(nick);
                    }
                }
                //break pętli for szukającego odpowiedniego franchise; znaleźliśmy ten co chcieliśmy więc nie ma po co szukać dalej
                break;
            }
        }

        //zwróć listę user'ów
        return users;
    }

    /**
     * Zwróć mi listę imion postaci które należą do franczyzy jakiejśtam.
     * Uwaga! Imiona nie mogą się powtarzać.
     */
    public static ArrayList<String> getCharacterNamesFrom(FranchiseInfo fInfo){
        String franchiseName = fInfo.getFranchiseName(); //to musi być użyte aby odszukac
        CosplayDatabaseData cdd = new CosplayDatabaseData();
        ArrayList<FranchiseEntity> franchisez = cdd.getFranchisesList();
        ArrayList<String> names = new ArrayList<String>();
        boolean wolne;

        for(FranchiseEntity franchise : franchisez){
            if(franchise.getName().equals(franchiseName)){
                Collection<CosplayEntity> charname = franchise.getCosplaysByIdFranchise();  //franchise.getCosplaysByIdFranchise();
                for(CosplayEntity cosplay : charname){
                    wolne = false;
                    String characterName = cosplay.getCharacterName();
                    for(String charNames : names){
                        if(charNames.equals(characterName)){
                            wolne = true;
                            break;
                        }
                    }
                    if(wolne == false){
                        names.add(characterName);
                    }
            }
            break;
        }
        }
        /* Coś takiego powinno zwrócić aby przejść 2 pierwsze scenariusze, (trzeci to po prostu pusta lista)
        ArrayList<String> names = new ArrayList<String>();
        names.add("Charizard");
        names.add("Pikachu");
        names.add("Pichu");
        return names;
        */
        return names;
      //  throw new NotImplementedException();
    }

}
