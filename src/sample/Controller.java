package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private JFXTextField textFieldName,textFieldEmail,textFieldKeyStroke,textEmailSignIn,textKeystrokeSignIn;
    @FXML
    private JFXButton buttonSignUp,buttonSignIn,buttonClose,buttonSignUpPane,buttonSignInPane;
    @FXML
    private Label labelKey,labelkeySignIn;
    @FXML
    private AnchorPane paneSignIn,paneSignUp;

    public long start,end,time;
    public String delimeter = "#";
    public ArrayList<Long> pressTimes = new ArrayList<Long>();

    @FXML
    public void handlePane(MouseEvent event){
        if (event.getSource()==buttonSignInPane){
            paneSignIn.toFront();
        } else if (event.getSource()==buttonSignUpPane){
            paneSignUp.toFront();
        }
    }

    public void handleClose(){
        System.exit(0);
    }

    public void onKeyPressed(KeyEvent keyEvent){
        System.out.println(keyEvent.getText());
        String value = String.valueOf(keyEvent.getText());
        if (value.matches("[a-zA-Z]+")){
            start = System.currentTimeMillis();
        }
    }
    @FXML
    public void onKeyReleased(KeyEvent keyEvent){
        String character = String.valueOf(keyEvent.getText());
        if ((keyEvent.getCode().equals(KeyCode.BACK_SPACE)) && (pressTimes.size()>=1)){
            this.pressTimes.remove(pressTimes.size()-1);
            for(long i: pressTimes){
                System.out.print(i+" ");
            }
        } else if (character.matches("[a-zA-Z]+")){
            end = System.currentTimeMillis();
            time = end - start;
            this.pressTimes.add(time);
            for(long i: pressTimes){
                System.out.print(i+" ");
            }
            System.out.println("Time pressed: "+time+ " milliseconds");
        }
        textKeystrokeSignIn.setCursor(Cursor.DISAPPEAR);
    }
    String keystrokeStringSignUp = "";
    @FXML
    public void processSignUpInfo(){


        if (!(textFieldKeyStroke.getText().trim().equals(labelKey.getText().trim()))){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Typed in text doesn't match the text displayed");
            pressTimes.clear();
            keystrokeStringSignUp = "";
            textFieldKeyStroke.clear();
            alert.showAndWait();


        }else {
            Boolean value = UserData.getInstance().checkUseremail(textFieldEmail.getText().trim());
            if (!value){
                String name = textFieldName.getText().trim();
                String email = textFieldEmail.getText().trim();

                for (Long time: this.pressTimes){
                    keystrokeStringSignUp += delimeter + Long.toString(time);
                }
                UserProfile newUser = new UserProfile(name,email,keystrokeStringSignUp);
                UserData.getInstance().addUserProfile(newUser);
                try {
                    UserData.getInstance().storeUserProfiles();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(newUser.getName()+"\t"+ newUser.getEmail()+"\t" + newUser.getKeystrokeString());
                keystrokeStringSignUp = "";
                pressTimes.clear();
                paneSignIn.toFront();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR,"User already exists!");
                alert.showAndWait();
                textFieldName.clear();
                textFieldEmail.clear();
                textFieldKeyStroke.clear();
                keystrokeStringSignUp = "";
                pressTimes.clear();


            }

        }



//        paneSignIn.toFront();
    }

    public void processSignInInfo(){
        //System.out.println("processSignInInfo: "+textEmailSignIn.getText().trim());
        String masterKeystrokeString = UserData.getInstance().getUserKeystrokeString(textEmailSignIn.getText().trim());
        String slaveKeystrokeString = "";
        System.out.println("masterKeystrokeString: "+ masterKeystrokeString);
        if (!(textKeystrokeSignIn.getText().trim().equals(labelkeySignIn.getText().trim()))){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Typed in text doesn't match the text displayed");
            alert.showAndWait();
            textKeystrokeSignIn.clear();
            pressTimes.clear();
        } else {
            for (Long time: this.pressTimes){
                slaveKeystrokeString += delimeter + Long.toString(time);
            }

            System.out.println("salveKeystrokeString: "+ slaveKeystrokeString);
            Boolean acceptance = Pair.getInstance().match(masterKeystrokeString,slaveKeystrokeString);
            if (acceptance){
                System.out.println("\nLog In Successful!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Hello "+
                        UserData.getInstance().getUserName(textEmailSignIn.getText().trim())+"\nLog in successful!");
                alert.showAndWait();
                System.exit(0);
            } else {
                System.out.println("\nDynamic Mismatch!");
                Alert alert = new Alert(Alert.AlertType.ERROR,"Keystroke dynamics mismatch!\nRe-check the email ID and Re-Enter the password");
                alert.showAndWait();
                pressTimes.clear();
                textEmailSignIn.clear();
                textKeystrokeSignIn.clear();
            }
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}