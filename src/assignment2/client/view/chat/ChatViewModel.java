package assignment2.client.view.chat;

import assignment2.client.core.Observer;
import assignment2.client.model.ClientModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatViewModel implements Observer {
    private ClientModel clientModel;

    private StringProperty messageInputProperty = new SimpleStringProperty("");
    private StringProperty accountNameLabelProperty;
    private ObservableList<String> messageList;
    private ObservableList<String> accountList;
    private int no_msg = 0;


    public ChatViewModel(ClientModel model){
        clientModel = model;
        clientModel.attachObserver(this);
        messageList = FXCollections.observableArrayList();
        accountList = FXCollections.observableArrayList();
        accountNameLabelProperty = new SimpleStringProperty("Hi " + clientModel.getUserName());


    }

    public StringProperty getMessageInputProperty(){
        return messageInputProperty;
    }
    public StringProperty getAccountNameLabelProperty(){
        return accountNameLabelProperty;
    }
    public ObservableList<String> getMessagesList(){
        return messageList;
    }
    public ObservableList<String> getAccountsList(){
        return accountList;
    }
    public void sentMessage(){
        clientModel.sendMessage(messageInputProperty.get());
        messageInputProperty.setValue("");

    }
    public void openConversation(String userName){
        clientModel.getConversation(userName);
        updateMessageList();
    }

    public void updateMessageList(){
        messageList.clear();
        for(int i = 0; i < clientModel.getMessagesArrayList().size(); i++) {
            messageList.add(clientModel.getMessagesArrayList().get(i));
        }

    }
    public void updateAccountList(){
        accountList.clear();
        for(int i = 0; i < clientModel.getAccountArrayList().size();i++){
            accountList.add(clientModel.getAccountArrayList().get(i));
        }
    }

    @Override
    public void update() {
        System.out.println("ChatViewModel::update");
        updateMessageList();
        updateAccountList();
    }
}
