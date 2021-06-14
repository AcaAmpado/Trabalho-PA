package jogo.logica;

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
    }
}
