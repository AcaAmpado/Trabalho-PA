package jogo.logica;

import jogo.logica.dados.Erro;
import jogo.logica.dados.Jogo;
import jogo.logica.estados.IEstado;
import jogo.logica.estados.Inicio;
import jogo.logica.estados.Jogada;
import jogo.logica.estados.PassarTurno;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

    @SuppressWarnings("unchecked")
    public void carregaJogo(String value) { //TODO: procurar maneira de nao ter o warning
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(value))) {
            jogo = (Jogo) in.readObject();
            atual = switchState((Situacao) in.readObject());
            jogo.setJogadas((ArrayList<Jogo>) in.readObject());
        } catch (IOException | ClassNotFoundException ignored) {
            jogo.setEstadoErro(Erro.Critico);
        }
    }
    private IEstado switchState(Situacao readObject) {
        switch (readObject){
            case Jogada -> {
                return new Jogada(jogo);
            }
            case PassarTurno -> {
                return new PassarTurno(jogo);
            }
        }
        return null;
    }

    public Erro getEstadoErro() {
        return jogo.getEstadoErro();
    }

    public void historicoJogos() {
        atual=atual.historicoJogos();
    }
}
