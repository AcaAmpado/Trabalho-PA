package jogo.logica.dados;

import java.io.Serial;
import java.io.Serializable;

public class Peca implements  Cloneable, Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    private final int jogador;

    public Peca(int jogador){
        this.jogador=jogador;
    }

    //_______________GET_______________

    public int getJogador(){
        return jogador;
    }

    //_______________OVERRIDES__________________

    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return null;
    }
    @Override
    public String toString() {
        return "\n "+jogador +" - ";
    }

}
