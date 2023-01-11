package assignment2.client.view.logIn;

import assignment2.client.model.ClientModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogInViewModel {
    private ClientModel clientModel;

    private StringProperty userNameProperty = new SimpleStringProperty("");
    private StringProperty ipProperty = new SimpleStringProperty("");
    private StringProperty errorLabelProperty = new SimpleStringProperty("");


    public LogInViewModel(ClientModel model){
        clientModel = model;
    }


    public StringProperty getUserNameProperty(){
        return userNameProperty;
    }
    public StringProperty getIpProperty(){
        return ipProperty;
    }
    public StringProperty getErrorLabelProperty(){
        return errorLabelProperty;
    }
    public boolean login(){
        clientModel.setUserName(userNameProperty.get());
        clientModel.setIpAddress(ipProperty.get());
        return clientModel.login();
    }

    public void logInError(){
        errorLabelProperty.setValue("Invalid Login Info");
    }

}
