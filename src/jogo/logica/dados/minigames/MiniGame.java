package jogo.logica.dados.minigames;

public abstract class MiniGame {
    protected int pontos;
    protected int NECESSARIO = 5;

    public abstract int joga();
}
