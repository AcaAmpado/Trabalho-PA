package jogo.ui.gui.estados;

import javafx.scene.layout.VBox;
import jogo.logica.GameObserver;

public class NamePlayersPane extends VBox {
    private final GameObserver gameObserver;

    public NamePlayersPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
    }
}
