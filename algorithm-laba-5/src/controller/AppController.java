package controller;

import java.util.List;

import Data.InfoAboutSort;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import logic.Solution;
import logic.SolutionWithInfo;
import logic.SortMethod;

public class AppController {

    @FXML private TableView<List<Integer>> bubbleTable;
    @FXML private Button btnBubble;

    @FXML private TableView<List<Integer>> selectTable;
    @FXML private Button btnSelect;

    @FXML private TableView<List<Integer>> insertTable;
    @FXML private Button btnInsert;

    @FXML private TableView<List<Integer>> shellTable;
    @FXML private Button btnShell;

    @FXML private TableView<List<Integer>> linearTable;
    @FXML private Button btnLinear;

    @FXML private TableView<InfoAboutSort> infoTable;    
    @FXML private TableColumn<InfoAboutSort, String> nameSorting;
    @FXML private TableColumn<InfoAboutSort, Double> countCompare;
    @FXML private TableColumn<InfoAboutSort, Double> countSwap;
    @FXML private TableColumn<InfoAboutSort, Double> time;
    @FXML private Button btnInfo;

    @FXML private Spinner<Integer> spinnerLength;
    
    @FXML
    void initialize() {
        spinnerLength.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 10000, 500, 500));

        nameSorting.setCellValueFactory(new PropertyValueFactory<InfoAboutSort, String>("nameSorting"));
        countCompare.setCellValueFactory(new PropertyValueFactory<InfoAboutSort, Double>("countCompare"));
        countSwap.setCellValueFactory(new PropertyValueFactory<InfoAboutSort, Double>("countSwap"));
        time.setCellValueFactory(new PropertyValueFactory<InfoAboutSort, Double>("time"));

        btnBubble.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int[] array = generateArray(20, 1, 100);
            fillTable(bubbleTable, SolutionWithInfo::BubbleSortBidirectional, array);
        });

        btnSelect.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int[] array = generateArray(10, 1, 100);
            fillTable(selectTable, SolutionWithInfo::selectionSort, array);
        });

        btnInsert.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int[] array = generateArray(10, 1, 100);
            fillTable(insertTable, SolutionWithInfo::SortByInserts, array);
        });

        btnShell.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int[] array = generateArray(20, 1, 100);
            // int[] array = {19, 10, 68, 99, 55, 11, 68, 30, 56, 83, 8, 91, 35, 63, 7, 67, 7, 30, 75, 96};
            fillTable(shellTable, SolutionWithInfo::SortShell, array);
        });

        btnLinear.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int[] array = generateArray(10, 1, 9);
            fillTable(linearTable, SolutionWithInfo::LinearSort, array);
        });

        btnInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            infoTable.getItems().clear();
            ObservableList<InfoAboutSort> rows = FXCollections.observableArrayList();

            int size = spinnerLength.getValue();
            int[] arr = generateArray(size, 1, 100);

            double[] infoBubble = Solution.bubbleSortBidirectional(arr.clone());
            double[] infoSelect = Solution.selectionSort(arr.clone());
            double[] infoInsert = Solution.sortbByInserts(arr.clone());
            double[] infoShell = Solution.sortShell(arr.clone());
            double[] infoLinear = Solution.linearSort(arr.clone());

            rows.add(InfoAboutSort.create("Обмен", infoBubble));
            rows.add(InfoAboutSort.create("Выбор", infoSelect));
            rows.add(InfoAboutSort.create("Вставка", infoInsert));
            rows.add(InfoAboutSort.create("Шелла", infoShell));
            rows.add(InfoAboutSort.create("Линейнная", infoLinear));

            infoTable.setItems(rows);
        });
    }

    private void fillTable(TableView<List<Integer>> table, SortMethod method, int[] array) {
        table.getColumns().clear();

        List<List<Integer>> allSteps = method.sort(array);
        ObservableList<List<Integer>> data = FXCollections.observableArrayList();
        data.addAll(allSteps);

        for(int i = 0; i < allSteps.get(0).size(); i++) {
            TableColumn tc = new TableColumn<>(i == 0 ? "" : (i-1) + "");
            tc.setSortable(false);
            int number = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Integer>, Integer>, ObservableValue<Integer>>() {

                @Override
                public ObservableValue<Integer> call(CellDataFeatures<List<Integer>, Integer> arg) {
                    return new SimpleIntegerProperty(arg.getValue().get(number)).asObject();
                }
                
            });
            table.getColumns().add(tc);
        }
        table.setItems(data);
    }

    private int[] generateArray(int size, int min, int max) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int)(Math.random() * max + 1);
        }
        return arr;
    }

    // private List<Integer> generateArray(int size, int min, int max) {
    //     List<Integer> arr = new ArrayList<>(size);
    //     for (int i = 0; i < size; i++) {
    //         arr.add((int)(Math.random()*(max-min) + 1 - min));
    //     }
    //     return arr;
    // }
}
