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
    
    public MaquinaEstados(){
        jogo = new Jogo();
        atual = new Inicio(jogo);
        historico = new ArrayList<>();
        try {
            carregaHistoricoF();
        }catch (IOException | ClassNotFoundException ignored){}
    }

    public void start() {
        jogo.setupJogo();
        atual = atual.start();
    }

    public void selGameMode(int tipo){
        jogo.setTipo(tipo);
        atual = atual.selGameMode();
    }

    public boolean guardaJogo(String nomeSave) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeSave))) {
            out.writeObject(jogo);
            out.writeObject(atual.getStatus());
        } catch (IOException ignored) {
            return false;
        }
        return true;
    }

    public boolean carregaJogo(String name) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))) {
            jogo = (Jogo) in.readObject();
            atual = switchState((Situacao) in.readObject());
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("FUCK ME");
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

    public void historicoJogos() {
        atual = atual.historicoJogos();
    }

    public boolean comecaJogo(String player1, String player2) {
        jogo.setPlayer(player1, 'A');
        jogo.setPlayer(player2, 'B');
        if(jogo.comecaJogo()) {
            atual = atual.comecaJogo();
            tempJogo = new ArrayList<>();
            guardaState();
            return true;
        }
        return false;
    }

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

    public Erro fazJogada(int coluna) {
        switch(jogo.fazJogada(coluna)){
            case Ganhou -> {
                guardaState();
                jogoParaHist(tempJogo);
                atual = atual.acabaJogo();
                return Erro.Ganhou;
            }
            case ColunaCheia -> {
                return Erro.ColunaCheia;
            }
            case JogadaValida -> {
                guardaState();
                atual=atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> {
                guardaState();
                jogoParaHist(tempJogo);
                atual=atual.acabaJogo();
                return Erro.TabuleiroCheio;
            }
        }
        return Erro.Critico;
    }
    public void jogaPecaEspecial(int coluna) {
        if (jogo.jogaPecaEspecial(coluna) == Erro.JogadaValida) {
            guardaState();
            atual = atual.aguardaPassarTurno();
        }
    }

    public Erro jogaAI() {
        switch (jogo.jogaAI()) {
            case Ganhou -> {
                guardaState();
                jogoParaHist(tempJogo);
                atual = atual.acabaJogo();
                return Erro.Ganhou;
            }
            case JogadaValida -> {
                guardaState();
                atual = atual.aguardaPassarTurno();
                return Erro.JogadaValida;
            }
            case TabuleiroCheio -> {
                guardaState();
                jogoParaHist(tempJogo);
                atual = atual.acabaJogo();
                return Erro.TabuleiroCheio;
            }
        }
        return Erro.Critico;
    }

    private void jogoParaHist(ArrayList<Jogo> tempJogo) {
        historico.add(tempJogo);
        if(historico.size()>5){
            historico.remove(0);
        }
    }

    private void guardaState(){
        tempJogo.add( (Jogo) jogo.clone());
        jogo.resetMinijogo();
    }

    public void passaTurno() {
        jogo.setVezJogador();
        jogo.atualizaBonus();
        if(jogo.checkMiniGame())
            atual=atual.decideMiniGame();
        else
            atual=atual.passaTurno();
    }

    public void terminaJogo() {
        atual=atual.terminaJogo();
    }

    public void startMiniGame() {
        atual=atual.startMiniGame();
    }

    public void semMiniGame() {
        atual=atual.passaTurno();
        jogo.resetBonus(-2);
    }

    public int getMiniJogo() {
        return jogo.getMiniJogo();
    }

    public int jogaMinijogo() {
        int pontos;
        pontos=jogo.jogaMinijogo();
        if(pontos>=5){
            jogo.addPecaEspecial();
            atual=atual.continuaJogada();
        }
        else{
            atual=atual.passaTurno();
        }
        jogo.setMinigame();
        jogo.resetBonus(pontos);

        return pontos ;
    }

    public int getPecaEspecial() {
        return jogo.getPecaEspecial();
    }


    public String getHist() {
        if(historico.size()==0) {
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

    public void replayHistorico(int game) {
        jogo.innitReplay(historico.get(game));
        jogo.replayHistorico();
        atual = atual.aguardaPassarTurno();
    }

    public boolean isHistorico() {
        return jogo.isHistorico();
    }

    public void passaTurnoHistorico() {
        if(jogo.replayHistorico() == Erro.FimJogo)
            atual=atual.acabaJogo();
    }

    public Erro isMinigame() {
        return jogo.isMinigame();
    }

    public void guardaHistoricoF() throws IOException {
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
}
