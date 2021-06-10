package jogo.logica.dados.minigames;

import jogo.auxfunc.auxFunc;

import java.io.*;
import java.util.*;

public class EscrevePalavras extends MiniGame{

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


    @Override
    public int joga() {
        String input;
        String [] inputS;
        long letras = 0;
        for(int i = 0;i<NECESSARIO;i++){
            palavrasEscolhidas.add(palavras.get(randomNum(palavras.size())));
            letras += palavrasEscolhidas.get(i).length();
        }

        long start = System.currentTimeMillis();
        long end = start + ((letras+NECESSARIO-1)/2)*1000;
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
            if(pontos==NECESSARIO)
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

