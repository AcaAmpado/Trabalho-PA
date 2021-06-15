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

    private GameObserver gameObserver;

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

/*
        Label coluna1Label = new Label("Coluna 1");
        RadioButton coluna1 = new RadioButton();
        VBox coluna1vBox = new VBox(10);
        coluna1vBox.getChildren().addAll(coluna1,coluna1Label);
        coluna1vBox.setAlignment(Pos.CENTER);

        Label coluna2Label = new Label("Coluna 2");
        RadioButton coluna2 = new RadioButton();
        VBox coluna2vBox = new VBox(10);
        coluna2vBox.getChildren().addAll(coluna2,coluna2Label);
        coluna2vBox.setAlignment(Pos.CENTER);

        Label coluna3Label = new Label("Coluna 3");
        RadioButton coluna3 = new RadioButton();
        VBox coluna3vBox = new VBox(10);
        coluna3vBox.getChildren().addAll(coluna3,coluna3Label);
        coluna3vBox.setAlignment(Pos.CENTER);

        Label coluna4Label = new Label("Coluna 4");
        RadioButton coluna4 = new RadioButton();
        VBox coluna4vBox = new VBox(10);
        coluna4vBox.getChildren().addAll(coluna4,coluna4Label);
        coluna4vBox.setAlignment(Pos.CENTER);

        Label coluna5Label = new Label("Coluna 5");
        RadioButton coluna5 = new RadioButton();
        VBox coluna5vBox = new VBox(10);
        coluna5vBox.getChildren().addAll(coluna5,coluna5Label);
        coluna5vBox.setAlignment(Pos.CENTER);

        Label coluna6Label = new Label("Coluna 6");
        RadioButton coluna6 = new RadioButton();
        VBox coluna6vBox = new VBox(10);
        coluna6vBox.getChildren().addAll(coluna6,coluna6Label);
        coluna6vBox.setAlignment(Pos.CENTER);

        Label coluna7Label = new Label("Coluna 7");
        RadioButton coluna7 = new RadioButton();
        VBox coluna7vBox = new VBox(10);
        coluna7vBox.getChildren().addAll(coluna7,coluna7Label);
        coluna7vBox.setAlignment(Pos.CENTER);

        ToggleGroup radioGroup = new ToggleGroup();
        radioGroup.getToggles().addAll(coluna1,coluna2,coluna3,coluna4,coluna5,coluna6,coluna7);

        HBox colunasBox = new HBox(10);
        colunasBox.getChildren().addAll(coluna1vBox,coluna2vBox,coluna3vBox,coluna4vBox,coluna5vBox,coluna6vBox,coluna7vBox);
        colunasBox.setMinSize(TAM_X_COL,TAM_Y_COL);
        colunasBox.setPrefSize(TAM_X_COL,TAM_Y_COL);
        */

        tabuleiroBox.getChildren().addAll(tabuleiroLabel,tabuleiroPane/*,colunasBox*/);
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

        StackPane menuBox = new StackPane(decisaoMiniGamePane,escolhaJogoHPane,gameModePane,gameOverPane,inicioPane,jogadaPane,namePlayersPane,passarTurnoPane);
        menuBox.setBorder(new Border(new BorderStroke(Color.BEIGE,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(5))));
        menuBox.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGREY ,new CornerRadii(12), null)));
        menuBox.setMinSize(TAM_X_MENU,TAM_Y_MENU);
        menuBox.setPrefSize(TAM_X_MENU,TAM_Y_MENU);
        setRight(menuBox);
        menuBox.setAlignment(Pos.CENTER);
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
    }

    private void atualiza(){

    }

}
