package com.ngingearth.maintenanceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by NgiNG on 5/11/2560.
 */

public class MonthPlan extends Fragment {
    private String s;
    private EditText edt;
    private MobileServiceClient mClient;
    private HashMap<String, String> machineMap;
    private String name,code,type;

    RadioButton checkBoxm1;
    RadioButton checkBoxm2;
    RadioButton checkBoxm3;
    RadioButton checkBoxm4;
    RadioButton checkBoxm5;
    RadioButton checkBoxm6;
    RadioButton checkBoxm7;
    RadioButton checkBoxm8;
    RadioButton checkBoxm9;
    RadioButton checkBoxm10;
    RadioButton checkBoxm11;
    RadioButton checkBoxm12;
    RadioButton checkBoxm13;
    RadioButton checkBoxm14;
    RadioButton checkBoxm15;
    RadioButton checkBoxm16;
    RadioButton checkBoxm17;
    RadioButton checkBoxm18;
    private String note;
    private int status;
    private String typePm;

    private MyFragmentListener listener;

    public static MonthPlan newInstance() {
        return new MonthPlan();
    }


    public interface MyFragmentListener {
        void getDataFromFragment(String note, int status, String typePm);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MyFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement MyFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.month_plan, container, false);

        Intent intent = getActivity().getIntent();
        machineMap = (HashMap<String, String>)intent.getSerializableExtra("machineMap");
        type = machineMap.get("type");

        TextView tvchoicem2 = rootView.findViewById(R.id.tvchoicem2);
        TextView tvchoicem3 = rootView.findViewById(R.id.tvchoicem3);
        TextView tvchoicem4 = rootView.findViewById(R.id.tvchoicem4);
        TextView tvchoicem5 =  rootView.findViewById(R.id.tvchoicem5);
        TextView tvchoicem6 =  rootView.findViewById(R.id.tvchoicem6);
        TextView tvchoicem7 =  rootView.findViewById(R.id.tvchoicem7);
        TextView tvchoicem8 =  rootView.findViewById(R.id.tvchoicem8);
        TextView tvchoicem9 =  rootView.findViewById(R.id.tvchoicem9);
         checkBoxm1 =  rootView.findViewById(R.id.checkBoxm1);
         checkBoxm2 =  rootView.findViewById(R.id.checkBoxm2);
         checkBoxm3 =  rootView.findViewById(R.id.checkBoxm3);
         checkBoxm4 =  rootView.findViewById(R.id.checkBoxm4);
         checkBoxm5 =  rootView.findViewById(R.id.checkBoxm5);
         checkBoxm6 =  rootView.findViewById(R.id.checkBoxm6);
         checkBoxm7 =  rootView.findViewById(R.id.checkBoxm7);
         checkBoxm8 =  rootView.findViewById(R.id.checkBoxm8);
         checkBoxm9 =  rootView.findViewById(R.id.checkBoxm9);
         checkBoxm10 =  rootView.findViewById(R.id.checkBoxm10);
         checkBoxm11 =  rootView.findViewById(R.id.checkBoxm11);
         checkBoxm12 =  rootView.findViewById(R.id.checkBoxm12);
         checkBoxm13 =  rootView.findViewById(R.id.checkBoxm13);
         checkBoxm14 =  rootView.findViewById(R.id.checkBoxm14);
         checkBoxm15 =  rootView.findViewById(R.id.checkBoxm15);
         checkBoxm16 =  rootView.findViewById(R.id.checkBoxm16);

        edt = rootView.findViewById(R.id.edt2);
        typePm = "monthly";

        ImageView saveMonth = rootView.findViewById(R.id.saveMonth);

