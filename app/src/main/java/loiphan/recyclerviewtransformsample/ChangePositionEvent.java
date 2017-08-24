package loiphan.recyclerviewtransformsample;

/**
 * Copyright (c) 2017, VNG Corp. All rights reserved.
 *
 * @author loiphan <loipn@vng.com.vn>
 * @version 1.0
 * @since August 24, 2017
 */

public class ChangePositionEvent {

    private int mPosition;

    public ChangePositionEvent(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
