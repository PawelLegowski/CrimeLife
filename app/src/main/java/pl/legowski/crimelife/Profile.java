package pl.legowski.crimelife;

/**
 * Created by Pablo on 2015-11-27.
 */
public class Profile {
    private static Profile ourInstance = new Profile();

    public static Profile getInstance() {
        return ourInstance;
    }

    private Profile()
    {
        
    }
}
