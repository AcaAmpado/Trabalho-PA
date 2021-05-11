package jogo.auxfunc;

import java.util.Scanner;

public class auxFunc {
    public static Scanner sc;
    static {sc=new Scanner(System.in);}

    public static int lerInteiro(String pergunta) {
        System.out.print(pergunta);
        return lerInteiro();
    }

    public static int lerInteiro(){
        while(!sc.hasNextInt())
            sc.next();
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    public static int escolherOpcao(String...opcao) {
        int input;
        do{
            for(int i = 0; i < opcao.length - 1; i++)
                System.out.println(i+1 + " - " + opcao[i]);
            System.out.println( 0 + " - "+ opcao[opcao.length - 1]);
            input = lerInteiro();
        }while(input < 0 || input >= opcao.length);
        return input;
    }

    public static String pedeString(String pergunta) {
        String resposta;
        do {
            System.out.println(pergunta);
            resposta = sc.nextLine().trim();
        }while(resposta.isEmpty());
        return resposta;
    }
}