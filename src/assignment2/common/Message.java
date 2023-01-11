package assignment2.common;

import java.io.Serializable;

public class Message implements Serializable {
    private String text;
    private User sender;
    public Message(User sender,String text){
        this.text = text;
        this.sender =sender;
    }
    public String getText(){
        return text;
    }
    public User getSender(){
        return sender;
    }

}
