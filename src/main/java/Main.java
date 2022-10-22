import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main extends ApplicationFrame {

    public Main(String title) {
        super(title);

        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

        private static CategoryDataset createDataset() {

            String path = "src/main/resources/Charts/200-500.csv";
            String line = "";
            String series1 = "500";
            String series2 = "200";
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            try (BufferedReader br = new BufferedReader(new FileReader(path))){

                while ((line = br.readLine()) != null){
                    String[] values = line.split(";");
                    //System.out.println(values[3]);
                    dataset.addValue(Integer.valueOf(values[1]), series1, values[0]);
                    dataset.addValue(Integer.valueOf(values[3]), series2, values[2]);
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
                    "График интенсивности успешных и неуспешных запросов",
                    "Продолжительность теста",
                    "Запросов в минуту",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );
            //chart.addSubtitle(new TextTitle("Number of Classes By Release"));
            TextTitle source = new TextTitle(
                    "График интенсивности"
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

//            renderer.setSeriesPaint(0, Color.red);
//            renderer.setSeriesPaint(1, Color.blue);

            try {
                ChartUtils.saveChartAsPNG(new File("src/main/resources/Images/Other.png"),
                        chart, 500, 270);
            }catch (IOException e) {
                e.printStackTrace();
            }
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

