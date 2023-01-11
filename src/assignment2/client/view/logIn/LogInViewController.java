package assignment2.client.view.logIn;

import assignment2.client.core.ViewHandler;
import assignment2.client.core.ViewModelFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LogInViewController {
    private ViewHandler viewHandler;
    private LogInViewModel logInVM;

    @FXML
    TextField loginTextField;
    @FXML
    TextField ipTextField;
    @FXML
    Label errorLabel;


    public void init(ViewHandler viewHandler, LogInViewModel logInVM){
        this.viewHandler = viewHandler;
        this.logInVM = logInVM;
        loginTextField.textProperty().bindBidirectional(logInVM.getUserNameProperty());
        ipTextField.textProperty().bindBidirectional(logInVM.getIpProperty());
        errorLabel.textProperty().bindBidirectional(logInVM.getErrorLabelProperty());
    }


    @FXML
    public void onLoginBtn(){
        if(logInVM.login()==true){
            viewHandler.openChatView();
        }
        logInVM.logInError();
    }
}
