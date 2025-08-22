package com.example.app;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

public class MainApp extends Application {

    private final ObservableList<BatchRun> masterData = FXCollections.observableArrayList();
    private final TableView<BatchRun> tableView = new TableView<>();
    private final Pagination pagination = new Pagination();
    private final int rowsPerPage = 10;

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

    @Override
    public void start(Stage stage) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableView.setPlaceholder(new Label(""));

        tableView.getColumns().addAll(
                col("ID", "id"),
                col("Name", "name"),
                intCol("Monday (sec)", "monday"),
                intCol("Tuesday (sec)", "tuesday"),
                intCol("Wednesday (sec)", "wednesday"),
                intCol("Thursday (sec)", "thursday"),
                intCol("Friday (sec)", "friday"),
                intCol("Saturday (sec)", "saturday"),
                intCol("Sunday (sec)", "sunday")
        );

        tableView.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<BatchRun>) change -> refreshChart()
        );

        pagination.setPageFactory(this::createPage);

        Button addBtn = new Button("Add Batch");
        addBtn.setOnAction(e -> addBatch());

        Button delBtn = new Button("Delete Batch");
        delBtn.setOnAction(e -> deleteSelected());

        Button exportBtn = new Button("Export PDF");
        exportBtn.setOnAction(e -> exportPDF());

        HBox controls = new HBox(10, addBtn, delBtn, exportBtn);
        controls.setPadding(new Insets(10));

        xAxis.setLabel("Day");
        yAxis.setLabel("Seconds");
        chart.setTitle("Batch Run Times");

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(pagination);
        root.setBottom(chart);

        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.setTitle("Batch Run Table & Chart");
        stage.show();
    }

    private void addBatch() {
        int id = masterData.size() + 1;
        Random r = new Random();
        masterData.add(new BatchRun(id, "Batch " + (char) ('A' + (id - 1) % 26),
                r.nextInt(100), r.nextInt(100), r.nextInt(100),
                r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100)));
        pagination.setPageCount((int) Math.ceil((double) masterData.size() / rowsPerPage));
        pagination.setCurrentPageIndex(pagination.getPageCount() - 1);
    }

    private void deleteSelected() {
        BatchRun selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            masterData.remove(selected);
            pagination.setPageCount((int) Math.ceil((double) masterData.size() / rowsPerPage));
            pagination.setCurrentPageIndex(Math.min(pagination.getCurrentPageIndex(), pagination.getPageCount() - 1));
        }
    }

    private TableView<BatchRun> createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, masterData.size());
        tableView.setItems(FXCollections.observableArrayList(masterData.subList(fromIndex, toIndex)));
        return tableView;
    }

    private void refreshChart() {
        BatchRun selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            chart.getData().clear();
            return;
        }
        Series<String, Number> series = new Series<>();
        series.setName(selected.getName());
        series.getData().add(new XYChart.Data<>("Monday", selected.getMonday()));
        series.getData().add(new XYChart.Data<>("Tuesday", selected.getTuesday()));
        series.getData().add(new XYChart.Data<>("Wednesday", selected.getWednesday()));
        series.getData().add(new XYChart.Data<>("Thursday", selected.getThursday()));
        series.getData().add(new XYChart.Data<>("Friday", selected.getFriday()));
        series.getData().add(new XYChart.Data<>("Saturday", selected.getSaturday()));
        series.getData().add(new XYChart.Data<>("Sunday", selected.getSunday()));

        chart.getData().clear();
        chart.getData().add(series);
    }

    private void exportPDF() {
        try {
            String pdfPath = "batch_report.pdf";
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Table export
            float[] colWidths = {50, 80, 80, 80, 80, 80, 80, 80, 80};
            Table pdfTable = new Table(colWidths);
            pdfTable.addCell("ID");
            pdfTable.addCell("Name");
            pdfTable.addCell("Monday");
            pdfTable.addCell("Tuesday");
            pdfTable.addCell("Wednesday");
            pdfTable.addCell("Thursday");
            pdfTable.addCell("Friday");
            pdfTable.addCell("Saturday");
            pdfTable.addCell("Sunday");

            for (BatchRun b : masterData) {
                pdfTable.addCell(String.valueOf(b.getId()));
                pdfTable.addCell(b.getName());
                pdfTable.addCell(String.valueOf(b.getMonday()));
                pdfTable.addCell(String.valueOf(b.getTuesday()));
                pdfTable.addCell(String.valueOf(b.getWednesday()));
                pdfTable.addCell(String.valueOf(b.getThursday()));
                pdfTable.addCell(String.valueOf(b.getFriday()));
                pdfTable.addCell(String.valueOf(b.getSaturday()));
                pdfTable.addCell(String.valueOf(b.getSunday()));
            }

            document.add(new Paragraph("Batch Run Table"));
            document.add(pdfTable);

            document.add(new Paragraph("\n\nBatch Run Chart"));

            // Chart export
            File chartImgFile = new File("chart.png");
            ImageIO.write(SwingFXUtils.fromFXImage(chart.snapshot(null, null), null), "png", chartImgFile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(chart.snapshot(null, null), null), "png", baos);

            Image chartImage = new Image(ImageDataFactory.create(baos.toByteArray()));
            document.add(chartImage);

            document.close();
            System.out.println("PDF Exported: " + pdfPath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private TableColumn<BatchRun, String> col(String title, String prop) {
        TableColumn<BatchRun, String> c = new TableColumn<>(title);
        c.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(prop));
        return c;
    }

    private TableColumn<BatchRun, Integer> intCol(String title, String prop) {
        TableColumn<BatchRun, Integer> c = new TableColumn<>(title);
        c.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(prop));
        return c;
    }

    public static void main(String[] args) {
        launch(args);
    }
}