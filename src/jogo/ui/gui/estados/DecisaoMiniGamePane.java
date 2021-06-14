package jogo.ui.gui.estados;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;

public class DecisaoMiniGamePane extends VBox {

    private final GameObserver gameObserver;

    public DecisaoMiniGamePane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
    }

    private void criarVista() {
        Label label = new Label("Temp para crl!");
        getChildren().addAll(label);
    }

    private void atualiza() {
        this.setVisible(gameObserver.getStatus() ==  Situacao.DecisaoMiniGame );
    }
}
