package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class UserData {
    private static UserData instance = new UserData();
    private static String filename = "UserProfileData.txt";
    private ObservableList<UserProfile> userProfiles;

    public static UserData getInstance(){
        return instance;
    }

    public ObservableList<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void addUserProfile(UserProfile profile){
        userProfiles.add(profile);
    }

    public void loadUserProfiles() throws IOException{
        userProfiles = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);
        String input;
        try{
            while ((input = br.readLine()) != null){

                String[] profileData = input.split("\t");


                //String[] profileData = input.split("\t");
                String name;
                name = profileData[0];
                //String name = profileData[0];
                String email = profileData[1];
                String keystrokeString;
                keystrokeString = profileData[2];

                UserProfile userProfile = new UserProfile(name,email,keystrokeString);
                userProfiles.add(userProfile);
            }
        } finally {
            if (br != null){
                br.close();
            }
        }
    }

    public void storeUserProfiles() throws IOException{
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<UserProfile> iter = userProfiles.iterator();
            while (iter.hasNext()){
                UserProfile profile = iter.next();
                bw.write(String.format("%s\t%s\t%s",profile.getName(),profile.getEmail(),profile.getKeystrokeString()));
                bw.newLine();
            }
        }finally {
            if (bw != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"User registered successfully!");
                alert.showAndWait();
                bw.close();
            }
        }
    }

    public String getUserKeystrokeString(String email){
        String userKeystroke = "";
        try {
            instance.loadUserProfiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<userProfiles.size();i++){
            if (email.equals (userProfiles.get(i).getEmail().trim())){
                userKeystroke = userProfiles.get(i).getKeystrokeString().trim();
            }
        }
        return userKeystroke;
    }

    public String getUserName(String email){
        String userName = "";
        try {
            instance.loadUserProfiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<userProfiles.size();i++){
            if (email.equals (userProfiles.get(i).getEmail().trim())){
                userName = userProfiles.get(i).getName().trim();
            }
        }
        return userName;
    }
    public Boolean checkUseremail(String email){
        Boolean availability = false;
        try {
            instance.loadUserProfiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<userProfiles.size();i++){
            if (email.equals (userProfiles.get(i).getEmail().trim())){
                availability = true;
            }
        }
        return availability;
    }
}
