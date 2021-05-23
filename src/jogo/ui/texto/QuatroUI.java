package jogo.ui.texto;

import jogo.logica.MaquinaEstados;
import jogo.logica.dados.Erro;
import jogo.logica.dados.Jogo;
import jogo.auxfunc.auxFunc;
import jogo.logica.dados.TipoJogador;
import jogo.logica.dados.minigames.EscrevePalavras;
import jogo.logica.dados.minigames.RandomContas;

import java.io.IOException;

public class QuatroUI {
    MaquinaEstados maquinaEstados;
    private static boolean sair;

    public QuatroUI(MaquinaEstados maquinaEstados){
        this.maquinaEstados = maquinaEstados;
    }

    public void start(){
        sair=false;
        while(!sair){
            switch(maquinaEstados.getStatus()){
                case Inicio -> uiInicio();
                case GameMode -> uiGameMode();
                case NamePlayers -> uiNamePlayers();
                case Historico -> uiHistorico();
                case Jogada -> uiJogada();
                case PassarTurno -> uiPassarTurno();
                case DecisaoMiniGame -> uiDecisao();
                case MiniGame -> uiMiniGame();
                case GameOver -> uiGameOver();

            }
        }
    }
    //______________________UI________________________

    private void uiInicio(){
        System.out.println("Bem Vindo ao Jogo do Quatro em Linha!");
        System.out.println("Este é um jogo para dois jogadores");
        System.out.println("Regras:");
        System.out.println("- Os jogadores jogam alternadamente colocando uma peça da sua cor numa das 7 colunas.");
        System.out.println("- Ganha o primeiro jogador a colocar 4 peças em linha, quer seja numa coluna, linha ou diagonal!");
        System.out.println("- Os jogadores possuem "+ Jogo.NUMCREDITOS +" créditos para desfazer jogadas. Cada crédito usado volta atrás uma jogada.");
        System.out.println("- A cada "+ Jogo.RONDASPBONUS+" rondas o jogador pode escolher participar num minijogo que, se concluído com sucesso, o recompensa com uma peça especial.");
        System.out.println("- A peça especial permite remover as peças de uma das colunas do jogo.");
        System.out.println("- Se o jogador perder o minijogo perde a sua vez de jogar.");
        System.out.println("- Se o tabuleiro ficar cheio o jogo acaba, independentemente do numero de pecas especiais do proximo jogador!");
        System.out.println("Boa Sorte!");
        if(auxFunc.lerInteiro("Introduza 1 para continuar ou 0 para sair!") == 0)
            sair=true;
        else
            maquinaEstados.start();
    }

