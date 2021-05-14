package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class NamePlayers extends EstadoAdapter{

    int tipo;

    public NamePlayers(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado comecaJogo() {
        return new Jogada(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.NamePlayers;
    }
}
