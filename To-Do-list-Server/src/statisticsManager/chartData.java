
package statisticsManager;


public class chartData {
    
    
    private int [] ySeries;
    private String [] xSeries;
    private String title;

    public chartData(int[] ySeries, String[] xSeries, String title) {
        this.ySeries = ySeries;
        this.xSeries = xSeries;
        this.title = title;
    }

    public int[] getySeries() {
        return ySeries;
    }

    public void setySeries(int[] ySeries) {
        this.ySeries = ySeries;
    }

    public String[] getxSeries() {
        return xSeries;
    }

    public void setxSeries(String[] xSeries) {
        this.xSeries = xSeries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
