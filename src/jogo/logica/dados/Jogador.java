package jogo.logica.dados;

public class Jogador {

    private String nome;
    private TipoJogador tipo;
    private int creditos;
    private int rondasBonus;
    //TODO peças especiais

    public Jogador(String nome, TipoJogador tipo){
        this.nome=nome;
        this.tipo=tipo;
        this.creditos=0;
        this.rondasBonus=-1;
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
        //TODO
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
}
