package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import jogo.logica.dados.Jogo;


public class Jogada extends EstadoAdapter{
    public Jogada(Jogo jogo) {
        super(jogo);
        jogo.addLog("Jogada");
    }

    @Override
    public IEstado usaCreditos(int numCr){

        if(jogo.getCreditos() < numCr) {
            jogo.addLog("usaCreditos() = semCreditos");
            jogo.setEstadoErro(Erro.SemCreditos);
            return this;
        }
        if(jogo.getJogada()-numCr<0){
            jogo.addLog("usaCreditos() = semJogadas");
            jogo.setEstadoErro(Erro.SemJogadas);
            return this;
        }
        Jogo temporario;
        try{
            temporario = (Jogo) jogo.getJogadas().get(jogo.getJogadas().size()-1-numCr).clone();
        }catch (IndexOutOfBoundsException e){
            jogo.addLog("usaCreditos() = semJogadas");
            jogo.setEstadoErro(Erro.SemJogadas);
            return this;
        }
        temporario.setCreditos( jogo.getCreditos()-numCr, jogo.getVezJogador());
        temporario.setCreditos( jogo.getCreditos((jogo.getVezJogador()+1)%2), (jogo.getVezJogador()+1)%2);
        temporario.resetBonus(6);
        temporario.setPecaEspecial(0,jogo.getPecaEspecial(0));
        temporario.setPecaEspecial(1,jogo.getPecaEspecial(1));
        temporario.setLogME(jogo.getLogME());
        if(jogo.getJogadas().size()-1-numCr!=0)
            temporario.setVezJogador();
        temporario.setJogadas(jogo.getJogadas());
        jogo.copiaValues(temporario);
        jogo.setEstadoErro(Erro.SemErros);
        jogo.setJogada(1);
        jogo.addLog("usaCreditos() = sem Erros");
        return new Jogada(jogo);
    }

    @Override
    public IEstado fazJogada(int coluna) {
        jogo.addLog("fazJogada()");
        jogo.setEstadoErro(jogo.fazJogada(coluna));
        switch(jogo.getEstadoErro()){
            case Ganhou, TabuleiroCheio -> {
                jogo.guardaJogada();
                return new GameOver(jogo);
            }
            case ColunaCheia -> {
                return this;
            }
            case JogadaValida -> {
                jogo.guardaJogada();
                return new PassarTurno(jogo);
            }
        }
        return this;
    }

    @Override
    public IEstado jogaPecaEspecial(int coluna) {
        jogo.addLog("jogaPecaEspecial()");
        jogo.setEstadoErro(jogo.jogaPecaEspecial(coluna));
        if (jogo.getEstadoErro() == Erro.JogadaValida) {
            jogo.guardaJogada();
            return new PassarTurno(jogo);
        }
        return this;
    }

    @Override
    public IEstado jogaAI() {
        jogo.addLog("jogaAI()");
        jogo.setEstadoErro(jogo.jogaAI());
        switch (jogo.getEstadoErro()) {
            case Ganhou, TabuleiroCheio-> {
                jogo.guardaJogada();
                return new GameOver(jogo);
            }
            case JogadaValida -> {
                jogo.guardaJogada();
                return new PassarTurno(jogo);
            }
        }
        return this;
    }

    @Override
    public Situacao getStatus() {
        return Situacao.Jogada;
    }

}
