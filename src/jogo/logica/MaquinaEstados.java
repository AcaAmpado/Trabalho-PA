package jogo.logica;

import jogo.logica.estados.GameMode;
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

    public void comecaJogo(String player1, String player2) {
        jogo.setPlayer1(player1);
        jogo.setPlayer2(player2);
        jogo.comecaJogo();
        atual = atual.Jogada();
    }

    public Situacao getStatus(){
        return atual.getStatus();
    }



}
