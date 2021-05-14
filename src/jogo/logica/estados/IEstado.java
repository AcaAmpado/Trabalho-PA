package jogo.logica.estados;

import jogo.logica.Situacao;

public interface IEstado {


    Situacao getStatus();

    IEstado start();

    IEstado comecaJogo();

    IEstado selGameMode();

    IEstado carregaJogo();

    IEstado historicoJogos();

    void jogaAI();

    IEstado acabaJogo();
}
