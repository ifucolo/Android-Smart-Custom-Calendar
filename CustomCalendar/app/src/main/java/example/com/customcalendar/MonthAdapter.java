package example.com.customcalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iagomendesfucolo on 24/03/17.
 */

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {
    private GregorianCalendar mCalendar;
    private Calendar mCalendarToday;
    private Context mContext;
    private List<String> mItems;
    private int mMonth;
    private int mYear;
    private int mDaysShown;
    private int mDaysLastMonth;
    private int mDaysNextMonth;
    private final String[] mDays = { "Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom" };
    private final int[] mDaysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private ArrayList<Mood> moods;

    public MonthAdapter(Context context, int month, int year, DisplayMetrics metrics, ArrayList<Mood> moodies) {
        mContext = context;
        mMonth = month;
        mYear = year;
        mCalendar = new GregorianCalendar(mYear, mMonth, 1);
        mCalendarToday = Calendar.getInstance();
        this.moods = moodies;
        populateMonth();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.img)
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public MonthAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MonthAdapter.ViewHolder(inflater.inflate(R.layout.item_calendar, parent, false));
    }

    @Override
    public void onBindViewHolder(MonthAdapter.ViewHolder holder, int position) {
        holder.day.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        holder.day.setText(mItems.get(position));
        holder.day.setTextColor(Color.BLACK);

        int[] date = getDate(position);
        if (date != null) {

            holder.day.setTextColor(Util.resolveDate(date[1], mMonth) ? Color.BLACK : Color.GRAY);

            for (Mood mood: moods)
                holder.img.setVisibility(Util.setImageVisiblity(date, mood.day, mood.month, mood.year)? View.VISIBLE:View.INVISIBLE);

            if (isToday(date[0], date[1], date[2])) {
                holder.day.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            }
        }
    }


    @Override
    public int getItemCount() {
        return 40;
    }


    private void populateMonth() {
        mItems = new ArrayList<String>();
        for (String day : mDays) {
            mItems.add(day);
            mDaysShown++;
        }

        int firstDay = getDay(mCalendar.get(Calendar.DAY_OF_WEEK));
        int prevDay;
        if (mMonth == 0)
            prevDay = daysInMonth(11) - firstDay + 1;
        else
            prevDay = daysInMonth(mMonth - 1) - firstDay + 1;
        for (int i = 0; i < firstDay; i++) {
            mItems.add(String.valueOf(prevDay + i));
            mDaysLastMonth++;
            mDaysShown++;
        }

        int daysInMonth = daysInMonth(mMonth);
        for (int i = 1; i <= daysInMonth; i++) {
            mItems.add(String.valueOf(i));
            mDaysShown++;
        }

        mDaysNextMonth = 1;
        while (mDaysShown % 7 != 0) {
            mItems.add(String.valueOf(mDaysNextMonth));
            mDaysShown++;
            mDaysNextMonth++;
        }
    }

    private int daysInMonth(int month) {
        int daysInMonth = mDaysInMonth[month];
        if (month == 1 && mCalendar.isLeapYear(mYear))
            daysInMonth++;
        return daysInMonth;
    }


    private int getDay(int day) {
        switch (day) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return 0;
        }
    }

    private boolean isToday(int day, int month, int year) {
        return mCalendarToday.get(Calendar.MONTH) == month
                && mCalendarToday.get(Calendar.YEAR) == year
                && mCalendarToday.get(Calendar.DAY_OF_MONTH) == day;
    }

    private int[] getDate(int position) {
        int date[] = new int[3];
        if (position <= 6) {
            return null; // day names
        } else if (position <= mDaysLastMonth + 6) {
            // previous month
            date[0] = Integer.parseInt(mItems.get(position));
            if (mMonth == 0) {
                date[1] = 11;
                date[2] = mYear - 1;
            } else {
                date[1] = mMonth - 1;
                date[2] = mYear;
            }
        } else if (position <= mDaysShown - mDaysNextMonth  ) {
            // current month
            date[0] = position - (mDaysLastMonth + 6);
            date[1] = mMonth;
            date[2] = mYear;
        } else {
            // next month
            date[0] = Integer.parseInt(mItems.get(position));
            if (mMonth == 11) {
                date[1] = 0;
                date[2] = mYear + 1;
            } else {
                date[1] = mMonth + 1;
                date[2] = mYear;
            }
        }
        return date;
    }


}
