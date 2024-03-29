
package library.assistant.settings;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.assistant.alert.AlertMaker;

public class Preferences {
    public static final String CONFIG_FILE="config.txt";
int nDaysWithoutFine;
float finePerDay;
String username;
String password;
public Preferences(){
    nDaysWithoutFine=14;
    finePerDay=2;
    username= "vishnu";
    password="vishnu";
}

    public int getnDaysWithoutFine() {
        return nDaysWithoutFine;
    }

    public void setnDaysWithoutFine(int nDaysWithoutFine) {
        this.nDaysWithoutFine = nDaysWithoutFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static void initConfiguration(){
        Writer writer=null;
        try {
        Preferences preferences= new Preferences();
        Gson gson = new Gson();
        
        
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences,writer);
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
    }
    public static  Preferences getPreferences(){
    Gson gson= new Gson();
    Preferences preferences=new Preferences();
        try {
            preferences= gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException ex) {
            initConfiguration();
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preferences;
    
    }
    public static void writePreferencesToFile(Preferences preferences){
        Writer writer=null;
        try {
       
        Gson gson = new Gson();
        writer = new FileWriter(CONFIG_FILE);
        gson.toJson(preferences,writer);
          AlertMaker.showSimpleAlert("Success", "Settings Updated");
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            AlertMaker.showSimpleAlert("Failed", "Can't save configuration");
        }finally{
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    }
}
