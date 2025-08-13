package com.institute.app.services;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.plot.PiePlot;

/**
 * A service for creating various types of charts using the JFreeChart library.
 * The generated charts are returned as JPanels, ready to be embedded in a Swing UI.
 */
public class ChartService {

    // Apply a standard theme to all charts for a consistent look.
    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    /**
     * Creates a Pie Chart and wraps it in a ChartPanel inside a JPanel.
     *
     * @param title   The title of the chart, displayed at the top.
     * @param dataset The data for the chart, where each key is a slice label and the value is the slice's size.
     * @return A JPanel containing the generated pie chart.
     */
    public JPanel createPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart pieChart = ChartFactory.createPieChart(
                title,
                dataset,
                true,  // includeLegend
                true,  // generateTooltips
                false  // generateURLs
        );

        // Customize the plot for better appearance (e.g., transparent background)
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(null); // Use panel's background
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 100)); // Semi-transparent label background

        return createChartPanel(pieChart);
    }

    /**
     * Creates a Bar Chart.
     *
     * @param title             The title of the chart.
     * @param categoryAxisLabel The label for the X-axis (e.g., "Month").
     * @param valueAxisLabel    The label for the Y-axis (e.g., "Enrollments").
     * @param dataset           The data for the chart.
     * @param orientation       The plot orientation (VERTICAL or HORIZONTAL).
     * @return A JPanel containing the generated bar chart.
     */
    public JPanel createBarChart(String title, String categoryAxisLabel, String valueAxisLabel, DefaultCategoryDataset dataset, PlotOrientation orientation) {
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                categoryAxisLabel,
                valueAxisLabel,
                dataset,
                orientation,
                true,  // includeLegend
                true,  // generateTooltips
                false  // generateURLs
        );

        barChart.getPlot().setBackgroundPaint(null);

        return createChartPanel(barChart);
    }

    /**
     * Creates a Line Chart.
     *
     * @param title             The title of the chart.
     * @param categoryAxisLabel The label for the X-axis (e.g., "Date").
     * @param valueAxisLabel    The label for the Y-axis (e.g., "Revenue").
     * @param dataset           The data for the chart.
     * @return A JPanel containing the generated line chart.
     */
    public JPanel createLineChart(String title, String categoryAxisLabel, String valueAxisLabel, DefaultCategoryDataset dataset) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                title,
                categoryAxisLabel,
                valueAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,  // includeLegend
                true,  // generateTooltips
                false  // generateURLs
        );

        lineChart.getPlot().setBackgroundPaint(null);

        return createChartPanel(lineChart);
    }

    /**
     * Helper method to create a standardized JPanel for a JFreeChart.
     *
     * @param chart The chart to wrap.
     * @return A new JPanel containing the chart.
     */
    private JPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true); // Allow zooming with the mouse wheel

        // The final panel uses a BorderLayout to ensure the ChartPanel resizes correctly.
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate(); // Re-layout the panel
        return panel;
    }
}
