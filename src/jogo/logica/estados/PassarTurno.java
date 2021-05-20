package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class PassarTurno extends EstadoAdapter{

    public PassarTurno(Jogo jogo) {
        super(jogo);
    }


    @Override
    public IEstado decideMiniGame() {
        return new DecisaoMiniGame(jogo);
    }

    @Override
    public IEstado acabaJogo() {
        return new GameOver(jogo);
    }

    @Override
    public IEstado passaTurno() {
        return new Jogada(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.PassarTurno;
    }
}
