package jogo.logica.dados;

import java.util.ArrayList;
import java.util.Arrays;

import jogo.logica.dados.Jogador;

public class Peca {
    private Jogador jogador;
    private int [] coordenadas;
    private int symbol;

    public Peca(Jogador jogador, int numJog, int [] coordenadas){
        this.jogador=jogador;
        this.coordenadas=coordenadas;
        this.symbol=numJog;
    }

    public int getSymbol(){
        return symbol;
    }

    @Override
    public String toString() {
        return "\n "+jogador.toString()+" - "+ Arrays.toString(coordenadas);
    }

}
