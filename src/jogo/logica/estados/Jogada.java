package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class Jogada extends EstadoAdapter{
    public Jogada(Jogo jogo) {
        super(jogo);

    }




    @Override
    public void jogaAI() {
        //TODO meter o AI a jogar
    }

    @Override
    public Situacao getStatus() {
        return Situacao.Jogada;
    }


}
