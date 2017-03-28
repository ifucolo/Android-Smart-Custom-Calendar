package example.com.customcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gridview)
    RecyclerView recyclerView;

    @BindView(R.id.currentMonth)
    TextView currentMonth;

    ArrayList<Mood> moods;
    DisplayMetrics metrics;

    int month;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setCalendar();
    }

    public void setCalendar(){
        Calendar mCalendar = Calendar.getInstance();

        month = mCalendar.get(Calendar.MONTH); // zero based
        year = mCalendar.get(Calendar.YEAR);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView.setHasFixedSize(false);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()
        ));

        currentMonth.setText(Util.getMonth(month + 1) + " / " + String.valueOf(year));


        populateMoods(mCalendar);
    }

    public void populateMoods(Calendar mCalendar){
        this.moods = new ArrayList<>();

        Mood mood = new Mood();
        mood.day = mCalendar.get(Calendar.DAY_OF_MONTH);
        mood.month = mCalendar.get(Calendar.MONTH) + 1;
        mood.year = mCalendar.get(Calendar.YEAR);

        this.moods.add(mood);

        recyclerView.setAdapter(new MonthAdapter(this, month, year, metrics, this.moods));

    }

    @OnClick(R.id.btnNext)
    public void onClickNext(){
        month = month + 1;

        if (month > 11){
            month = 0;
            year = year + 1;
        }

        currentMonth.setText(Util.getMonth(month + 1) + " / " + String.valueOf(year));
        recyclerView.setAdapter(new MonthAdapter(this, month, year, metrics, this.moods));
    }

    @OnClick(R.id.btnBefore)
    public void onClickBefore(){
        month = month - 1;

        if (month < 0) {
            month = 11;
            year = year - 1;
        }
        currentMonth.setText(Util.getMonth(month + 1) + " / " + String.valueOf(year));
        recyclerView.setAdapter(new MonthAdapter(this, month, year, metrics, this.moods));
    }

}
