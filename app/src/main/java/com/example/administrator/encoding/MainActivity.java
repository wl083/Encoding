package com.example.administrator.encoding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7,btn8,btn9;
    private EditText edtMessage;
    //* 显示加密后内容
    private TextView tvShowEncodingResult, tvDecodingTitle, tvShowDecodingResult;
    //* 待加密内容
    public String encodingMessage = null;
    private String TAG = "lei";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);

        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);

        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);

        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);

        btnSetUnenable();
        //* 输入框
        edtMessage = (EditText) findViewById(R.id.edt_message);
        edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null && charSequence.length() == 0) {
                    btnSetUnenable();
                } else {
                    btnSetEnable();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String message = edtMessage.getText().toString();
                ;
                if (message == null && message.length() == 0) {
                    btnSetUnenable();
                } else {
                    encodingMessage = message;
                    btnSetEnable();
                }
            }
        });


        tvShowEncodingResult = (TextView) findViewById(R.id.tv_encoding);

        tvDecodingTitle = (TextView) findViewById(R.id.tv_decoding_title);
        tvShowDecodingResult = (TextView) findViewById(R.id.tv_decoding_result);
    }

    public void btnSetEnable() {

        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);


        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
    }

    public void btnSetUnenable() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);

        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);
    }

    public void tvSetVisible() {
        tvDecodingTitle.setVisibility(View.VISIBLE);
        tvShowDecodingResult.setVisibility(View.VISIBLE);
    }

    public void tvSetInvisible() {
        tvDecodingTitle.setVisibility(View.GONE);
        tvShowDecodingResult.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {

        tvShowEncodingResult.setText("");
        //*MD5 加密
        switch (view.getId()) {
            case R.id.btn1:
//                boolean a = encodingMessage.equals("");
//                boolean b = encodingMessage == null;
                //* null 不等于 ""
//                Log.i("lei", "test : "  + a+"  ----   " + b);
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
//                    btnSetEnable();
                    Md5Coding();
                }
                break;

            //* base64 加密
            case R.id.btn2:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    btnSetEnable();
                    Base64Coding();
                }
                break;
            //* base64 解码
            case R.id.btn3:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    tvSetVisible();
                    String result = EncodingHelper.decodeBase64(encodingMessage);
                    tvShowDecodingResult.setText(result);
                }
                break;
            //* 对称加密DES算法
            case R.id.btn4:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    btnSetEnable();
                    DesEncoding();
                }
                break;
            //* 对称加密DES算法
            case R.id.btn5:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    btnSetEnable();

                    tvSetVisible();
                    DesDecoding();
                }
                break;

            /**
             * * RSA加密
             */
            case R.id.btn6:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    btnSetEnable();

                    rsaEncoding();
                }
                break;
            /**
             * *RSA 解密
             */
            case R.id.btn7:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    btnSetEnable();
                    tvSetVisible();
                    rsaDecoding();
                }
                break;

            //* AES 加密
            case R.id.btn8:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    btnSetEnable();
                    tvSetVisible();
                    aesEncoding();
                }
                break;

            /**
             * * AES解密（有问题）
             */
            case R.id.btn9:
                if (!(encodingMessage == null) && encodingMessage.equals("")) {
                    showToast("请输入有效内容~~~");
                    btnSetUnenable();
                } else {
                    btnSetEnable();
                    tvSetVisible();
                    //* 解密有问题
//                    aesDecoding();
                    showToast("请期待~~");
                }
                break;
        }

    }

    //    String message = "";
    private void aesDecoding() {
        String result = EncodingHelper.aesDeconding(encodingMessage,"666888");
//        Log.i(TAG, "aesDecoding message is : " + message);
//        byte[] result = EncodingHelper.decrypt(message.getBytes(),"12345678");

        Log.i(TAG, "aesDecoding: " + result);
        tvShowDecodingResult.setText(result);


    }

    private void aesEncoding() {
        String result = EncodingHelper.aesEncoding(encodingMessage,"666888");
//        byte[] result = EncodingHelper.encrypt(encodingMessage, "12345678");
        Log.i(TAG, "aesEncoding: " + result);

//        message = new String(result);
        tvShowEncodingResult.setText(result);


//        byte[] decrypt = EncodingHelper.decrypt(result, "12345678");
//        Log.i(TAG, "aesDecoding: 解码" + decrypt.toString());
//        Log.i(TAG, "aesDecoding: 解码" + new String(decrypt));
    }


    private void rsaDecoding() {
        String result = EncodingHelper.rsaDecoding(encodingMessage);
        tvShowDecodingResult.setText(result);
    }

    private void rsaEncoding() {
        String result = EncodingHelper.rsaEncoding(encodingMessage);
        Log.i(TAG, "rsaEncoding: " + result);
        tvShowEncodingResult.setText(result);
    }

    private void DesDecoding() {
        String result = EncodingHelper
                .desEncode(encodingMessage
                        , "666888", EncodingHelper.DES_DECODING);
        Log.i(TAG, "DesDecoding: " + result);
        tvShowDecodingResult.setText(result);

    }

    private void DesEncoding() {
        String result = EncodingHelper.desEncode(encodingMessage
                , "666888", EncodingHelper.DES_ENCODING);
        Log.i(TAG, "DesEncoding: " + result);
        tvShowEncodingResult.setText(result);
    }

    private void Base64Coding() {
        String result = EncodingHelper.base64Encoding(encodingMessage);
        Log.i(TAG, "Base64Coding: " + result);
        tvShowEncodingResult.setText(result);
    }

    public void Md5Coding() {
//        MessageDigest digest = MessageDigest.getInstance("md5");
        String result = EncodingHelper.md5Encoding(encodingMessage);
        tvShowEncodingResult.setText(result);
    }


    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showLogi(String msg) {
        Log.i(TAG, msg);
    }
}
