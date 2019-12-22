package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.about;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    private GLSurfaceView mGLSurfaceView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mGLSurfaceView = new AboutGLSurfaceView(this.getActivity());
        return mGLSurfaceView;
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }
}
