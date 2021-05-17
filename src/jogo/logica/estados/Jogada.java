package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class Jogada extends EstadoAdapter{
    public Jogada(Jogo jogo) {
        super(jogo);
    }


    @Override
    public IEstado acabaJogo() {
        return new GameOver(jogo);
    }


    @Override
    public IEstado aguardaPassarTurno() {
        return new PassarTurno(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.Jogada;
    }


}
