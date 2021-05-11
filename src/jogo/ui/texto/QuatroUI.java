package jogo.ui.texto;

import jogo.logica.MaquinaEstados;
import jogo.logica.dados.Jogo;
import jogo.auxfunc.auxFunc;

public class QuatroUI {
    MaquinaEstados maquinaEstados;
    public QuatroUI(MaquinaEstados maquinaEstados){this.maquinaEstados = maquinaEstados;}
    private static boolean sair;
    public void start(){
        sair=false;
        while(!sair){
            switch(maquinaEstados.getStatus()){
                case Inicio -> uiInicio();
                case GameMode -> uiGameMode();
                case NamePlayers -> uiNamePlayers();
                case Historico -> uiHistorico();
                case Carrega -> uiCarrega();
                case Jogada -> uiJogada();
                case PassarTurno -> uiPassarTurno();
                case Decisao -> uiDecisao();
                case MiniGame -> uiMiniGame();
                case GameOver -> uiGameOver();
            }
        }

    }



    private void uiInicio(){
        System.out.println("Bem Vindo ao Jogo do Quatro em Linha!");
        System.out.println("Este é um jogo para dois jogadores");
        System.out.println("Regras:");
        System.out.println("- Os jogadores jogam alternadamente colocando uma peça da sua cor numa das 7 colunas.");
        System.out.println("- Ganha o primeiro jogador a colocar 4 peças em linha, quer seja numa coluna, linha ou diagonal!");
        System.out.println("- Os jogadores possuem "+ Jogo.NUMCREDITOS +" créditos para desfazer jogadas. Cada crédito usado volta atrás uma jogada.");
        System.out.println("- A cada "+ Jogo.RONDASPBONUS +" rondas o jogador pode escolher participar num minijogo que, se concluído com sucesso, o recompensa com uma peça especial.");
        System.out.println("- A peça especial permite remover as peças de uma das colunas do jogo.");
        System.out.println("- Se o jogador perder o minijogo perde a sua vez de jogar.");
        System.out.println("Boa Sorte!");
        if(auxFunc.lerInteiro("Introduza 1 para continuar ou 0 para sair!") == 0)
            sair=true;
        else
            maquinaEstados.start();
    }
    private void uiGameMode(){
        switch (auxFunc.escolherOpcao("Novo Jogo","Carregar Jogo","Ver Jogos Anteriores","Sair")){
            case 1: // Novo Jogo
                switch (auxFunc.escolherOpcao("Jogador vs Jogador", "Jogador vs AI", "AI vs AI", "Sair")) {
                    case 1 -> //PvP
                            maquinaEstados.selGameMode(1);
                    case 2 -> //PvAi
                            maquinaEstados.selGameMode(2);
                    case 3 -> //AivAi
                            maquinaEstados.selGameMode(3);
                    case 4 -> //Sair
                            sair = true;
                    default -> {
                        System.out.println("Erro critico!");
                        sair = true;
                    }
                }
                break;
            case 2: //Carregar jogo
                maquinaEstados.carregaJogo();
                break;
            case 3:// Ver jogos anteriores
                maquinaEstados.historicoJogos();
                break;
            case 4: //Sair
                sair=true;
                break;
            default:
                System.out.println("Erro critico!");
                sair=true;
                break;
        }
    }
    private void uiNamePlayers(){
        String player1 = auxFunc.pedeString("Introduza o nome do jogador 1:");
        String player2;
        do{
            player2 = auxFunc.pedeString("Introduza o nome do jogador 2:");
            if(player2.compareToIgnoreCase(player1)==0){
                System.out.println("Os nomes não podem ser iguais!");
            }
        }while (player2.compareTo(player1)==0);
        maquinaEstados.comecaJogo(player1,player2);
    }
    private void uiCarrega(){

    }
    private void uiHistorico(){

    }
    private void uiJogada(){

    }
    private void uiPassarTurno(){

    }
    private void uiDecisao(){

    }
    private void uiMiniGame(){

    }
    private void uiGameOver(){

    }


}
