package com.example.myreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Chronometer chrono;
    Button btnStart, btnEnd;
    RadioButton rdoCal, rdoTime;
    CalendarView calView;
    TimePicker tPicker;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMinute;
    int selectYear, selectMonth, selectDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("시간 예약");

        // 버튼
        btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);

        // 크로노미터
        chrono = (Chronometer) findViewById(R.id.chronometer1);

        // 라디오버튼 2개
        rdoCal = (RadioButton) findViewById(R.id.rdoCal);
        rdoTime = (RadioButton) findViewById(R.id.rdoTime);

        // FrameLayout의 2개 위젯
        tPicker = (TimePicker) findViewById(R.id.timePicker1);
        calView = (CalendarView) findViewById(R.id.calendarView1);

        // 텍스트뷰 중에서 연,월,일,시,분 숫자
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMinute = (TextView) findViewById(R.id.tvMinute);

        // 처음에는 2개를 안보이게 설정
        tPicker.setVisibility(View.INVISIBLE);
        calView.setVisibility(View.INVISIBLE);

        rdoCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tPicker.setVisibility(View.INVISIBLE);
                calView.setVisibility(View.VISIBLE);
            }
        });

        rdoTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tPicker.setVisibility(View.VISIBLE);
                calView.setVisibility(View.INVISIBLE);
            }
        });

        // 타이머 설정
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                chrono.setTextColor(Color.RED);
            }
        });

        // 버튼을 클릭하면 날짜,시간을 가져온다.
        btnEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chrono.stop();
                chrono.setTextColor(Color.BLUE);

                tvYear.setText(Integer.toString(selectYear));
                tvMonth.setText(Integer.toString(selectMonth));
                tvDay.setText(Integer.toString(selectDay));
                tvHour.setText(Integer.toString(tPicker.getHour())); // getHour() 메소드를 사용해야
                tvMinute.setText(Integer.toString(tPicker.getMinute()));

                // D day 계산 - 중간고사때 나온 문제
                TextView tvDday = (TextView) findViewById(R.id.dday);
                Calendar today = Calendar.getInstance();
                today.setTime(new Date()); //오늘 날짜

                //String s_date = "2021-10-28"; // 임의로 정한 목표 날짜
                String s_date = selectYear + "-" + selectMonth + "-" + selectDay;


                try {

                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s_date);
                    Calendar cmpDate = Calendar.getInstance();

                    cmpDate.setTime(date);
                    long interval = cmpDate.getTimeInMillis() - today.getTimeInMillis();
                    long intervalDay = interval / (1000 * 60 * 60 * 24) + 1;
                    //System.out.println(s_date + " 까지는 "+intervalDay+"일 남았습니다");
                    tvDday.setText(s_date + " 까지는 " + intervalDay + "일 남았습니다");


                } catch (ParseException e) {
                    System.out.println("잘못된 형식입니다. ");

                }



                LocalDateTime currentDate = LocalDate.now().atStartOfDay(); // 컴퓨터의 현재 날짜 정보
                //LocalDateTime dueDate = LocalDate.of(,10, 28).atStartOfDay(); //년,월,일
                LocalDateTime dueDate = LocalDate.of(selectYear,selectMonth, selectDay).atStartOfDay(); //년,월,일
                //System.out.println( Duration.between(currentDate,dueDate).toDays() + "일 남았습니다" );
                tvDday.setText(Duration.between(currentDate,dueDate).toDays() + "일 남았습니다");
                //https://d2.naver.com/helloworld/645609
                //

            }
        });

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectYear =  year;
                selectMonth = month + 1;
                selectDay = dayOfMonth;
            }
        });

    }


}