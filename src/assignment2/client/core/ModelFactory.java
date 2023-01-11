package assignment2.client.core;

import assignment2.client.model.ClientModel;

public class ModelFactory {
    private ClientModel clientModel;

    public ClientModel getClientModel(){
        if(clientModel == null){
            clientModel = new ClientModel();
        }
        return clientModel;
    }
}
