package assignment2.client.core;

import assignment2.client.view.chat.ChatViewModel;
import assignment2.client.view.logIn.LogInViewModel;

public class ViewModelFactory {
    private ModelFactory mf;
    public ViewModelFactory(ModelFactory mf){
        this.mf = mf;
    }
    public LogInViewModel getLogInVM(){
        return new LogInViewModel(mf.getClientModel());
    }

    public ChatViewModel getChatVM() {
        return new ChatViewModel(mf.getClientModel());
    }
}
