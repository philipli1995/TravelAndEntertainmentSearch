package com.philipli.travelandentertainmentsearch.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by philip on 2017/10/10.
 *
 *
 */

public abstract class RxBaseActivity extends RxAppCompatActivity {

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initViews(savedInstanceState);

    }

    /**
    *  Created by philip on 2017/10/10 21:52.
    */
    public abstract int getLayoutId();

    /**
    *  Created by philip on 2017/10/10 22:23.
     *
     *  initialize Views
     *
     *  @param savedInstanceState
    */
    public abstract void initViews(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
