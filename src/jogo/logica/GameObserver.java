package jogo.logica;

import jogo.logica.dados.Erro;
import jogo.logica.dados.TipoJogador;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import static jogo.logica.dados.Erro.Ganhou;

public class GameObserver {

    private final MaquinaEstados maquinaEstados;
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
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public void start() {
        maquinaEstados.start();
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public void selGameMode(int tipo) {
        maquinaEstados.selGameMode(tipo);
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public boolean carregaJogo(String value) {
        boolean bool = maquinaEstados.carregaJogo(value);
        propertyChangeSupport.firePropertyChange("estados",null,null);
        return bool;
    }

    public void historicoJogos() {
        maquinaEstados.historicoJogos();
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public void comecaJogo(String jog1, String jog2) {
        maquinaEstados.comecaJogo(jog1,jog2);
        propertyChangeSupport.firePropertyChange("estados",null,null);
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
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public void fazJogada(int value) {
        maquinaEstados.fazJogada(value);
        if(maquinaEstados.getEstadoErro() == Ganhou || maquinaEstados.getEstadoErro() == Erro.TabuleiroCheio)
            propertyChangeSupport.firePropertyChange("historico",null,null);
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public boolean guardaJogo(String nomeFich) {
        return maquinaEstados.guardaJogo(nomeFich);
    }

    public void jogaPecaEspecial(int value) {
        maquinaEstados.jogaPecaEspecial(value);
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public void jogaAI() {
        maquinaEstados.jogaAI();
        if(maquinaEstados.getEstadoErro() == Ganhou || maquinaEstados.getEstadoErro() == Erro.TabuleiroCheio)
            propertyChangeSupport.firePropertyChange("historico",null,null);
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public boolean isHistorico() {
        return maquinaEstados.isHistorico();
    }

    public void passaTurnoHistorico() {
        maquinaEstados.passaTurnoHistorico();
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public void passaTurno() {
        maquinaEstados.passaTurno();
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public int getHistoricoNum() {
        return maquinaEstados.getHistoricoNum();
    }

    public String getJogoHistorico(int i) {
        return maquinaEstados.getJogoHistorico(i);
    }

    public void replayHistorico(int id) {
        maquinaEstados.replayHistorico(id);
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public Erro isMinigame() {
        return maquinaEstados.isMinigame();
    }

    public void guardaHistoricoF(){
        maquinaEstados.guardaHistoricoF();
    }

    public char getSymbol() {
        return maquinaEstados.getSymbol();
    }

    public void startMinigame() {
        maquinaEstados.startMiniGame();
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public void semMinigame() {
        maquinaEstados.semMiniGame();
        propertyChangeSupport.firePropertyChange("estados",null,null);
    }

    public int getMiniJogo() {
        return maquinaEstados.getMiniJogo();
    }

    public String getRules() {
        return maquinaEstados.getRules();
    }

    public String getPergunta() {
        return maquinaEstados.getPergunta();

    }

    public void jogaMinijogo(String input) {
        maquinaEstados.jogaMinijogo(input);
        propertyChangeSupport.firePropertyChange("minijogoPergunta",null,null);
        switch (maquinaEstados.isMinigame()) {
            case Ganhou, Perdeu -> propertyChangeSupport.firePropertyChange("estados",null,null);
        }
    }

    public void jogaMinijogo(Double input) {
        maquinaEstados.jogaMinijogo(input);
        propertyChangeSupport.firePropertyChange("minijogoPergunta",null,null);
        switch (maquinaEstados.isMinigame()) {
            case Ganhou, Perdeu -> propertyChangeSupport.firePropertyChange("estados",null,null);
        }
    }

    public Integer getTempoRonda() {
        return maquinaEstados.getTempoRonda();
    }

    public void getLogsME() {
        maquinaEstados.getLogsME();
    }
}
