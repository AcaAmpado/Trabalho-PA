package jogo.ui.gui;

import javafx.scene.layout.BorderPane;
import jogo.logica.GameObserver;

public class Root extends BorderPane {

    private final GameObserver gameObserver;

    private PrincipalPane principalPane;

    public Root(GameObserver gameObserver){
        this.gameObserver=gameObserver;
        criarVista();
        registarObserver();
        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
    }

    private void criarVista(){
        principalPane = new PrincipalPane(gameObserver);
        setCenter(principalPane);
    }


    public void atualiza(){

    }

}
