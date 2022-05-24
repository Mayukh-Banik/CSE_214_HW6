//CSE214 R03 Mayukh Banik mayukh.banik@stonybrook.edu 114489797
import java.util.*;
public class WindSpeedComparator<E> implements Comparator
{
    /**
     * compares the value of two objects
     * @param o1 object 1
     * @param o2 object 2
     * @return codes for the case
     */
    public int compare(Object o1, Object o2)
    {
        String object1 = "" + ((Storm) o1).getWindSpeed();
        String object2 = "" + ((Storm) o2).getWindSpeed();
        return object1.compareTo(object2);
    }
}