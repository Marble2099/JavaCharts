import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends ApplicationFrame {
//    public Main() {
//
//        initUI();
//    }

    public Main(String title) {
        super(title);

        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
        //chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        //chartPanel.setBackground(Color.white);

//        add(chartPanel);
//
//        pack();
//        setTitle("Line chart");
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

        private static CategoryDataset createDataset() {

            String path = "src/main/resources/Plot.csv";
            String line = "";
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            try (BufferedReader br = new BufferedReader(new FileReader(path))){

                while ((line = br.readLine()) != null){
                    String[] values = line.split(";");
                    dataset.addValue(Integer.valueOf(values[1]), "Classes", values[0]);
                    //dataset.addValue(512, "Classes", "qwer");
                    //return dataset;
                }
               // br.close();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println(e);
            }

            return dataset;
        }

        private static JFreeChart createChart(CategoryDataset dataset) {

            JFreeChart chart = ChartFactory.createLineChart(
                    "Java Standard Class Library",
                    null,
                    "Class Count",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false,
                    true,
                    false
            );
            //chart.addSubtitle(new TextTitle("Number of Classes By Release"));
            TextTitle source = new TextTitle(
                    "Source: Java IN A  Nutshell (4th edition) "
                    + "by David Flanagan"
            );
            source.setFont(new Font("SansSerif", Font.PLAIN, 1));
            source.setPosition(RectangleEdge.BOTTOM);
            source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            chart.addSubtitle(source);

            chart.setBackgroundPaint(Color.white);

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setRangeGridlinePaint(Color.white);

            NumberAxis rangeAxis = (NumberAxis)  plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            LineAndShapeRenderer renderer
                    = (LineAndShapeRenderer) plot.getRenderer();
            renderer.setDefaultShapesVisible(true);
            renderer.setDrawOutlines(true);
            renderer.setUseFillPaint(true);
            renderer.setDefaultFillPaint(Color.white);

            return chart;
        }

        public static JPanel createDemoPanel(){
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
        }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            var ex = new Main("Example");
            ex.setVisible(true);
        });

    }

}

