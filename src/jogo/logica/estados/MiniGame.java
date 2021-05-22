package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class MiniGame extends EstadoAdapter{

    public MiniGame(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado aguardaPassarTurno() {
        return new PassarTurno(jogo);
    }

    @Override
    public IEstado continuaJogada() {
        return new Jogada(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.MiniGame;
    }

}

