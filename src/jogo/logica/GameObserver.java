package jogo.logica;

import jogo.logica.dados.Erro;
import jogo.logica.dados.TipoJogador;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GameObserver {

    private MaquinaEstados maquinaEstados;
    private final PropertyChangeSupport propertyChangeSupport;

    public GameObserver (MaquinaEstados maquinaEstados){
        this.maquinaEstados=maquinaEstados;
        propertyChangeSupport= new PropertyChangeSupport(maquinaEstados);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public ArrayList<ArrayList<String>> getTabuleiro() {
        return maquinaEstados.getTabuleiro();
    }

    public String getRegras() {
        return maquinaEstados.getRegras();
    }

    public Situacao getStatus() {
        return maquinaEstados.getStatus();
    }

    public Erro getEstadoErro() {
        return maquinaEstados.getEstadoErro();
    }

    public void terminaJogo() {
        maquinaEstados.terminaJogo();
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public void start() {
        maquinaEstados.start();
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public void selGameMode(int tipo) {
        maquinaEstados.selGameMode(tipo);
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public void carregaJogo(String value) {
        maquinaEstados.carregaJogo(value);
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public void historicoJogos() {
        maquinaEstados.historicoJogos();
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public void comecaJogo(String jog1, String jog2) {
        maquinaEstados.comecaJogo(jog1,jog2);
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public String getNomeJogadorVez() {
        return maquinaEstados.getNomeJogadorVez();
    }

    public TipoJogador getTipoJogador() {
        return maquinaEstados.getTipoJogador();
    }

    public int getCreditos() {
        return maquinaEstados.getCreditos();
    }

    public int getPecaEspecial() {
        return maquinaEstados.getPecaEspecial();
    }

    public void usaCreditos(int numero) {
        maquinaEstados.usaCreditos(numero);
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public void fazJogada(int value) {
        maquinaEstados.fazJogada(value);
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public boolean guardaJogo(String nomeFich) {
        return maquinaEstados.guardaJogo(nomeFich);
    }

    public void jogaPecaEspecial(int value) {
        maquinaEstados.jogaPecaEspecial(value);
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }

    public void jogaAI() {
        maquinaEstados.jogaAI();
        propertyChangeSupport.firePropertyChange("yeet",null,null);
    }
}
