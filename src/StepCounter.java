import Plot.PlotWindow;
import Plot.ScatterPlot;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StepCounter {
    public StepCounter() throws IOException {
        String data = readFile("./allison chang - 100 steps - 100 hz - data.csv");
        String[] lines = data.split("\n");
        ArrayList<Double> accelerometerX = getColumn(getValues(lines), 0);
        ArrayList<Double> accelerometerY = getColumn(getValues(lines), 1);
        ArrayList<Double> accelerometerZ = getColumn(getValues(lines), 2);
        plotData(accelerometerX);
        ArrayList<Double> magnitudes = getMagnitudes(accelerometerX, accelerometerY, accelerometerZ);
        ArrayList<Integer> peakIndexes = getPeakIndexes(magnitudes);
        ArrayList<Double> peakValues = getValuesAt(magnitudes, peakIndexes);
        ScatterPlot plot = new ScatterPlot(100, 100, 1100, 700);
        for (int i = 0; i < magnitudes.size(); i++) {
            double value = magnitudes.get(i);
            plot.plot(0, i, value).strokeColor("red").strokeWeight(2).style("-");
        }
        for (int i = 0; i < peakIndexes.size(); i++) {
            double index = peakIndexes.get(i);
            double value = peakValues.get(i);
            plot.plot(1, index, value).strokeColor("blue").strokeWeight(5).style(".");
        }
        PlotWindow window = PlotWindow.getWindowFor(plot, 1200,800);
        window.show();
    }

    public String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public ArrayList<Double> getValues(String[] lines) {
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] elements = lines[i].split(",");
            for (String element : elements) {
                element = element.trim();
                double value = Double.parseDouble(element);
                values.add(value);
            }
        }
        return values;
    }

    public ArrayList<Double> getColumn(ArrayList<Double> values, int columnNumber) {
        ArrayList<Double> column = new ArrayList<>();
        for (int i = columnNumber; i < values.size(); i += 6) {
            column.add(values.get(i));
        }
        return column;
    }

    public void plotData(ArrayList<Double> accelerometerX) {
        ScatterPlot plot = new ScatterPlot(100, 100, 1100, 700);
        for (int i = 0; i < accelerometerX.size(); i++) {
            plot.plot(0, i, accelerometerX.get(i)).strokeColor("red").strokeWeight(2).style("-");
        }
        PlotWindow window = PlotWindow.getWindowFor(plot, 1200,800);
        window.show();
    }

    public ArrayList<Double> getMagnitudes(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> z) {
        ArrayList<Double> magnitudes = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            magnitudes.add(getMagnitude(x.get(i), y.get(i), z.get(i)));
        }
        return magnitudes;
    }

    public double getMagnitude(double x, double y, double z) {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public int countPeaks(ArrayList<Double> magnitudes) {
        int numPeaks = 0;
        for (int i = 1; i < magnitudes.size() - 1; i++) {
            if (magnitudes.get(i) > magnitudes.get(i - 1) && magnitudes.get(i) > magnitudes.get(i + 1)) {
                numPeaks++;
            }
        }
        return numPeaks;
    }

    public static ArrayList<Integer> getPeakIndexes(ArrayList<Double> data) {
        ArrayList<Integer> peakIndexes = new ArrayList<>();
        for (int i = 1; i < data.size() - 1; i++) {
            if (data.get(i) > data.get(i - 1) && data.get(i) > data.get(i + 1)) {
                peakIndexes.add((i));
            }
        }
        return peakIndexes;
    }
    public static ArrayList<Double> getValuesAt(ArrayList<Double> data, ArrayList<Integer> peakIndexes) {
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < peakIndexes.size(); i++) {
            values.add(data.get(peakIndexes.get(i)));
        }
        return values;
    }
}