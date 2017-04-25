import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GraphHelper extends JFrame {

    private Map<Integer, Integer> dataSource;
    private DataTable dataTable;

    public GraphHelper(Map<Integer, Integer> dataSource){

        this.dataSource = dataSource;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1900,1000);
        setTitle("Q-Learning Plot");
    }

    public void plotGraph() {

        populateDataTable();
        XYPlot plotter = new XYPlot(dataTable);
        getContentPane().add(new InteractivePanel(plotter));
        LineRenderer lines = new DefaultLineRenderer2D();
        plotter.setLineRenderers(dataTable, lines);

        plotter.getAxisRenderer(XYPlot.AXIS_X).setTickSpacing(10);
        plotter.getAxisRenderer(XYPlot.AXIS_Y).setTickSpacing(20);

        Color color = new Color(0.0f, 0.3f, 1.0f);
        for (PointRenderer pointRenderer : plotter.getPointRenderers(dataTable)) {
            pointRenderer.setColor(color);
        }

        for (LineRenderer lineRenderer : plotter.getLineRenderers(dataTable)) {
            lineRenderer.setColor(color);
        }
    }

    private void populateDataTable()
    {
        dataTable = new DataTable(Integer.class, Integer.class);

        for (int i : dataSource.keySet()){
            dataTable.add(i, dataSource.get(i));
        }
    }
}
