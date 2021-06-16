package jogo.logica.estados;

import jogo.logica.Situacao;

public interface IEstado {

    Situacao getStatus();

    IEstado start();

    IEstado comecaJogo(String player1, String player2);

    IEstado selGameMode(int tipo);

    IEstado historicoJogos();

    IEstado aguardaPassarTurno();

    IEstado passaTurno();

    IEstado terminaJogo();

    IEstado startMiniGame();

    IEstado usaCreditos(int numCr);

    IEstado fazJogada(int coluna);

    IEstado jogaPecaEspecial(int coluna);

    IEstado jogaAI();

    IEstado passaTurnoHistorico();

    IEstado semMinigame();

    IEstado jogaMinijogo(double numero);

    IEstado jogaMinijogo(String input);
}
