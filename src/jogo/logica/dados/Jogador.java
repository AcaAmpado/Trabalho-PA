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

    public TipoJogador GetTipo(){
        return tipo;
    }

    public int getMinigame(){
        return minigame;
    }

    public void setMinigame(int numMG) {
        if(minigame < numMG-1)
            minigame++;
        else
            minigame=0;
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
