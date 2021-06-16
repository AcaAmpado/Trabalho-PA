package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class MiniGame extends EstadoAdapter{

    public MiniGame(Jogo jogo) {
        super(jogo);
        jogo.addLog("MiniGame");
    }

    @Override
    public IEstado aguardaPassarTurno() {
        return new PassarTurno(jogo);
    }

    @Override
    public IEstado jogaMinijogo(double numero) {
        jogo.addLog("jogaMinijogo()");
        if(jogo.getMiniGame().checkTimer()){
            if(jogo.getMiniGame().verificaInput(numero)){ // tem pontuação necessária
                return endGame();
            }
        } else { //Acabou o tempo
            return endGame();
        }
        return this;
    }

    @Override
    public IEstado jogaMinijogo(String input) {
        jogo.addLog("jogaMinijogo()");
        if(jogo.getMiniGame().checkTimer()){
            if(jogo.getMiniGame().verificaInput(input)){ // tem pontuaçao necessaria
                return endGame();
            }
        } else { //Acabou o tempo
            return endGame();
        }
        return this;
    }

    private IEstado endGame(){
        jogo.addLog("engGame()");
        jogo.resetBonus(jogo.getMiniGame().getPontuacao());
        if(jogo.getMiniGame().getPontuacao() >= 5){
            jogo.addPecaEspecial();
            jogo.setMinigame();
            return new Jogada(jogo);
        }
        else{
            jogo.setMinigame();
            return new PassarTurno(jogo);
        }
    }

    @Override
    public Situacao getStatus() {
        return Situacao.MiniGame;
    }

}

