package jogo.logica.dados.minigames;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class EscrevePalavras extends MiniGame {

    private final String path = "./src/jogo/logica/dados/minigames/palavras.txt";

    private final ArrayList<String> palavras;
    private ArrayList<String> palavrasEscolhidas;
    private long letras;
    private long start;
    private long end;

    public EscrevePalavras(){
        pontos=0;
        letras=0;
        palavras = new ArrayList<>();
        palavrasEscolhidas=new ArrayList<>();

        try { // inicializar palavras
            lerFTexto(path);
        } catch(IOException ignored) {

        }

        for(int i = 0;i<NECESSARIO;i++){
            palavrasEscolhidas.add(palavras.get(randomNum(palavras.size())));
            letras += palavrasEscolhidas.get(i).length();
        }


    }

    public void lerFTexto( String nome) throws IOException{
            try {
                File txt = new File(path);
                Scanner leitorFich = new Scanner(txt);
                while (leitorFich.hasNextLine()) {
                    palavras.add(leitorFich.nextLine().toLowerCase());
                }
                leitorFich.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }
    }

    @Override
    public String rules() {
        return "Introduza as palavras separadas por um unico espaço:";
    }

    @Override
    public String getPergunta() {
        return palavrasEscolhidas.toString();
    }

    @Override
    public void startTimer() {
        start = System.currentTimeMillis();
        end = start + ((letras+NECESSARIO-1)/2)* 1000;
    }

    @Override
    public boolean checkTimer() {
        return System.currentTimeMillis() < end;
    }

    @Override
    public boolean verificaInput(String input) {
        String [] inputS;
        inputS = input.split("\\s+");
        for (String s : inputS) {
            for (int j = 0; j < palavrasEscolhidas.size(); j++) {
                if (s.compareToIgnoreCase(palavrasEscolhidas.get(j)) == 0) {
                    palavrasEscolhidas.remove(s.toLowerCase());
                    pontos++;
                    break;
                }
            }
        }
        return pontos == NECESSARIO;
    }

    @Override
    public boolean verificaInput(double numero) {
        return false; // nao se deve aplicar
    }

    private int randomNum(int max){
        return (int)(Math.random()*max);
    }
}

