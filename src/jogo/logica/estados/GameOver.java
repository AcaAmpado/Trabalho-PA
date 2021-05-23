package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class GameOver extends EstadoAdapter{
    public GameOver(Jogo jogo) {
        super(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.GameOver;
    }
}