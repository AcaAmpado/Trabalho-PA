package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class EscolhaJogoH extends EstadoAdapter{

    public EscolhaJogoH(Jogo jogo) {
        super(jogo);
    }




    @Override
    public Situacao getStatus() {
        return Situacao.Historico;
    }
}