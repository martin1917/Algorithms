import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class AppController {

    @FXML
    private Spinner<Integer> countRepeat;

    @FXML
    private TextField div;

    @FXML
    private TextField midSquare;

    @FXML
    private TextField svert;

    @FXML
    private TextField mult;

    @FXML
    private TextField replaceBase;

    @FXML
    private TextField openAddr;

    @FXML
    private TextField chain;

    @FXML
    private TextField openAddr1;

    @FXML
    private TextField openAddr2;

    @FXML
    private TextField chain1;

    @FXML
    private TextField chain2;

    @FXML
    private Button firstButton;

    @FXML
    private Button secondButton;

    @FXML
    void initialize() {
        countRepeat.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, 100, 100));
        FuncHash h3 = (key) -> {
            double A = (Math.sqrt(5) - 1)/ 2;
            int m = 10000;

            long res = (int)(m * ((key*A)%1));

            return res;
        };
        FuncHashWrapper funcHashWrapper = new FuncHashWrapper("Умножение", h3);
        firstButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int [] score = App.getWinnerFunc(App.getHashFunction(), countRepeat.getValue());
                div.setText(score[0]+"");
                midSquare.setText(score[1]+"");
                svert.setText(score[2]+"");
                mult.setText(score[3]+"");
                replaceBase.setText(score[4]+"");
            }
        });
        secondButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double [] res = App.getInfo(funcHashWrapper);
                openAddr.setText(res[0]+"");
                openAddr1.setText(res[1]+"");
                openAddr2.setText(res[2]+"");
                chain.setText(res[3]+"");
                chain1.setText(res[4]+"");
                chain2.setText(res[5]+"");
            }
        });
    }
}