        if(type.equals("compressor")){
            tvchoicem2.setText("1.ทำความสะอาดไส้กรองอากาศ");
            tvchoicem3.setText("2.ทำความสะอาดชุดระบายความร้อน intercooler");
            tvchoicem4.setText("3.ทำความสะอาดชุดระบายความร้อน after-cooler");
            tvchoicem5.setText("4.ระบบส่งกำลังของเครื่องอัดอากาศ");
            tvchoicem6.setText("5.ประสิทธิภาพของเครื่องมือที่ใช้ลม");
            tvchoicem7.setVisibility(View.GONE);
            tvchoicem8.setVisibility(View.GONE);
            tvchoicem9.setVisibility(View.GONE);
            checkBoxm11.setVisibility(View.GONE);
            checkBoxm12.setVisibility(View.GONE);
            checkBoxm13.setVisibility(View.GONE);
            checkBoxm14.setVisibility(View.GONE);
            checkBoxm15.setVisibility(View.GONE);
            checkBoxm16.setVisibility(View.GONE);

            saveMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((checkBoxm1.isChecked() || checkBoxm2.isChecked() )&&
                            (checkBoxm3.isChecked() || checkBoxm4.isChecked())&&
                            (checkBoxm5.isChecked() || checkBoxm6.isChecked())&&
                            (checkBoxm7.isChecked() || checkBoxm8.isChecked())&&
                            (checkBoxm9.isChecked() || checkBoxm10.isChecked())

                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBoxm2.isChecked() || checkBoxm4.isChecked() ||
                                checkBoxm6.isChecked() || checkBoxm8.isChecked() ||
                                checkBoxm10.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                listener.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            listener.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (type.equals("transformer")){
            tvchoicem2.setText("1.ระดับน้ำมันเกียร์ของมอเตอร์เปลี่ยนแทป");
            tvchoicem3.setText("2.ฐานรองรับหม้อแปลง");
            tvchoicem4.setText("3.ระดับน้ำมันของุชชิ่งหรือปลอกรองแกน(Bushing)");
            tvchoicem5.setText("4.สี silicagel ใน Air Breather");
            tvchoicem6.setText("5.ระบบ Nitrogen-Seal");
            tvchoicem7.setText("6.สภาพทั่วไปของล่อฟ้า");
            tvchoicem8.setText("7.อุปกรณ์เปลี่ยนแทปขณะรับโหลด");
            tvchoicem9.setVisibility(View.GONE);
            checkBoxm15.setVisibility(View.GONE);
            checkBoxm16.setVisibility(View.GONE);

            saveMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((checkBoxm1.isChecked() || checkBoxm2.isChecked() )&&
                            (checkBoxm3.isChecked() || checkBoxm4.isChecked())&&
                            (checkBoxm5.isChecked() || checkBoxm6.isChecked())&&
                            (checkBoxm7.isChecked() || checkBoxm8.isChecked())&&
                            (checkBoxm9.isChecked() || checkBoxm10.isChecked())&&
                            (checkBoxm11.isChecked() || checkBoxm12.isChecked())&&
                            (checkBoxm13.isChecked() || checkBoxm14.isChecked())
                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBoxm2.isChecked() || checkBoxm4.isChecked() ||
                                checkBoxm6.isChecked() || checkBoxm8.isChecked() ||
                                checkBoxm10.isChecked() || checkBoxm12.isChecked() ||
                                checkBoxm14.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                listener.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            listener.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (type.equals("boiler")){
            tvchoicem2.setText("1.การทํางานของ burner ");
            tvchoicem3.setText("2.ท่อไอเสีย");
            tvchoicem4.setText("3.จุดที่ร้อนผิดปกติ");
            tvchoicem5.setText("4.ตัวกรองต่างๆ");
            tvchoicem6.setText("5.สายพานขับอุปกรณ์ต่างๆ");
            tvchoicem7.setText("6.การหล่อลื่น bearing ของอุปกรณ์ต่างๆที่มีการหมุน");
            tvchoicem8.setText("7.เสียงและการสั่นสะเทือน");
            tvchoicem9.setText("8.ระดับของน้ำมันหล่อลื่น");

            saveMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((checkBoxm1.isChecked() || checkBoxm2.isChecked() )&&
                            (checkBoxm3.isChecked() || checkBoxm4.isChecked())&&
                            (checkBoxm5.isChecked() || checkBoxm6.isChecked())&&
                            (checkBoxm7.isChecked() || checkBoxm8.isChecked())&&
                            (checkBoxm9.isChecked() || checkBoxm10.isChecked())&&
                            (checkBoxm11.isChecked() || checkBoxm12.isChecked())&&
                            (checkBoxm13.isChecked() || checkBoxm14.isChecked())&&
                            (checkBoxm15.isChecked() || checkBoxm16.isChecked())
                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBoxm2.isChecked() || checkBoxm4.isChecked() ||
                                checkBoxm6.isChecked() || checkBoxm8.isChecked() ||
                                checkBoxm10.isChecked() || checkBoxm12.isChecked() ||
                                checkBoxm14.isChecked() || checkBoxm16.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                listener.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            listener.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });
        }

        else if (type.equals("motor")){
            tvchoicem2.setText("1.ตรวจสอบแปรงถ่านและคอมมิวเตเตอร์ ");
            tvchoicem3.setText("2.ทตรวจสอบความดันสปริงที่ยึดแปลงถ่าน");
            tvchoicem4.setText("3.จตรวจสอบความตึงของสายพาน");
            tvchoicem5.setText("4.ตรวจสอบการวางแนวของมอเตอร์กับโหลด");
            tvchoicem6.setText("5.ทำความสะอาดอุปกรณควบคุม");
            tvchoicem7.setVisibility(View.GONE);
            tvchoicem8.setVisibility(View.GONE);
            tvchoicem9.setVisibility(View.GONE);
            checkBoxm11.setVisibility(View.GONE);
            checkBoxm12.setVisibility(View.GONE);
            checkBoxm13.setVisibility(View.GONE);
            checkBoxm14.setVisibility(View.GONE);
            checkBoxm15.setVisibility(View.GONE);
            checkBoxm16.setVisibility(View.GONE);

            saveMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((checkBoxm1.isChecked() || checkBoxm2.isChecked() )&&
                            (checkBoxm3.isChecked() || checkBoxm4.isChecked())&&
                            (checkBoxm5.isChecked() || checkBoxm6.isChecked())&&
                            (checkBoxm7.isChecked() || checkBoxm8.isChecked())&&
                            (checkBoxm9.isChecked() || checkBoxm10.isChecked())
                            ) {
                        note = edt.getText().toString();
                        status = 0;
                        if(checkBoxm2.isChecked() || checkBoxm4.isChecked() ||
                                checkBoxm6.isChecked() || checkBoxm8.isChecked() ||
                                checkBoxm10.isChecked()) {
                            if (note.matches("")){
                                Toast.makeText(getActivity(), "Please take a note for some mistake", Toast.LENGTH_SHORT).show();

                            } else {
                                listener.getDataFromFragment(note, status, typePm);
                                Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            status = 1;
                            note = "ปกติ";
                            edt.setText("", TextView.BufferType.EDITABLE);
                            listener.getDataFromFragment(note, status, typePm);
                            Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                            note = "";
                        }
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });
        }




        return rootView;
    }


}
