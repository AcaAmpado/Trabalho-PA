package jogo.ui.gui.estados;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;


import static jogo.ui.gui.Constantes.*;

public class InicioPane extends VBox {

    private final GameObserver gameObserver;
    private Label rules;
    Button btContinuar;
    Button btSair;

    public InicioPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registarListeners();

        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
    }

    private void criarVista() {

        setPrefSize(TAM_X_MENU,TAM_Y_MENU);
        setMinSize(TAM_X_MENU,TAM_Y_MENU);

        rules = new Label();
        rules.setFont(FONTE_TEXTO);
        rules.setPadding(new Insets(30));
        rules.setTextFill(Color.WHITE);
        rules.setWrapText(true);
        rules.setTextAlignment(TextAlignment.JUSTIFY);


        btContinuar = new Button("Continuar");
        btContinuar.setFont(FONTE_TEXTO);

        btSair = new Button("Sair");
        btSair.setFont(FONTE_TEXTO);

        HBox buttonHBox = new HBox(10);
        buttonHBox.getChildren().addAll(btContinuar,btSair);
        buttonHBox.setPadding(new Insets(10));
        buttonHBox.setAlignment(Pos.CENTER);

        getChildren().addAll(rules,buttonHBox);
        setSpacing(10);
    }

    private void registarListeners(){
        btContinuar.setOnAction( (e) -> gameObserver.start() );

        btSair.setOnAction( (e) -> Platform.exit() );
    }

    private void atualiza() {
        rules.setText(gameObserver.getRegras());
        this.setVisible(gameObserver.getStatus() ==  Situacao.Inicio );
    }
}
