package jogo.logica.dados;

import java.io.Serial;
import java.io.Serializable;

public class Jogador implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private final String nome;
    private final TipoJogador tipo;
    private final char symbol;
    private int creditos;
    private int rondasBonus;
    private int pecaEspecial;
    private int minigame;

    public Jogador(String nome, TipoJogador tipo, char symbol, int numMG) {
        this.nome = nome;
        this.tipo = tipo;
        this.symbol = symbol;
        this.creditos = 0;
        this.rondasBonus = -1;
        this.pecaEspecial = 0;
        this.minigame = (int) (Math.random() * numMG);
    }
    //________________GET________________
    public TipoJogador getTipo(){
        return tipo;
    }

    public int getMinigame(){
        return minigame;
    }

    public String getNome() {
        return nome;
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

    public int getCreditos() {
        return creditos;
    }

    //________________SET_________________

    public void setMinigame(int numMG) {
        if(minigame < numMG-1)
            minigame++;
        else
            minigame=0;
    }

    public void setCreditos(int creditos){
        this.creditos=creditos;
    }

    public void setBonus(int rondas){
        this.rondasBonus=rondas;
    }

    public void adicionaPecaEspecial(){
        this.pecaEspecial++;
    }

    public void removePecaEspecial() {
        this.pecaEspecial--;
    }

    public void decrementaBonus() {
        this.rondasBonus--;
    }

    public void setPecaEspecial(int num) {
        this.pecaEspecial=num;
    }

    //_____________________OVERRIDES________________

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return null;
    }

    @Override
    public String toString() {
        return "Jogador: "+nome+" - S "+ symbol +" - Tp "+tipo.toString()+" - Cr "+ creditos +" - RB "+ rondasBonus + " - PE "+ pecaEspecial +" - MG "+ minigame;
    }
}
