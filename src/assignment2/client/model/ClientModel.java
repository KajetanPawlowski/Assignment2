package assignment2.client.model;

import assignment2.common.Conversation;
import assignment2.common.Message;
import assignment2.common.User;
import assignment2.client.core.Observer;
import assignment2.client.core.Subject;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientModel implements Subject {
    private User user;
    private final int PORT_NO = 1234;
    private ClientSocketHandler clientSocketHandler;
    private ArrayList<Message> messages;
    private ArrayList<User> accounts;
    private ArrayList<Conversation> conversations;
    private int messageBuffer = 0;


    public ClientModel(){
        user = new User(null, null);
        messages = new ArrayList<Message>();
        accounts = new ArrayList<User>();
        conversations = new ArrayList<Conversation>();
    }

    public boolean loginClient(){
        try
        {
            Socket socket = new Socket(user.getIpAddress(), PORT_NO);
            clientSocketHandler =  new ClientSocketHandler(socket, this);
            runClient(clientSocketHandler);
            System.out.println("ClientModel::login[" + user.getUserName() +"("+  user.getIpAddress() + ")]");
            return true;

        }
        catch (IOException e)
        {
            System.out.println("ClientModel::loginClient::UnknownHostException");
            return false;
        }
    }

    private void runClient(ClientSocketHandler csh)
    {
        try
        {
            Thread thread =  new Thread(csh);
            thread.setDaemon(true);
            thread.start();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setId(int nextId){
        user.setServerId(nextId);
    }
    public int getId(){
        return user.getServerId();
    }

    public User getUser() {
        return user;
    }
    public void setAccountsArray(ArrayList<User> users){
        System.out.println("ClientMode::setAccountArray::" + accounts.toString());
        this.accounts = users;
    }
    public void addUser(User newUser){
        System.out.println("ClientModel::adduser");
        accounts.add(newUser);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                notifyObservers();
            }
        });
    }

    public void setMessagesArray(ArrayList<Message> messages){
        this.messages = messages;
    }
    public void setConversationArray(ArrayList<Conversation> conversation){
        this.conversations = conversation;
    }

    public void sendMessage(String messageText){
        if(messageText.equals("")==false){
            clientSocketHandler.sendMessageToServer(new Message(user, messageText));
        }
    }
    public void receiveMessage(Message messageFormServer){
        messages.add(messageFormServer);
        messageBuffer = messages.size();
        Platform.runLater(new Runnable() {
            @Override public void run() {
                notifyObservers();
                messageBuffer--;
            }
        });

    }

    public int getMessageBuffer() {
        return messageBuffer;
    }

    public  ArrayList<String>getMessagesArrayList(){
        ArrayList<String> messagesStringArrayList = new ArrayList<String>();
        for(int i = 0; i < messages.size(); i++){
            String newMessage = messages.get(i).getSender().getUserName() +": "+ messages.get(i).getText();
            messagesStringArrayList.add(newMessage);
        }

        return messagesStringArrayList;
    }
    public void getConversation(String userName){
        for(int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).getConversation(user, new User(userName, "")) != null) {
                messages = conversations.get(i).getMessages();
                break;
            }
        }

    }

    public ArrayList<String> getAccountArrayList(){
        ArrayList<String> accountStringArrayList = new ArrayList<String>();
        for(int i = 0; i < accounts.size(); i++){
            accountStringArrayList.add(accounts.get(i).getUserName());
        }

        return accountStringArrayList;
    }

    public boolean validate() {
        return true;
    }

    public boolean login(){
        return loginClient();
    }

    public void setUserName(String name){
        user.setUserName(name);
    }

    public String getUserName(){
        return user.getUserName();
    }
    public void setIpAddress(String ip){
        user.setIpAddress(ip);
    }

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    @Override
    public synchronized void attachObserver( Observer observer )
    {
        observers.add( observer );
    }
    @Override
    public synchronized void detachObserver( Observer observer)
    {
        observers.remove( observer );
    }
    @Override
    public void notifyObservers()
    {
        System.out.println("ClientModel::notifyAllObservers");
        for( Observer o: observers )
            o.update();
    }


}
