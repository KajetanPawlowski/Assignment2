package assignment2.client.view.chat;

import assignment2.client.core.ViewHandler;
import assignment2.client.core.ViewModelFactory;
import assignment2.client.view.logIn.LogInViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ChatViewController {
    private ViewHandler viewHandler;
    private ChatViewModel chatVM;

    @FXML
    ListView<String> messagesListView;

    @FXML
    ListView<String> accountListView;

    @FXML
    TextField inputTextField;

    @FXML
    Label accountNameLabel;

    public void init(ViewHandler viewHandler, ChatViewModel chatVM){
        this.viewHandler = viewHandler;
        this.chatVM = chatVM;
        accountNameLabel.textProperty().bindBidirectional(chatVM.getAccountNameLabelProperty());
        inputTextField.textProperty().bindBidirectional(chatVM.getMessageInputProperty());
        accountListView.setItems(chatVM.getAccountsList());
        messagesListView.setItems(chatVM.getMessagesList());


    }



    @FXML
    private void onLogoutBtn(){
        viewHandler.openLogInView();
    }
    @FXML
    private void onSentBtn(){
        chatVM.sentMessage();
    }

    @FXML
    void onContextRequested(ContextMenuEvent event) {
        System.out.println("ChatViewController::onContext::" + event.toString());
    }

    @FXML
    void onListMousePress(MouseEvent event) {
        if(event.getTarget() instanceof Text) {
            String target = event.getTarget().toString();
            String[] newTarget = target.split("\"");

            System.out.println("ChatViewController::onListMousePress::" + newTarget[1]);
            chatVM.openConversation(newTarget[1]);
        }
    }
}
