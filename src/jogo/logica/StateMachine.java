package jogo.logica;

import jogo.logica.dados.Jogo;
import jogo.logica.estados.IEstado;
import jogo.logica.estados.Inicio;

import java.util.ArrayList;

public class StateMachine {

    private Jogo jogo;
    private IEstado atual;

    public StateMachine(){
        jogo = new Jogo();
        atual = new Inicio(jogo);
    }



    public ArrayList<ArrayList<String>> getTabuleiro() {
        return jogo.getTabuleiro();
    }

    public String getRegras() {
        return jogo.getRegras();
    }

    public Situacao getStatus() {
        return atual.getStatus();
    }

    public void terminar() {
        atual = atual.terminaJogo();
    }

    public void start() {
        atual = atual.start();
    }

    public void selGameMode(int tipo) {
       atual = atual.selGameMode(tipo);
    }
}
