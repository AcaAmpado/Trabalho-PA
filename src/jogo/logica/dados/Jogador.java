package jogo.logica.dados;

public class Jogador {

    private String nome;
    private TipoJogador tipo;
    private int creditos;
    private int rondasBonus;
    private int pecaEspecial;
    private char symbol;

    public Jogador(String nome, TipoJogador tipo,char symbol){
        this.nome=nome;
        this.tipo=tipo;
        this.creditos=0;
        this.rondasBonus=-1;
        this.pecaEspecial=0;
        this.symbol = symbol;
    }

    public TipoJogador GetTipo(){
        return tipo;
    }

    public void SetCreditos(int creditos){
        this.creditos=creditos;
    }

    public void SetBonus(int rondas){
        this.rondasBonus=rondas;
    }

    public void AdicionaPecaEspecial(){
        this.pecaEspecial++;
    }
    @Override
    public String toString() {
        return "Jogador: "+nome+" - "+tipo.toString()+" - "+ creditos +" - "+ rondasBonus;
    }

    public String getNome() {
        return nome;
    }

    public TipoJogador getTipo() {
        return tipo;
    }

    public void removePecaEspecial() {
        this.pecaEspecial--;
    }

    public int getPecaEspecial() {
        return this.pecaEspecial;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getBonus() {
        return this.rondasBonus;
    }

    public void decrementaBonus() {
        this.rondasBonus--;
    }
}
