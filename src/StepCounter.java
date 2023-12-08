import Plot.PlotWindow;
import Plot.ScatterPlot;
import java.util.ArrayList;

public class StepCounter {
    public int countSteps(ArrayList<Double> xAcc, ArrayList<Double> yAcc, ArrayList<Double> zAcc,
                          ArrayList<Double> xGyro, ArrayList<Double> yGyro, ArrayList<Double> zGyro) {
        return countPeaks(getMagnitudes(xAcc, yAcc, zAcc));
    }

    public int countSteps(String csvFileText) {
        String[] lines = csvFileText.split("\n");
        ArrayList<Double> accelerometerX = getColumn(getValues(lines), 0);
        ArrayList<Double> accelerometerY = getColumn(getValues(lines), 1);
        ArrayList<Double> accelerometerZ = getColumn(getValues(lines), 2);
        plotData(smoothData(getMagnitudes(accelerometerX, accelerometerY, accelerometerZ)));
        return countPeaks(smoothData(getMagnitudes(accelerometerX, accelerometerY, accelerometerZ)));
    }

    public int countStepsBaseline(String csvFileText) {
        String[] lines = csvFileText.split("\n");
        ArrayList<Double> accelerometerX = getColumn(getValues(lines), 0);
        ArrayList<Double> accelerometerY = getColumn(getValues(lines), 1);
        ArrayList<Double> accelerometerZ = getColumn(getValues(lines), 2);
        plotData(getMagnitudes(accelerometerX, accelerometerY, accelerometerZ));
        return countPeaks((getMagnitudes(accelerometerX, accelerometerY, accelerometerZ)));
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

    public ArrayList<Double> smoothData(ArrayList<Double> magnitudes) {
        for (int i = 0; i < 100; i++) {
            for (int j = 1; j < magnitudes.size() - 1; j++) {
                magnitudes.set(j, weightedAverage(magnitudes.get(j - 1), magnitudes.get(j), magnitudes.get(j + 1)));
            }
        }
        return magnitudes;
    }

    public double weightedAverage(double a, double b, double c) {
        return ((a/4) + (b/2) + (c/4));
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

    public double getThreshold(ArrayList<Double> magnitudes) {
        int total = 0;
        double sum = 0;
        for (Double magnitude : magnitudes) {
            sum += magnitude;
            total++;
        }
        return sum/total;
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

    public void plotData(ArrayList<Double> magnitudes) {
        ScatterPlot basePlot = new ScatterPlot(100, 100, 1100, 700);
        for (int i = 0; i < magnitudes.size(); i++) {
            basePlot.plot(0, i, magnitudes.get(i)).strokeColor("red").strokeWeight(2).style("-");
        }
        PlotWindow window = PlotWindow.getWindowFor(basePlot, 1200,800);
        window.show();
    }

    public static void plotPeaks(ArrayList<Double> magnitudes, ArrayList<Integer> peakIndexes, ArrayList<Double> peakValues) {
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
}