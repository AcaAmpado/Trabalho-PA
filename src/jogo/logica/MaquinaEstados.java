package jogo.logica;

import jogo.logica.dados.Erro;
import jogo.logica.dados.TipoJogador;
import jogo.logica.estados.*;
import jogo.logica.dados.Jogo;

import java.io.*;
import java.util.ArrayList;

public class MaquinaEstados {

    Jogo jogo;
    IEstado atual;
    ArrayList<ArrayList<Jogo>> historico;
    ArrayList<Jogo> tempJogo;
    ArrayList<String> logME;

    public MaquinaEstados(){
        jogo = new Jogo();
        historico = new ArrayList<>();
        logME = new ArrayList<>();
        atual = new Inicio(jogo);
        addLog(Situacao.Inicio.toString());
        try {
            carregaHistoricoF();
        }catch (IOException | ClassNotFoundException ignored){}
    }

    //__________________GET___________________________

    public String getNomeJogadorVez(){
        return jogo.getNomeJogadorVez();
    }

    public TipoJogador getTipoJogador(){
        return jogo.getTipoJogador();
    }

    public Situacao getStatus(){
        return atual.getStatus();
    }

    public StringBuilder getBoard() {
        return jogo.getBoard();
    }

    public int getCreditos() {
        return jogo.getCreditos();
    }

    public int getMiniJogo() {
        return jogo.getMiniJogo();
    }

    public int getPecaEspecial() {
        return jogo.getPecaEspecial();
    }

    public String getHist() {
        if(historico.size()==0) {
            addLog(Situacao.GameMode.toString());
            atual=atual.start();
            return Erro.SemJogosHist.toString();
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < getHistoricoNum(); i++){
            sb.append("\nJogo ").append(i+1).append(":").append(getJogoHistorico(i));
        }
        return sb.toString();
    }

    public int getHistoricoNum() {
        return historico.size();
    }

    public String getJogoHistorico(int i) {
        return historico.get(i).get(0).toString();
    }

    //_______________________LOG_____________________

    public ArrayList<String> getLogME(){
        return logME;
    }

    public void addLog(String log){
        logME.add(log);
    }

    //_____________________CHECKS__________________

    public boolean isHistorico() {
        return jogo.isHistorico();
    }

    public Erro isMinigame() {
        return jogo.isMinigame();
    }

    //______________________________________

    public void start() {
        addLog("start()");
        addLog(Situacao.GameMode.toString());
        atual = atual.start();
    }

    public void selGameMode(int tipo){
        addLog("selGameMode()");
        jogo.setTipo(tipo);
        addLog(Situacao.NamePlayers.toString());
        atual = atual.selGameMode();
    }

    public boolean comecaJogo(String player1, String player2) {
        addLog("comecaJogo()");
        jogo.setPlayer(player1, 'A');
        jogo.setPlayer(player2, 'B');
        if(jogo.comecaJogo()) {
            addLog(Situacao.Jogada.toString());
            atual = atual.comecaJogo();
            tempJogo = new ArrayList<>();
            guardaState();
            return true;
        }
        return false;
    }

    //______________________GUARDA_CARREGA________________________

