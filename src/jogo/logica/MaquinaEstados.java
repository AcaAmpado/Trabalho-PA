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

    public MaquinaEstados(){
        jogo = new Jogo();
        historico = new ArrayList<>();
        atual = new Inicio(jogo);
        try {
            carregaHistoricoF();
        }catch (IOException | ClassNotFoundException ignored){
        }
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
            atual=atual.start();
            return null;
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

    public ArrayList<ArrayList<String>> getTabuleiro() {
        return jogo.getTabuleiro();
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
        atual = atual.start();
    }

    public void selGameMode(int tipo){
        atual = atual.selGameMode(tipo);
    }

    public void comecaJogo(String player1, String player2) {
        atual = atual.comecaJogo(player1,player2);
    }

    //______________________GUARDA_CARREGA________________________

    public boolean guardaJogo(String nomeSave) {
        jogo.addLog("guardaJogo");
        ArrayList <Jogo> tempJogada = jogo.getJogadas();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeSave))) {
            out.writeObject(jogo);
            out.writeObject(atual.getStatus());
            out.writeObject(jogo.getJogadas().size());
            for (int i = 0; i < jogo.getJogadas().size(); i++) {
                out.writeObject(tempJogada.get(i));
            }
        } catch (IOException ignored) {
            return false;
        }
        return true;
    }

    public boolean carregaJogo(String name) {
        jogo.addLog("carregaJogo()");
        int temp;
        ArrayList<Jogo> jogadasTemp = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))) {
            jogo = (Jogo) in.readObject();
            atual = switchState((Situacao) in.readObject());
            temp = (int) in.readObject();
            for(int i = 0; i<temp;i++){
                jogadasTemp.add((Jogo) in.readObject());
            }
            jogo.setJogadas(jogadasTemp);
        } catch (IOException | ClassNotFoundException ignored) {
            return false;
        }
        return true;
    }

    private IEstado switchState(Situacao readObject) {
        switch (readObject){
            case Jogada -> {
                return new Jogada(jogo);
            }
            case PassarTurno -> {
                return new PassarTurno(jogo);
            }
        }
        return null;
    }

    //____________________JOGADAS________________

    public void fazJogada(int coluna) {
        atual = atual.fazJogada(coluna);
        if(jogo.getEstadoErro() == Erro.Ganhou || jogo.getEstadoErro() == Erro.TabuleiroCheio)
            jogoParaHist(jogo.getJogadas()); // guardar no historico
    }

    public void jogaPecaEspecial(int coluna) {
        atual = atual.jogaPecaEspecial(coluna);
    }

    public void jogaAI() {
        atual = atual.jogaAI();
        if(jogo.getEstadoErro() == Erro.Ganhou || jogo.getEstadoErro() == Erro.TabuleiroCheio)
            jogoParaHist(jogo.getJogadas()); // guardar no historico
    }

    //_________________PASSARTURNO_________________

    public void passaTurno() {
        atual =atual.passaTurno();
    }

    //____________________MINIJOGO______________________

    public void startMiniGame() {
        atual=atual.startMiniGame();
    }

    public void semMiniGame() {
        atual=atual.semMinigame();
    }

    public void jogaMinijogo(double numero){
        atual=atual.jogaMinijogo(numero);
    }

    public void jogaMinijogo(String input){
        atual=atual.jogaMinijogo(input);
    }

    //___________________HISTORICO_____________________

    private void jogoParaHist(ArrayList<Jogo> tempJogo) {
        historico.add(tempJogo);
        if(historico.size()>5){
            historico.remove(0);
        }
    }

    public void historicoJogos() {
        atual = atual.historicoJogos();
    }

    public void replayHistorico(int game) {
        jogo.innitReplay(historico.get(game));
        atual = atual.aguardaPassarTurno();
    }

    public void passaTurnoHistorico() {
        atual = atual.passaTurnoHistorico();
    }

    public void guardaHistoricoF(){
        jogo.addLog("guardaHistoricoF()");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Historico.dat"))) {
            out.writeObject(historico);
        }catch (IOException ignored){

        }
    }

    @SuppressWarnings("unchecked")
    public void carregaHistoricoF() throws IOException, ClassNotFoundException {
        jogo.addLog("carregaHistoricoF()");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Historico.dat"))) {
            historico = (ArrayList<ArrayList<Jogo>>) in.readObject();
        }
    }

    //__________________CREDITOS______________________

    public void usaCreditos(int numCr) {
        atual = atual.usaCreditos(numCr);

        if(jogo.getEstadoErro() == Erro.SemErros)
            jogoParaHist(jogo.getJogadas()); // guardar no historico
    }

    //___________________TERMINA______________________

    public void terminaJogo() {
        atual=atual.terminaJogo();
    }

    public Erro getEstadoErro() {
        return jogo.getEstadoErro();
    }

    public String getRules() {
        return jogo.getRules();
    }

    public String getPergunta() {
        return jogo.getPergunta();
    }

    public String getRegras() {
        return jogo.getRegras();
    }

    public char getSymbol() {
        return jogo.getSymbol();
    }

    public Integer getTempoRonda() {
        return jogo.getTempoRonda();
    }

    public void getLogsME() {
        System.out.println(jogo.getLogME());
    }
}
