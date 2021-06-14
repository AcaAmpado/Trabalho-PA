package jogo.ui.gui.estados;

import javafx.scene.layout.VBox;
import jogo.logica.GameObserver;

public class GameOverPane extends VBox {
    private final GameObserver gameObserver;

    public GameOverPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
    }
}
