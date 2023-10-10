package com.suto.s4anext;


import com.suto.s4anext.chartext.GapLineChart;
import com.suto.s4anext.chartext.GapNumberAxis;
import eu.hansolo.fx.charts.*;
import eu.hansolo.fx.charts.converter.Converter;
import eu.hansolo.fx.charts.data.TYChartItem;
import eu.hansolo.fx.charts.series.XYSeries;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static eu.hansolo.fx.charts.converter.Converter.Category.TEMPERATURE;
import static eu.hansolo.fx.charts.converter.Converter.UnitDefinition.CELSIUS;
import static eu.hansolo.fx.charts.converter.Converter.UnitDefinition.FAHRENHEIT;

public class MainController {

    Logger logger = LogManager.getLogger(MainController.class);

    SimpleDateFormat timeFormater = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    @FXML
    private Label welcomeText;


    @FXML
    private VBox _parent;

    private static final Double    AXIS_WIDTH      = 25d;
    private static final Color[]   COLORS          = { Color.RED, Color.BLUE, Color.CYAN, Color.LIME };
    private static final Random    RND             = new Random();
    private XYSeries<TYChartItem> tySeries1;
    private XYSeries<TYChartItem> tySeries2;
    private eu.hansolo.fx.charts.XYChart<TYChartItem> tyChart;
    private Axis xAxisBottom;
    private Axis                  yAxisLeft;
    private Axis                  yAxisLeft2;
    private Axis                  yAxisRight;

    @FXML
    private void initialize(){
        int               noOfValues =  60;
        LocalDateTime start      = LocalDateTime.now();
//        LocalDateTime     end        = start.plusHours(24);
        LocalDateTime     end        = start.plusMinutes(1);
        List<TYChartItem> tyData1    = new ArrayList<>();
        List<TYChartItem> tyData2    = new ArrayList<>();

        for (int i = 0 ; i < noOfValues ; i++) {
            tyData1.add(new TYChartItem(start.plusSeconds(i), RND.nextDouble() * 12 + RND.nextDouble() * 6, "P" + i, COLORS[RND.nextInt(3)]));

            if(i>30 && i< 40){
                tyData1.add(new TYChartItem(start.plusSeconds(i),  Double.NaN, "P" + i, COLORS[RND.nextInt(3)]));
            }
            tyData2.add(new TYChartItem(start.plusSeconds(i), RND.nextDouble() * 12 + 30, "P" + i, COLORS[RND.nextInt(3)]));
        }

        tySeries1 = new XYSeries(tyData1, ChartType.LINE, Color.RED, Color.rgb(255, 0, 0, 0.5));
        tySeries1.setSymbolsVisible(false);
        tySeries1.setName("s1");

        tySeries2 = new XYSeries(tyData2, ChartType.LINE, Color.BLUE, Color.rgb(0, 0, 255, 0.5));
//        tySeries2.setSymbolsVisible(false);
        // XYChart
        Converter tempConverter     = new Converter(TEMPERATURE, CELSIUS); // Type Temperature with BaseUnit Celsius
        double    tempFahrenheitMin = tempConverter.convert(0, FAHRENHEIT);
        double    tempFahrenheitMax = tempConverter.convert(20, FAHRENHEIT);

        xAxisBottom = createBottomTimeAxis(start, end, "HH:mm:ss", true);
        yAxisLeft   = createLeftYAxis(0, 20, true);
        yAxisLeft2   = createLeftYAxis2(0, 100, true);
        yAxisRight  = createRightYAxis(tempFahrenheitMin, tempFahrenheitMax, false);
//        tyChart     = new XYChart<>(new XYPane(tySeries1), yAxisLeft, yAxisRight, xAxisBottom);
//        tyChart     = new XYChart<>(new XYPane(tySeries1,tySeries2), yAxisLeft,yAxisLeft2, yAxisRight, xAxisBottom);
        XYPane xyPane = new XYPane(tySeries1,tySeries2);

        tyChart     = new XYChart<>(xyPane , yAxisLeft,yAxisLeft2, yAxisRight, xAxisBottom);
//        tyChart     = new XYChart<>(xyPane , yAxisLeft, yAxisRight, xAxisBottom);
//        tyChart.addXYPane(new XYPane<>(tySeries2));
        tyChart.setPrefSize(800, 600);
        tyChart.setTitle("xxx.CSD");
        tyChart.setSubTitle("Time piero : 11:01 - 18:33");
        tyChart.setGrid(new Grid(xAxisBottom,yAxisLeft));
        yAxisLeft.setPosition(Position.LEFT);
        yAxisLeft2.setPosition(Position.RIGHT);
//        yAxisLeft.setPosition(Position.RIGHT);
//        yAxisLeft2.setPosition(Position.LEFT);
        tyChart.refresh();


        _parent.getChildren().add(_parent.getChildren().size()-2 ,tyChart);

    }
    private Axis createLeftYAxis(final double MIN, final double MAX, final boolean AUTO_SCALE) {
        Axis axis = new Axis(Orientation.VERTICAL, Position.LEFT);
        axis.setMinValue(MIN);
        axis.setMaxValue(MAX);
        axis.setPrefWidth(AXIS_WIDTH);
        axis.setAutoScale(AUTO_SCALE);

        AnchorPane.setTopAnchor(axis, 0d);
        AnchorPane.setBottomAnchor(axis, 25d);
        AnchorPane.setLeftAnchor(axis, 0d);

        axis.setTitle("m/s");
        return axis;
    }
    private Axis createLeftYAxis2(final double MIN, final double MAX, final boolean AUTO_SCALE) {
        Axis axis = new Axis(Orientation.VERTICAL, Position.LEFT);
        axis.setMinValue(MIN);
        axis.setMaxValue(MAX);
        axis.setPrefWidth(AXIS_WIDTH);
        axis.setAutoScale(AUTO_SCALE);

        AnchorPane.setTopAnchor(axis, 0d);
        AnchorPane.setBottomAnchor(axis, 25d);
//        AnchorPane.setLeftAnchor(axis, 0d);
        AnchorPane.setLeftAnchor(axis, -25d);

        axis.setTitle("m/s");
        return axis;
    }
    private Axis createRightYAxis(final double MIN, final double MAX, final boolean AUTO_SCALE) {
        Axis axis = new Axis(Orientation.VERTICAL, Position.RIGHT);
        axis.setMinValue(MIN);
        axis.setMaxValue(MAX);
        axis.setPrefWidth(AXIS_WIDTH);
        axis.setAutoScale(AUTO_SCALE);

        AnchorPane.setRightAnchor(axis, 0d);
        AnchorPane.setTopAnchor(axis, 0d);
        AnchorPane.setBottomAnchor(axis, 25d);

        return axis;
    }
    private Axis createBottomTimeAxis(final LocalDateTime START, final LocalDateTime END, final String PATTERN, final boolean AUTO_SCALE) {
        Axis axis = new Axis(START, END, Orientation.HORIZONTAL, Position.BOTTOM);
        axis.setDateTimeFormatPattern(PATTERN);
        axis.setPrefHeight(AXIS_WIDTH);

        AnchorPane.setBottomAnchor(axis, 0d);
        AnchorPane.setLeftAnchor(axis, 25d);
        AnchorPane.setRightAnchor(axis, 25d);

        return axis;
    }


    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");

        //NOTE: show point

        System.out.println("wwww");
        System.out.println(System.currentTimeMillis());
    }
}