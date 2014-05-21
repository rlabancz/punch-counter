package ca.rldesigns.android.punchcounter;

import android.content.SharedPreferences;

public class ApplicationData
{
  public static final String DATABASE_NAME = "CounterData";

  public static final String COUNTER = "Counter";

  private final SharedPreferences preferences;

  public ApplicationData(SharedPreferences preferences)
  {
    this.preferences = preferences;
  }

  public String getCounter()
  {
    return getString(COUNTER);
  }

  private String getString(String name)
  {
    String result = preferences.getString(name, null);
    if (result == null)
      result = "";
    return result;
  }
}