package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.about;

import android.content.Context;
import android.opengl.GLSurfaceView;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.opengl.LineRenderer;

public class AboutGLSurfaceView extends GLSurfaceView {

    private final LineRenderer renderer;

    public AboutGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        renderer = new LineRenderer();
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
