package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import jogo.logica.dados.Jogo;

public class PassarTurno extends EstadoAdapter{

    public PassarTurno(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado passaTurno() {
        jogo.setVezJogador();
        jogo.atualizaBonus();
        if(jogo.checkMiniGame()){
            return new DecisaoMiniGame(jogo);
        }
        else{
            return new Jogada(jogo);
        }
    }

    @Override
    public IEstado passaTurnoHistorico() {
        jogo.setEstadoErro(jogo.replayHistorico());
        if(jogo.getEstadoErro() == Erro.FimJogo){
            return new GameOver(jogo);
        }
        return this;
    }

    @Override
    public Situacao getStatus() {
        return Situacao.PassarTurno;
    }
}
