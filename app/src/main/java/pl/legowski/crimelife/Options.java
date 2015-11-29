package pl.legowski.crimelife;

/**
 * Created by Pablo on 2015-11-27.
 */
public class Options {
    private static Options ourInstance = new Options();

    public static Options getInstance() {
        return ourInstance;
    }

    private Options()
    {
        
    }
}
