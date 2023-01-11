package assignment2.server;

import assignment2.common.Conversation;
import assignment2.common.Message;
import assignment2.common.User;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private final int PORT_NO = 1234;
    private ConnectionPool connectionPool = new ConnectionPool();
    private ArrayList<Message> allMessages = new ArrayList<Message>();
    private ArrayList<Conversation> allConversations = new ArrayList<Conversation>();

    public ChatServer(){

    }
    public void run(){
        try {
            ServerSocket welcomeSocket = new ServerSocket(PORT_NO);

            System.out.println("ChatServer::run listening on " + InetAddress.getLocalHost().getHostAddress());

            int id = 0;

            while(true) {
                System.out.println("ChatServer::run Waiting for a client...");
                Socket socket = welcomeSocket.accept();
                System.out.println("ChatServer::run Client connected from " + socket.getInetAddress().getHostAddress() + " " + socket.getLocalPort());

                ServerSocketHandler serverSocketHandler = new ServerSocketHandler(socket,id, this);
                connectionPool.addConn(serverSocketHandler);

                Thread thread = new Thread(serverSocketHandler);
                thread.setDaemon(true);
                thread.start();


                id++;
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
    public ArrayList<Conversation> getAllConversations(){
        return allConversations;
    }
    public ArrayList<Message> getAllMessages(){
        return allMessages;
    }
    public ArrayList<User> getAllAccountsArray(){
        ArrayList<User> temp = new ArrayList<User>();
        for(int i = 0; i < connectionPool.getConnections().size()-1; i++){
            temp.add(connectionPool.getConnections().get(i).getUser());
        }
        return temp;
    }

    public ConnectionPool getConnectionPool(){
        return connectionPool;
    }

    public void sendServerMessage(String text){
        connectionPool.broadcast(createServerMessage(text));
    }
    public Message createServerMessage(String text){
        Message msg = new Message(new User("Server", ""), text);
        allMessages.add(msg);
        return msg;
    }
    public void userLogOut(ServerSocketHandler ssh){
        connectionPool.removeConn(ssh);
        sendServerMessage(ssh.getUser().getUserName() + " left the chat");
    }


}
