package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class EscolhaJogoH extends EstadoAdapter{

    public EscolhaJogoH(Jogo jogo) {
        super(jogo);
        jogo.addLog("EscolhaJogoH");
    }

    @Override
    public IEstado aguardaPassarTurno() {
        jogo.replayHistorico();
        jogo.addLog("replayHistorico()");
        return new PassarTurno(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.Historico;
    }
}