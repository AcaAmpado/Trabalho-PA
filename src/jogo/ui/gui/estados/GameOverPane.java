package jogo.ui.gui.estados;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;
import java.util.Optional;

import static jogo.ui.gui.Constantes.FONTE_TEXTO;

public class GameOverPane extends VBox {
    private final GameObserver gameObserver;
    private Button btContinuar;
    private Button btSair;

    public GameOverPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registarListeners();

        atualiza();
    }

    private void registarListeners() {
        btContinuar.setOnAction( (e)-> gameObserver.start());

        btSair.setOnAction( (e)-> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fechar Aplicação?");
            alert.setHeaderText(null);
            alert.setContentText("Tem a certeza que quer sair da aplicação?");
            alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if (result.get() == ButtonType.YES){
                    gameObserver.guardaHistoricoF();
                    Platform.exit();
                } else {
                    e.consume();
                }
            }
        });
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
    }

    private void criarVista() {
        btContinuar = new Button("Continuar");
        btContinuar.setFont(FONTE_TEXTO);

        HBox boxEscolheJogoH = new HBox(10);
        boxEscolheJogoH.getChildren().addAll(btContinuar);
        boxEscolheJogoH.setAlignment(Pos.CENTER);
        boxEscolheJogoH.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        boxEscolheJogoH.setPadding(new Insets(10));

        btSair = new Button("Sair");
        btSair.setFont(FONTE_TEXTO);

        HBox boxSair = new HBox(10);
        boxSair.getChildren().addAll(btSair);
        boxSair.setAlignment(Pos.CENTER);
        boxSair.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        boxSair.setPadding(new Insets(10));

        Label pergunta = new Label("Pretende continuar a utilizar a aplicação ou pretende sair da mesma?");
        pergunta.setFont(FONTE_TEXTO);
        pergunta.setTextFill(Color.WHITE);


        VBox menu = new VBox(10);
        menu.getChildren().addAll(pergunta,boxEscolheJogoH,boxSair);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(menu);
    }

    private void atualiza() {
        this.setVisible(gameObserver.getStatus() ==  Situacao.GameOver );
    }
}
