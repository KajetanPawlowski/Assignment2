package assignment2.server;

import assignment2.common.Message;
import assignment2.common.User;

import java.util.ArrayList;

public class ConnectionPool {
    private ArrayList<ServerSocketHandler> connections;

    public ConnectionPool() {
        connections =  new ArrayList<ServerSocketHandler>();
    }

    public synchronized void broadcast(Message msg) {
        System.out.println("ConnectionPool::broadcast>>" + msg.getSender().getUserName()+ " send: "+ msg.getText());
        for(ServerSocketHandler conn: connections )
        {
            conn.sendMessage(msg);
        }
    }
    public synchronized void broadcastNewUser(User user){
        System.out.println("ConnectionPool::broadcastNewUser++" + user.getUserName());
        for(ServerSocketHandler conn: connections )
        {
            conn.sendNewUser(user);
        }
    }

    public synchronized void  addConn(ServerSocketHandler ssh)
    {
        connections.add(ssh);
    }
    public synchronized void  removeConn(ServerSocketHandler ssh)
    {
        connections.remove(ssh);
    }

    public ArrayList<ServerSocketHandler> getConnections() {
        return connections;
    }
}
