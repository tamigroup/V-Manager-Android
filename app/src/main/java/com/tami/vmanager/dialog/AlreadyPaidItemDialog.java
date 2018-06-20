package com.tami.vmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.tami.vmanager.R;
import com.tami.vmanager.utils.Utils;

/**
 * Created by why on 2018/6/19.
 */
public class AlreadyPaidItemDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String contractMoney;
    private String paidMoney;

    public AlreadyPaidItemDialog(Context context, String contractMoney, String paidMoney) {
        super(context);
        this.context = context;
        this.contractMoney = contractMoney;
        this.paidMoney = paidMoney;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_dialog_already_paid_item, null);
        view.findViewById(R.id.sdap_close).setOnClickListener(this);
        //合同金额
        TextView contractAmout = view.findViewById(R.id.sdap_show_contract_amount);
        setText(contractAmout, R.string.show_contract_amount, contractMoney);
        //已付款项
        TextView amountPaid = view.findViewById(R.id.sdap_show_amount_paid);
        setText(amountPaid, R.string.show_amount_paid, paidMoney);
        setCanceledOnTouchOutside(false);
//        setCancelable(false);//对话框以后地方点击不消息包含返回键
        setContentView(view);
    }

    private void setText(TextView view, @StringRes int resId, String money) {
        String paidMoneyStr = context.getString(resId, money);
        int start = paidMoneyStr.length() - money.length();
        int end = paidMoneyStr.length();
        view.setText(Utils.getSplicing(context, paidMoneyStr, start, end, 16));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdap_close:
                dismiss();
                break;
        }
    }
}
