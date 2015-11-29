package pl.legowski.crimelife;

/**
 * Created by Pablo on 2015-11-27.
 */
public class City {
    public String name;
    public double posX;
    public double posY;
    public City(String aName, double aPosX, double aPosY) {
        name = aName;
        posX = aPosX;
        posY = aPosY;
    }

    public double getDistance(City otherCity) {
        return Math.sqrt(Math.pow(otherCity.posX-posX,2)+Math.pow(otherCity.posY-posY,2));
    }

    //public Store getStore(double time)
    //{
    //
    //}


}
