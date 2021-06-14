package jogo.ui.gui.estados;

import javafx.scene.layout.VBox;
import jogo.logica.GameObserver;

public class PassarTurnoPane extends VBox {
    private final GameObserver gameObserver;

    public PassarTurnoPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;
    }

}
