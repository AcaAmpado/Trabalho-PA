package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public abstract class EstadoAdapter implements IEstado {
    Jogo jogo;

    public EstadoAdapter(Jogo jogo){this.jogo = jogo;}

    @Override
    public IEstado start() {
        jogo.setupJogo();
        return new GameMode(jogo);
    }

    @Override
    public IEstado historicoJogos() {
        return null;
    }

    @Override
    public Situacao getStatus() {
        return null;
    }

    @Override
    public IEstado passaTurno() {
        return null;
    }

    @Override
    public IEstado aguardaPassarTurno() {
        return null;
    }

    @Override
    public IEstado startMiniGame() {
        return null;
    }

    @Override
    public IEstado decideMiniGame() {
        return null;
    }

    @Override
    public IEstado terminaJogo() {
        return new GameOver(jogo);
    }

    @Override
    public IEstado comecaJogo() {
        return null;
    }

    @Override
    public IEstado continuaJogada() {
        return null;
    }

    @Override
    public IEstado selGameMode() {
        return null;
    }
}
