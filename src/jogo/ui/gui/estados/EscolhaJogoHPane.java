package jogo.ui.gui.estados;

import javafx.scene.layout.VBox;
import jogo.logica.GameObserver;

public class EscolhaJogoHPane extends VBox {

    private final GameObserver gameObserver;

    public EscolhaJogoHPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
    }
}
