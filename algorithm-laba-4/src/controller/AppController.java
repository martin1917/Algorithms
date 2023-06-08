package controller;

import java.util.List;
import java.util.ArrayList;


import logic.DigitTree;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AppController {

    @FXML private TextField txtFind;
    @FXML private Button btnFind;
    @FXML private TextField txtAdd;
    @FXML private Button btnAdd;
    @FXML private TextField txtRemove;
    @FXML private Button btnRemove;
    @FXML private TextArea words;
    @FXML private TextField timeFind;
    @FXML private Label warning;
    @FXML private Label warning2;
    @FXML private Label warning3;
    @FXML private Button btnRemoveAll;
    @FXML private CheckBox cbRaplace;

    List<String> setWords = new ArrayList<>();
    DigitTree tree = new DigitTree();

    private void addWord(String inputWord) {
        boolean exist = setWords.contains(inputWord);
        if (inputWord.isEmpty()){
            warning.setText("Пустая строка");
            return;
        }
        if(!exist) {
            setWords.add(inputWord);
            tree.add(inputWord);
            String textBefore = words.getText();
            if(textBefore.length() == 0) {
                words.setText(inputWord);
            } else {
                words.setText(textBefore + ", " + inputWord);
            }
            warning.setText("");
        } else {
            warning.setText("Слово уже добавлено");
        }
    }

    @FXML
    void initialize() {
        btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (cbRaplace.isSelected()) {
                String[] inputWord = txtAdd.getText().split(" ");
                for(String part : inputWord) {
                    addWord(part);
                }
            } else {
                String inputWord = txtAdd.getText();
                addWord(inputWord);
            }
        });

        btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String inputWord = txtRemove.getText();
            if(setWords.contains(inputWord)) {
                setWords.remove(inputWord);
                tree.remove(inputWord);

                StringBuilder builder = new StringBuilder();
                for(String word : setWords) {
                    builder.append(word);
                    builder.append(", ");
                }
                if (builder.length()>2) {
                    builder.delete(builder.length() - 2, builder.length());
                }
                
                words.setText(builder.toString());
                warning2.setText("");
            }
            else {
                warning2.setText("Такое слово не обнаружено");
            }
        });

        btnFind.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String inputWord = txtFind.getText();
            double start = System.nanoTime();
            boolean find = tree.find(inputWord);
            double time = (System.nanoTime() - start) / 1000;
            
            warning3.setText("");
            if(inputWord.isEmpty()) {
                warning3.setText("Вы не ввели слово для поиска");
                timeFind.setText("");
            } else if(find) {
                timeFind.setText("Найдено за "+time + " мкс");
            }else {
                timeFind.setText("Не найдено за "+time + " мкс");
            }
        });

        btnRemoveAll.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for(String word : setWords) {
                tree.remove(word);
            }
            setWords.clear();
            words.clear();
        });
    }
}
