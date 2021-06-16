package jogo.ui.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.logica.GameObserver;
import jogo.logica.Situacao;
import jogo.logica.dados.Erro;
import jogo.logica.dados.TipoJogador;

import java.util.Optional;

import static jogo.ui.gui.Constantes.DIM_BOLA;
import static jogo.ui.gui.Constantes.FONTE_TEXTO;

public class PassarTurnoPane extends VBox {
    private final GameObserver gameObserver;
    private Label jogadorLabel;
    private Label creditosLabel;
    private Label pecaEspecialLabel;
    private Button btPassarTurno;
    private Button btGuardaJogo;
    private TextField guardaJogoNome;
    private Button btCreditos;
    private TextField creditosText;
    private HBox creditosBox;
    private Button btSair;
    private Label erro;
    private Label labelMensagem;
    private HBox guardaJogoBox;
    private Circle corJogador;
    private Button btLogs;

    public PassarTurnoPane(GameObserver gameObserver){
        this.gameObserver=gameObserver;

        criarVista();

        registarObserver();

        registarListeners();

        atualiza();
    }

    private void registarObserver() {
        gameObserver.addPropertyChangeListener("estados", evt -> atualiza());
    }

    private void criarVista() {

        corJogador = new Circle();
        corJogador.setRadius(DIM_BOLA);


        jogadorLabel = new Label();
        jogadorLabel.setFont(FONTE_TEXTO);
        jogadorLabel.setTextFill(Color.WHITE);
        jogadorLabel.setPadding(new Insets(10));

        creditosLabel = new Label();
        creditosLabel.setFont(FONTE_TEXTO);
        creditosLabel.setTextFill(Color.WHITE);
        creditosLabel.setPadding(new Insets(10));

        pecaEspecialLabel = new Label();
        pecaEspecialLabel.setFont(FONTE_TEXTO);
        pecaEspecialLabel.setTextFill(Color.WHITE);
        pecaEspecialLabel.setPadding(new Insets(10));

        HBox especiaisBox = new HBox(10);
        especiaisBox.getChildren().addAll(creditosLabel,pecaEspecialLabel,corJogador);
        especiaisBox.setAlignment(Pos.CENTER);
        especiaisBox.setPadding(new Insets(10));

        VBox dadosBox = new VBox(10);
        dadosBox.getChildren().addAll(jogadorLabel,especiaisBox);
        dadosBox.setAlignment(Pos.CENTER);
        dadosBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        dadosBox.setPadding(new Insets(10));


        btPassarTurno = new Button("Passar Turno");
        btPassarTurno.setFont(FONTE_TEXTO);

        HBox passarTurnoBox = new HBox(10);
        passarTurnoBox.getChildren().addAll(btPassarTurno);
        passarTurnoBox.setAlignment(Pos.CENTER);
        passarTurnoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        passarTurnoBox.setPadding(new Insets(10));


        btGuardaJogo = new Button("Guardar Jogo");
        btGuardaJogo.setFont(FONTE_TEXTO);

        guardaJogoNome = new TextField();
        guardaJogoNome.setFont(FONTE_TEXTO);
        guardaJogoNome.setPromptText("Nome do ficheiro");

        Label guardaJogoLabel = new Label("Nome do Ficheiro: ");
        guardaJogoLabel.setFont(FONTE_TEXTO);
        guardaJogoLabel.setTextFill(Color.WHITE);

        HBox textGuardaBox = new HBox(10);
        textGuardaBox.getChildren().addAll(guardaJogoLabel, guardaJogoNome);
        textGuardaBox.setAlignment(Pos.CENTER);

        VBox btGuardaBox = new VBox(10);
        btGuardaBox.setSpacing(20);
        btGuardaBox.getChildren().addAll(btGuardaJogo,textGuardaBox);
        btGuardaBox.setAlignment(Pos.CENTER);

        guardaJogoBox = new HBox(10);
        guardaJogoBox.getChildren().addAll(btGuardaBox);
        guardaJogoBox.setAlignment(Pos.CENTER);
        guardaJogoBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        guardaJogoBox.setPadding(new Insets(10));


        btCreditos = new Button("Utilizar Créditos");
        btCreditos.setFont(FONTE_TEXTO);

        Label creditosLabel = new Label("Numero de Créditos: ");
        creditosLabel.setFont(FONTE_TEXTO);
        creditosLabel.setTextFill(Color.WHITE);

        creditosText = new TextField();
        creditosText.setFont(FONTE_TEXTO);
        creditosText.setPromptText("Numero de Creditos");

        HBox textCreditosBox = new HBox(10);
        textCreditosBox.getChildren().addAll(creditosLabel, creditosText);
        textCreditosBox.setAlignment(Pos.CENTER);

        VBox btCreditosBox = new VBox(10);
        btCreditosBox.setSpacing(20);
        btCreditosBox.getChildren().addAll(btCreditos,textCreditosBox);
        btCreditosBox.setAlignment(Pos.CENTER);

        creditosBox = new HBox(10);
        creditosBox.getChildren().addAll(btCreditosBox);
        creditosBox.setAlignment(Pos.CENTER);
        creditosBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        creditosBox.setPadding(new Insets(10));


        btLogs = new Button("Logs");
        btLogs.setFont(FONTE_TEXTO);

        HBox logsBox = new HBox(10);
        logsBox.getChildren().addAll(btLogs);
        logsBox.setAlignment(Pos.CENTER);
        logsBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        logsBox.setPadding(new Insets(10));


        btSair = new Button("Sair");
        btSair.setFont(FONTE_TEXTO);

        HBox sairBox = new HBox(10);
        sairBox.getChildren().addAll(btSair);
        sairBox.setAlignment(Pos.CENTER);
        sairBox.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        sairBox.setPadding(new Insets(10));

        erro = new Label();
        erro.setVisible(false);
        erro.setFont(FONTE_TEXTO);
        erro.setTextFill(Color.RED);

        labelMensagem = new Label();
        labelMensagem.setVisible(false);
        labelMensagem.setFont(FONTE_TEXTO);
        labelMensagem.setTextFill(Color.GREEN);


        VBox menu = new VBox(10);
        menu.getChildren().addAll(dadosBox, passarTurnoBox, guardaJogoBox, creditosBox, logsBox, sairBox, erro, labelMensagem);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        getChildren().addAll(menu);
    }

