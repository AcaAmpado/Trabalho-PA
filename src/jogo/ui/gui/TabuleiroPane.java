package jogo.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.logica.GameObserver;
import jogo.logica.dados.Jogo;

import java.util.ArrayList;

import static jogo.ui.gui.Constantes.*;

public class TabuleiroPane extends GridPane {

    private final GameObserver gameObserver;
    private GridPane gridPane;

    public TabuleiroPane(GameObserver gameObserver){
        this.gameObserver = gameObserver;

        criarVista();

        registarObserver();

        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("yeet", evt -> atualiza());
    }

    private void criarVista() {
        //Escolher Tamanho da Janela
        setPrefSize(TAM_X_TAB, TAM_Y_TAB);
        setMinSize(TAM_X_TAB, TAM_Y_TAB);

        //Dar Cor ao background e dar lhe uma border
        setBackground(new Background(new BackgroundFill(Color.GREEN,null, null) ));
        setBorder(new Border( new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID,null,new BorderWidths(5))));

        this.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        //gridPane.setGridLinesVisible(true);
        getChildren().addAll(gridPane);
    }

    private void atualiza() {
        ArrayList<ArrayList<String>> tabuleiro;
        tabuleiro = gameObserver.getTabuleiro();

        Label colunasLabel;

        int colunas = 0, linhas = 0;

        for(ArrayList<String> coluna : tabuleiro){
            for(String peca: coluna){
                if(linhas >= Jogo.ALTURA ){

                    linhas=0;
                    colunas++;

                }
                Circle circle = new Circle();
                circle.setRadius(DIM_BOLA);
                if(peca.equals(String.valueOf('A')))
                    circle.setFill(Color.BLUE);
                else if (peca.equals(String.valueOf('B')))
                    circle.setFill(Color.RED);
                else
                    circle.setFill(Color.WHITE);
                gridPane.add(circle,colunas,linhas);
                linhas++;
            }
            colunasLabel = new Label(String.valueOf(colunas+1));
            colunasLabel.setTextFill(Color.WHITE);
            colunasLabel.setFont(FONTE);
            colunasLabel.setPadding(new Insets(10, 20, 10, 20));
            colunasLabel.setAlignment(Pos.CENTER);
            gridPane.add(colunasLabel, colunas,Jogo.ALTURA+1);
        }
    }
}
