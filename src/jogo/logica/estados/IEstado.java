package jogo.logica.estados;

import jogo.logica.Situacao;

public interface IEstado {


    Situacao getStatus();

    IEstado start();

    IEstado selGameMode();

    IEstado carregaJogo();

    IEstado historicoJogos();

    IEstado Jogada();
}
