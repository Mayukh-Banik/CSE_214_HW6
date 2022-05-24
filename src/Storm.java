//CSE214 R03 Mayukh Banik mayukh.banik@stonybrook.edu 114489797
import java.io.*;
public class Storm implements Serializable
{
    private String name;
    private double precipitation;
    private double windSpeed;
    private String date;

    /**
     * constructor for all variables
     * @param name name
     * @param precipitation precipitation
     * @param windSpeed wind speed
     * @param date date
     */
    public Storm(String name, double precipitation, double windSpeed, String date)
    {
        setName(name);
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.date = date;
    }

    /**
     * returns name
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * returns precipitation amount
     * @return precipitation amount
     */
    public double getPrecipitation()
    {
        return precipitation;
    }

    /**
     * returns the wind speed
     * @return double of speed
     */
    public double getWindSpeed()
    {
        return windSpeed;
    }

    /**
     * returns the date
     * @return date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * sets the name
     * @param name name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * returns the precipitation
     * @param precipitation precipitation
     */
    public void setPrecipitation(double precipitation)
    {
        this.precipitation = precipitation;
    }

    /**
     * returns the wind speed
     * @param windSpeed wind speed
     */
    public void setWindSpeed(double windSpeed)
    {
        this.windSpeed = windSpeed;
    }

    /**
     * sets the date
     * @param date date
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * returns the string form of this
     * @return string
     */
    public String toString()
    {
        return String.format("%1s%10s%10s%12s", name , getDate(), windSpeed , precipitation);
    }
}




























