package jogo.logica.dados;

import java.util.Arrays;

public class Peca implements  Cloneable {
    private final int jogador;

    public Peca(int jogador){
        this.jogador=jogador;
    }

    public int getJogador(){
        return jogador;
    }

    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Rebentei na Peca!");
        }
        return null;
    }
    @Override
    public String toString() {
        return "\n "+jogador +" - ";
    }

}
