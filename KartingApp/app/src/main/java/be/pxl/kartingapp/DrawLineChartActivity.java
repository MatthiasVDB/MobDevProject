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

import java.util.ArrayList;
import java.util.List;

import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.models.Lap;
import be.pxl.kartingapp.models.Session;
import be.pxl.kartingapp.utilities.Converters;

public class DrawLineChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> timeStamps = new ArrayList<>();
    private ArrayList<Lap> laptimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawlinechart);
        ArrayList<String> arguments = getIntent().getExtras().getStringArrayList("args");
        int circuitId = Integer.parseInt(arguments.get(0)) ;
        String trackLayout = arguments.get(1);

        lineChart = findViewById(R.id.lc_linechart);
        laptimes = getFastestLapsByTrackLayout(trackLayout, circuitId);
        List<Entry> lapTimes = new ArrayList<Entry>();

        for (int i = 0; i < laptimes.size(); i++){

            dates.add(laptimes.get(i).getDate());
            timeStamps.add(laptimes.get(i).getLapTime());

            Entry entry = new Entry((float)i, Converters.convertLaptimeStringToMilliseconds(laptimes.get(i).getLapTime()));

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

                for (int i =0; i < dates.size(); i++){
                    datesAsString[i] = dates.get(i);
                }
                return datesAsString[(int) value];

            }

        };

        IAxisValueFormatter yAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Converters.convertMillisecondsToLaptimeString(value);

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

    public ArrayList<Lap> getFastestLapsByTrackLayout(String trackLayout, int circuitId){
        KartingDbHelper dbHelper = new KartingDbHelper(this);
        ArrayList<Session> sessions = dbHelper.getAllCircuitsessionsByTrackLayoutAndCircuitId(trackLayout, circuitId);
        ArrayList<Lap> laps = new ArrayList<>();

        for (Session session: sessions) {
            ArrayList<String> lapTimes = dbHelper.getAllLaptimesBySessionId(session.getId());
            float fastestTime = 900000f;
            for (String laptime: lapTimes ) {
                float time = Converters.convertLaptimeStringToMilliseconds(laptime);
                if(time < fastestTime){
                    fastestTime = time;
                }
            }

            String time = Converters.convertMillisecondsToLaptimeString(fastestTime);
            Lap lap = new Lap(time, session.getDate());
            laps.add(lap);

        }

        return laps;
    }

}

