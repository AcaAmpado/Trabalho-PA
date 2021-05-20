package jogo.logica.dados;

import java.util.Arrays;

public class Peca implements  Cloneable {
    private final Jogador jogador;

    public Peca(Jogador jogador){
        this.jogador=jogador;
    }

    public char getSymbol(){
        return jogador.getSymbol();
    }


    @Override
    public String toString() {
        return "\n "+jogador.toString()+" - ";
    }

}
