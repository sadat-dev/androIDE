package dev.sadat.androide;

import android.app.Activity;
import android.os.Bundle;
import dev.sadat.androide.views.EditorView;


public class WelcomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditorView editor = new EditorView(this);
        setContentView(editor);
    }
}