//CSE214 R03 Mayukh Banik mayukh.banik@stonybrook.edu 114489797
import java.io.*;
import java.util.*;
import java.util.Map.*;
public class StormStatServer
{
    private static HashMap<String, Storm> database = new HashMap<>();
    public static String[] outputString = {"A - Add A Storm", "L - Look Up A Storm", "D - Remove A Storm",
            "E - Edit A Storm", "R - Print Storms by Rainfall", "W - Print Storms (Sorted by wind speed)",
            "X - Save and quit", "Q - Quit without saving state (and delete any save-file)"};
    public static Scanner input = new Scanner(System.in);
    public static FileReader fileReader;
    public static File file;
    public static FileInputStream fileInputStream;
    public static ObjectInputStream inputStream;

    /**
     * just selects what methods to run through
     * @param args default
     */
    @SuppressWarnings (value="unchecked")
    public static void main(String[] args)
    {
        System.out.println("Welcome to the StormStatServer, we may not be able to make it rain, " +
                "but we sure can tell you when it happened!");
        try
        {
            if (new File("hurricane.ser").exists())
            {
                file = new File("hurricane.ser");
                fileInputStream = new FileInputStream("hurricane.ser");
                fileReader = new FileReader("hurricane.ser");
                inputStream = new ObjectInputStream(fileInputStream);
                database = (HashMap<String, Storm>) inputStream.readObject();
                inputStream.close();
                fileReader.close();
                fileInputStream.close();
                System.out.println("hurricane.ser has been found and its data loaded.");
            }
        }
        catch (Exception e)
        {
            System.out.println("No previous data found. ");
        }
        String string = "";
        while (!string.equalsIgnoreCase("q") || !string.equalsIgnoreCase("Q"))
        {
            arrayStringOutput(outputString);
            string = stringToUpperCase(input.nextLine());
            try
            {
                switch (string)
                {
                    case "A":
                        A();
                        input.nextLine();
                        break;
                    case "L":
                        L();
                        break;
                    case "D":
                        D();
                        break;
                    case "E":
                        E();
                        break;
                    case "R":
                        R();
                        break;
                    case "W":
                        W();
                        break;
                    case "X":
                        X();
                        break;
                    case "Q":
                        Q();
                        break;
                    default:
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println(" Error!");
            }
        }
    }

    /**
     * adds a storm to the hashmap
     */
    public static void A() throws CustomException
    {
        System.out.println("Enter a name: ");
        String name = input.nextLine();
        System.out.println("Enter a date: ");
        String date = input.nextLine();
        if (!isDateProperFormat(date))
        {
            System.out.print("Non YYYY-MM-DD Date input");
            throw new CustomException();
        }
        System.out.println("Enter the precipitation (cm): ");
        double precipitation = input.nextDouble();
        System.out.println("Please enter the wind speed: ");
        double windSpeed = input.nextDouble();
        database.put(name, new Storm(name, precipitation, windSpeed, date));
        System.out.println(name + " has been added.");
    }

    /**
     * looks up a storm in the hashmap
     */
    public static void L()
    {
        System.out.println("Enter a name: ");
        Storm tempStorm = database.get(input.nextLine());
        if (tempStorm == null)
        {
            System.out.println("A storm with that name does not exist.");
        }
        else
        {
            System.out.println("Storm " + tempStorm.getName() + " Date: " + tempStorm.getDate() + ", " +
                    tempStorm.getWindSpeed() + "km/h, " + tempStorm.getPrecipitation() + " cm of rain. ");
        }
    }

    /**
     * deletes a specified storm in the hashmap
     */
    public static void D()
    {
        System.out.println("Enter a name: ");
        String string = input.nextLine();
        Storm tempStorm = database.get(string);
        database.remove(string);
        System.out.println("Storm " + tempStorm.getName() + " has been removed. ");
    }

    /**
     * edits a storm in the hashmap
     */
    public static void E()
    {
        try
        {
            System.out.println("Enter the name of the storm you wish to edit: ");
            String tempName = input.nextLine();
            Storm tempStorm = database.get(tempName);
            if (tempStorm == null)
            {
                throw new NullPointerException();
            }
            System.out.println("In Edit Mode, press enter without any input to leave data unchanged.\nNew Date: ");
            String tempDate = input.nextLine();
            if (!isDateProperFormat(tempDate) && !tempDate.equalsIgnoreCase(""))
            {
                throw new CustomException();
            }
            if (!tempDate.equalsIgnoreCase(""))
            {
                tempStorm.setDate(tempDate);
            }
            System.out.println("Please enter the precipitation (cm): ");
            String tempPrecipitation = input.nextLine();
            if (!tempPrecipitation.equalsIgnoreCase(""))
            {
                tempStorm.setPrecipitation(Double.parseDouble(tempPrecipitation));
            }
            System.out.println("Please enter the wind speed (cm): ");
            String tempWindSpeed = input.nextLine();
            if (!tempWindSpeed.equalsIgnoreCase(""))
            {
                tempStorm.setWindSpeed(Double.parseDouble(tempWindSpeed));
            }
            database.replace(tempName, database.get(tempName), tempStorm);
        }
        catch (Exception e)
        {
            if (e instanceof NullPointerException)
            {
                System.out.println("That storm was not found.");
            }
            if (e instanceof CustomException)
            {
                System.out.println("Incorrect Date Input.");
            }
        }
    }

    /**
     * prints the storm by the rainfall amount
     */
    @SuppressWarnings ("unchecked")
    public static void R()
    {
        try
        {
            List<Storm> list = new ArrayList<>();
            for (Entry<String, Storm> entry : database.entrySet())
            {
                list.add(entry.getValue());
            }
            list.sort(new PrecipitationComparator<Entry<String, Storm>>());
            Storm tempStorm = list.get(0);
            if (list.size() >= 2)
            {
                if (list.get(0).getPrecipitation() > list.get(1).getPrecipitation())
                {
                    list.remove(0);
                    for (int counter = 0; counter < list.size(); counter++)
                    {
                        if (tempStorm.getPrecipitation() < list.get(counter).getPrecipitation())
                        {
                            list.add(counter, tempStorm);
                        }
                    }
                    if (tempStorm.getPrecipitation() > list.get(list.size() - 1).getPrecipitation())
                    {
                        list.add(tempStorm);
                    }
                }
            }
            System.out.println(String.format("%1s%7s%20s%10s", "Name", "Date", "Wind (km/h)", "Rain (cm)")
                    + "\n-----------------------------------------------------------------------");
            for (Storm storm : list)
            {
                System.out.println(storm);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error sorting");
        }
    }

    /**
     * prints the storm by wind speed
     */
    @SuppressWarnings ("unchecked")
    public static void W()
    {
        try
        {
            List<Storm> list = new ArrayList<>();
            for (Entry<String, Storm> entry : database.entrySet())
            {
                list.add(entry.getValue());
            }
            list.sort(new WindSpeedComparator<Entry<String, Storm>>());
            Storm tempStorm = list.get(0);
            if (list.size() >= 2)
            {
                if (list.get(0).getWindSpeed() > list.get(1).getWindSpeed())
                {
                    list.remove(0);
                    for (int counter = 0; counter < list.size(); counter++)
                    {
                        if (tempStorm.getWindSpeed() < list.get(counter).getWindSpeed())
                        {
                            list.add(counter, tempStorm);
                        }
                    }
                    if (tempStorm.getWindSpeed() > list.get(list.size() - 1).getWindSpeed())
                    {
                        list.add(tempStorm);
                    }
                }
            }
            System.out.println(String.format("%1s%7s%20s%10s", "Name", "Date", "Wind (km/h)", "Rain (cm)")
                    + "\n-----------------------------------------------------------------------");
            for (Storm storm : list)
            {
                System.out.println(storm);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error sorting");
        }
    }

    /**
     * saves the hashmap to a file and terminates the program
     */
    public static void X()
    {
        try
        {
            file = new File("hurricane.ser");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(database);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("File saved to hurricane.ser.");
            System.exit(0);
        }
        catch (Exception e)
        {
            System.out.println("There was an error");
        }
    }

    /**
     * terminates the program without saving
     */
    public static void Q()
    {
        File myObj = new File("hurricane.ser");
        if (myObj.delete())
        {
            System.out.println("hurricane.ser has been found and deleted.");
        }
        else
        {
            System.out.println("Failed to delete the file.");
        }
        System.exit(0);
    }

    /**
     * prints out any string array
     * @param string string array
     */
    public static void arrayStringOutput(String[] string)
    {
        for (String s : string)
        {
            System.out.println(s);
        }
    }

    /**
     * converts all strings to uppercase
     * @param string string to be made uppercase
     * @return uppercase string
     */
    public static String stringToUpperCase(String string)
    {
        return string.toUpperCase(Locale.ENGLISH);
    }

    /**
     * checks if the date is the proper format true if it is
     * @param string of date to be checked
     * @return if it's a valid string
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isDateProperFormat(String string)
    {
        if (string.length() < 8 || string.length() > 10 || !string.contains("-"))
        {
            return false;
        }
        try
        {
            String[] tempString = string.split("-");
            if (tempString.length != 3 || tempString[0].length() != 4 || !(tempString[1].length() <= 2) ||
                    !(tempString[2].length() <= 2))
            {
                return false;
            }
            if (Integer.parseInt(tempString[1]) > 12)
            {
                System.out.println("The inputted month numerical value is greater than 12. ");
                return false;
            }
            if (Integer.parseInt(tempString[2]) > 31)
            {
                System.out.println("The inputted day numerical value is greater than 31. ");
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        String tempString = "qwertyuioplkjhgfdsazxcvbnmMNBVCXZLKJHGFDSAPOIUYTREWQ";
        for (int counter = 0; counter < tempString.length(); counter++)
        {
            if (string.contains("" + tempString.charAt(counter)))
            {
                return false;
            }
        }
        return true;
    }
}