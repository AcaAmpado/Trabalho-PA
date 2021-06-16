package jogo.ui.texto;

import jogo.logica.MaquinaEstados;
import jogo.logica.dados.Erro;
import jogo.auxfunc.auxFunc;
import jogo.logica.dados.TipoJogador;

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
        System.out.println(maquinaEstados.getRegras());
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
                maquinaEstados.getLogsME();
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
        maquinaEstados.comecaJogo(player1,player2);
        if(maquinaEstados.getEstadoErro()==Erro.Critico){
            System.out.println("Erro a comecar o jogo!");
        }
    }

    private void uiJogada(){
        System.out.println(maquinaEstados.getBoard());
        System.out.println("Jogador: "+ maquinaEstados.getNomeJogadorVez() +" Tipo: "+maquinaEstados.getTipoJogador().toString());
        if(maquinaEstados.getTipoJogador() == TipoJogador.AI){
            switch (auxFunc.escolherOpcao("Fazer Jogada", "Guardar Jogo", "LogsME" , "Sair")) {
                case 1:
                    maquinaEstados.jogaAI();
                    switch(maquinaEstados.getEstadoErro()){
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
                    maquinaEstados.getLogsME();
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
                    maquinaEstados.fazJogada(auxFunc.lerInteiro("Insira a coluna onde pretende jogar:",1,7)-1);
                    switch(maquinaEstados.getEstadoErro()){
                        case Ganhou -> {
                            System.out.println(maquinaEstados.getBoard());
                            System.out.println("Ganhou!");
                        }
                        case ColunaCheia -> System.out.println("Jogada inválida! A coluna já está cheia");
                        case JogadaValida -> { }
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
                    maquinaEstados.getLogsME();
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
        if(res==null){
            System.out.println("Não existem jogos guardados");
            return;
        }
        System.out.println(res);
        maquinaEstados.replayHistorico(auxFunc.lerInteiro("Insira o numero do jogo que pretende visualizar:", 1,maquinaEstados.getHistoricoNum())-1);
    }

    private void uiPassarTurnoHistorico(){
        System.out.println("Jogador "+ maquinaEstados.getNomeJogadorVez());
        Erro minijogo = maquinaEstados.isMinigame();
        switch (minijogo){
            case Perdeu -> System.out.println("O jogador jogou um minijogo e perdeu!");
            case Ganhou -> System.out.println("O jogador jogou um minijogo e ganhou uma peça especial!");
            case NaoJogou -> System.out.println("O jogador optou por nao jogar o minijogo");
            case Especial -> System.out.println("O jogador jogou uma peca especial");
            case Creditos -> System.out.println("O jogador utilizou creditos");
        }
        System.out.println(maquinaEstados.getBoard());
        switch (auxFunc.escolherOpcao("Passar Turno", "Sair")) {
            case 1 -> {
                maquinaEstados.passaTurnoHistorico();
                if(maquinaEstados.getEstadoErro()==Erro.FimJogo){
                    System.out.println("O jogo acabou!");
                    System.out.println("O jogador "+ maquinaEstados.getNomeJogadorVez() +" foi o ultimo a jogar!");
                }
            }
            case 0 -> maquinaEstados.terminaJogo();
        }
    }

    private void uiPassarTurno(){
        if(maquinaEstados.isHistorico()){
           uiPassarTurnoHistorico();
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
                maquinaEstados.getLogsME();
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
            case 1 -> {
                maquinaEstados.startMiniGame();
                System.out.println( maquinaEstados.getRules());
            }
            case 0 -> maquinaEstados.semMiniGame();
        }
    }

    private void uiMiniGame(){
        switch (maquinaEstados.getMiniJogo()){
            case 0 -> maquinaEstados.jogaMinijogo(auxFunc.lerNumero(maquinaEstados.getPergunta()));
            case 1 -> maquinaEstados.jogaMinijogo(auxFunc.pedeString(maquinaEstados.getPergunta()));
        }
        switch (maquinaEstados.isMinigame()) {
            case Ganhou -> System.out.println("Ganhou o Minijogo!");
            case Perdeu -> System.out.println("Perdeu o Minijogo!");
        }
    }

    private void uiGameOver(){
        System.out.println("Pretende continuar a jogar?");
        switch (auxFunc.escolherOpcao("Continuar", "Sair")) {
            case 1 -> maquinaEstados.start();
            case 0 -> {
                maquinaEstados.guardaHistoricoF();
                System.out.println("Obrigado por jogar 4 em linha!");
                sair=true;
            }
        }
    }

    //_________________MENUS_AUXILIARES_______

    private void utilizaCreditos() {
        if(maquinaEstados.getCreditos() > 0)
            maquinaEstados.usaCreditos(auxFunc.lerInteiro("Introduza o numero de creditos que pretende usar:",1, maquinaEstados.getCreditos()));
        else {
            System.out.println("Nao possui creditos para utilizar!");
            return;
        }
        System.out.println( maquinaEstados.getEstadoErro().toString());
        switch (maquinaEstados.getEstadoErro()){
            case SemErros -> System.out.println("Creditos usados com sucesso!");
            case SemCreditos -> System.out.println("Não tem creditos suficientes!");
            case SemJogadas -> System.out.println("Não existem jogadas para o numero de creditos!");
            default -> System.out.println("FUCK ME");
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
