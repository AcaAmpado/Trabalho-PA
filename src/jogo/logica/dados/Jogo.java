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

        tabuleiro.get(1).add(new Peca(players.get(1), new int[]{1, 0} ));
        tabuleiro.get(1).add(new Peca(players.get(0), new int[]{1, 1} ));
        tabuleiro.get(1).add(new Peca(players.get(1), new int[]{1, 2} ));
        tabuleiro.get(5).add(new Peca(players.get(0), new int[]{5, 0} ));
        tabuleiro.get(5).add(new Peca(players.get(1), new int[]{5, 1} ));

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
        for(int i = ALTURA;i >= 0;i--){
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

    public boolean fazJogada(int coluna) {
        //TODO verifica se tem espaco na coluna(-1), joga, muda de vez e verifica se ganhou
        return false;
    }
}
