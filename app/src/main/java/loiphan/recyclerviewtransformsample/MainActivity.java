package loiphan.recyclerviewtransformsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements PhotoAdapter.PhotoAdapterCallBack {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private boolean mShouldTransform;
    private int mTempPosition;
    private ActivityOptionsCompat mTempOptionsCompat;

    private TopSnapLinearLayoutManager mLinearLayoutManager;

    private final Transition.TransitionListener sharedExitListener =
            new TransitionCallback() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    setExitSharedElementCallback(null);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        getWindow().getSharedElementExitTransition().addListener(sharedExitListener);

        mLinearLayoutManager = new TopSnapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        PhotoAdapter adapter = new PhotoAdapter(this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d("WTF", "newState " + newState);
//                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
                if (mShouldTransform) {
                    mShouldTransform = false;
                    Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                    intent.putExtra("position", mTempPosition);
                    startActivityForResult(intent, 100, mTempOptionsCompat.toBundle());
                }
            }
        });
    }

    @Override
    public void onItemClick(ImageView imageView, int position) {
        if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() > position) {
            mShouldTransform = true;
            mRecyclerView.smoothScrollToPosition(position);
            mTempPosition = position;
            mTempOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "target");
        } else {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "target");
            Intent intent = new Intent(this, PhotoActivity.class);
            intent.putExtra("position", position);
            startActivityForResult(intent, 100, optionsCompat.toBundle());
        }
    }

    @Override
    public void onActivityReenter(int resultCode, final Intent data) {
        super.onActivityReenter(resultCode, data);
        setExitSharedElementCallback(new android.app.SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                super.onMapSharedElements(names, sharedElements);
                int position = data.getIntExtra("position", 0);
                PhotoAdapter.ViewHolder viewHolder = (PhotoAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
                Log.d("WTF", "position " + position + " - viewHolder != null " + (viewHolder != null));
                if (viewHolder == null) return;
                names.add("target");
                sharedElements.put("target", viewHolder.mImageView);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangePositionEvent(ChangePositionEvent event) {
        Log.d("WTF", "onChangePositionEvent " + event.getPosition());
        mLinearLayoutManager.scrollToPositionWithOffset(event.getPosition(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
