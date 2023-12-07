package profit.sysanal;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.text.DecimalFormat;
import java.util.Collections;


public class ProfitController {
    public Button calculate;
    public TextField nakopuchyvachi;
    public TextField verstatu;
    public TableView<double[]> result;
    public TextArea maxProfit;
    public TextArea minProfit;

    @FXML
    protected void showResults() {
        Profit profit = new Profit();
        double lambda = 61.57;
        double t = 0.157;
        int k = 20;
        double d = 6.57;
        double vVers = 4.57;
        double vNako = 1.57;
        int s = Integer.parseInt(nakopuchyvachi.getText());
        int n = Integer.parseInt(verstatu.getText());
        double[][] matrix = new double[s][n];

        for (int i = 0; i < s; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = profit.calcProfit(lambda, i + 1, j + 1, t, k, d, vVers, vNako);
            }
        }
        showMaxValue(matrix, n, s);
        showMinValue(matrix, n, s);

        displayMatrixInTableView(matrix);
    }

    private void showMaxValue(double[][] matrix, double n, double s) {
        double maxValue = matrix[0][0];
        int maxRow = 0;
        int maxCol = 0;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > maxValue) {
                    maxValue = matrix[i][j];
                    maxRow = i + 1;
                    maxCol = j + 1;
                }
            }
        }
        maxProfit.clear();
        maxProfit.appendText("Максимальний прибуток: ");
        maxProfit.appendText(String.format("%.2f\t", maxValue));
        maxProfit.appendText("\n");
        maxProfit.appendText("Кількість накопичувачів: " + maxRow);
        maxProfit.appendText("\n");
        maxProfit.appendText("Кількість верстатів: " + maxCol);
    }


    private void showMinValue(double[][] matrix, int s, int n) {
        double targetValue = 0.00;
        int foundRow = -1;
        int foundCol = -1;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == targetValue) {
                    foundRow = i + 1;
                    foundCol = j + 1;
                    break;
                }
            }
            if (foundRow != -1) {
                break;
            }
        }
        minProfit.clear();
        if (foundRow != -1) {
            minProfit.appendText("Система стає збитковою якщо: \n");
            minProfit.appendText("Накопичувачів: " + foundRow);
            maxProfit.appendText(" \n");
            minProfit.appendText("Верстатів: " + foundCol);
        } else {
            minProfit.appendText("Система э прибутковою");
        }
    }

    private void displayMatrixInTableView(double[][] matrix) {
        ObservableList<double[]> observableList = FXCollections.observableArrayList();
        Collections.addAll(observableList, matrix);
        result.setItems(observableList);
        for (int columnIndex = 0; columnIndex < matrix[0].length; columnIndex++) {
            TableColumn<double[], Number> column = new TableColumn<>("В " + (columnIndex + 1));

            final int colIndex = columnIndex;
            column.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue()[colIndex]));
            column.setCellFactory(tc -> getDecimalFormattedCellFactory());

            result.getColumns().add(column);
        }
    }

    private javafx.scene.control.TableCell<double[], Number> getDecimalFormattedCellFactory() {
        return new javafx.scene.control.TableCell<>() {
            private final DecimalFormat format = new DecimalFormat("0.00");
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : format.format(item.doubleValue()));
            }
        };
    }
}
