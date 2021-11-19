package com.compact.widget;

import com.google.android.material.appbar.AppBarLayout;

/**
 * App bar collapsing state
 *
 * @author Paulo Caldeira <paulo.caldeira@acin.pt>.
 */
public class CompactAppBarLayout {
    // State
    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    public static abstract class OnStateChangedListener implements AppBarLayout.OnOffsetChangedListener {
        private State mCurrentState = State.IDLE;

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (verticalOffset == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                mCurrentState = State.COLLAPSED;
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE);
                }
                mCurrentState = State.IDLE;
            }
        }

        /**
         * Notifies on state change
         *
         * @param appBarLayout Layout
         * @param state        Collapse state
         */
        public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
    }
}