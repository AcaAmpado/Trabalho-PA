package jogo.logica.dados.minigames;

import jogo.auxfunc.*;

import java.io.*;
import java.util.*;

public class EscrevePalavras extends MiniGame{

    private final int NUMPALAVRAS=5;
    private final String path = "palavras.txt";
    private ArrayList<String> palavras;

    public EscrevePalavras(){
        pontos=0;
        palavras = new ArrayList<>();
        try {
            lerFTexto(path);
        } catch(IOException e) {
            System.out.println("ERRO!");
        }
    }

    public void lerFTexto( String nome) throws IOException{
            try {
                File txt = new File("./src/jogo/logica/dados/minigames/palavras.txt");
                Scanner leitorFich = new Scanner(txt);
                while (leitorFich.hasNextLine()) {
                    palavras.add(leitorFich.nextLine().toLowerCase());
                }
                leitorFich.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }
    }

    public static String rules() {
        return "Introduza as palavras separadas por um unico espaço:";
    }

    @Override
    public int joga() {
        ArrayList<String> palavrasEscolhidas = new ArrayList<>();
        String input;
        String [] inputS;
        long letras = 0;
        for(int i = 0;i<NUMPALAVRAS;i++){
            palavrasEscolhidas.add(palavras.get(randomNum(palavras.size())));
            letras += palavrasEscolhidas.get(i).length();
        }

        long start = System.currentTimeMillis();
        long end = start + ((letras+NUMPALAVRAS-1)/2)*1000;
        while (System.currentTimeMillis() < end) {
            System.out.println(palavrasEscolhidas);
            input = auxFunc.pedeString("");
            inputS = input.split("\\s+");
            int teste = 0;
            for (int i = 0; i < inputS.length; i++) {
                for(int j=0;j<palavrasEscolhidas.size();j++){
                    teste=0;
                    if (inputS[i].compareToIgnoreCase(palavrasEscolhidas.get(j)) == 0) {
                        palavrasEscolhidas.remove(inputS[i].toLowerCase());
                        pontos++;
                        teste=1;
                        break;
                    }
                    
                }
                if(teste==0)
                    System.out.println("Palavra " + (i+1) + " Errada!");
            }
            if(pontos==NUMPALAVRAS)
                break;
        }
        if(System.currentTimeMillis() < end){
            return pontos;
        }
        System.out.println("Demorou demasiado tempo!");
        return 0;
    }

    private int randomNum(int max){
        return (int)(Math.random()*max);
    }
}

