package jogo.ui.gui.estados;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import static jogo.ui.gui.Constantes.*;


public class GameModePane extends VBox {

    private final GameObserver gameObserver;
    private Button btNovoJogo;
    private Button btCarregarJogo;
    private Button btHistorico;
    private Button btSair;
    private ComboBox<String> comboTipoJogo;
    private TextField carregaJogoText;
    private Label labelErro;

    public GameModePane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
        
        criarVista();

        registarObserver();

        registarListeners();

        atualiza();
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

        ObservableList<String> options = FXCollections.observableArrayList("Jogador vs Jogador","Jogador vs AI","AI vs AI");
        comboTipoJogo = new ComboBox<>(options);
        comboTipoJogo.setValue("Jogador vs Jogador");

        Label labelTipoJogo = new Label("Tipo de Jogo:");
        labelTipoJogo.setFont(FONTE_TEXTO);
        labelTipoJogo.setTextFill(Color.WHITE);

        HBox novoJogoBox = new HBox(10);
        novoJogoBox.getChildren().addAll(btNovoJogo, labelTipoJogo,comboTipoJogo);
        novoJogoBox.setAlignment(Pos.CENTER);
        novoJogoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        novoJogoBox.setPadding(new Insets(10));


        btCarregarJogo = new Button("Carregar Jogo");
        btCarregarJogo.setFont(FONTE_TEXTO);

        carregaJogoText = new TextField();
        carregaJogoText.setPromptText("Nome do Ficheiro");
        carregaJogoText.setFont(FONTE_TEXTO);

        HBox carregaJogoBox = new HBox(10);
        carregaJogoBox.getChildren().addAll(btCarregarJogo, carregaJogoText);
        carregaJogoBox.setAlignment(Pos.CENTER);
        carregaJogoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        carregaJogoBox.setPadding(new Insets(10));


        btHistorico = new Button("Historico");
        btHistorico.setFont(FONTE_TEXTO);

        HBox historicoBox = new HBox(10);
        historicoBox.getChildren().addAll(btHistorico);
        historicoBox.setAlignment(Pos.CENTER);
        historicoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        historicoBox.setPadding(new Insets(10));


        //?
        Button btLogs = new Button("Logs");
        btLogs.setFont(FONTE_TEXTO);

        HBox logsBox = new HBox(10);
        logsBox.getChildren().addAll(btLogs);
        logsBox.setAlignment(Pos.CENTER);
        logsBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        logsBox.setPadding(new Insets(10));


        btSair = new Button("Sair");
        btSair.setFont(FONTE_TEXTO);

        HBox sairBox = new HBox(10);
        sairBox.getChildren().addAll(btSair);
        sairBox.setAlignment(Pos.CENTER);
        sairBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        sairBox.setPadding(new Insets(10));


        VBox menu = new VBox(10);
        menu.getChildren().addAll(novoJogoBox, carregaJogoBox, historicoBox, logsBox, sairBox, labelErro);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);



        getChildren().addAll(menu);
        setSpacing(20);
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
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

        btSair.setOnAction( (e)-> gameObserver.terminaJogo());
    }

    private void atualiza() {
        this.setVisible(gameObserver.getStatus() ==  Situacao.GameMode );
    }
}
