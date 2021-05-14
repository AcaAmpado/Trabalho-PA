package jogo.logica;

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
        jogo.setPlayer(player1);
        jogo.setPlayer(player2);
        if(jogo.comecaJogo()) {
            atual = atual.comecaJogo();
            return true;
        }
        return false;
    }

    public String getNomeJogadorVez(){return jogo.getNomeJogadorVez();}
    public TipoJogador getTipoJogador(){return jogo.getTipoJogador();}
    public Situacao getStatus(){return atual.getStatus();}


    public void jogaAI() { atual.jogaAI();}

    public boolean guardaJogo(String nomeSave) {
        return jogo.guardaJogo(nomeSave);
    }

    public StringBuilder getBoard() {
        return jogo.getBoard();
    }

    public boolean fazJogada(int coluna) {
        return jogo.fazJogada(coluna);
    }
}
