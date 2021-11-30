package com.my.clickapp.act;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.braintreepayments.cardform.view.CardForm;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;
    private View promptsView;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment);

        binding.RRback.setOnClickListener(view -> {

            onBackPressed();

        });

        binding.RRBook.setOnClickListener(view -> {

            AlertDaliogRecharge();

            //startActivity(new Intent(PaymentActivity.this, HomeActivity.class));

        });

        binding.RRCredit.setOnClickListener(v -> {

            binding.RadioOne1.setChecked(true);
            binding.RadioOne11.setChecked(false);

        });

        binding.RRDebit.setOnClickListener(v -> {

            binding.RadioOne1.setChecked(false);
            binding.RadioOne11.setChecked(true);

        });
    }



    private void AlertDaliogRecharge() {

        LayoutInflater li;
        ImageView ivBack;
        CardForm cardForm;
        RelativeLayout RRDone;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(PaymentActivity.this);
        promptsView = li.inflate(R.layout.activity_stripe_payment, null);
        ivBack = (ImageView) promptsView.findViewById(R.id.ivBack);
        cardForm = (CardForm) promptsView.findViewById(R.id.card_form);
        RRDone = (RelativeLayout) promptsView.findViewById(R.id.RRDone);

        alertDialogBuilder = new AlertDialog.Builder(PaymentActivity.this, R.style.myFullscreenAlertDialogStyle);   //second argument

        alertDialogBuilder.setView(promptsView);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                //.mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentActivity.this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        RRDone.setOnClickListener(v -> {

            startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
            finish();

        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}