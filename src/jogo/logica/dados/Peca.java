package jogo.logica.dados;

import java.util.ArrayList;
import java.util.Arrays;

public class Peca {
    private Jogador jogador;
    private int [] coordenadas;
    private char symbol;

    public Peca(jogo.logica.dados.Jogador jogador, int [] coordenadas){
        this.jogador=jogador;
        this.coordenadas=coordenadas;
        this.symbol=jogador.getNome().charAt(0);//TODO o simbolo pode ser igual atm!!!
    }

    public char getSymbol(){
        return symbol;
    }

    @Override
    public String toString() {
        return "\n "+jogador.toString()+" - "+ Arrays.toString(coordenadas);
    }

}
