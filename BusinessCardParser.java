import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class BusinessCardParser
{
    //Array of Constants used to determine whether or not a character is a number
    public static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    //The file that contains the data on names
    public static final String NAMEDATAFILE = "names.txt";

    public static void main(String []args)
    {   
        //Open a Scanner and read in input until the program is no longer in use
        Scanner scanner = new Scanner(System.in);
        boolean inUse = true;
        while (inUse)
        {
            //Prompt the user for the name of the file or to close to program
            System.out.println("Enter the name of the file (or type quit to exit): ");
            String fileName = scanner.nextLine();

            //If they typed "quit" don't look for a file and end the loop
            if (fileName.equals("quit"))
            {
                inUse = false;
            }

            //Otherwise find and parse the file
            else
            {
                ContactInfo info = getContactInfo(fileName);

                //Print out the parsed information
                System.out.println("\n" + "Name: " + info.getName());
                System.out.println("Phone: " + info.getPhoneNumber());
                System.out.println("Email: " + info.getEmailAddress() + "\n");
            }
        }

        scanner.close();
    }
    
    /*
    Returns an object containing the parsed information from the document, namely the name,
    phone number, and email of the person in the document. If one of those is not found it's
    value is saved as "Unknown".
    */
    static ContactInfo getContactInfo(String document)
    {
        //Load in all the data we have on names (in this case in names.txt)
        String[] names = getNameData();

        //Set default values to unknown so that if an instance of any of them is not found in
        //the data they are stored as Unknown
        String name = "Unknown";
        String phoneNum = "Unknown";
        String email = "Unknown";

        //Read in the data from the file line by line
        try(BufferedReader br = new BufferedReader(new FileReader(document)))
        {
            String data;

            while ((data = br.readLine()) != null)
            {
                //If a name has not been found and the data matches the data for a name
                if (isName(data, names) && name.equals("Unknown"))
                {
                    //Make that line the name
                    name = data;
                }

                //If a phone number has not been found
                if (phoneNum.equals("Unknown"))
                {
                    //Find out whether or not the currnet line is a phone number
                    phoneNum = FindPhoneNum(data);
                }

                //If an email has not been found and the data matches the data for an email
                if (isEmail(data) && email.equals("Unknown"))
                {
                    //Make that line the email
                    email = data;
                }
            }
        } 

        //Incase the specified file is not found
        catch(IOException error)
        {
            System.out.println(error);
        }

        //Create an object containing the parsed information and return it
        ContactInfo info = new ContactInfo(name, phoneNum, email);

        return info;
    }

    //Stores all the lines from a text document in an array of Strings and returns
    //that array
    static String[] getNameData()
    {
        //Variable to count the lines in the data file so we know how big the array should be
        int lines = 0;

        //Loop through the file once and count the lines
        try (BufferedReader reader = new BufferedReader(new FileReader("names.txt")))
        {
            while (reader.readLine() != null) 
            {
                lines++;
            }
        }

         //Incase the specified file is not found
        catch(IOException error)
        {
            System.out.println(error);
        }

        //Make an array that is the size of the amount of lines in the file
        String[] names = new String[lines];

        //Loop through the file and put each name in the file into the array
        try (BufferedReader reader = new BufferedReader(new FileReader("names.txt")))
        {
            int index = 0;
            String name;
            while ((name = reader.readLine()) != null)
            {
                names[index] = name.toLowerCase();
                index++;
            }

            //return the array when done
            return names;
        }

        //Incase the specified file is not found
        catch(IOException error)
        {
            System.out.println(error);
        }

        //returns the array in case the file is not found
        return names;
    }

    //Returns whether or not a given string is a name by comparing the first word of a string 
    //to the array containing the data on names and returns whether or not there is a match.
    static boolean isName(String data, String[] names)
    {
        //Splits the line into an array at white space
        String firstName = data.split(" ")[0];

        //Check if the first word matches any of the names in our names array
        return (Arrays.asList(names).contains(firstName.toLowerCase()));
    }

    /*
    Checks if a given string is a phone by parsing all the numbers from a given string and
    checking the length of the result. If it is longer then or equal to 7, it is a phone 
    number so the number is returned. Otherwise the value "Unknown" returned because a
    number was not found. 
    */
    static String FindPhoneNum(String data)
    {
        //A string made to hold only the numbers in the passed data
        String numbersOnly = "";

        //Loop through the passed data, if any character is a number then add it to numbersOnly
        for (int i = 0; i < data.length(); i++)
        {
            for (int j = 0; j < NUMBERS.length; j++)
            {
                if(data.charAt(i) == NUMBERS[j])
                {
                    numbersOnly += String.valueOf(NUMBERS[j]);
                }
            }
        }

        //If numbers only is less than 7 then its not a phone number so it is still Unknown
        if (numbersOnly.length() < 7)
        {
            return "Unknown";
        }

        //Othwerise a number was found so return that number
        else
        {
            return numbersOnly;
        }
    }
    
    //Checks if a given string is an email by looking for the @ character. If that character
    //is found then it is an email and it returns true
    static boolean isEmail(String data)
    {
        //Look through every character in the passed data, if an @ symbol is found return true
        //otherwise return false
        for (int i = 0; i < data.length(); i++)
        {
            if (data.charAt(i) == '@')
            {
                return true;
            }
        }   
        return false;
    }
}