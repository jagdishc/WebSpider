package webspider;

import java.io.File;
import org.jfree.data.jdbc.JDBCPieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.Locale;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

public class ResultChart 
{
    Connection conn = null;    
    public ResultChart()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/result", "root", "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void drawChart()
    {
        JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn);
        try
        {
            dataset.executeQuery("select * from resultdata");   
            JFreeChart chart = ChartFactory.createBarChart("Bar Chart", "webcrawler", "mobilecrawler", dataset, PlotOrientation.VERTICAL, true, true, false);
            //JFreeChart chart = ChartFactory.createPieChart("Pie Chart", dataset, true, true, false);
            if (chart != null) 
            {
                ChartUtilities.saveChartAsJPEG(new File("G:\\Jagdish\\c.jpg"),chart,450,400);
                System.out.println("Finished.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[])
    {
        ResultChart rc = new ResultChart();
        rc.drawChart();
    }
}
