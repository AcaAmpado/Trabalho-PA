package jogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jogo.logica.*;
import jogo.ui.gui.Root;

public class MainGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StateMachine maquinaEstados = new StateMachine();
        GameObserver gameObserver = new GameObserver(maquinaEstados);

        Root root = new Root(gameObserver);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
