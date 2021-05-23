package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class EscolhaJogoH extends EstadoAdapter{

    public EscolhaJogoH(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado aguardaPassarTurno() {
        return new PassarTurno(jogo);
    }

    @Override
    public IEstado start() {
        return new GameMode(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.Historico;
    }
}