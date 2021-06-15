package jogo;

import javafx.application.Application;
import javafx.application.Platform;
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
        MaquinaEstados maquinaEstados = new MaquinaEstados();
        GameObserver gameObserver = new GameObserver(maquinaEstados);

        Root root = new Root(gameObserver);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(windowEvent -> Platform.exit());
        primaryStage.setResizable(false);
        primaryStage.setTitle("4 em Linha");

        primaryStage.show();
    }
}
