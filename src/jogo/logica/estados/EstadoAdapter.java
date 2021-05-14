package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public abstract class EstadoAdapter implements IEstado {
    Jogo jogo;

    public EstadoAdapter(Jogo jogo){this.jogo = jogo;}

    @Override
    public IEstado start() {
        return null;
    }

    @Override
    public IEstado carregaJogo() {
        return null;
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
    public void jogaAI() {
        //return;
    }


    @Override
    public IEstado comecaJogo() {
        return null;
    }
    @Override
    public IEstado selGameMode() {
        return null;
    }
}
