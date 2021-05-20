package jogo.logica.dados.minigames;

import jogo.auxfunc.auxFunc;

import java.text.DecimalFormat;


public class RandomContas extends MiniGame {
    private final int NUMCONTAS=5;
    private final int MIN=1, MAX=10;


    public RandomContas(){
        pontos=0;
    }

    public static String rules() {
        return "Introduza o resultado das contas (2 casas decimais nas divisões):";
    }

    @Override
    public int joga() {

        long start = System.currentTimeMillis();
        long end = start + 30*1000;
        while (System.currentTimeMillis() < end) {
            int num1 =randomNum(),num2 = randomNum();
            switch ((int)(Math.random()*4)){
                case 0 -> {
                    if( num1-num2== auxFunc.lerInteiro(num1+" - "+ num2+" = "))
                        pontos++;
                }
                case 1 -> {
                    if( num1+num2==auxFunc.lerInteiro( num1 + " + " + num2 + " = "))
                        pontos++;
                }
                case 2 -> {
                    if( num1*num2==auxFunc.lerInteiro(num1+" * "+ num2+" = "))
                        pontos++;
                }
                case 3 -> {
                    double res=Math.round( (double) num1/num2 * 100.0) / 100.0;
                    if(res == auxFunc.lerNumero(num1 + " / " + num2 + " = "))
                        pontos++;
                }
            }
            if(pontos==NUMCONTAS)
                break;
        }
        if(System.currentTimeMillis() < end){
            return pontos;
        }
        System.out.println("Demorou demasiado tempo!");
        return 0;
    }

    private int randomNum(){
        return (int)(Math.random()*MAX)+MIN;
    }
}
