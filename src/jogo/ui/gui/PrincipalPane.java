package jogo.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import jogo.logica.GameObserver;

import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import jogo.ui.gui.estados.*;

import static jogo.ui.gui.Constantes.*;

public class PrincipalPane extends BorderPane {

    private final GameObserver gameObserver;

    public PrincipalPane (GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        atualiza();

    }

    private void criarVista() {

        //Escolher Tamanho da Janela
        setMaxSize(TAM_X_JANELA, TAM_Y_JANELA);
        setPrefSize(TAM_X_JANELA, TAM_Y_JANELA);
        setMinSize(TAM_X_JANELA, TAM_Y_JANELA);


        //Dar Cor ao background e dar lhe uma border
        setBackground(new Background(new BackgroundFill(Color.GRAY,null, null) ));
        setBorder(new Border( new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID,null,new BorderWidths(5))));
        setPadding(new Insets(10));

        VBox tabuleiroBox = new VBox(10);
        TabuleiroPane tabuleiroPane = new TabuleiroPane(gameObserver);

        Label tabuleiroLabel = new Label("TABULEIRO");
        tabuleiroLabel.setFont(FONTE);
        tabuleiroLabel.setTextFill(Color.WHITE);

        tabuleiroBox.getChildren().addAll(tabuleiroLabel,tabuleiroPane);
        setLeft(tabuleiroBox);
        tabuleiroBox.setAlignment(Pos.CENTER);


        DecisaoMiniGamePane decisaoMiniGamePane = new DecisaoMiniGamePane(gameObserver);
        EscolhaJogoHPane escolhaJogoHPane = new EscolhaJogoHPane(gameObserver);
        GameModePane gameModePane = new GameModePane(gameObserver);
        GameOverPane gameOverPane = new GameOverPane(gameObserver);
        InicioPane inicioPane = new InicioPane(gameObserver);
        JogadaPane jogadaPane = new JogadaPane(gameObserver);
        NamePlayersPane namePlayersPane = new NamePlayersPane(gameObserver);
        PassarTurnoPane passarTurnoPane = new PassarTurnoPane(gameObserver);
        MiniGamePane miniGamePane = new MiniGamePane(gameObserver);

        StackPane menuBox = new StackPane(decisaoMiniGamePane,escolhaJogoHPane,gameModePane,gameOverPane,inicioPane,jogadaPane,namePlayersPane,passarTurnoPane,miniGamePane);
        menuBox.setBorder(new Border(new BorderStroke(Color.BEIGE,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(5))));
        menuBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGREY ,new CornerRadii(12), null)));
        menuBox.setMinSize(TAM_X_MENU,TAM_Y_MENU);
        menuBox.setPrefSize(TAM_X_MENU,TAM_Y_MENU);
        setRight(menuBox);
        menuBox.setAlignment(Pos.CENTER);
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
    }

    private void atualiza(){

    }

}
