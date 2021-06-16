package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class Inicio extends EstadoAdapter{
    public Inicio(Jogo jogo) {
        super(jogo);
        jogo.addLog("Inicio");
    }

    @Override
    public Situacao getStatus() {
        return Situacao.Inicio;
    }

}
