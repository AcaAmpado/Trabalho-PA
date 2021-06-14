package jogo;

import javafx.application.Application;
import javafx.stage.Stage;
import jogo.ui.texto.QuatroUI;
import jogo.logica.MaquinaEstados;

public class Main{
    public static void main(String[] args)  {
        MaquinaEstados maquinaEstados= new MaquinaEstados();
        QuatroUI intFace = new QuatroUI(maquinaEstados);
        intFace.start();
    }
}
