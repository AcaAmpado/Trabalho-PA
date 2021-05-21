package jogo.logica.estados;

import jogo.logica.Situacao;

public interface IEstado {

    Situacao getStatus();

    IEstado start();

    IEstado comecaJogo();

    IEstado selGameMode();

    IEstado historicoJogos();

    IEstado acabaJogo();

    IEstado aguardaPassarTurno();

    IEstado passaTurno();

    IEstado terminaJogo();

    IEstado decideMiniGame();

    IEstado startMiniGame();

    IEstado continuaJogada();
}
