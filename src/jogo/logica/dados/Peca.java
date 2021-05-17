package jogo.logica.dados;

import java.util.ArrayList;
import java.util.Arrays;

import jogo.logica.dados.Jogador;

public class Peca {
    private final Jogador jogador;
    private final int [] coordenadas;

    public Peca(Jogador jogador, int [] coordenadas){
        this.jogador=jogador;
        this.coordenadas=coordenadas;
    }

    public char getSymbol(){
        return jogador.getSymbol();
    }

    @Override
    public String toString() {
        return "\n "+jogador.toString()+" - "+ Arrays.toString(coordenadas);
    }

}
