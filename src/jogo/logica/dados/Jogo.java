package jogo.logica.dados;

import java.util.ArrayList;

public class Jogo {
    public static final int NUMCREDITOS = 5;
    public static final int RONDASPBONUS = 4;
    public static final int LARGURA = 7;
    public static final int ALTURA = 6;
    private int tipo;
    private String player1;
    private String player2;

    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    public void setPlayer1(String nome){
        this.player1=nome;
    }
    public void setPlayer2(String nome){
        this.player2=nome;
    }

    public void comecaJogo() {

    }
}
