package ca.rldesigns.android.punchcounter;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

public class MainActivity extends Activity
{

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    Toast.makeText(this, "Widget ready to be added!", Toast.LENGTH_LONG).show();

    finish();
  }
}
