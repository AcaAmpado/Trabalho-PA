package jogo.logica.dados;

import jogo.logica.dados.minigames.EscrevePalavras;
import jogo.logica.dados.minigames.MiniGame;
import jogo.logica.dados.minigames.RandomContas;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Jogo implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 4L;

    private Erro estadoErro;

    public static final int NUMCREDITOS = 5;
    public static final int RONDASPBONUS = 4;
    public static final int LARGURA = 7;
    public static final int ALTURA = 6;
    private static final int NUMMG = 2; //Num de minijogos
    private int tipo;
    private ArrayList<Jogador>  players;
    private ArrayList<ArrayList<Peca>> tabuleiro;
    private int vezJogador;
    private boolean historico;
    private ArrayList<Jogo> jogadas;
    private int jogada;
    private Erro minijogo;
    private MiniGame miniGame;

    public Jogo (){
       setupJogo();
    }

    public void setupJogo(){
        players = new ArrayList<>();
        tabuleiro = new ArrayList<>();
        for (int i = 0; i < LARGURA ; i++) {
            tabuleiro.add(new ArrayList<>());
        }
        historico = false;
        minijogo=Erro.Critico;
    }

    // ____________GET____________

    public String getRegras(){ //new
        return  "Bem Vindo ao Jogo do Quatro em Linha!\n" +
                "Este é um jogo para dois jogadores\n" +
                "Regras:\n" +
                "- Os jogadores jogam alternadamente colocando uma peça da sua cor numa das 7 colunas.\n" +
                "- Ganha o primeiro jogador a colocar 4 peças em linha, quer seja numa coluna, linha ou diagonal!\n" +
                "- Os jogadores possuem " + NUMCREDITOS + " créditos para desfazer jogadas. Cada crédito usado volta atrás uma jogada.\n" +
                "- A cada " + RONDASPBONUS + " rondas o jogador pode escolher participar num minijogo que, se concluído com sucesso, o recompensa com uma peça especial.\n" +
                "- A peça especial permite remover as peças de uma das colunas do jogo.\n" +
                "- Se o jogador perder o minijogo perde a sua vez de jogar.\n" +
                "- Se o tabuleiro ficar cheio o jogo acaba, independentemente do numero de pecas especiais do proximo jogador!\n" +
                "Boa Sorte!\n";
    }

    public TipoJogador getTipoJogador(){
        return players.get(vezJogador).getTipo();
    }

    public String getNomeJogadorVez(){
        return players.get(vezJogador).getNome();
    }

    public ArrayList<ArrayList<String>> getTabuleiro(){ //new GUI

        ArrayList<ArrayList<String>> strTabuleiro = new ArrayList<>();

        for (int i = 0; i < LARGURA ; i++) {
            strTabuleiro.add(new ArrayList<>());
        }

        for(int i = ALTURA-1;i >= 0;i--){
            for(int j = 0; j < LARGURA; j++){
                if(i >= tabuleiro.get(j).size())
                    strTabuleiro.get(j).add("NULL");
                else
                    strTabuleiro.get(j).add(String.valueOf(players.get(tabuleiro.get(j).get(i).getJogador()).getSymbol()));
            }
        }

        return strTabuleiro;
    }

    public StringBuilder getBoard() {
        StringBuilder board= new StringBuilder();
        board.append("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n");
        board.append("-----------------------------\n");
        for(int i = ALTURA-1;i >= 0;i--){
            board.append("| ");
            for(int j = 0; j < LARGURA; j++){
                if(i >= tabuleiro.get(j).size())
                    board.append("  | ");
                else
                    board.append(players.get(tabuleiro.get(j).get(i).getJogador()).getSymbol()).append(" | ");
            }
            board.append("\n");
        }
        board.append("-----------------------------\n");
        board.append("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n");
        return board;
    }

    public int getCreditos() {
        return players.get(vezJogador).getCreditos();
    }

    public int getVezJogador() {
        return vezJogador;
    }

    public int getMiniJogo() {
        return players.get(vezJogador).getMinigame();
    }

    public int getPecaEspecial(int vez) {
        return players.get(vez).getPecaEspecial();
    }

    public int getPecaEspecial() {
        return players.get(vezJogador).getPecaEspecial();
    }

    // ____________SET____________

    public void setEstadoErro(Erro estado){
        estadoErro=estado;
    }

    public void setTipo(int tipo){
        this.tipo = tipo;
    }

    public void setPlayer(String nome, char symbol){
        if(tipo==1) // PvP
            this.players.add(new Jogador(nome,TipoJogador.Player,symbol, NUMMG));
        if(tipo==2) //PvAI
            if(players.size()==0)
                this.players.add(new Jogador(nome,TipoJogador.Player,symbol, NUMMG));
            else
                this.players.add(new Jogador(nome,TipoJogador.AI,symbol, NUMMG));
        if(tipo==3) //AIvAI
            this.players.add(new Jogador(nome,TipoJogador.AI,symbol, NUMMG));
    }

    public void setVezJogador(){
        vezJogador=(vezJogador+1)%2;
    }

    public void setCreditos(int i,int vez) {
        players.get(vez).setCreditos(i);
    }

    public void setPecaEspecial(int vez, int num){
        players.get(vez).setPecaEspecial(num);
    }

    public void setMinigame() {
        players.get(vezJogador).setMinigame(NUMMG);
    }

    public void atualizaBonus() {
        players.get(vezJogador).decrementaBonus();
    }

    public void addPecaEspecial(){
        this.players.get(vezJogador).adicionaPecaEspecial();
    }

    //__________________RESET_____________________

    public void resetMinijogo() {
        minijogo=Erro.SemErros;
    }

    public void resetBonus(int pontos){
        players.get(vezJogador).setBonus(RONDASPBONUS);
        if(pontos == -2){
            minijogo = Erro.NaoJogou;
        }else if (pontos < 5){
            minijogo = Erro.Perdeu;
        }else if(pontos == 5){
            minijogo = Erro.Ganhou;
        }else minijogo = Erro.SemErros;
    }

    ///________________CHECK_________________

    public boolean isHistorico() {
        return historico;
    }

    public Erro isMinigame(){
        return minijogo;
    }

    public Boolean isPlayable(){
        int contador=0;
        for(ArrayList<Peca> e : tabuleiro)
            if(e.size()==ALTURA)
                contador++;
        return contador != LARGURA;
    }

    public boolean checkMiniGame() {
        return players.get(vezJogador).getBonus() == 0;
    }

    public boolean isWinner(){
        for(int linha = 0; linha<ALTURA;linha++){ // verifica se tem na horizontal
            for(int col = 0; col<LARGURA-3;col++){
                try{
                    if(tabuleiro.get(col).get(linha).getJogador() == tabuleiro.get(col+1).get(linha).getJogador()
                    && tabuleiro.get(col+1).get(linha).getJogador()== tabuleiro.get(col+2).get(linha).getJogador()
                    && tabuleiro.get(col+2).get(linha).getJogador() == tabuleiro.get(col+3).get(linha).getJogador()){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }
        for(int col = 0; col<LARGURA;col++){ // verifica se tem na vertical
            for(int linha = 0; linha<ALTURA-3;linha++){
                try{
                    if(tabuleiro.get(col).get(linha).getJogador() == tabuleiro.get(col).get(linha+1).getJogador()
                            && tabuleiro.get(col).get(linha+1).getJogador() == tabuleiro.get(col).get(linha+2).getJogador()
                            && tabuleiro.get(col).get(linha+2).getJogador() == tabuleiro.get(col).get(linha+3).getJogador()){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }
        for(int linha = 3; linha<ALTURA; linha++){ // verifica se tem na diagonal asc
            for(int col = 3; col < LARGURA; col++){
                try{
                    if(tabuleiro.get(col).get(linha).getJogador() == tabuleiro.get(col-1).get(linha-1).getJogador()
                            && tabuleiro.get(col-1).get(linha-1).getJogador() == tabuleiro.get(col-2).get(linha-2).getJogador()
                            && tabuleiro.get(col-2).get(linha-2).getJogador() == tabuleiro.get(col-3).get(linha-3).getJogador()){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }
        for(int linha = 3; linha < ALTURA ;linha++){ // verifica se tem na diagonal desc
            for(int col = 0; col < LARGURA - 3 ;col++){
                try{
                    if(tabuleiro.get(col).get(linha).getJogador() == tabuleiro.get(col+1).get(linha-1).getJogador()
                            && tabuleiro.get(col+1).get(linha-1).getJogador() == tabuleiro.get(col+2).get(linha-2).getJogador()
                            && tabuleiro.get(col+2).get(linha-2).getJogador() == tabuleiro.get(col+3).get(linha-3).getJogador()){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }
        return false;
    }

    // ___________________LOGICA__________________

    public boolean comecaJogo() {
        if(players.size()!=2){
            return false;
        }
        for(Jogador jog:players) {
            if(jog.getTipo()==TipoJogador.Player){
                jog.setBonus(RONDASPBONUS);
                jog.setCreditos(NUMCREDITOS);
            }
        }
        vezJogador= (int) (Math.random()*2);
        players.get(vezJogador).decrementaBonus();
        return true;
    }

    public Erro fazJogada(int coluna) {
        if(tabuleiro.get(coluna).size()>=ALTURA){
            return Erro.ColunaCheia;
        }
        tabuleiro.get(coluna).add(new Peca(vezJogador));
        if(isWinner()) {
            return Erro.Ganhou;
        }
        if(!isPlayable())
            return Erro.TabuleiroCheio;
        return Erro.JogadaValida;
    }

    public Erro jogaPecaEspecial(int coluna) {
        if(players.get(vezJogador).getPecaEspecial()==0){
            return Erro.SemEspecial;
        }
        minijogo=Erro.Especial;
        tabuleiro.get(coluna).clear();
        players.get(vezJogador).removePecaEspecial();
        return Erro.JogadaValida;
    }

    public Erro jogaAI() {
        while(isPlayable()){
            Erro erro = fazJogada((int)(Math.random()*ALTURA));
            if(erro != Erro.ColunaCheia)
                break;
        }
        if(isWinner()) {
            return Erro.Ganhou;
        }
        if(!isPlayable())
            return Erro.TabuleiroCheio;
        return Erro.JogadaValida;
    }

    public Erro replayHistorico() {
        jogada++;
        try{
            this.vezJogador = jogadas.get(jogada).vezJogador;
            this.tipo = jogadas.get(jogada).tipo;
            this.tabuleiro = jogadas.get(jogada).tabuleiro;
            this.minijogo = jogadas.get(jogada).minijogo;
            this.players = jogadas.get(jogada).players;
        }catch (IndexOutOfBoundsException ignored){
            return Erro.FimJogo;
        }
        return Erro.JogadaValida;
    }

    public void innitReplay(ArrayList<Jogo> jogos) {
        historico = true;
        jogadas = jogos;
        jogada = -1;
    }

    public void copiaValues(Jogo temp){
        try {
            this.tipo = temp.tipo ;
            this.vezJogador = temp.vezJogador;
            this.minijogo = temp.minijogo;

            ArrayList<Jogador> clonePlayers = new ArrayList<>();

            for(Jogador jog: temp.players) // Clone de Jogadores
                clonePlayers.add((Jogador) jog.clone());

            this.players=clonePlayers;

            ArrayList<ArrayList<Peca>> cloneTabuleiro = new ArrayList<>(); //Clone de Tabuleiro

            for (int i = 0; i < LARGURA ; i++) {
                cloneTabuleiro.add(new ArrayList<>());
                for(int j = 0 ; j < temp.tabuleiro.get(i).size() ; j++ ){
                    cloneTabuleiro.get(i).add(new Peca(temp.tabuleiro.get(i).get(j).getJogador()));
                }
            }
            this.tabuleiro=cloneTabuleiro;
        } catch (CloneNotSupportedException ignored) {
        }
    }

    //_________________OVERRIDE_______________________

    @Override
    public Object clone(){
        Jogo clone = null;
        try {
            clone = (Jogo) super.clone();
            clone.tipo = this.tipo ;
            clone.vezJogador = this.vezJogador;
            clone.minijogo = this.minijogo;
            ArrayList<Jogador> clonePlayers = new ArrayList<>();

            for(Jogador jog:players) // Clone de Jogadores
                clonePlayers.add((Jogador) jog.clone());

            clone.players=clonePlayers;

            ArrayList<ArrayList<Peca>> cloneTabuleiro = new ArrayList<>(); //Clone de Tabuleiro

            for (int i = 0; i < LARGURA ; i++) {
                cloneTabuleiro.add(new ArrayList<>());
                for(int j = 0 ; j < tabuleiro.get(i).size() ; j++ ){
                    cloneTabuleiro.get(i).add(new Peca(tabuleiro.get(i).get(j).getJogador()));
                }
            }
            clone.tabuleiro=cloneTabuleiro;
        } catch (CloneNotSupportedException ignored) {
        }
        return clone;
    }

    @Override
    public String toString() {
        return "| "+players.get(0).getTipo().toString()+" - " + players.get(0).getNome()+" Simb: "+ players.get(0).getSymbol() +" vs " +
                " "+ players.get(1).getTipo().toString()+" - " + players.get(1).getNome()+" Simb: "+ players.get(1).getSymbol() +" |";
    }

    public Erro getEstadoErro() {
        return estadoErro;
    }

    public void startJogadas() {
        jogadas= new ArrayList<>();
    }

    public void guardaJogada() {
        jogadas.add((Jogo) this.clone());
        this.resetMinijogo();
    }

    public ArrayList<Jogo> getJogadas() {
        return jogadas;
    }

    public void startMiniGame() {
        switch(getMiniJogo()){
            case 0 ->{
                miniGame = new RandomContas();
                miniGame.startTimer();
            }
            case 1 ->{
                miniGame = new EscrevePalavras();
                miniGame.startTimer();
            }
        }
    }

    public String getRules() {
        return miniGame.rules();
    }

    public String getPergunta() {
        return miniGame.getPergunta();
    }

    public MiniGame getMiniGame(){
        return miniGame;
    }

    public void setJogadas(ArrayList<Jogo> jogadas ) {
        this.jogadas = jogadas;
    }
}


