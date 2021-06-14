package jogo.logica;

import jogo.logica.dados.Erro;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GameObserver {

    private StateMachine maquinaEstados;
    private final PropertyChangeSupport propertyChangeSupport;

    public GameObserver (StateMachine maquinaEstados){
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


    public void terminar() {
        maquinaEstados.terminar();
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
}
