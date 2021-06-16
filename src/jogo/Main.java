package jogo;

import jogo.ui.texto.QuatroUI;
import jogo.logica.MaquinaEstados;

public class Main{
    public static void main(String[] args)  {
        MaquinaEstados maquinaEstados= new MaquinaEstados();
        QuatroUI intFace = new QuatroUI(maquinaEstados);
        intFace.start();
    }
}