    public boolean guardaJogo(String nomeSave) {
        addLog("guardaJogo()");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeSave))) {
            out.writeObject(jogo);
            out.writeObject(atual.getStatus());
            out.writeObject(tempJogo);
        } catch (IOException ignored) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean carregaJogo(String name) {
        addLog("carregaJogo()");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))) {
            jogo = (Jogo) in.readObject();
            addLog(Situacao.Jogada.toString());
            atual = switchState((Situacao) in.readObject());
            tempJogo = (ArrayList<Jogo>) in.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
            return false;
        }
        return true;
    }

    private IEstado switchState(Situacao readObject) {
        switch (readObject){
            case Inicio -> {
                return new Inicio(jogo);
            }
            case Jogada -> {
                return new Jogada(jogo);
            }
            case DecisaoMiniGame  -> {
                return new DecisaoMiniGame(jogo);
            }
            case MiniGame -> {
                return new MiniGame(jogo);
            }
            case GameMode -> {
                return new GameMode(jogo);
            }
            case GameOver -> {
                return new GameOver(jogo);
            }
            case PassarTurno -> {
                return new PassarTurno(jogo);
            }
            case NamePlayers -> {
                return new NamePlayers(jogo);
            }
            case Historico -> {
                return new EscolhaJogoH(jogo);
            }
        }
        return null;
    }

    //____________________JOGADAS________________

    public Erro fazJogada(int coluna) {
        addLog("fazJogada()");
        switch(jogo.fazJogada(coluna)){
            case Ganhou -> {
                guardaState();
                jogoParaHist(tempJogo);
                addLog(Situacao.GameOver.toString());
                atual = atual.terminaJogo();
                return Erro.Ganhou;
            }
            case ColunaCheia -> {
                return Erro.ColunaCheia;
            }
            case JogadaValida -> {
                guardaState();
                addLog(Situacao.PassarTurno.toString());
                atual=atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> {
                guardaState();
                jogoParaHist(tempJogo);
                addLog(Situacao.GameOver.toString());
                atual=atual.terminaJogo();
                return Erro.TabuleiroCheio;
            }
        }
        return Erro.Critico;
    }

    public void jogaPecaEspecial(int coluna) {
        addLog("jogaPecaEspecial()");
        if (jogo.jogaPecaEspecial(coluna) == Erro.JogadaValida) {
            guardaState();
            addLog(Situacao.PassarTurno.toString());
            atual = atual.aguardaPassarTurno();
        }
    }

    public Erro jogaAI() {
        addLog("jogaAI()");
        switch (jogo.jogaAI()) {
            case Ganhou -> {
                guardaState();
                jogoParaHist(tempJogo);
                addLog(Situacao.GameOver.toString());
                atual = atual.terminaJogo();
                return Erro.Ganhou;
            }
            case JogadaValida -> {
                guardaState();
                addLog(Situacao.PassarTurno.toString());
                atual = atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> {
                guardaState();
                jogoParaHist(tempJogo);
                addLog(Situacao.GameOver.toString());
                atual = atual.terminaJogo();
                return Erro.TabuleiroCheio;
            }
        }
        return Erro.Critico;
    }

    //_________________PASSARTURNO_________________

    public void passaTurno() {
        addLog("passaTurno()");
        jogo.setVezJogador();
        jogo.atualizaBonus();
        if(jogo.checkMiniGame()){
            addLog(Situacao.DecisaoMiniGame.toString());
            atual=atual.decideMiniGame();
        }
        else{
            addLog(Situacao.Jogada.toString());
            atual=atual.passaTurno();
        }
    }

    //____________________MINIJOGO______________________

    public void startMiniGame() {
        addLog(Situacao.MiniGame.toString());
        atual=atual.startMiniGame();
    }

    public void semMiniGame() {
        addLog("semMiniGame()");
        atual=atual.passaTurno();
        jogo.resetBonus(-2);
    }

    public int jogaMinijogo() {
        addLog("jogaMiniJogo");
        int pontos;
        pontos=jogo.jogaMinijogo();
        if(pontos>=5){
            jogo.addPecaEspecial();
            addLog(Situacao.Jogada.toString());
            atual=atual.continuaJogada();
        }
        else{
            addLog(Situacao.PassarTurno.toString());
            atual=atual.aguardaPassarTurno();
        }
        jogo.setMinigame();
        jogo.resetBonus(pontos);
        return pontos ;
    }

    //___________________HISTORICO_____________________

    private void jogoParaHist(ArrayList<Jogo> tempJogo) {
        historico.add(tempJogo);
        if(historico.size()>5){
            historico.remove(0);
        }
    }

    public void historicoJogos() {
        addLog(Situacao.Historico.toString());
        atual = atual.historicoJogos();
    }

    public void replayHistorico(int game) {
        jogo.innitReplay(historico.get(game));
        jogo.replayHistorico();
        addLog(Situacao.PassarTurno.toString());
        atual = atual.aguardaPassarTurno();
    }

    public Erro passaTurnoHistorico() {
        if(jogo.replayHistorico() == Erro.FimJogo){
            addLog(Situacao.GameOver.toString());
            atual=atual.terminaJogo();
            return Erro.Ganhou;
        }
        return Erro.JogadaValida;
    }

    public void guardaHistoricoF() throws IOException {
        addLog("guardaHistoricoF()");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Historico.dat"))) {
            out.writeObject(historico);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregaHistoricoF() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Historico.dat"))) {
            historico = (ArrayList<ArrayList<Jogo>>) in.readObject();
        }
    }

    private void guardaState(){
        tempJogo.add((Jogo) jogo.clone());
        jogo.setTurnoCreditos();
        jogo.resetMinijogo();
    }

    //__________________CREDITOS______________________

    public Erro usaCreditos(int numCr) {
        addLog("usaCreditos()");
        Jogo temporario;
        if(jogo.getCreditos() < numCr)
            return Erro.SemCreditos;
        if(jogo.getTurnoCreditos() < numCr){
            return Erro.VoltarAntesCreditos;
        }
        try{
            temporario = (Jogo) tempJogo.get(tempJogo.size()-1-numCr).clone();
        }catch (IndexOutOfBoundsException ignored){
            return Erro.SemJogadas;
        }
        temporario.setCreditos( jogo.getCreditos ()-numCr, jogo.getVezJogador());
        temporario.resetBonus(6);
        temporario.setPecaEspecial(0,jogo.getPecaEspecial(0));
        temporario.setPecaEspecial(1,jogo.getPecaEspecial(1));
        jogo = temporario;
        if(tempJogo.size()-1-numCr!=0)
            jogo.setVezJogador();
        jogo.resetTurnoCreditos();
        addLog(Situacao.Jogada.toString());
        atual = new Jogada(jogo);
        return Erro.JogadaValida;
    }

    //___________________TERMINA______________________

    public void terminaJogo() {
        addLog(Situacao.GameOver.toString());
        atual=atual.terminaJogo();
    }
}
