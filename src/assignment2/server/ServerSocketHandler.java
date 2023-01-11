package assignment2.server;

import assignment2.common.Message;
import assignment2.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerSocketHandler implements Runnable
{
    private ChatServer server;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int id;
    private Message initialMessage;
    private User user;

    public ServerSocketHandler(Socket socket, int id, ChatServer server)
    {
        this.socket = socket;
        this.server = server;
        this.id =id;
        try
        {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            System.out.println("ServerSocketHandle::constructor::IOException");
        }
    }

    @Override
    public void run() {
        try {
            out.writeObject(id);
            out.flush();
            user = (User) in.readObject();
            initialMessage = (Message) in.readObject();
            out.writeObject(server.getAllConversations());
            out.flush();
            sendAllMessages();
            out.writeObject(server.getAllAccountsArray());
            server.getAllMessages().add(initialMessage);
            server.getConnectionPool().broadcast(initialMessage);
            server.getConnectionPool().broadcastNewUser(user);

            while (true) {
                Message message = (Message) in.readObject();
                System.out.println("ServerSocketHandler::run<<" + message.getSender().getUserName() +" send: "+ message.getText());
                server.getAllMessages().add(message);
                server.getConnectionPool().broadcast(message);

            }

        }

        catch (SocketException logoutEx) {
            server.userLogOut(this);

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }


    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
        }
        catch (SocketException sEx){
            System.out.println("ServerSocketHandler::sendMessage::SocketException");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendAllMessages(){
        try {
            System.out.println("ServerSocketHandler::sendAllMessages");
            out.writeObject(server.getAllMessages());
            out.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNewUser(User user) {
        try {
            out.writeObject(user);
            out.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
