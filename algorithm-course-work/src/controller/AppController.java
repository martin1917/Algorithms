package controller;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import Data.RowForTable;
import Enums.KindArray;
import logic.Measure;
import logic.Solution;
import util.GeneratorArrays;

public class AppController {

    @FXML private ToggleGroup mainGroup1;
    @FXML private RadioButton rbSorted1;
    @FXML private RadioButton rbUnsorted1;
    @FXML private RadioButton rbSortedDesc1;
    @FXML private RadioButton rbSomeSorted1;
    @FXML private Spinner<Integer> spinnerSizeArr;
    @FXML private Spinner<Integer> spinnerProcent1;
    @FXML private VBox procentVBox1;
    @FXML private Button btnSort1;
    @FXML private TextField tfLinear;
    @FXML private TextField tfBinary;
    @FXML private TextField tfLinearMin;

    @FXML private LineChart<String, Double> chart;
    @FXML private ToggleGroup mainGroup2;
    @FXML private RadioButton rbSorted2;
    @FXML private RadioButton rbUnsorted2;
    @FXML private RadioButton rbSortedDesc2;
    @FXML private RadioButton rbSomeSorted2;
    @FXML private VBox procentVBox2;
    @FXML private Spinner<Integer> spinnerProcent2;
    @FXML private Button btnSort2;

    @FXML private TableView<RowForTable> table;
    @FXML private TableColumn<RowForTable, Integer> colSizeArr;
    @FXML private TableColumn<RowForTable, Double> colLinear;
    @FXML private TableColumn<RowForTable, Double> colBinary;
    @FXML private TableColumn<RowForTable, Double> colLinearMin;
    @FXML private Label nameArray;

    @FXML private Label label1;
    @FXML private Label label2;

