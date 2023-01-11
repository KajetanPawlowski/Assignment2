package assignment2.client;

import assignment2.client.core.ModelFactory;
import assignment2.client.core.ViewHandler;
import assignment2.client.core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {
    public void start(Stage stage) throws Exception{
        ModelFactory mf = new ModelFactory();
        ViewModelFactory vmf = new ViewModelFactory(mf);
        ViewHandler viewHandler = new ViewHandler(vmf);
        viewHandler.start();

    }
}
