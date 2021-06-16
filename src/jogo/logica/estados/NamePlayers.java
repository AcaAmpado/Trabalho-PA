package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import jogo.logica.dados.Jogo;

public class NamePlayers extends EstadoAdapter{

    public NamePlayers(Jogo jogo) {
        super(jogo);
        jogo.addLog("NamePlayers");
    }

    @Override
    public IEstado comecaJogo(String player1,String player2) {
        jogo.addLog("comecaJogo()");
        jogo.setPlayer(player1, 'A');
        jogo.setPlayer(player2, 'B');
        if(jogo.comecaJogo()){
            jogo.startJogadas();
            jogo.guardaJogada();
        }else{
            jogo.setEstadoErro(Erro.Critico);
            return new GameOver(jogo);
        }
        return new Jogada(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.NamePlayers;
    }
}
