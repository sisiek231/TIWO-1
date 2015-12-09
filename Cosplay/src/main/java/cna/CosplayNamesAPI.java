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
        String franchiseName = cInfo.getFranchiseName(); //to musi być użyte aby odszukac
        String characterName = cInfo.getCharacterName(); //to musi być użyte aby odszukac

        /* Coś takiego powinno zwrócić aby przejść 2 pierwsze scenariusze oraz czwarty, (trzeci to po prostu pusta lista)
        ArrayList<String> names = new ArrayList<String>();
        names.add("ThreeXe");
        names.add("Shafear");
        return names;
        */

        throw new NotImplementedException();
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

        /* Coś takiego powinno zwrócić aby przejść 2 pierwsze scenariusze, (trzeci to po prostu pusta lista)
        ArrayList<String> names = new ArrayList<String>();
        names.add("Charizard");
        names.add("Pikachu");
        names.add("Pichu");
        return names;
        */

        throw new NotImplementedException();
    }
}
