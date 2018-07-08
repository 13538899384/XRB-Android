package com.ygip.xrb_android.mvp.homepage.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.KeyBoardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.support.v7.widget.Toolbar;
import cn.droidlover.xdroidmvp.mvp.XActivity;

public class SearchActivity extends XActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.vs_delete)
    ViewStub vsDelete;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ImageView iv_delete;
    KeyBoardUtils keyBoardUtils;

    @Override
    public void initData(Bundle savedInstanceState) {
        initEdtView();
        keyBoardUtils = KeyBoardUtils.getInstance();
        keyBoardUtils.showKeyboard(this);
    }

    private void initEdtView() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    if (vsDelete.getParent() != null) {
                        vsDelete.inflate();
                        iv_delete = (ImageView)findViewById(R.id.iv_vs_delete);
                        if (iv_delete != null){
                            iv_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    etSearch.setText("");
                                }
                            });
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_search_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        keyBoardUtils.hideKeyboard(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        keyBoardUtils.hideKeyboard(this);
        super.onPause();
    }

}
