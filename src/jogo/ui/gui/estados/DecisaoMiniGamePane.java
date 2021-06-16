package jogo.ui.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;

import static jogo.ui.gui.Constantes.*;

public class DecisaoMiniGamePane extends VBox {

    private final GameObserver gameObserver;

    private Circle corJogador;
    private Label jogadorLabel;
    private Label creditosLabel;
    private Label pecaEspecialLabel;
    private Button btJogar;
    private Button btNaoJogar;


    public DecisaoMiniGamePane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registaListeners();

        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
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


        btJogar = new Button("Jogar Minijogo");
        btJogar.setFont(FONTE_TEXTO);

        HBox boxJogar = new HBox(10);
        boxJogar.getChildren().addAll(btJogar);
        boxJogar.setAlignment(Pos.CENTER);
        boxJogar.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        boxJogar.setPadding(new Insets(10));


        btNaoJogar = new Button("Não Jogar Minijogo");
        btNaoJogar.setFont(FONTE_TEXTO);

        HBox boxNaoJogar = new HBox(10);
        boxNaoJogar.getChildren().addAll(btNaoJogar);
        boxNaoJogar.setAlignment(Pos.CENTER);
        boxNaoJogar.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        boxNaoJogar.setPadding(new Insets(10));

        Label mensagemLabel= new Label("Pretende jogar um minijogo para ganhar uma peça especial?");
        mensagemLabel.setFont(FONTE_TEXTO);
        mensagemLabel.setTextFill(Color.WHITE);
        mensagemLabel.setWrapText(true);

        HBox mensagemBox = new HBox(10);
        mensagemBox.getChildren().addAll(mensagemLabel);
        mensagemBox.setAlignment(Pos.CENTER);
        mensagemBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        mensagemBox.setPadding(new Insets(10));


        VBox menu = new VBox(10);
        menu.getChildren().addAll(dadosBox, mensagemBox ,boxJogar,boxNaoJogar);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(menu);

    }

    private void registaListeners(){

        btJogar.setOnAction((e)-> gameObserver.startMinigame());

        btNaoJogar.setOnAction((e)-> gameObserver.semMinigame());

    }

    private void atualiza() {
        if(gameObserver.getStatus() ==  Situacao.DecisaoMiniGame){
            if(gameObserver.getSymbol()=='A')
                corJogador.setFill(Color.BLUE);
            else if (gameObserver.getSymbol()=='B')
                corJogador.setFill(Color.RED);
            else
                corJogador.setFill(Color.WHITE);
            jogadorLabel.setText( "Jogador: " + gameObserver.getNomeJogadorVez() + "\tTipo de Jogador: " +  gameObserver.getTipoJogador().toString());
            creditosLabel.setText("Creditos: " + gameObserver.getCreditos());
            pecaEspecialLabel.setText("Peca Especial: " + gameObserver.getPecaEspecial());
        }

        this.setVisible(gameObserver.getStatus() ==  Situacao.DecisaoMiniGame );
    }
}
