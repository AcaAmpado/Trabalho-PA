package jogo.ui.gui.estados;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import jogo.logica.dados.TipoJogador;
import jogo.logica.estados.GameOver;
import jogo.logica.estados.PassarTurno;

import static jogo.ui.gui.Constantes.FONTE_TEXTO;

public class JogadaPane extends VBox {
    private final GameObserver gameObserver;
    ComboBox<String> colunaJogadaCombo;
    ComboBox<String> colunaEspecialCombo;
    Button btJogada;
    Label jogadorLabel;
    Label creditosLabel;
    Label pecaEspecialLabel;
    Button btGuardaJogo;
    TextField guardaJogoNome;
    Button btCreditos;
    TextField creditosText;
    Button btEspecial;
    Button btLogs;
    Button btSair;
    Label erro;
    Label colunaJogadaLabel;
    HBox creditosBox;
    HBox especialBox;


    public JogadaPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registarListeners();

        atualiza();
    }

    private void criarVista() {
    /*  1 - Fazer Jogada
            2 - Guardar Jogo
            3 - Utilizar Créditos
            4 - Jogar Peca Especial
            5 - LogsME
    */
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
        especiaisBox.getChildren().addAll(creditosLabel,pecaEspecialLabel);
        especiaisBox.setAlignment(Pos.CENTER);
        especiaisBox.setPadding(new Insets(10));

        VBox dadosBox = new VBox(10);
        dadosBox.getChildren().addAll(jogadorLabel,especiaisBox);
        dadosBox.setAlignment(Pos.CENTER);
        dadosBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        dadosBox.setPadding(new Insets(10));

        btJogada = new Button("Fazer Jogada");
        btJogada.setFont(FONTE_TEXTO);

        colunaJogadaLabel = new Label("Coluna:");
        colunaJogadaLabel.setFont(FONTE_TEXTO);
        colunaJogadaLabel.setTextFill(Color.WHITE);

        ObservableList<String> options = FXCollections.observableArrayList("1","2","3","4","5","6","7");
        colunaJogadaCombo = new ComboBox<>(options);
        colunaJogadaCombo.setValue("1");

        HBox fazJogadaBox = new HBox(10);
        fazJogadaBox.getChildren().addAll(btJogada, colunaJogadaLabel, colunaJogadaCombo);
        fazJogadaBox.setAlignment(Pos.CENTER);
        fazJogadaBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        fazJogadaBox.setPadding(new Insets(10));


        btGuardaJogo = new Button("Guardar Jogo");
        btGuardaJogo.setFont(FONTE_TEXTO);

        guardaJogoNome = new TextField();
        guardaJogoNome.setFont(FONTE_TEXTO);
        guardaJogoNome.setPromptText("Nome do ficheiro");

        Label guardaJogoLabel = new Label("  Nome do Ficheiro:  ");
        guardaJogoLabel.setFont(FONTE_TEXTO);
        guardaJogoLabel.setTextFill(Color.WHITE);

        HBox guardaJogoBox = new HBox();
        guardaJogoBox.getChildren().addAll(btGuardaJogo, guardaJogoLabel, guardaJogoNome);
        guardaJogoBox.setAlignment(Pos.CENTER);
        guardaJogoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        guardaJogoBox.setPadding(new Insets(10));


        btCreditos = new Button("Utilizar Créditos");
        btCreditos.setFont(FONTE_TEXTO);

        Label creditosLabel = new Label("Numero de Créditos: ");
        creditosLabel.setFont(FONTE_TEXTO);
        creditosLabel.setTextFill(Color.WHITE);

        creditosText = new TextField();
        creditosText.setFont(FONTE_TEXTO);
        creditosText.setPromptText("Numero de Creditos");

        creditosBox = new HBox(10);
        creditosBox.getChildren().addAll(btCreditos, creditosLabel, creditosText);
        creditosBox.setAlignment(Pos.CENTER);
        creditosBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        creditosBox.setPadding(new Insets(10));


        btEspecial = new Button("Jogar Peça Especial");
        btEspecial.setFont(FONTE_TEXTO);

        Label especialLabel = new Label("Coluna:");
        especialLabel.setFont(FONTE_TEXTO);
        especialLabel.setTextFill(Color.WHITE);

        ObservableList<String> opcoesEspecial = FXCollections.observableArrayList("1","2","3","4","5","6","7");
        colunaEspecialCombo = new ComboBox<>(opcoesEspecial);
        colunaEspecialCombo.setValue("1");

        especialBox = new HBox(10);
        especialBox.getChildren().addAll(btEspecial, especialLabel, colunaEspecialCombo);
        especialBox.setAlignment(Pos.CENTER);
        especialBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        especialBox.setPadding(new Insets(10));


        btLogs = new Button("Logs");
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


        erro = new Label();
        erro.setVisible(false);
        erro.setFont(FONTE_TEXTO);
        erro.setTextFill(Color.RED);

        VBox menu = new VBox(10);
        menu.getChildren().addAll(dadosBox, fazJogadaBox, guardaJogoBox, creditosBox, especialBox,logsBox,sairBox,erro);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(menu);

    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
    }

    private void registarListeners(){
        btJogada.setOnAction(e -> {
            String value = colunaJogadaCombo.getValue();
            if(gameObserver.getTipoJogador() == TipoJogador.AI){
                gameObserver.jogaAI();
            }else{
                gameObserver.fazJogada(Integer.parseInt(value)-1);
            }
            switch(gameObserver.getEstadoErro()){
                case Ganhou, TabuleiroCheio -> {
                    //TODO: popup a dizer que ganhou e ficar à espera que diga okay ou assim
                }
                case ColunaCheia -> {
                    erro.setText("Coluna escolhida cheia!");
                    erro.setVisible(true);
                }
                case JogadaValida -> erro.setVisible(false);
            }
        });

        btGuardaJogo.setOnAction((e)->{
            String value;
            value =  guardaJogoNome.getText();
            if(value.length() < 1){
                guardaJogoNome.requestFocus();
                return;
            }
            if(gameObserver.guardaJogo(value+".dat")){
                erro.setText("Jogo Guardado com Sucesso!");
                erro.setVisible(true);
            }
            else{
                erro.setText("Erro a Guardar o Jogo!");
                erro.setVisible(true);
            }
        });

        btCreditos.setOnAction(e -> {
            String value = creditosText.getText().trim();
            if(value.length()<1){
                creditosText.requestFocus();
                erro.setText("Introduza um número nos créditos");
                erro.setVisible(true);
                return;
            }
            int numero;
            try{
                numero = Integer.parseInt(value);
            }catch (NumberFormatException ignored){
                creditosText.requestFocus();
                erro.setText("Introduza um número nos créditos");
                erro.setVisible(true);
                return;
            }
            if(numero>0)
                gameObserver.usaCreditos(numero);
            else{
                erro.setText("Numero tem que ser superior a 0!");
                erro.setVisible(true);
            }
            switch (gameObserver.getEstadoErro()){
                case SemCreditos -> {
                    erro.setText("Nao possui créditos suficientes!");
                    erro.setVisible(true);
                }
                case SemJogadas -> {
                    erro.setText("Nao existem jogadas para o número de créditos!");
                    erro.setVisible(true);
                }
                case SemErros -> erro.setVisible(false);
            }
        });

        btEspecial.setOnAction(e -> {
            String value = colunaEspecialCombo.getValue();
            gameObserver.jogaPecaEspecial(Integer.parseInt(value)-1);
            switch(gameObserver.getEstadoErro()){
                case SemEspecial -> {
                    erro.setText("Coluna escolhida cheia!");
                    erro.setVisible(true);
                }
                case JogadaValida -> erro.setVisible(false);
            }
        });


       // btHistorico.setOnAction((e)->gameObserver.historicoJogos());

        // btLogs.setOnAction((e)->gameObserver.yeet());

        btSair.setOnAction( (e)-> gameObserver.terminaJogo());
    }


    private void atualiza() {
        if(gameObserver.getStatus() ==  Situacao.Jogada){
            jogadorLabel.setText( "Jogador: " + gameObserver.getNomeJogadorVez() + "\tTipo de Jogador: " +  gameObserver.getTipoJogador().toString());
            creditosLabel.setText("Creditos: " + gameObserver.getCreditos());
            pecaEspecialLabel.setText("Peca Especial: " + gameObserver.getPecaEspecial());
            if(gameObserver.getTipoJogador() == TipoJogador.AI){
                colunaJogadaLabel.setVisible(false);
                colunaJogadaCombo.setVisible(false);
                creditosBox.setVisible(false);
                especialBox.setVisible(false);
            }
            else{
                colunaJogadaLabel.setVisible(true);
                colunaJogadaCombo.setVisible(true);
                creditosBox.setVisible(true);
                especialBox.setVisible(true);
            }
            this.setVisible(true);
        }
        else this.setVisible(false);
    }
}
