package in.srain.cube.views.ptr;

import android.view.View;
import android.widget.AbsListView;

/**
 * 抽象类，实现了 PtrHandler.java 接口，给出了 checkCanDoRefresh 的默认实现，给出了常见 View 是否可以下拉的判断方法。
 */
public abstract class PtrDefaultHandler implements PtrHandler {
    /**
     * 如果 SDK 版本为 14 以上，可以用 canScrollVertically 判断是否能在竖直方向上，向上滑动</br>
     * 不能向上，表示已经滑动到在顶部或者 Content 不能滑动，返回 true，可以下拉</br>
     * 可以向上，返回 false，不能下拉
     */
    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            // SDK 版本小于 14，如果 Content 是 ScrollView 或者 AbsListView,通过 getScrollY 判断滑动位置 </br>
            // 如果位置为 0，表示在最顶部，返回 true，可以下拉
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            //检查是否该视图可以在一个方向垂直滚动。
            return view.canScrollVertically(-1);
        }
    }

    /**
     * Default implement for check can perform pull to refresh
     *
     * @param frame
     * @param content
     * @param header
     * @return
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }
}