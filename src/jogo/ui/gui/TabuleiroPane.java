package jogo.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.logica.GameObserver;
import jogo.logica.dados.Jogo;
import com.sun.javafx.scene.canvas.CanvasHelper;
import com.sun.javafx.scene.canvas.CanvasHelper.CanvasAccessor;

import java.util.ArrayList;

import static jogo.ui.gui.Constantes.*;

public class TabuleiroPane extends GridPane {

    private GameObserver gameObserver;
    GridPane gridPane = new GridPane();

    public TabuleiroPane(GameObserver gameObserver){
        this.gameObserver = gameObserver;

        criarVista();

        atualiza();
    }



    private void criarVista() {
        //Escolher Tamanho da Janela
        setPrefSize(TAM_X_TAB, TAM_Y_TAB);
        setMinSize(TAM_X_TAB, TAM_Y_TAB);
        //Dar Cor ao background e dar lhe uma border
        setBackground(new Background(new BackgroundFill(Color.GREEN,null, null) ));
        setBorder(new Border( new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID,null,new BorderWidths(5))));

        this.setAlignment(Pos.CENTER);


    }

    private void atualiza() {
        ArrayList<ArrayList<String>> tabuleiro;
        tabuleiro = gameObserver.getTabuleiro();

        int colunas = 0, linhas = 0;

        for(ArrayList<String> coluna : tabuleiro){
            for(String peca: coluna){
                if(linhas >= Jogo.ALTURA ){
                    linhas=0;
                    colunas++;
                }
                Circle circle = new Circle();
                circle.setRadius(DIM_X_BOLA);
                if(peca.equals(String.valueOf('A')))
                    circle.setFill(Color.YELLOW);
                else if (peca.equals(String.valueOf('B')))
                    circle.setFill(Color.RED);
                else
                    circle.setFill(Color.WHITE);
                gridPane.add(circle,linhas,colunas);
                linhas++;
            }
        }
    }
}
