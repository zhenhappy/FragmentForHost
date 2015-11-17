package com.jay.android.fragmentforhost;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.content.SharedPreferences;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_5)
public class Fragment5 extends Fragment {
    private SharedPreferences sp;
    private String strName, strAge, strSex, strUro, strMac1, strMac2;
    private Activity activity;
    private Dialog nameDialog, ageDialog, sexDialog, uroclepsiaDialog, macDialog, clearDialog;
    @ViewById
    RelativeLayout rly_clear;
    @ViewById
    RelativeLayout rly_name;
    @ViewById
    RelativeLayout rly_age;
    @ViewById
    RelativeLayout rly_sex;
    @ViewById
    RelativeLayout rly_uroclepsia;
    @ViewById
    RelativeLayout rly_mac1;
    @ViewById
    RelativeLayout rly_mac2;

    @ViewById
    TextView tv_name;
    @ViewById
    TextView tv_age;
    @ViewById
    TextView tv_sex;
    @ViewById
    TextView tv_uro;
    @ViewById
    TextView tv_mac1;
    @ViewById
    TextView tv_mac2;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        iniViews();
//        sp = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
//        tv_name.setText(sp.getString("name", null));
//        tv_age.setText(sp.getString("age", null));
//        tv_sex.setText(sp.getString("sex", null));
//        tv_uro.setText(sp.getString("uro", null));
//        tv_mac1.setText(sp.getString("mac1", null));
//        tv_mac2.setText(sp.getString("mac2", null));
    }

    @UiThread
    public void iniViews() {
        sp = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
        tv_name.setText(sp.getString("name", null));
        tv_age.setText(sp.getString("age", null));
        tv_sex.setText(sp.getString("sex", null));
        tv_uro.setText(sp.getString("uro", null));
        tv_mac1.setText(sp.getString("mac1", null));
        tv_mac2.setText(sp.getString("mac2", null));
    }

    @Click(R.id.rly_clear)
    void clearData() {
        clearDialog = new ClearDialog().showDialog(getActivity());
    }

    @Click(R.id.rly_name)
    void addName() {
        nameDialog = new NameDialog().showDialog(getActivity());
        final EditText edtName;
        edtName = (EditText) nameDialog.findViewById(R.id.dialog_et_name);
        nameDialog.findViewById(R.id.dialog_name_tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName = edtName.getText().toString();
                tv_name.setText(strName);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("name", strName).commit();

                nameDialog.dismiss();
            }
        });


    }

    @Click(R.id.rly_sex)
    void addSex() {
        sexDialog = new SexDialog().showDialog(getActivity());
        sexDialog.findViewById(R.id.tv_dlg_sex_man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvMan;
                tvMan = (TextView) sexDialog.findViewById(R.id.tv_dlg_sex_man);
                strSex = tvMan.getText().toString();
                tv_sex.setText(strSex);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("sex", strSex).commit();

                sexDialog.dismiss();
            }
        });
        sexDialog.findViewById(R.id.tv_dlg_sex_wuman).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvWuman;
                tvWuman = (TextView) sexDialog.findViewById(R.id.tv_dlg_sex_wuman);
                strSex = tvWuman.getText().toString();
                tv_sex.setText(strSex);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("sex", strSex).commit();

                sexDialog.dismiss();
            }
        });
    }

    @Click(R.id.rly_age)
    void addAge() {
        ageDialog = new AgeDialog().showDialog(getActivity());
        final EditText edtAge;
        edtAge = (EditText) ageDialog.findViewById(R.id.dialog_et_age);
        ageDialog.findViewById(R.id.dialog_age_tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAge = edtAge.getText().toString();
                tv_age.setText(strAge);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("age", strAge).commit();

                ageDialog.dismiss();
            }
        });
    }

    @Click(R.id.rly_uroclepsia)
    void addUroclepsia() {
        uroclepsiaDialog = new UroclepsiaDialog().showDialog(getActivity());
        uroclepsiaDialog.findViewById(R.id.tv_dlg_uroclepsia_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvUroY;
                tvUroY = (TextView) uroclepsiaDialog.findViewById(R.id.tv_dlg_uroclepsia_yes);
                strUro = tvUroY.getText().toString();
                tv_uro.setText(strUro);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("uro", strUro).commit();

                uroclepsiaDialog.dismiss();
            }
        });
        uroclepsiaDialog.findViewById(R.id.tv_dlg_uroclepsia_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvUroN;
                tvUroN = (TextView) uroclepsiaDialog.findViewById(R.id.tv_dlg_uroclepsia_no);
                strUro = tvUroN.getText().toString();
                tv_uro.setText(strUro);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("uro", strUro).commit();

                uroclepsiaDialog.dismiss();
            }
        });
    }

    @Click(R.id.rly_mac1)
    void addMac1() {
        macDialog = new MacDialog().showDialog(getActivity());
        final EditText edtMac;
        edtMac = (EditText) macDialog.findViewById(R.id.dialog_et_mac);
        macDialog.findViewById(R.id.dialog_mac_tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMac1 = edtMac.getText().toString();
                tv_mac1.setText(strMac1);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("mac1", strMac1).commit();

                macDialog.dismiss();
            }
        });
    }

    @Click(R.id.rly_mac2)
    void addMac2() {
        macDialog = new MacDialog().showDialog(getActivity());
        final EditText edtMac;
        edtMac = (EditText) macDialog.findViewById(R.id.dialog_et_mac);
        macDialog.findViewById(R.id.dialog_mac_tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMac2 = edtMac.getText().toString();
                tv_mac2.setText(strMac2);
                getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
                        .putString("mac2", strMac2).commit();

                macDialog.dismiss();
            }
        });
    }
}
