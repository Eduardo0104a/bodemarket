package view.Reporte;

import dao.VentaDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import dto.Usuario;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;

/**
 *
 * @author Eduardo
 */
public class ReporteView extends javax.swing.JPanel {

    private JTabbedPane tabbedPane;
    private VentaDAO ventaDAO = new VentaDAO();
    private final DefaultCategoryDataset datasetBarras;
    private final DefaultCategoryDataset datasetDias;

    public ReporteView(Usuario usuario) {
        setLayout(new BorderLayout());

        this.ventaDAO = new VentaDAO();
        this.datasetBarras = new DefaultCategoryDataset();
        this.datasetDias = new DefaultCategoryDataset();
        this.tabbedPane = new JTabbedPane();

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(233, 164, 157));
        tabbedPane.setForeground(new Color(120, 30, 20));

        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                selectedTabPadInsets = new Insets(5, 5, 5, 5);
                tabInsets = new Insets(5, 10, 5, 10);
                contentBorderInsets = new Insets(0, 0, 0, 0);
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }

            @Override
            protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, tabbedPane.getWidth(), calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));
                super.paintTabArea(g, tabPlacement, selectedIndex);
            }
        });

        tabbedPane.addTab("Ventas por Categoría", crearPanelPastel());
        tabbedPane.addTab("Ingresos por Mes", crearPanelBarras());
        tabbedPane.addTab("Ventas por Día", crearPanelDias());
        tabbedPane.addTab("Top Productos Vendidos", crearPanelProductos());

        add(tabbedPane, BorderLayout.CENTER);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private ChartPanel crearGraficoPastel() {
        DefaultPieDataset datasetPastel = new DefaultPieDataset();
        Map<String, Double> datos = ventaDAO.obtenerVentasPorCategoria();

        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            datasetPastel.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribución de Ventas por Categoría",
                datasetPastel,
                true,
                true,
                false
        );

        // Estilizar gráfico de pastel
        chart.getTitle().setPaint(new Color(120, 30, 20));
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 12));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.00%")
        ));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    private ChartPanel crearGraficoBarras() {
        datasetBarras.clear();
        Map<String, Double> datos = ventaDAO.obtenerVentasPorMes();

        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            datasetBarras.addValue(entry.getValue(), "Ventas", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Total de Ventas por Mes",
                "Mes",
                "Total (S/.)",
                datasetBarras,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Estilizar gráfico de barras
        chart.getTitle().setPaint(new Color(120, 30, 20));
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 12));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    private ChartPanel crearGraficoBarrasPorDia() {
        datasetDias.clear();
        Map<String, Double> datos = ventaDAO.obtenerVentasPorDia();

        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            datasetDias.addValue(entry.getValue(), "Ventas", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas por Día",
                "Día",
                "Total de Ventas",
                datasetDias
        );
        chart.getTitle().setPaint(new Color(120, 30, 20));
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 12));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    private JPanel crearPanelTopProductos() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> datos = ventaDAO.obtenerProductosMasVendidos();

        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            dataset.addValue(entry.getValue(), "Cantidad Vendida", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top Productos Vendidos",
                "Producto",
                "Cantidad Vendida",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.getTitle().setPaint(new Color(120, 30, 20));
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 12));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    private JPanel crearPanelPastel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearGraficoPastel(), BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel crearPanelBarras() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearGraficoBarras(), BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel crearPanelDias() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearGraficoBarrasPorDia(), BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearPanelTopProductos(), BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public void refrescarGraficos() {
        tabbedPane.setComponentAt(0, crearPanelPastel());
        tabbedPane.setComponentAt(1, crearPanelBarras());
        tabbedPane.setComponentAt(2, crearPanelDias());
        tabbedPane.setComponentAt(3, crearPanelTopProductos());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
