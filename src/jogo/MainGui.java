package jogo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import jogo.logica.*;
import jogo.ui.gui.Root;

import java.util.Optional;

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
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest((e)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fechar Aplicação?");
            alert.setHeaderText(null);
            alert.setContentText("Tem a certeza que quer sair da aplicação?");
            alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if (result.get() == ButtonType.YES){
                    if(gameObserver.getHistoricoNum()>0)
                        gameObserver.guardaHistoricoF();
                    Platform.exit();
                } else {
                     e.consume();
                }
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle("4 em Linha");

        primaryStage.show();

    }
}
