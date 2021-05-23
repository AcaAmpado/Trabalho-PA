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

    public static final int NUMCREDITOS = 5;
    public static final int RONDASPBONUS = 4;
    private static final int LARGURA = 7;
    private static final int ALTURA = 6;
    private static final int NUMMG = 2; //Num de minijogos
    private int turnoCreditos;
    private int tipo;
    private ArrayList<Jogador>  players;
    private ArrayList<ArrayList<Peca>> tabuleiro;
    private int vezJogador;
    private boolean historico;
    private ArrayList<Jogo> jogadas;
    private int jogada;
    private Erro minijogo;

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
        turnoCreditos=0;
        minijogo=Erro.Critico;
    }

    // ____________GET____________

    public TipoJogador getTipoJogador(){
        return players.get(vezJogador).getTipo();
    }

    public String getNomeJogadorVez(){
        return players.get(vezJogador).getNome();
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

    public int getTurnoCreditos(){
        return turnoCreditos;
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

    public void setTurnoCreditos(){
        turnoCreditos++;
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

    public void resetTurnoCreditos(){
        turnoCreditos = -1;
    }

    public void resetBonus(int pontos){
        players.get(vezJogador).setBonus(RONDASPBONUS);
        if(pontos == -2){
            minijogo = Erro.NaoJogou;
        }else if (pontos < 5){
            minijogo = Erro.Perdeu;
        }else if(pontos == 5){
            minijogo = Erro.Ganhou;
        }else minijogo = Erro.Critico;
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

    public int jogaMinijogo(){
        MiniGame minijogo;
        switch (getMiniJogo()){
            case 0 -> minijogo = new RandomContas();
            case 1 -> minijogo = new EscrevePalavras();
            default -> {
                return -1;
            }
        }
        return minijogo.joga();
    }

    public Erro replayHistorico() {
        jogada++;
        try{
            this.vezJogador= jogadas.get(jogada).vezJogador;
            this.tipo = jogadas.get(jogada).tipo;
            this.tabuleiro = jogadas.get(jogada).tabuleiro;
            this.minijogo = jogadas.get(jogada).minijogo;
            this.players = jogadas.get(jogada).players;
            this.tabuleiro = jogadas.get(jogada).tabuleiro;

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

    //_________________OVERRIDE_______________________

    @Override
    public Object clone(){
        Jogo clone = null;
        try {
            clone = (Jogo) super.clone();
            clone.tipo = this.tipo ;
            clone.vezJogador = this.vezJogador;
            clone.minijogo = this.minijogo;
            ArrayList<Jogador> clonePlayers = new ArrayList<>(players.size());

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
}


