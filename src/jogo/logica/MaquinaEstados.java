package jogo.logica;

import jogo.logica.dados.Erro;
import jogo.logica.dados.TipoJogador;
import jogo.logica.estados.IEstado;
import jogo.logica.dados.Jogo;
import jogo.logica.estados.Inicio;

public class MaquinaEstados {

    Jogo jogo;
    IEstado atual;

    public MaquinaEstados(){
        jogo = new Jogo();
        atual = new Inicio(jogo);
    }

    public void start() {
        atual = atual.start();
    }

    public void selGameMode(int tipo){
        jogo.setTipo(tipo);
        atual = atual.selGameMode();
    }

    public void carregaJogo() {
        atual = atual.carregaJogo();
    }

    public void historicoJogos() {
        atual = atual.historicoJogos();
    }

    public boolean comecaJogo(String player1, String player2) {
        jogo.setPlayer(player1, 'A');
        jogo.setPlayer(player2, 'B');
        if(jogo.comecaJogo()) {
            atual = atual.comecaJogo();
            return true;
        }
        return false;
    }

    public String getNomeJogadorVez(){
        return jogo.getNomeJogadorVez();
    }

    public TipoJogador getTipoJogador(){
        return jogo.getTipoJogador();
    }

    public Situacao getStatus(){
        return atual.getStatus();
    }

    public boolean guardaJogo(String nomeSave) {
        return jogo.guardaJogo(nomeSave);
    }

    public StringBuilder getBoard() {
        return jogo.getBoard();
    }

    public Erro fazJogada(int coluna) {
        switch(jogo.fazJogada(coluna)){
            case Ganhou -> {
                atual = atual.acabaJogo();
                return Erro.Ganhou;
            }
            case ColunaCheia -> {
                atual=atual.aguardaPassarTurno();
                return Erro.ColunaCheia;
            }
            case JogadaValida -> {
                atual=atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> {
                atual=atual.acabaJogo();
                return Erro.TabuleiroCheio;
            }
        }
        return Erro.Critico;
    }
    public Erro jogaPecaEspecial(int coluna) {
        switch(jogo.jogaPecaEspecial(coluna)){
            case SemEspecial -> {
                return Erro.SemEspecial;
            }
            case JogadaValida -> {
                atual= atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
        }
        return Erro.Critico;
    }

    public Erro jogaAI() {
        switch (jogo.jogaAI()) {
            case Ganhou -> {
                atual = atual.acabaJogo();
                return Erro.Ganhou;
            }
            case JogadaValida -> {
                atual = atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> {
                atual = atual.acabaJogo();
                return Erro.TabuleiroCheio;
            }
        }
        return Erro.Critico;
    }

    public void passaTurno() {
        jogo.setVezJogador();
        jogo.atualizaBonus();
        if(jogo.checkMiniGame())
            atual=atual.decideMiniGame();
        else
            atual=atual.passaTurno();
    }

    public void terminaJogo() {
        atual=atual.terminaJogo();
    }

    public void startMiniGame() {
        atual=atual.startMiniGame();
    }

    public void semMiniGame() {
        atual=atual.passaTurno();
    }
}
