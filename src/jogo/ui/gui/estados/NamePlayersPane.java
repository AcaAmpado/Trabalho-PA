package jogo.ui.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;
import jogo.logica.dados.Erro;

import static jogo.ui.gui.Constantes.*;

public class NamePlayersPane extends VBox {
    private final GameObserver gameObserver;

    TextField nomeJogador1Text;
    TextField nomeJogador2Text;
    Label erro;
    Button btComecaJogo;

    public NamePlayersPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registarListener();

        atualiza();
    }

    private void criarVista() {

        setPrefSize(TAM_X_MENU,TAM_Y_MENU);
        setMinSize(TAM_X_MENU,TAM_Y_MENU);

        Label nomeJogador1Label = new Label("Jogador 1:");
        nomeJogador1Label.setFont(FONTE_TEXTO);
        nomeJogador1Label.setTextFill(Color.WHITE);

        nomeJogador1Text = new TextField();
        nomeJogador1Text.setPromptText("Nome do Jogador 1");
        nomeJogador1Text.setFont(FONTE_TEXTO);

        HBox jog1Box = new HBox(10);
        jog1Box.getChildren().addAll(nomeJogador1Label, nomeJogador1Text);
        jog1Box.setAlignment(Pos.CENTER);
        jog1Box.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        jog1Box.setPadding(new Insets(10));

        Label nomeJogador2Label = new Label("Jogador 2:");
        nomeJogador2Label.setFont(FONTE_TEXTO);
        nomeJogador2Label.setTextFill(Color.WHITE);

        nomeJogador2Text = new TextField();
        nomeJogador2Text.setPromptText("Nome do Jogador 2");
        nomeJogador2Text.setFont(FONTE_TEXTO);

        HBox jog2Box = new HBox(10);
        jog2Box.getChildren().addAll(nomeJogador2Label, nomeJogador2Text);
        jog2Box.setAlignment(Pos.CENTER);
        jog2Box.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        jog2Box.setPadding(new Insets(10));

        btComecaJogo = new Button("Começa Jogo");
        btComecaJogo.setFont(FONTE_TEXTO);

        erro = new Label();
        erro.setVisible(false);
        erro.setFont(FONTE_TEXTO);
        erro.setTextFill(Color.RED);

        VBox menu = new VBox(10);
        menu.getChildren().addAll(jog1Box, jog2Box, btComecaJogo, erro);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(menu);
        setSpacing(20);
    }

    private void registarListener() {
        btComecaJogo.setOnAction( e ->{
            String jog1,jog2;
            jog1 =  nomeJogador1Text.getText();
            if(jog1.length() < 1){
                nomeJogador1Text.requestFocus();
                return;
            }
            jog2 =  nomeJogador2Text.getText();
            if(jog2.length() < 1){
                nomeJogador2Text.requestFocus();
                return;
            }
            if(jog1.equals(jog2))
            {
                erro.setText("Os nomes não podem ser iguais");
                erro.setVisible(true);
                return;
            }
            gameObserver.comecaJogo(jog1,jog2);
        });
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
    }

    private void atualiza() {
        this.setVisible(gameObserver.getStatus() ==  Situacao.NamePlayers );
    }
}