    private void uiGameMode(){
        switch (auxFunc.escolherOpcao("Novo Jogo","Carregar Jogo","Ver Jogos Anteriores","LogsME" ,"Sair")){
            case 1: // Novo Jogo
                switch (auxFunc.escolherOpcao("Jogador vs Jogador", "Jogador vs AI", "AI vs AI", "Sair")) {
                    case 1 -> //PvP
                            maquinaEstados.selGameMode(1);
                    case 2 -> //PvAi
                            maquinaEstados.selGameMode(2);
                    case 3 -> //AivAi
                            maquinaEstados.selGameMode(3);
                    case 0 -> //Sair
                            maquinaEstados.terminaJogo();
                    default -> {
                        System.out.println("Erro critico!");
                        maquinaEstados.terminaJogo();
                    }
                }
                break;
            case 2: //Carregar jogo
                uiCarrega();
                break;
            case 3:// Ver jogos anteriores
                maquinaEstados.historicoJogos();
                break;
            case 4:
                System.out.println(maquinaEstados.getLogME().toString());
                break;
            case 0: //Sair
                maquinaEstados.terminaJogo();
                break;
            default:
                System.out.println("Erro critico!");
                maquinaEstados.terminaJogo();
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
        if(!maquinaEstados.comecaJogo(player1,player2)){
            System.out.println("Erro a comecar o jogo!");
            maquinaEstados.terminaJogo();
        }
    }

    private void uiJogada(){
        System.out.println(maquinaEstados.getBoard());
        System.out.println("Jogador: "+ maquinaEstados.getNomeJogadorVez());
        if(maquinaEstados.getTipoJogador() == TipoJogador.AI){
            switch (auxFunc.escolherOpcao("Fazer Jogada", "Guardar Jogo", "LogsME" , "Sair")) {
                case 1:
                    switch(maquinaEstados.jogaAI()){
                        case TabuleiroCheio -> {
                            System.out.println(maquinaEstados.getBoard());
                            System.out.println("Jogo empatou!");
                        }
                        case JogadaValida -> {}
                        case Ganhou -> {
                            System.out.println(maquinaEstados.getBoard());
                            System.out.println("Ganhou!");
                        }
                        default -> {
                            System.out.println("Erro critico!");
                            maquinaEstados.terminaJogo();
                        }
                    }
                    break;
                case 2:
                    guardaJogo();
                    break;
                case 3:
                    System.out.println(maquinaEstados.getLogME().toString());
                    break;
                case 0:
                    if(sairGuarda())
                        maquinaEstados.terminaJogo();
                    break;
                default:
                    System.out.println("Erro critico!");
                    maquinaEstados.terminaJogo();
                    break;
            }

        }
        else{
            System.out.println("Peças Especiais: "+maquinaEstados.getPecaEspecial());
            System.out.println("Creditos:" +maquinaEstados.getCreditos());
            switch (auxFunc.escolherOpcao("Fazer Jogada", "Guardar Jogo", "Utilizar Créditos", "Jogar Peca Especial", "LogsME" ,"Sair")) {
                case 1:
                    switch(maquinaEstados.fazJogada(auxFunc.lerInteiro("Insira a coluna onde pretende jogar:",1,7)-1)){
                        case Ganhou -> {
                            System.out.println(maquinaEstados.getBoard());
                            System.out.println("Ganhou!");
                        }
                        case ColunaCheia -> System.out.println("Jogada inválida! A coluna já está cheia");
                        case JogadaValida -> {}
                        case TabuleiroCheio -> {
                            System.out.println(maquinaEstados.getBoard());
                            System.out.println("Jogo Empatou!");
                        }
                        default -> {
                            System.out.println("Erro critico!");
                            maquinaEstados.terminaJogo();
                        }
                    }
                    break;
                case 2:
                    guardaJogo();
                    break;
                case 3:
                    utilizaCreditos();
                    break;
                case 4:
                    if(maquinaEstados.getPecaEspecial()==0){
                        System.out.println("Nao tem pecas especiais para jogar");
                        break;
                    }
                    maquinaEstados.jogaPecaEspecial(auxFunc.lerInteiro("Insira a coluna onde pretende jogar:",1,7)-1);
                    break;
                case 5:
                    System.out.println(maquinaEstados.getLogME().toString());
                    break;
                case 0:
                    if(sairGuarda())
                        maquinaEstados.terminaJogo();
                    break;
                default:
                    System.out.println("Erro critico!");
                    maquinaEstados.terminaJogo();
                    break;
            }
        }
    }

    private void uiCarrega(){
         if(!maquinaEstados.carregaJogo(auxFunc.pedeString("Introduza o nome do ficheiro que pretende carregar:")+".dat")){
             System.out.println("Não foi possivel carregar o jogo!");
         }
         else System.out.println("Jogo carregado com sucesso!");
    }

    private void uiHistorico(){
        String res = maquinaEstados.getHist();
        if(res.equals(Erro.SemJogosHist.toString())){
            System.out.println("Não existem jogos guardados");
            return;
        }
        System.out.println(res);
        maquinaEstados.replayHistorico(auxFunc.lerInteiro("Insira o numero do jogo que pretende visualizar:", 1,maquinaEstados.getHistoricoNum())-1);
    }

    private void uiPassarTurno(){
        if(maquinaEstados.isHistorico()){
            System.out.println("Jogador "+ maquinaEstados.getNomeJogadorVez());
            Erro erro = maquinaEstados.isMinigame();
            if(erro == Erro.Perdeu){
                System.out.println("O jogador jogou um minijogo e perdeu!");
            }else if (erro == Erro.Ganhou){
                System.out.println("O jogador jogou um minijogo e ganhou uma peça especial!");
            }
            else if(erro == Erro.NaoJogou){
                System.out.println("O jogador optou por nao jogar o minijogo");
            }
            System.out.println(maquinaEstados.getBoard());
            switch (auxFunc.escolherOpcao("Passar Turno", "Sair")) {
                case 1 -> maquinaEstados.passaTurnoHistorico();
                case 0 -> maquinaEstados.terminaJogo();
            }
            return;
        }
        System.out.println(maquinaEstados.getBoard());
        switch (auxFunc.escolherOpcao("Passar Turno", "Guardar Jogo","Utilizar Créditos", "LogsME" ,"Sair")) {
            case 1:
                maquinaEstados.passaTurno();
                break;
            case 2:
                guardaJogo();
                break;
            case 3:
                utilizaCreditos();
                break;
            case 4:
                System.out.println(maquinaEstados.getLogME().toString());
                break;
            case 0:
                if(sairGuarda())
                    maquinaEstados.terminaJogo();
                break;
            default:
                System.out.println("Erro critico!");
                maquinaEstados.terminaJogo();
                break;
        }
    }

    private void uiDecisao(){
        System.out.println("Jogador "+maquinaEstados.getNomeJogadorVez());
        System.out.println("Pretende jogar um minijogo para ganhar uma peça especial?");
        switch (auxFunc.escolherOpcao("Jogar minijogo", "Continuar sem minijogo")) {
            case 1 -> maquinaEstados.startMiniGame();
            case 0 -> maquinaEstados.semMiniGame();
        }
    }

    private void uiMiniGame(){
        switch (maquinaEstados.getMiniJogo()){
            case 0 -> {
                System.out.println(RandomContas.rules());
                if(maquinaEstados.jogaMinijogo()>=5){
                    System.out.println("Ganhou uma Peça Especial");
                }
                else {
                    System.out.println("Perdeu o minijogo");
                }
            }
            case 1 -> {
                System.out.println(EscrevePalavras.rules());
                if(maquinaEstados.jogaMinijogo()>=5){
                    System.out.println("Ganhou uma Peça Especial");
                }
                    else {
                    System.out.println("Perdeu o minijogo");
                }
            }

        }
    }

    private void uiGameOver(){
        System.out.println("Pretende continuar a jogar?");
        switch (auxFunc.escolherOpcao("Continuar", "Sair")) {
            case 1 -> maquinaEstados.start();
            case 0 -> {
                try{
                maquinaEstados.guardaHistoricoF();
                }catch (IOException ignored){}
                System.out.println("Obrigado por jogar 4 em linha!");
                sair=true;
            }
        }
    }

    //_________________MENUS_AUXILIARES_______

    private void utilizaCreditos() {
        switch (maquinaEstados.usaCreditos(auxFunc.lerInteiro("Introduza o numero de creditos que pretende usar:",1,5))){
            case JogadaValida -> System.out.println("Creditos usados com sucesso!");
            case SemCreditos -> System.out.println("Não tem creditos suficientes!");
            case SemJogadas -> System.out.println("Não existem jogadas para o numero de creditos!");
            case VoltarAntesCreditos -> System.out.println("Não é possivel para a ronda antes de terem sido usados os ultimos creditos!");
        }
    }

    private boolean sairGuarda(){
        System.out.println("Pretende Guardar antes de sair?");
        switch (auxFunc.escolherOpcao("Guardar e Sair", "Sair sem Guardar", "Cancelar")) {
            case 1:
                if(guardaJogo()){
                    System.out.println("Erro a guardar Jogo!");
                    return false;
                }
                return true;
            case 2:
                return true;
            case 0:
                return false;
            default:
                System.out.println("Erro critico!");
                return true;
        }
    }

    private boolean guardaJogo(){
        return maquinaEstados.guardaJogo(auxFunc.pedeString("Introduza o nome para o save:")+".dat");
    }
}
