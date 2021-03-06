package com.ngingearth.maintenanceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DailyPlan extends Fragment {
    private planActivity planActivity;

    private String type;
    private HashMap<String, String> machineMap;
    RadioButton checkBox1;
    RadioButton checkBox2;
    RadioButton checkBox3;
    RadioButton checkBox4;
    RadioButton checkBox5;
    RadioButton checkBox6;
    RadioButton checkBox7;
    RadioButton checkBox8;
    RadioButton checkBox9;
    RadioButton checkBox10;
    RadioButton checkBox11;
    RadioButton checkBox12;
    RadioButton checkBox13;
    RadioButton checkBox14;
    RadioButton checkBox15;
    RadioButton checkBox16;
    RadioButton checkBox17;
    RadioButton checkBox18;
    EditText edt;

    private String note;
    private int status;
    private String typePm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.daily_plan, container, false);

        Intent intent = getActivity().getIntent();
        machineMap = (HashMap<String, String>)intent.getSerializableExtra("machineMap");
        type = machineMap.get("type");

        TextView tvchoice2 = rootView.findViewById(R.id.tvchoice2);
        TextView tvchoice3 = rootView.findViewById(R.id.tvchoice3);
        TextView tvchoice4 = rootView.findViewById(R.id.tvchoice4);
        TextView tvchoice5 = rootView.findViewById(R.id.tvchoice5);
        TextView tvchoice6 = rootView.findViewById(R.id.tvchoice6);
        TextView tvchoice7 = rootView.findViewById(R.id.tvchoice7);
        TextView tvchoice8 = rootView.findViewById(R.id.tvchoice8);
        TextView tvchoice9 = rootView.findViewById(R.id.tvchoice9);
        TextView tvchoice10 = rootView.findViewById(R.id.tvchoice10);
        checkBox1 = rootView.findViewById(R.id.checkBox1);
        checkBox2 = rootView.findViewById(R.id.checkBox2);
        checkBox3 = rootView.findViewById(R.id.checkBox3);
        checkBox4 = rootView.findViewById(R.id.checkBox4);
        checkBox5 = rootView.findViewById(R.id.checkBox5);
        checkBox6 = rootView.findViewById(R.id.checkBox6);
        checkBox7 = rootView.findViewById(R.id.checkBox7);
        checkBox8 = rootView.findViewById(R.id.checkBox8);
        checkBox9 = rootView.findViewById(R.id.checkBox9);
        checkBox10 = rootView.findViewById(R.id.checkBox10);
        checkBox11 = rootView.findViewById(R.id.checkBox11);
        checkBox12 = rootView.findViewById(R.id.checkBox12);
        checkBox13 = rootView.findViewById(R.id.checkBox13);
        checkBox14 = rootView.findViewById(R.id.checkBox14);
        checkBox15 = rootView.findViewById(R.id.checkBox15);
        checkBox16 = rootView.findViewById(R.id.checkBox16);
        checkBox17 = rootView.findViewById(R.id.checkBox17);
        checkBox18 = rootView.findViewById(R.id.checkBox18);

        edt = rootView.findViewById(R.id.edt);

        typePm = "daily";

        ImageView save = rootView.findViewById(R.id.save);

        if(type.equals("compressor")){
            tvchoice2.setText("1.ระดับน้ำมันของเครื่องอัดอากาศ");
            tvchoice3.setText("2.วาล์วนิรภัย");
            tvchoice4.setText("3.Auto drain ของถังอากาศอัด");
            tvchoice5.setText("4.Auto drain ของ Air dryer");
            tvchoice6.setText("5.ความดันตกคร่อม Air Filter");
            tvchoice7.setText("6.อัตราการปล่อยอากาศของ Auto drain");
            tvchoice8.setText("7.การทำงาน Auto drain");
            tvchoice9.setVisibility(View.GONE);
            tvchoice10.setVisibility(View.GONE);
            checkBox15.setVisibility(View.GONE);
            checkBox16.setVisibility(View.GONE);
            checkBox17.setVisibility(View.GONE);
            checkBox18.setVisibility(View.GONE);



            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((checkBox1.isChecked() || checkBox2.isChecked() )&&
                            (checkBox3.isChecked() || checkBox4.isChecked())&&
                            (checkBox5.isChecked() || checkBox6.isChecked())&&
                            (checkBox7.isChecked() || checkBox8.isChecked())&&
                            (checkBox9.isChecked() || checkBox10.isChecked())&&
                            (checkBox11.isChecked() || checkBox12.isChecked())&&
                            (checkBox13.isChecked() || checkBox14.isChecked())
                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBox2.isChecked() || checkBox4.isChecked() ||
                                checkBox6.isChecked() || checkBox8.isChecked() ||
                                checkBox10.isChecked() || checkBox12.isChecked() ||
                                checkBox14.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                planActivity.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            planActivity.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }


                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (type.equals("transformer")){
            tvchoice2.setText("1.ระดับน้ำมันหม้อแปลง");
            tvchoice3.setText("2.ระดับเสียง");
            tvchoice4.setText("3.ระดับน้ำมันของุชชิ่งหรือปลอกรองแกน(Bushing)");
            tvchoice5.setText("4.สี silicagel ใน Air Breather");
            tvchoice6.setText("5.ระบบ Nitrogen-Seal");
            tvchoice7.setText("6.สภาพทั่วไปของล่อฟ้า");
            tvchoice8.setText("7.อุปกรณ์เปลี่ยนแทปขณะรับโหลด");
            tvchoice9.setText("8.สภาพภายนอกโดยทั่วๆไป");
            tvchoice10.setVisibility(View.GONE);
            checkBox17.setVisibility(View.GONE);
            checkBox18.setVisibility(View.GONE);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    if((checkBox1.isChecked() || checkBox2.isChecked() )&&
                            (checkBox3.isChecked() || checkBox4.isChecked())&&
                            (checkBox5.isChecked() || checkBox6.isChecked())&&
                            (checkBox7.isChecked() || checkBox8.isChecked())&&
                            (checkBox9.isChecked() || checkBox10.isChecked())&&
                            (checkBox11.isChecked() || checkBox12.isChecked())&&
                            (checkBox13.isChecked() || checkBox14.isChecked())&&
                            (checkBox15.isChecked() || checkBox16.isChecked())
                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBox2.isChecked() || checkBox4.isChecked() ||
                                checkBox6.isChecked() || checkBox8.isChecked() ||
                                checkBox10.isChecked() || checkBox12.isChecked() ||
                                checkBox14.isChecked() || checkBox16.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                planActivity.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            planActivity.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (type.equals("boiler")){
            tvchoice2.setText("1.ระดับน้ำใน Gauge glass");
            tvchoice3.setText("2.คุณภาพน้ำและส่วนประกอบทางเคมีของหม้อไอน้ำ");
            tvchoice4.setText("3.ระบบการเผาไหม้");
            tvchoice5.setText("4.วาล์วของท่อจ่ายเชื้อเพลิง");
            tvchoice6.setText("5.รอยต่อหรือหน้าแปลนที่ยึดท่อต่างๆ");
            tvchoice7.setText("6.การทํางานของอุปกรณ์ควบคุมระดับน้ํ้ำ");
            tvchoice8.setText("7.เสียงและการสั่นสะเทือน");
            tvchoice9.setText("8.ระดับของน้ำมันหล่อลื่น");
            tvchoice10.setText("9.การทํางานของมอเตอร์ขับ");

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    if((checkBox1.isChecked() || checkBox2.isChecked() )&&
                            (checkBox3.isChecked() || checkBox4.isChecked())&&
                            (checkBox5.isChecked() || checkBox6.isChecked())&&
                            (checkBox7.isChecked() || checkBox8.isChecked())&&
                            (checkBox9.isChecked() || checkBox10.isChecked())&&
                            (checkBox11.isChecked() || checkBox12.isChecked())&&
                            (checkBox13.isChecked() || checkBox14.isChecked())&&
                            (checkBox15.isChecked() || checkBox16.isChecked())&&
                            (checkBox17.isChecked() || checkBox18.isChecked())
                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBox2.isChecked() || checkBox4.isChecked() ||
                                checkBox6.isChecked() || checkBox8.isChecked() ||
                                checkBox10.isChecked() || checkBox12.isChecked() ||
                                checkBox14.isChecked() || checkBox16.isChecked() ||
                                checkBox18.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                planActivity.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            planActivity.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }
                    }

                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });
        }//*/
        else if (type.equals("motor")){
            tvchoice2.setText("1.ความสั่นสะเทือนขณะทำงานที่โหลดจริง");
            tvchoice3.setText("2.อุณหภูมิจริงขณะทำงาน");
            tvchoice4.setText("3.วัดความไม่สมดุลของค่าความเหนี่ยวนำ");
            tvchoice5.setText("4.กระแสไฟฟ้าที่เกิดขึ้นที่โหลดจริง");
            tvchoice6.setText("5.ความต้านทานของขดลวดในแต่ละเฟส");
            tvchoice7.setText("6.วัดสภาพการเป็นฉนวนไฟฟ้า");
            tvchoice8.setText("7.การทดสอบค่าความต้านทานแม่เหล็ก");
            tvchoice9.setVisibility(View.GONE);
            tvchoice10.setVisibility(View.GONE);
            checkBox15.setVisibility(View.GONE);
            checkBox16.setVisibility(View.GONE);
            checkBox17.setVisibility(View.GONE);
            checkBox18.setVisibility(View.GONE);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((checkBox1.isChecked() || checkBox2.isChecked() )&&
                            (checkBox3.isChecked() || checkBox4.isChecked())&&
                            (checkBox5.isChecked() || checkBox6.isChecked())&&
                            (checkBox7.isChecked() || checkBox8.isChecked())&&
                            (checkBox9.isChecked() || checkBox10.isChecked())&&
                            (checkBox11.isChecked() || checkBox12.isChecked())&&
                            (checkBox13.isChecked() || checkBox14.isChecked())
                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBox2.isChecked() || checkBox4.isChecked() ||
                                checkBox6.isChecked() || checkBox8.isChecked() ||
                                checkBox10.isChecked() || checkBox12.isChecked() ||
                                checkBox14.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                planActivity.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            planActivity.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }
                    }

                    else
                            Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
            }
        });
    } else {
            Toast.makeText(getActivity(), "Please check type of your machine.", Toast.LENGTH_SHORT).show();
        }
        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        planActivity = (planActivity) getActivity();
    }
}
