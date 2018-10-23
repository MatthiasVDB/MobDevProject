package be.pxl.kartingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DrawLineChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    private ArrayList<Date> dates = new ArrayList<>();
    private ArrayList<String> timeStamps = new ArrayList<>();

    private Laptime[] laptimes = new Laptime[]{
            new Laptime("00:12:05", new Date(2005,5,2) ),
            new Laptime("00:10:00", new Date(2005,5,5) ),
            new Laptime("00:11:00", new Date(2005,6,5) )
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawlinechart);

        lineChart = findViewById(R.id.lc_linechart);

        List<Entry> lapTimes = new ArrayList<Entry>();

        for (int i = 0; i < laptimes.length; i++){

            dates.add(laptimes[i].getDate());
            timeStamps.add(laptimes[i].getLapTime());

            Entry entry = new Entry((float)i, convertToFloat(laptimes[i].getLapTime()));

            lapTimes.add(entry);
        }

        LineDataSet setComp1 = new LineDataSet(lapTimes, "Fastest lap laptimes");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);

        final LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate(); // refresh


        // the labels that should be drawn on the XAxis
        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String[] datesAsString = new String[dates.size()];
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");


                for (int i =0; i < dates.size(); i++){
                    datesAsString[i] = df.format(dates.get(i));
                }
                return datesAsString[(int) value];
            }

        };

        IAxisValueFormatter yAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //String[] timeStampsAsString = new String[timeStamps.size()];
                //convert value back to lapTime

                return convertToTimeStamp(value);
            }
        };

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis left = lineChart.getAxisLeft();
        left.setValueFormatter(yAxisFormatter);
        lineChart.getAxisRight().setEnabled(false);
    }

    private float convertToFloat(String lapTime) {
        //String timeString = "01:00:100";
        //hh:mm:ss
        int multiplier[] = {3600000, 60000, 100};
        String splits[] = lapTime.split(":");
        float time = 0;
        for (int x = 0; x < splits.length; x++) {
            time += (Integer.parseInt(splits[x]) * multiplier[x]);
        }
        System.out.println(time);
        return time;
    }

    private String convertToTimeStamp(float milliseconds){
        return String.format("%02d min %02d sec",
                TimeUnit.MILLISECONDS.toMinutes((long)milliseconds),
                TimeUnit.MILLISECONDS.toSeconds((long)milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)milliseconds))
        );
    }

    private class Laptime{
        private String lapTime;
        private Date date;

        public Laptime(String laptime, Date date){
            this.lapTime = laptime;
            this.date = date;
        }


        public String getLapTime() {
            return lapTime;
        }

        public Date getDate() {
            return date;
        }
    }

}

