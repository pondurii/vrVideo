package org.rajawali3d.cardboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.vrtoolkit.cardboard.CardboardView;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.IRajawaliSurfaceRenderer;

public class RajawaliCardboardView extends CardboardView implements IRajawaliSurface {

    private IRajawaliSurfaceRenderer renderer;

    public RajawaliCardboardView(Context context) {
        super(context);
    }

    public RajawaliCardboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPause() {
        super.onPause();
        renderer.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        renderer.onResume();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            onPause();
        } else {
            onResume();
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onResume();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        renderer.onRenderSurfaceDestroyed(null);
    }

    @Override
    public void setFrameRate(double rate) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void setAntiAliasingMode(ANTI_ALIASING_CONFIG config) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void setSampleCount(int count) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void setSurfaceRenderer(IRajawaliSurfaceRenderer renderer) throws IllegalStateException {

        if (this.renderer != null)
            throw new IllegalStateException("A renderer has already been set for this view.");

        renderer.setRenderSurface(this);

        this.renderer = renderer;

        onPause(); // We want to halt the surface view until we are ready
    }

    @Override
    public void requestRenderUpdate() {
        requestRender();
    }
}
