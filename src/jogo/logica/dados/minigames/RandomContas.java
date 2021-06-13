package jogo.logica.dados.minigames;

import jogo.auxfunc.auxFunc;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

public class RandomContas extends MiniGame{

    private final int MIN = 1, MAX = 10;
    private double resultado;

    private long start;
    private long end;

    public RandomContas(){
        pontos=0;
    }

    @Override
    public String rules() {
        return "Introduza o resultado das contas (2 casas decimais nas divisões):";
    }

    @Override
    public boolean checkTimer() {
        return System.currentTimeMillis() < end;
    }

    @Override
    public boolean verificaInput(String input) {
        return false; //não se deve aplicar
    }

    @Override
    public boolean verificaInput(double numero) {
        if(numero == resultado)
            pontos++;
        return pontos == NECESSARIO;
    }

    @Override
    public String getPergunta(){
        int num1 = randomNum(), num2 = randomNum();
        switch ((int)(Math.random()*4)){
            case 0 -> {
                resultado = num1-num2;
                return num1 + " - " + num2 + " = ";
            }
            case 1 -> {
                resultado = num1+num2;
                return  num1+" + "+ num2+" = ";
            }
            case 2 -> {
                resultado = num1*num2;
                return num1 + " * " + num2 + " = ";
            }
            case 3 -> {
                resultado = Math.round( (double) num1/num2 * 100.0) / 100.0;
                return num1 + " / " + num2 + " = ";
            }
        }
        return null;
    }

    @Override
    public void startTimer() {
        start = System.currentTimeMillis();
        end = start + 30*1000;
    }

    private int randomNum(){
        return (int)(Math.random()*MAX)+MIN;
    }
}
