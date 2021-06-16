package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class DecisaoMiniGame extends EstadoAdapter{

    public DecisaoMiniGame(Jogo jogo) {
        super(jogo);
        jogo.addLog("DecisaoMiniGame");
    }

    @Override
    public IEstado startMiniGame() {
        jogo.resetMinijogo();
        jogo.startMiniGame();
        jogo.addLog("startMinigame()");
        return new MiniGame(jogo);
    }

    @Override
    public IEstado semMinigame() {
        jogo.resetBonus(-2);
        jogo.addLog("semMinigame()");
        return new Jogada(jogo);
    }

    @Override
    public Situacao getStatus() {
        return Situacao.DecisaoMiniGame;
    }
}