package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class GameMode extends EstadoAdapter{

    public GameMode(Jogo jogo) {
        super(jogo);
        jogo.addLog("GameMode");
    }

    @Override
    public IEstado selGameMode(int tipo) {
        jogo.addLog("selGameMode()");
        jogo.setTipo(tipo);
        return new NamePlayers(jogo);
    }

    @Override
    public IEstado historicoJogos() {
        return new EscolhaJogoH(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.GameMode;
    }


}
