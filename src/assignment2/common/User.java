package assignment2.common;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String ipAddress;
    private int serverId;
    public User(String name, String IP){
        userName = name;
        ipAddress = IP;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName(){
        return userName;
    }
    public String getIpAddress(){
        return ipAddress;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getServerId() {
        return serverId;
    }

    public boolean equals(Object obj){
        if(obj instanceof User){
            User otherUser = (User)obj;
            if(this.userName.equals(otherUser.getUserName())){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }
}
