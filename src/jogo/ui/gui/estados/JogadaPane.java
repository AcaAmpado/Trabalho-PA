package jogo.ui.gui.estados;

import javafx.scene.layout.VBox;
import jogo.logica.GameObserver;

public class JogadaPane extends VBox {
    private final GameObserver gameObserver;

    public JogadaPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
    }
}
