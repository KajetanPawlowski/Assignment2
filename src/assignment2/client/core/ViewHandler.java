package assignment2.client.core;

import assignment2.client.view.chat.ChatViewController;
import assignment2.client.view.logIn.LogInViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class ViewHandler {
    private ViewModelFactory vmf;
    private Scene logInScene;
    private Scene chatScene;
    private Stage stage;

    public ViewHandler(ViewModelFactory vmf){
        this.vmf = vmf;
        stage = new Stage();
    }

    public void start(){
        openLogInView();

    }

    public void openLogInView(){
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/logIn/LogInView.fxml"));
        Parent root = loader.load();

        LogInViewController controller = loader.getController();
        controller.init(this, vmf.getLogInVM());


        logInScene = new Scene(root);
        stage.setScene(logInScene);
        stage.setTitle("LogIn");
        stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openChatView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/chat/ChatView.fxml"));
            Parent root = loader.load();

            ChatViewController controller = loader.getController();
            controller.init(this, vmf.getChatVM());

            chatScene = new Scene(root);
            stage.setScene(chatScene);
            stage.setTitle("Chat");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
