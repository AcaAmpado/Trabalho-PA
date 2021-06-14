package jogo.logica.dados.minigames;

import java.io.Serial;
import java.io.Serializable;

public abstract class MiniGame implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    protected int pontos;
    protected int NECESSARIO = 5;

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
