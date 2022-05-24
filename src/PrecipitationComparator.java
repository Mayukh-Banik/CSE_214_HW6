//CSE214 R03 Mayukh Banik mayukh.banik@stonybrook.edu 114489797
import java.util.*;
public class PrecipitationComparator<E> implements Comparator
{
    /**
     * compares the precipitation of two objects
     * @param o1 object 1
     * @param o2 object 2
     * @return the value of the higher
     */
    public int compare(Object o1, Object o2)
    {
        String object1 = "" + ((int) ((Storm) o1).getPrecipitation());
        String object2 = "" + ((int) ((Storm) o2).getPrecipitation());
        return object1.compareTo(object2);
    }
}