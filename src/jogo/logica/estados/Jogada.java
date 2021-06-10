package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import jogo.logica.dados.Jogo;

import java.util.ArrayList;

public class Jogada extends EstadoAdapter{
    public Jogada(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado usaCreditos(int numCr){
        if(jogo.getCreditos() < numCr) {
            jogo.setEstadoErro(Erro.SemCreditos);
            return this;
        }
        /*if(jogo.getTurnoCreditos() < numCr){
            jogo.setEstadoErro(Erro.VoltarAntesCreditos);
            return this;
        }*/
        Jogo temporario;
        try{
            temporario = (Jogo) jogo.getJogadas().get(jogo.getJogadas().size()-1-numCr).clone();
        }catch (IndexOutOfBoundsException ignored){
            jogo.setEstadoErro(Erro.SemJogadas);
            return this;
        }
        temporario.setCreditos( jogo.getCreditos()-numCr, jogo.getVezJogador());
        temporario.resetBonus(6);
        temporario.setPecaEspecial(0,jogo.getPecaEspecial(0));
        temporario.setPecaEspecial(1,jogo.getPecaEspecial(1));
        //TODO: copiar logs e sobrepor aos carregados
        jogo = temporario;
        if(jogo.getJogadas().size()-1-numCr!=0)
            jogo.setVezJogador();
        //jogo.resetTurnoCreditos();
        jogo.setEstadoErro(Erro.SemErros);
        return new Jogada(jogo);
    }

    @Override
    public IEstado fazJogada(int coluna) {
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
        jogo.setEstadoErro(jogo.jogaPecaEspecial(coluna));
        if (jogo.getEstadoErro() == Erro.JogadaValida) {
            jogo.guardaJogada();
            return new PassarTurno(jogo);
        }
        return this;
    }

    @Override
    public IEstado jogaAI() {
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
