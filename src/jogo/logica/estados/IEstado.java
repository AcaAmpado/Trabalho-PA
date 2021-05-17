package jogo.logica.estados;

import jogo.logica.Situacao;

public interface IEstado {


    Situacao getStatus();

    IEstado start();

    IEstado comecaJogo();

    IEstado selGameMode();

    IEstado carregaJogo();

    IEstado historicoJogos();

    IEstado acabaJogo();

    IEstado aguardaPassarTurno();

    IEstado passaTurno();

    IEstado terminaJogo();

    IEstado decideMiniGame();

    IEstado startMiniGame();
}
