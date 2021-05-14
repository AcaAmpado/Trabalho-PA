package jogo.logica.dados;

import java.util.ArrayList;

public class Jogo {
    public static final int NUMCREDITOS = 5;
    public static final int RONDASPBONUS = 4;
    public static final int LARGURA = 7;
    public static final int ALTURA = 6;
    private int tipo;
    private final ArrayList<Jogador>  players;
    private final ArrayList<ArrayList<Peca>> tabuleiro;
    private int vezJogador;

    public Jogo (){
        players= new ArrayList<>();
        tabuleiro = new ArrayList<>();
        for (int i = 0; i < LARGURA ; i++) {
            tabuleiro.add(new ArrayList<>());
        }
    }

    public void setTipo(int tipo){
        this.tipo = tipo;
    }

    public void setPlayer(String nome){
        if(tipo==1) // PvP
            this.players.add(new Jogador(nome,TipoJogador.Player));
        if(tipo==2) //PvAI
            if(players.size()==0)
                this.players.add(new Jogador(nome,TipoJogador.Player));
            else
                this.players.add(new Jogador(nome,TipoJogador.AI));
        if(tipo==3) //AIvAI
            this.players.add(new Jogador(nome,TipoJogador.AI));
    }

    public boolean comecaJogo() {
        if(players.size()!=2){
            return false;
        }
        for(Jogador jog:players) {
            if(jog.GetTipo()==TipoJogador.Player){
                jog.SetBonus(RONDASPBONUS);
                jog.SetCreditos(NUMCREDITOS);
            }
        }
        vezJogador= (int) (Math.random()%2);
/*
        tabuleiro.get(1).add(new Peca(players.get(1), 1, new int[]{1, 0} ));
        tabuleiro.get(1).add(new Peca(players.get(0), 0, new int[]{1, 1} ));
        tabuleiro.get(1).add(new Peca(players.get(1), 1, new int[]{1, 2} ));
        tabuleiro.get(5).add(new Peca(players.get(0), 0, new int[]{5, 0} ));
        tabuleiro.get(5).add(new Peca(players.get(1), 1, new int[]{5, 1} ));
*/
        return true;
    }

    public TipoJogador getTipoJogador(int nJogador){
        return players.get(nJogador).getTipo();
    }

    public TipoJogador getTipoJogador(){
        return players.get(vezJogador).getTipo();
    }

    public String getNomeJogadorVez(){
        return players.get(vezJogador).getNome();
    }

    public boolean guardaJogo(String nomeSave) {
        //TODO Guardar todos os dados relevantes para ficheiro
        return true;
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
                   board.append(tabuleiro.get(j).get(i).getSymbol()).append(" | "); //Tecnicamente imprime as pecas
            }
            board.append("\n");
        }
        board.append("-----------------------------\n");
        board.append("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n");
        return board;
    }

    public void setVezJogador(){
        vezJogador=(vezJogador+1)%2;
    }

    public boolean isWinner(){

        for(int linha = 0; linha<ALTURA;linha++){ // verifica se tem na horizontal
            for(int col = 0; col<LARGURA-3;col++){
                try{
                    if(tabuleiro.get(col).get(linha).getSymbol()==vezJogador
                    && tabuleiro.get(col+1).get(linha).getSymbol()==vezJogador
                    && tabuleiro.get(col+2).get(linha).getSymbol()==vezJogador
                    && tabuleiro.get(col+3).get(linha).getSymbol()==vezJogador){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }

        for(int col = 0; col<LARGURA;col++){ // verifica se tem na vertical
            for(int linha = 0; linha<ALTURA-3;linha++){
                try{
                    if(tabuleiro.get(col).get(linha).getSymbol()==vezJogador
                            && tabuleiro.get(col).get(linha+1).getSymbol()==vezJogador
                            && tabuleiro.get(col).get(linha+2).getSymbol()==vezJogador
                            && tabuleiro.get(col).get(linha+3).getSymbol()==vezJogador){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }

        for(int linha = 3; linha<ALTURA;linha++){ // verifica se tem na diagonal asc
            for(int col = 0; col < LARGURA-3;col++){
                try{
                    if(tabuleiro.get(col).get(linha).getSymbol()==vezJogador
                            && tabuleiro.get(col-1).get(linha-1).getSymbol()==vezJogador
                            && tabuleiro.get(col-2).get(linha-2).getSymbol()==vezJogador
                            && tabuleiro.get(col-3).get(linha-3).getSymbol()==vezJogador){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }

        for(int linha = 3; linha < ALTURA ;linha++){ // verifica se tem na diagonal desc
            for(int col = 0; col < LARGURA - 3 ;col++){
                try{
                    if(tabuleiro.get(col).get(linha).getSymbol()==vezJogador
                            && tabuleiro.get(col+1).get(linha-1).getSymbol()==vezJogador
                            && tabuleiro.get(col+2).get(linha-2).getSymbol()==vezJogador
                            && tabuleiro.get(col+3).get(linha-3).getSymbol()==vezJogador){
                        return true;
                    }
                }catch (IndexOutOfBoundsException ignored){}
            }
        }
        return false;
    }

    public Erro fazJogada(int coluna) {
        if(tabuleiro.get(coluna).size()>=ALTURA){
            return Erro.ColunaCheia;
        }
        tabuleiro.get(coluna).add(new Peca(players.get(vezJogador), vezJogador ,new int []{coluna,tabuleiro.get(coluna).size()}));
        if(isWinner()) {
            return Erro.Ganhou;
        }
        setVezJogador();
        return Erro.JogadaValida;
        //TODO verifica se tem espaco na coluna(-1), joga, muda de vez e verifica se ganhou

    }
}
