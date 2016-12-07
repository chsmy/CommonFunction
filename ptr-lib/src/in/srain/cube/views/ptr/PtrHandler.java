package in.srain.cube.views.ptr;

import android.view.View;

/**
 * 下拉刷新的功能接口
 * 包含刷新功能回调方法以及判断是否可以下拉的方法。用户实现此接口来进行数据刷新工作。
 */
public interface PtrHandler {

    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view.
     * <p/>检查是否可可以刷新  比如内容是空或者第一个子view 存在
     * {@link in.srain.cube.views.ptr.PtrDefaultHandler#checkContentCanBePulledDown}
     */
    public boolean checkCanDoRefresh(final PtrFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     * 开始刷新
     * @param frame
     */
    public void onRefreshBegin(final PtrFrameLayout frame);
}