    private void registarListeners(){
        btPassarTurno.setOnAction(e -> {
            if(gameObserver.isHistorico()){
                gameObserver.passaTurnoHistorico();
                if(gameObserver.getEstadoErro()== Erro.FimJogo){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Resultado do jogo");
                    alert.setHeaderText(null);
                    alert.setContentText("O jogo acabou!\nO jogador "+ gameObserver.getNomeJogadorVez() +" foi o ultimo a jogar!");
                    alert.showAndWait();
                }
                else labelMensagem.setVisible(false);
            }
            else gameObserver.passaTurno();
        });

        btGuardaJogo.setOnAction((e)->{
            String value;
            value =  guardaJogoNome.getText();
            if(value.length() < 1){
                guardaJogoNome.requestFocus();
                return;
            }
            if(gameObserver.guardaJogo(value+".dat")){
                erro.setText("Jogo Guardado com Sucesso!");
            }
            else{
                erro.setText("Erro a Guardar o Jogo!");
            }
            erro.setTextFill(Color.RED);
            erro.setVisible(true);
        });

        btCreditos.setOnAction(e -> {
            String value = creditosText.getText().trim();
            if(value.length()<1){
                creditosText.requestFocus();
                erro.setText("Introduza um número nos créditos");
                erro.setTextFill(Color.RED);
                erro.setVisible(true);
                return;
            }
            int numero;
            try{
                numero = Integer.parseInt(value);
            }catch (NumberFormatException ignored){
                creditosText.requestFocus();
                erro.setText("Introduza um número nos créditos");
                erro.setTextFill(Color.RED);
                erro.setVisible(true);
                return;
            }
            if(numero>0){
                gameObserver.usaCreditos(numero);
                erro.setVisible(false);
            }
            else{
                erro.setText("Numero tem que ser superior a 0!");
                erro.setTextFill(Color.RED);
                erro.setVisible(true);
            }
            switch (gameObserver.getEstadoErro()){
                case SemCreditos -> {
                    erro.setText("Nao possui créditos suficientes!");
                    erro.setTextFill(Color.RED);
                    erro.setVisible(true);
                }
                case SemJogadas -> {
                    erro.setText("Nao existem jogadas para o número de créditos!");
                    erro.setTextFill(Color.RED);
                    erro.setVisible(true);
                }
                case SemErros -> erro.setVisible(false);
            }
        });

        btLogs.setOnAction((e)->gameObserver.getLogsME());

        btSair.setOnAction( (e)-> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fechar Jogo?");
            alert.setHeaderText(null);
            alert.setContentText("Tem a certeza que quer sair do jogo?");
            alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if (result.get() == ButtonType.YES){
                    if(!gameObserver.isHistorico()){
                        TextInputDialog dialog = new TextInputDialog("ficheiro");
                        dialog.setTitle("Guarda Jogo");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Introduza o nome do ficheiro:");
                        Optional<String> resultado = dialog.showAndWait();
                        if (resultado.isPresent()){
                            gameObserver.guardaJogo(resultado.get()+".dat");
                        }else e.consume();
                    }
                    gameObserver.terminaJogo();
                } else {
                    e.consume();
                }
            }
        });
    }

    private void atualiza() {
        if(gameObserver.getStatus() == Situacao.PassarTurno){
            if(gameObserver.getSymbol()=='A')
                corJogador.setFill(Color.BLUE);
            else if (gameObserver.getSymbol()=='B')
                corJogador.setFill(Color.RED);
            else
                corJogador.setFill(Color.WHITE);
            jogadorLabel.setText( "Jogador: " + gameObserver.getNomeJogadorVez() + "\tTipo de Jogador: " +  gameObserver.getTipoJogador().toString());
            creditosLabel.setText("Creditos: " + gameObserver.getCreditos());
            creditosText.setText("");
            guardaJogoNome.setText("");
            pecaEspecialLabel.setText("Peca Especial: " + gameObserver.getPecaEspecial());
            creditosBox.setVisible(gameObserver.getTipoJogador() != TipoJogador.AI);
            creditosBox.setVisible(gameObserver.getTipoJogador() != TipoJogador.AI);
            if(gameObserver.isHistorico()){
                guardaJogoBox.setVisible(false);
                creditosBox.setVisible(false);
                erro.setTextFill(Color.WHITE);
                erro.setVisible(true);
                switch (gameObserver.isMinigame()){
                    case Perdeu -> erro.setText("O jogador jogou um minijogo e perdeu!");
                    case Ganhou -> erro.setText("O jogador jogou um minijogo e ganhou uma peça especial!");
                    case NaoJogou -> erro.setText("O jogador optou por nao jogar o minijogo");
                    case Especial -> erro.setText("O jogador jogou uma peca especial");
                    case Creditos -> erro.setText("O jogador utilizou creditos");
                    default -> erro.setVisible(false);
                }
            }else{
                guardaJogoBox.setVisible(true);
                creditosBox.setVisible(true);
            }

            this.setVisible(true);
        }
        else this.setVisible(false);
    }


}
