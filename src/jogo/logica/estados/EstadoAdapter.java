package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public abstract class EstadoAdapter implements IEstado {

    Jogo jogo;

    public EstadoAdapter(Jogo jogo){this.jogo = jogo;}

    @Override
    public IEstado start() {
        jogo.setupJogo();
        jogo.addLog("start()");
        return new GameMode(jogo);
    }

    @Override
    public IEstado usaCreditos(int numCr) {
        return this;
    }

    @Override
    public IEstado historicoJogos() {
        return this;
    }

    @Override
    public Situacao getStatus() {
        return null;
    }

    @Override
    public IEstado passaTurno() {
        return this;
    }

    @Override
    public IEstado aguardaPassarTurno() {
        return this;
    }

    @Override
    public IEstado startMiniGame() {
        return this;
    }

    @Override
    public IEstado terminaJogo() {
        return new GameOver(jogo);
    }

    @Override
    public IEstado jogaMinijogo(double numero) {
        return this;
    }

    @Override
    public IEstado jogaMinijogo(String input) {
        return this;
    }

    @Override
    public IEstado jogaPecaEspecial(int coluna) {
        return this;
    }

    @Override
    public IEstado fazJogada(int coluna) {
        return this;
    }

    @Override
    public IEstado comecaJogo(String player1, String player2) {
        return this;
    }

    @Override
    public IEstado passaTurnoHistorico() {
        return this;
    }

    @Override
    public IEstado semMinigame() {
        return this;
    }

    @Override
    public IEstado jogaAI() {
        return this;
    }

    @Override
    public IEstado selGameMode(int tipo) {
        return this;
    }
}
