package in.srain.cube.views.ptr;

import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 *下拉刷新的 UI 接口
 *包含准备下拉，下拉中，下拉完成，重置以及下拉过程中的位置变化
 *
 * 通常情况下， Header 需要实现此接口，来处理下拉刷新过程中头部 UI 的变化。
 */
public interface PtrUIHandler {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     * 当内容视图已达到顶部和更新已经完成,视图将被重新设置。
     * Content 重新回到顶部， Header 消失，整个下拉刷新过程完全结束以后，重置 View。
     * @param frame
     */
    public void onUIReset(PtrFrameLayout frame);

    /**
     * prepare for loading
     * 准备加载
     * 准备刷新，Header 将要出现时调用。
     * @param frame
     */
    public void onUIRefreshPrepare(PtrFrameLayout frame);

    /**
     * perform refreshing UI
     * 执行刷新界面
     * 开始刷新，Header 进入刷新状态之前调用。
     */
    public void onUIRefreshBegin(PtrFrameLayout frame);

    /**
     * perform UI after refresh
     * 执行刷新界面之后
     * 刷新结束，Header 开始向上移动之前调用。
     */
    public void onUIRefreshComplete(PtrFrameLayout frame);

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
