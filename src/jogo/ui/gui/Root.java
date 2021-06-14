package jogo.ui.gui;

import javafx.scene.layout.BorderPane;
import jogo.logica.GameObserver;

public class Root extends BorderPane {

    private GameObserver gameObserver;

    private PrincipalPane principalPane;

    public Root(GameObserver gameObserver){
        this.gameObserver=gameObserver;
        criarVistaCentral();
        registarObserver();
        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
    }

    private void criarVistaCentral(){

        principalPane = new PrincipalPane(gameObserver);
        setCenter(principalPane);
    }


    public void atualiza(){

    }

}
