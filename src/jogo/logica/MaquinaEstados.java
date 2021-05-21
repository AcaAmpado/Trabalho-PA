package jogo.logica;

import jogo.logica.dados.Erro;
import jogo.logica.dados.TipoJogador;
import jogo.logica.estados.IEstado;
import jogo.logica.dados.Jogo;
import jogo.logica.estados.Inicio;

import java.util.ArrayList;

public class MaquinaEstados {

    Jogo jogo;
    IEstado atual;
    ArrayList<ArrayList<Jogo>> Historico;
    ArrayList<Jogo> tempJogo;
    
    public MaquinaEstados(){
        jogo = new Jogo();
        atual = new Inicio(jogo);
        Historico = new ArrayList<>();
    }

    public void start() {
        jogo.setupJogo();
        atual = atual.start();
    }

    public void selGameMode(int tipo){
        jogo.setTipo(tipo);
        atual = atual.selGameMode();
    }

    public void carregaJogo() {
        //TODO CarregaJogo
    }

    public void historicoJogos() {
        atual = atual.historicoJogos();
    }

    public boolean comecaJogo(String player1, String player2) {
        jogo.setPlayer(player1, 'A');
        jogo.setPlayer(player2, 'B');
        if(jogo.comecaJogo()) {
            atual = atual.comecaJogo(); //TODO adiciona GuardaState
            tempJogo = new ArrayList<>();
            guardaState();
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
            case Ganhou -> { //TODO adiciona GuardaState
                guardaState();
                Historico.add(tempJogo);
                atual = atual.acabaJogo();
                return Erro.Ganhou;
            }
            case ColunaCheia -> {
                return Erro.ColunaCheia;
            }
            case JogadaValida -> {//TODO adiciona GuardaState
                guardaState();
                atual=atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> { //TODO adiciona GuardaState
                guardaState();
                Historico.add(tempJogo);
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
                guardaState();
                atual= atual.aguardaPassarTurno();  //TODO adiciona GuardaState
                return Erro.JogadaValida;
            }
        }
        return Erro.Critico;
    }

    public Erro jogaAI() {
        switch (jogo.jogaAI()) {
            case Ganhou -> {
                guardaState();
                Historico.add(tempJogo);
                atual = atual.acabaJogo();
                return Erro.Ganhou;
            }
            case JogadaValida -> {
                guardaState();
                atual = atual.aguardaPassarTurno(); //TODO adiciona GuardaState
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> {
                guardaState();
                Historico.add(tempJogo);
                atual = atual.acabaJogo(); //TODO adiciona GuardaState
                return Erro.TabuleiroCheio;
            }
        }
        return Erro.Critico;
    }
    
    private void guardaState(){
        tempJogo.add( (Jogo) jogo.clone());
        jogo.resetMinijogo();
        //System.out.println( tempJogo.get(tempJogo.size()-1).toString());
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
        jogo.resetBonus(-2);
    }

    public int getMiniJogo() {
        return jogo.getMiniJogo();
    }

    public int jogaMinijogo() {
        int pontos;
        pontos=jogo.jogaMinijogo();
        if(pontos>=5){
            jogo.addPecaEspecial();
            atual=atual.continuaJogada();
        }
        else{
            atual=atual.passaTurno();
        }
        jogo.setMinigame();
        jogo.resetBonus(pontos);

        return pontos ;
    }

    public int getPecaEspecial() {
        return jogo.getPecaEspecial();
    }


    public String getHistorico() {
        if(Historico.size()==0) {
            atual=atual.start();
            return Erro.SemJogosHist.toString();
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < getHistoricoNum(); i++){
            sb.append("\nJogo ").append(i+1).append(":").append(getJogoHistorico(i));
        }
        return sb.toString();
    }

    public int getHistoricoNum() {
        return Historico.size();
    }

    public String getJogoHistorico(int i) {
        return String.valueOf(Historico.get(i).get(0).toString());
    }

    public void replayHistorico(int game) {
        jogo.innitReplay(Historico.get(game));
        jogo.replayHistorico();
        atual = atual.aguardaPassarTurno();
    }

    public boolean isHistorico() {
        return jogo.getHistorico();
    }

    public void passaTurnoHistorico() {
        if(jogo.replayHistorico() == Erro.FimJogo)
            atual=atual.acabaJogo();
    }

    public Erro isMinigame() {
        return jogo.isMinigame();
    }
}
