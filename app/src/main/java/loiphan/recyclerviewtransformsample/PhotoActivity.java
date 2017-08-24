package loiphan.recyclerviewtransformsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017, VNG Corp. All rights reserved.
 *
 * @author loiphan <loipn@vng.com.vn>
 * @version 1.0
 * @since August 23, 2017
 */

public class PhotoActivity extends AppCompatActivity {

    @BindView(R.id.txtPosition)
    TextView txtPosition;
    @BindView(R.id.btnDown)
    TextView btnDown;
    @BindView(R.id.btnUp)
    TextView btnUp;

    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        mPosition = getIntent().getIntExtra("position", 0);
        txtPosition.setText(String.valueOf(mPosition));

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPosition > 0) {
                    mPosition--;
                    txtPosition.setText(String.valueOf(mPosition));
                    EventBus.getDefault().post(new ChangePositionEvent(mPosition));
                }
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPosition < 9) {
                    mPosition++;
                    txtPosition.setText(String.valueOf(mPosition));
                    EventBus.getDefault().post(new ChangePositionEvent(mPosition));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        intent.putExtra("position", mPosition);
        super.onBackPressed();
    }
}