    @FXML
    void initialize() {
        //spinner
        spinnerSizeArr.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 10000, 500, 500));
        spinnerProcent1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, 5, 5));
        spinnerProcent2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, 5, 5));

        //radio buttons actions
        rbSorted1.setOnAction(e -> procentVBox1.setDisable(true));
        rbUnsorted1.setOnAction(e -> procentVBox1.setDisable(true));
        rbSortedDesc1.setOnAction(e -> procentVBox1.setDisable(true));
        rbSomeSorted1.setOnAction(e -> procentVBox1.setDisable(false));

        rbSorted2.setOnAction(e -> procentVBox2.setDisable(true));
        rbUnsorted2.setOnAction(e -> procentVBox2.setDisable(true));
        rbSortedDesc2.setOnAction(e -> procentVBox2.setDisable(true));
        rbSomeSorted2.setOnAction(e -> procentVBox2.setDisable(false));
        
        //radio buttons set title
        rbSorted1.setText(KindArray.SORTED.getTitle());
        rbUnsorted1.setText(KindArray.UNSORTED.getTitle());
        rbSortedDesc1.setText(KindArray.SORTED_DESC.getTitle());
        rbSomeSorted1.setText(KindArray.SOME_SORTED.getTitle());
        
        rbSorted2.setText(KindArray.SORTED.getTitle());
        rbUnsorted2.setText(KindArray.UNSORTED.getTitle());
        rbSortedDesc2.setText(KindArray.SORTED_DESC.getTitle());
        rbSomeSorted2.setText(KindArray.SOME_SORTED.getTitle());

        //table
        colSizeArr.setCellValueFactory(new PropertyValueFactory<RowForTable, Integer>("size"));
        colLinear.setCellValueFactory(new PropertyValueFactory<RowForTable, Double>("timeLinear"));
        colBinary.setCellValueFactory(new PropertyValueFactory<RowForTable, Double>("timeBinary"));
        colLinearMin.setCellValueFactory(new PropertyValueFactory<RowForTable, Double>("timeLinearWithMin"));
    }

    @FXML void toCompareTime(ActionEvent event) {
        int[] arr1 = null;

        int size = spinnerSizeArr.getValue();
        RadioButton selectedBtn = (RadioButton)mainGroup1.getSelectedToggle();
        if (selectedBtn == null) {
            label1.setVisible(true);
            return;
        }
        String name = selectedBtn.getText();
        label1.setVisible(false);
                
        if(name.equals(KindArray.SORTED.getTitle())) {
            arr1 = GeneratorArrays.sortedArray(size);   
        }
        else if(name.equals(KindArray.UNSORTED.getTitle())) {
            arr1 = GeneratorArrays.unsortedArray(size); 
        }
        else if(name.equals(KindArray.SORTED_DESC.getTitle())) {
            arr1 = GeneratorArrays.sortedDescArray(size);   
        }
        else if(name.equals(KindArray.SOME_SORTED.getTitle())) {
            int procent = spinnerProcent1.getValue();
            arr1 = GeneratorArrays.someSortedArray(size, procent);
        }

        int[] arr2 = Arrays.copyOf(arr1, size);
        int[] arr3 = Arrays.copyOf(arr2, size);

        double timeLinear = Measure.getTime(arr1, Solution::sortLinear);
        double timeBinary = Measure.getTime(arr2, Solution::sortBinary);
        double timeLinearWithMin = Measure.getTime(arr3, Solution::sortWithMin);

        tfLinear.setText(Double.toString(timeLinearWithMin));
        tfBinary.setText(Double.toString(timeBinary));
        tfLinearMin.setText(Double.toString(timeLinear));
    }

    @FXML void toDrawChart(ActionEvent event) {
        RadioButton selectedBtn = (RadioButton)mainGroup2.getSelectedToggle();
        if (selectedBtn == null) {
            label2.setVisible(true);
            return;
        }
        String name = selectedBtn.getText();
        label2.setVisible(false);

        chart.getData().clear();
        ObservableList<RowForTable> rows = FXCollections.observableArrayList();

        ObservableList<XYChart.Data<String,Double>> list1 = FXCollections.observableArrayList();
        XYChart.Series<String, Double> series1 = new XYChart.Series<>(list1);
        series1.setName("Линейный поиск");
        chart.getData().add(series1);

        ObservableList<XYChart.Data<String,Double>> list2 = FXCollections.observableArrayList();
        XYChart.Series<String, Double> series2 = new XYChart.Series<>(list2);
        series2.setName("Бинарный поиск");
        chart.getData().add(series2);

        ObservableList<XYChart.Data<String,Double>> list3 = FXCollections.observableArrayList();
        XYChart.Series<String, Double> series3 = new XYChart.Series<>(list3);
        series3.setName("Линейный поиск с мин. элементом");
        chart.getData().add(series3);

        nameArray.setText(name);
        for (int size = 500; size <= 10000; size += 500) {
            int[] arr1 = null;

            if(name.equals(KindArray.SORTED.getTitle())) {
                arr1 = GeneratorArrays.sortedArray(size);   
            }
            else if(name.equals(KindArray.UNSORTED.getTitle())) {
                arr1 = GeneratorArrays.unsortedArray(size); 
            }
            else if(name.equals(KindArray.SORTED_DESC.getTitle())) {
                arr1 = GeneratorArrays.sortedDescArray(size);   
            }
            else if(name.equals(KindArray.SOME_SORTED.getTitle())) {
                int procent = spinnerProcent2.getValue();
                arr1 = GeneratorArrays.someSortedArray(size, procent);
            }

            int[] arr2 = Arrays.copyOf(arr1, size);
            int[] arr3 = Arrays.copyOf(arr2, size);

            double timeLinear = Measure.getTime(arr1, Solution::sortLinear);
            double timeBinary = Measure.getTime(arr2, Solution::sortBinary);
            double timeLinearWithMin = Measure.getTime(arr3, Solution::sortWithMin);

            rows.add(new RowForTable(size, timeLinearWithMin, timeBinary, timeLinear));

            list1.add(new XYChart.Data<String, Double>(Integer.toString(size), timeLinearWithMin));
            list2.add(new XYChart.Data<String, Double>(Integer.toString(size), timeBinary));
            list3.add(new XYChart.Data<String, Double>(Integer.toString(size), timeLinear));
        }

        if(!procentVBox2.isDisable()) {
            nameArray.setText(nameArray.getText() + " " + spinnerProcent2.getValue() + "%");
        }

        table.setItems(rows);
    }
}