package jogo.ui.gui.estados;

import javafx.scene.layout.VBox;
import jogo.logica.GameObserver;

public class DecisaoMiniGamePane extends VBox {

    private final GameObserver gameObserver;

    public DecisaoMiniGamePane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
    }
}
