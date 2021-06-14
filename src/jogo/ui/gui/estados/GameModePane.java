package jogo.ui.gui.estados;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;
import jogo.logica.dados.Erro;

import static jogo.ui.gui.Constantes.*;


public class GameModePane extends VBox {

    private final GameObserver gameObserver;
    Button btNovoJogo;
    Button btCarregarJogo;
    Button btHistorico;
    Button btLogs;//?
    Button btSair;
    ComboBox<String> comboTipoJogo;
    TextField carregaJogoText;
    Label labelErro;

    public GameModePane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
        
        criarVista();

        registarObserver();

        registarListeners();

        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
    }

    private void criarVista() {

        setPrefSize(TAM_X_MENU,TAM_Y_MENU);
        setMinSize(TAM_X_MENU,TAM_Y_MENU);

        labelErro = new Label(null);
        labelErro.setVisible(false);
        labelErro.setTextFill(Color.RED);
        labelErro.setFont(FONTE_TEXTO);

        btNovoJogo = new Button("Novo Jogo");
        btNovoJogo.setFont(FONTE_TEXTO);

        btCarregarJogo = new Button("Carregar Jogo");
        btCarregarJogo.setFont(FONTE_TEXTO);

        btHistorico = new Button("Ver Jogos Anteriores");
        btHistorico.setFont(FONTE_TEXTO);

        btLogs = new Button("Logs");//?
        btLogs.setFont(FONTE_TEXTO);

        btSair = new Button("Sair");
        btSair.setFont(FONTE_TEXTO);

        ObservableList<String> options = FXCollections.observableArrayList("Jogador vs Jogador","Jogador vs AI","AI vs AI");
        comboTipoJogo = new ComboBox<>(options);
        comboTipoJogo.setValue("Jogador vs Jogador");

        Label labelTipoJogo = new Label("Tipo de Jogo:");
        labelTipoJogo.setFont(FONTE_TEXTO);
        labelTipoJogo.setTextFill(Color.WHITE);


        HBox tipoJogoBox = new HBox(10);
        tipoJogoBox.getChildren().addAll(labelTipoJogo,comboTipoJogo);
        tipoJogoBox.setAlignment(Pos.CENTER);

        VBox novoJogoBox = new VBox(10);
        novoJogoBox.getChildren().addAll(btNovoJogo, tipoJogoBox);
        novoJogoBox.setAlignment(Pos.CENTER);
        novoJogoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        novoJogoBox.setPadding(new Insets(10));

        carregaJogoText = new TextField();
        carregaJogoText.setPromptText("Nome do Ficheiro");
        carregaJogoText.setFont(FONTE_TEXTO);

        HBox carregaJogoBox = new HBox(10);
        carregaJogoBox.getChildren().addAll(btCarregarJogo, carregaJogoText);
        carregaJogoBox.setAlignment(Pos.CENTER);
        carregaJogoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        carregaJogoBox.setPadding(new Insets(10));

        VBox menu = new VBox(10);
        menu.getChildren().addAll(novoJogoBox, carregaJogoBox, btHistorico, btLogs, btSair, labelErro);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);



        getChildren().addAll(menu);
        setSpacing(20);
    }

    private void registarListeners(){
        btNovoJogo.setOnAction(e -> {
            String value = comboTipoJogo.getValue();
            if(value.equals("Jogador vs Jogador"))
                gameObserver.selGameMode(1);
            else if(value.equals("Jogador vs AI"))
                gameObserver.selGameMode(2);
            else
                gameObserver.selGameMode(3);
        });

        btCarregarJogo.setOnAction((e)->{
            String value;
                value =  carregaJogoText.getText();
            if(value.length() < 1){
                carregaJogoText.requestFocus();
                return;
            }
            gameObserver.carregaJogo(value+".dat");
            Erro erro=gameObserver.getEstadoErro();
            if(erro == Erro.Critico)
            {
                labelErro.setVisible(true);
                labelErro.setText("Erro a carregar o jogo!");
            }
        });

        btHistorico.setOnAction((e)->gameObserver.historicoJogos());

       // btLogs.setOnAction((e)->gameObserver.yeet());

        btSair.setOnAction( (e)-> gameObserver.terminar());
    }

    private void atualiza() {
        this.setVisible(gameObserver.getStatus() ==  Situacao.GameMode );
    }
}
