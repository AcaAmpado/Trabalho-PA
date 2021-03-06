package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import jogo.logica.dados.Jogo;

public class PassarTurno extends EstadoAdapter{

    public PassarTurno(Jogo jogo) {
        super(jogo);
        jogo.addLog("PassarTurno");
    }

    @Override
    public IEstado passaTurno() {
        jogo.addLog("passaTurno()");
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
    public IEstado usaCreditos(int numCr){
        jogo.addLog("usaCreditos()");
        if(jogo.getCreditos() < numCr) {
            jogo.setEstadoErro(Erro.SemCreditos);
            return this;
        }
        if(jogo.getJogada()-numCr<0){
            jogo.setEstadoErro(Erro.SemJogadas);
            return this;
        }
        Jogo temporario;
        try{
            temporario = (Jogo) jogo.getJogadas().get(jogo.getJogadas().size()-1-numCr).clone();
        }catch (IndexOutOfBoundsException e){
            jogo.setEstadoErro(Erro.SemJogadas);
            return this;
        }
        temporario.setCreditos( jogo.getCreditos()-numCr, jogo.getVezJogador());
        temporario.setCreditos( jogo.getCreditos((jogo.getVezJogador()+1)%2), (jogo.getVezJogador()+1)%2);
        temporario.resetBonus(6);
        temporario.setPecaEspecial(0,jogo.getPecaEspecial(0));
        temporario.setPecaEspecial(1,jogo.getPecaEspecial(1));
        //TODO: copiar logs e sobrepor aos carregados
        if(jogo.getJogadas().size()-1-numCr!=0)
            temporario.setVezJogador();
        temporario.setJogadas(jogo.getJogadas());
        jogo.copiaValues(temporario);
        jogo.setEstadoErro(Erro.SemErros);
        jogo.setJogada(1);
        return new Jogada(jogo);
    }

    @Override
    public IEstado passaTurnoHistorico() {
        jogo.addLog("passaTurnoHistorico()");
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
