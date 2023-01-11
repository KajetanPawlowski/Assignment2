package assignment2.common;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversation implements Serializable {
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public Conversation(User user1, User user2){
        users = new ArrayList<User>();
        messages = new ArrayList<Message>();
        users.add(user2);
        users.add(user1);
    }

    public Conversation getConversation(User user1, User user2){
        if(findUser(user1) || findUser(user2)){
            return this;
        }else{
            return null;
        }

    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public void sendMessage(Message msg){
        messages.add(msg);
    }

    private boolean findUser(User user){
        if((users.get(0).equals(user))||(users.get(1).equals(user))){
            return true;
        }else{
            return false;
        }
    }



}
