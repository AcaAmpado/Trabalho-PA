package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class DecisaoMiniGame extends EstadoAdapter{

    public DecisaoMiniGame(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado startMiniGame() {
        return new MiniGame(jogo);
    }

    @Override
    public IEstado passaTurno() {
        return new Jogada(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.DecisaoMiniGame;
    }
}