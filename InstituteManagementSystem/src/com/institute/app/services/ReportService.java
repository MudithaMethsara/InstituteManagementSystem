package com.institute.app.services;

import com.institute.app.config.DBConnection;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Handles the generation of reports using the JasperReports library.
 */
public class ReportService {

    /**
     * Generates and displays a JasperReport using a collection of Java Beans as the data source.
     * This is ideal for reports based on data already loaded into the application.
     *
     * @param reportName The name of the .jrxml file located in the `resources/reports` folder.
     * @param parameters A map of parameters to pass to the report (e.g., report title).
     * @param beanList   A list of Java Beans (e.g., a `List<Student>`) to use as the report's data source.
     */
    public void generateBeanReport(String reportName, Map<String, Object> parameters, List<?> beanList) {
        try {
            // Create a data source from the provided list of objects.
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(beanList);

            // Load the report template (.jrxml) from the classpath.
            InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportName);
            if (reportStream == null) {
                throw new JRException("Cannot find report template: " + reportName);
            }

            // Compile the .jrxml template into a JasperReport object.
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fill the report with the given parameters and data source.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Display the report in the JasperViewer. The `false` flag prevents the
            // application from exiting when the viewer is closed.
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            handleReportError(e);
        }
    }

    /**
     * Generates a report by passing a direct database connection.
     * This is useful for complex reports where the SQL query is defined inside the .jrxml file.
     *
     * @param reportName The name of the .jrxml file.
     * @param parameters A map of parameters to pass to the report.
     */
    public void generateDbReport(String reportName, Map<String, Object> parameters) {
        try (Connection conn = DBConnection.getConnection()) {
            InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportName);
            if (reportStream == null) {
                throw new JRException("Cannot find report template: " + reportName);
            }

            // Compile and fill the report, passing the database connection.
            // JasperReports will use this connection to execute the report's internal SQL query.
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, conn);

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException | SQLException e) {
            handleReportError(e);
        }
    }

    /**
     * Exports a generated report to a PDF file.
     *
     * @param jasperPrint The filled report object to export.
     * @param filePath    The destination file path (e.g., "C:/reports/student_list.pdf").
     */
    public void exportToPdf(JasperPrint jasperPrint, String filePath) {
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
            JOptionPane.showMessageDialog(null, "Report successfully exported to:\n" + filePath, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (JRException e) {
            handleReportError(e);
        }
    }

    /**
     * Exports a generated report to an Excel (XLS) file.
     *
     * @param jasperPrint The filled report object to export.
     * @param filePath    The destination file path (e.g., "C:/reports/student_list.xls").
     */
    public void exportToXls(JasperPrint jasperPrint, String filePath) {
        try {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));

            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
            JOptionPane.showMessageDialog(null, "Report successfully exported to:\n" + filePath, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (JRException e) {
            handleReportError(e);
        }
    }

    /**
     * Centralized error handler for reporting exceptions to the user.
     */
    private void handleReportError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error generating report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
    }
}
