package assignment2.client.model;

import assignment2.common.Conversation;
import assignment2.common.Message;
import assignment2.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocketHandler implements Runnable{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ClientModel clientModel;

    public ClientSocketHandler(Socket socket, ClientModel clientModel) {
        this.socket = socket;
        this.clientModel = clientModel;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            int id = (int) in.readObject();
            clientModel.setId(id);
            out.writeObject(clientModel.getUser());
            out.writeObject(new Message(new User("Server", ""), clientModel.getUser().getUserName() + " join the chat"));
            out.flush();
            clientModel.setConversationArray((ArrayList<Conversation>) in.readObject());
            clientModel.setMessagesArray((ArrayList<Message>) in.readObject());
            clientModel.setAccountsArray((ArrayList<User>) in.readObject());

            while (true) {
                Object fromServer = in.readObject();
                if(fromServer instanceof User){
                    System.out.println("ClientSocketHandler::run++" + ((User) fromServer).getUserName());
                    clientModel.addUser((User)fromServer);
                }else{
                    Message messFromServer = (Message)fromServer;
                    clientModel.receiveMessage(messFromServer);
                    System.out.println("ClientSocketHandler::run<<"+messFromServer.getSender().getUserName()+ " send: " + messFromServer.getText());
                }





            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void sendMessageToServer(Message msg)
    {

        try
        {
            out.writeObject(msg);
            System.out.println("ClientSocketHandler::sendMessageToServer>>" + msg.getText());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
