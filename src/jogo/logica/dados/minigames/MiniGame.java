package jogo.logica.dados.minigames;

public abstract class MiniGame {
    protected int pontos;
    protected int NECESSARIO = 5;

    public abstract int joga();

    public abstract String rules();

    public abstract String getPergunta();

    public abstract void startTimer();

    public abstract boolean checkTimer();
    
    public abstract boolean verificaInput(String input);

    public abstract boolean verificaInput(double numero);

    public int getPontuacao(){
        return pontos;
    }
}
