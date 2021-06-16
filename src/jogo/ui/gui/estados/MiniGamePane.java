package jogo.ui.gui.estados;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static jogo.ui.gui.Constantes.DIM_BOLA;
import static jogo.ui.gui.Constantes.FONTE_TEXTO;


public class MiniGamePane extends VBox {

    private final GameObserver gameObserver;

    private Label minijogoLabel;
    private Label tempoLabel;
    private TextField inputText;
    private Button btSubmeter;
    private Circle corJogador;
    private Label creditosLabel;
    private Label pecaEspecialLabel;
    private Label jogadorLabel;
    private Label regrasLabel;
    private Label perguntaLabel;
    private Button btSair;
    private Timer timer;
    private Integer interval;

    public MiniGamePane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registaListeners();

        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
        gameObserver.addPropertyChangeListener("minijogoPergunta", evt -> atualizaPergunta());
    }

    public void setTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(interval > 0)
                {
                    Platform.runLater(() -> tempoLabel.setText("Tempo restante: "+interval));
                    interval--;
                }
                else{
                    Platform.runLater(() -> btSubmeter.fire());
                    timer.cancel();
                }
            }
        }, 1000,1000);
    }

    private void registaListeners() {

        btSubmeter.setOnAction((e)->{
            String value;
            value =  inputText.getText().trim();
            if(value.length() < 1){
                inputText.requestFocus();
                return;
            }
            switch (gameObserver.getMiniJogo()){
                case 0 -> {
                    double numero;
                    try{
                        numero = Double.parseDouble(value);
                    }catch (NumberFormatException ignored){
                        inputText.requestFocus();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro!");
                        alert.setHeaderText(null);
                        alert.setContentText("O input não é um numero");
                        alert.show();
                        return;
                    }
                    gameObserver.jogaMinijogo(numero);
                }
                case 1 -> gameObserver.jogaMinijogo(value);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado Minijogo");
            alert.setHeaderText(null);
            switch (gameObserver.isMinigame()) {
                case Ganhou -> {
                    alert.setContentText("Ganhou o minijogo");
                    alert.show();
                    timer.cancel();
                }
                case Perdeu -> {
                    alert.setContentText("Perdeu o minijogo");
                    alert.show();
                    timer.cancel();
                }
            }
        });

        btSair.setOnAction( (e)-> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fechar Jogo?");
            alert.setHeaderText(null);
            alert.setContentText("Tem a certeza que quer sair do jogo?");
            alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if (result.get() == ButtonType.YES){
                    TextInputDialog dialog = new TextInputDialog("ficheiro");
                    dialog.setTitle("Guarda Jogo");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Introduza o nome do ficheiro:");
                    Optional<String> resultado = dialog.showAndWait();
                    if (resultado.isPresent()){
                        gameObserver.guardaJogo(resultado.get()+".dat");
                    }else e.consume();
                    gameObserver.terminaJogo();
                } else {
                    e.consume();
                }
            }
        });
    }

    private void criarVista() {

        corJogador = new Circle();
        corJogador.setRadius(DIM_BOLA);

        Label corLabel = new Label("Cor das peças:");
        corLabel.setFont(FONTE_TEXTO);
        corLabel.setTextFill(Color.WHITE);
        corLabel.setPadding(new Insets(10));

        jogadorLabel = new Label();
        jogadorLabel.setFont(FONTE_TEXTO);
        jogadorLabel.setTextFill(Color.WHITE);
        jogadorLabel.setPadding(new Insets(10));

        creditosLabel = new Label();
        creditosLabel.setFont(FONTE_TEXTO);
        creditosLabel.setTextFill(Color.WHITE);
        creditosLabel.setPadding(new Insets(10));

        pecaEspecialLabel = new Label();
        pecaEspecialLabel.setFont(FONTE_TEXTO);
        pecaEspecialLabel.setTextFill(Color.WHITE);
        pecaEspecialLabel.setPadding(new Insets(10));

        HBox especiaisBox = new HBox(10);
        especiaisBox.getChildren().addAll(creditosLabel,pecaEspecialLabel,corLabel,corJogador);
        especiaisBox.setAlignment(Pos.CENTER);
        especiaisBox.setPadding(new Insets(10));

        VBox dadosBox = new VBox(10);
        dadosBox.getChildren().addAll(jogadorLabel,especiaisBox);
        dadosBox.setAlignment(Pos.CENTER);
        dadosBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        dadosBox.setPadding(new Insets(10));


        minijogoLabel = new Label("Minijogo: ");
        minijogoLabel.setFont(FONTE_TEXTO);
        minijogoLabel.setTextFill(Color.WHITE);


        tempoLabel = new Label("Tempo Restante: ");
        tempoLabel.setFont(FONTE_TEXTO);
        tempoLabel.setTextFill(Color.WHITE);


        regrasLabel = new Label("Regras: ");
        regrasLabel.setFont(FONTE_TEXTO);
        regrasLabel.setTextFill(Color.WHITE);


        perguntaLabel = new Label();
        perguntaLabel.setFont(FONTE_TEXTO);
        perguntaLabel.setTextFill(Color.WHITE);

        VBox perguntaBox = new VBox(10);
        perguntaBox.getChildren().addAll(regrasLabel, perguntaLabel);
        perguntaBox.setAlignment(Pos.CENTER);
        perguntaBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        perguntaBox.setPadding(new Insets(10));


        inputText = new TextField();
        inputText.setFont(FONTE_TEXTO);
        inputText.setPromptText("Resposta");

        btSubmeter = new Button("Submeter");
        btSubmeter.setFont(FONTE_TEXTO);

        VBox submeterBox = new VBox(10);
        submeterBox.getChildren().addAll(inputText,btSubmeter);
        submeterBox.setAlignment(Pos.CENTER);
        submeterBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        submeterBox.setPadding(new Insets(10));


        btSair = new Button("Sair");
        btSair.setFont(FONTE_TEXTO);

        HBox sairBox = new HBox(10);
        sairBox.getChildren().addAll(btSair);
        sairBox.setAlignment(Pos.CENTER);
        sairBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        sairBox.setPadding(new Insets(10));


        VBox menu = new VBox(10);
        menu.getChildren().addAll(dadosBox, minijogoLabel, tempoLabel, perguntaBox, submeterBox, sairBox);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(menu);

    }

    private void atualizaPergunta(){
        perguntaLabel.setText(gameObserver.getPergunta());
        inputText.setText("");
    }

    private void atualiza() {

        if(gameObserver.getStatus() ==  Situacao.MiniGame){
            interval = gameObserver.getTempoRonda();
            if(gameObserver.getSymbol()=='A')
                corJogador.setFill(Color.BLUE);
            else if (gameObserver.getSymbol()=='B')
                corJogador.setFill(Color.RED);
            else
                corJogador.setFill(Color.WHITE);
            jogadorLabel.setText( "Jogador: " + gameObserver.getNomeJogadorVez() + "\tTipo de Jogador: " +  gameObserver.getTipoJogador().toString());
            creditosLabel.setText("Creditos: " + gameObserver.getCreditos());
            pecaEspecialLabel.setText("Peca Especial: " + gameObserver.getPecaEspecial());
            atualizaPergunta();
            switch (gameObserver.getMiniJogo()){
                case 0 ->{
                    minijogoLabel.setText("Minijogo: RandomContas");
                    regrasLabel.setText(gameObserver.getRules());
                }
                case 1 ->{
                    minijogoLabel.setText("Minijogo: EscrevePalavras");
                    regrasLabel.setText(gameObserver.getRules());
                }
            }
            //atualiza timer tempoLabel

            setTimer();
            inputText.setText("");

        }
        this.setVisible(gameObserver.getStatus() ==  Situacao.MiniGame);
    }




}
