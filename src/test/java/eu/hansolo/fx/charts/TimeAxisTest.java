/*
 * Copyright (c) 2017 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.charts;

import eu.hansolo.fx.charts.converter.Converter;
import eu.hansolo.fx.charts.data.TYChartItem;
import eu.hansolo.fx.charts.series.XYSeries;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static eu.hansolo.fx.charts.converter.Converter.Category.TEMPERATURE;
import static eu.hansolo.fx.charts.converter.Converter.UnitDefinition.CELSIUS;
import static eu.hansolo.fx.charts.converter.Converter.UnitDefinition.FAHRENHEIT;


/**
 * User: hansolo
 * Date: 04.12.17
 * Time: 08:40
 */
public class TimeAxisTest extends Application {
    private static final Double    AXIS_WIDTH      = 25d;
    private static final Color[]   COLORS          = { Color.RED, Color.BLUE, Color.CYAN, Color.LIME };
    private static final Random    RND             = new Random();
    private XYSeries<TYChartItem> tySeries1;
    private XYSeries<TYChartItem> tySeries2;
    private XYChart<TYChartItem>  tyChart;
    private Axis                  xAxisBottom;
    private Axis                  yAxisLeft;
    private Axis                  yAxisLeft2;
    private Axis                  yAxisRight;


    @Override public void init() {
//        int               noOfValues = 24 * 60;
        int               noOfValues =  60;
        LocalDateTime     start      = LocalDateTime.now();
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

//        tyChart.

    }

    @Override public void start(Stage stage) {
        VBox pane = new VBox(tyChart);
        pane.getChildren().add(new Label("hhells"));
        pane.setPadding(new Insets(30));
        Scene scene = new Scene(new StackPane(pane));

        stage.setTitle("TimeAxis Test");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
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


    public static void main(String[] args) {
        launch(args);
    }
}

