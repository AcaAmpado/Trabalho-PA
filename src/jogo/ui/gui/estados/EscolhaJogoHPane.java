package jogo.ui.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;

import static jogo.ui.gui.Constantes.FONTE_TEXTO;

public class EscolhaJogoHPane extends VBox {

    private final GameObserver gameObserver;
    private VBox menu;
    final ToggleGroup group = new ToggleGroup();
    private Button btEscolheJogoH;
    private Button btSair;
    private Label erro;
    boolean carregado;
    private HBox boxEscolheJogoH;
    private HBox boxSair;

    public EscolhaJogoHPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registarListeners();

        atualiza();
    }

    private void registarListeners() {
        btEscolheJogoH.setOnAction( (e)-> {
            if (group.getSelectedToggle() != null) {
                gameObserver.replayHistorico((int) group.getSelectedToggle().getUserData());
                erro.setTextFill(Color.GREEN);
                erro.setVisible(true);
            }else{
                erro.setText("Selecione um jogo!");
                erro.setTextFill(Color.RED);
                erro.setVisible(true);
            }
        });

        btSair.setOnAction( (e)-> gameObserver.terminaJogo());
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
        gameObserver.addPropertyChangeListener("historico", evt -> carregaJogos());
    }

    private void criarVista() {

        carregado=false;

        btEscolheJogoH = new Button("Escolher Jogo");
        btEscolheJogoH.setFont(FONTE_TEXTO);

        boxEscolheJogoH = new HBox(10);
        boxEscolheJogoH.getChildren().addAll(btEscolheJogoH);
        boxEscolheJogoH.setAlignment(Pos.CENTER);
        boxEscolheJogoH.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        boxEscolheJogoH.setPadding(new Insets(10));

        btSair = new Button("Sair");
        btSair.setFont(FONTE_TEXTO);

        boxSair = new HBox(10);
        boxSair.getChildren().addAll(btSair);
        boxSair.setAlignment(Pos.CENTER);
        boxSair.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        boxSair.setPadding(new Insets(10));

        erro = new Label();
        erro.setVisible(false);
        erro.setFont(FONTE_TEXTO);


        menu = new VBox(10);
        menu.getChildren().addAll(erro,boxEscolheJogoH,boxSair);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(menu);

    }

    private void carregaJogos(){
        menu.getChildren().clear();
        menu.getChildren().addAll(erro,boxEscolheJogoH,boxSair);
        for(int i = 0; i < gameObserver.getHistoricoNum(); i++){


            RadioButton btjogo = new RadioButton();
            btjogo.setUserData(i);
            btjogo.setToggleGroup(group);

            Label jogo = new Label(gameObserver.getJogoHistorico(i));
            jogo.setFont(FONTE_TEXTO);
            jogo.setTextFill(Color.WHITE);

            HBox jogoBox = new HBox(10);
            jogoBox.getChildren().addAll(btjogo,jogo);
            jogoBox.setAlignment(Pos.CENTER);
            jogoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
            jogoBox.setPadding(new Insets(10));

            menu.getChildren().add(jogoBox);
            carregado = true;
        }
    }

    private void atualiza() {
        if(!carregado){
            carregaJogos();
        }

        this.setVisible(gameObserver.getStatus() ==  Situacao.Historico );
    }
}
