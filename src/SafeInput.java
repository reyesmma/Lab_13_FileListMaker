import java.util.Scanner;

public class SafeInput
{
    /**
     * A method that gets a non-zero length string from the user.
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do
        {
            System.out.print(prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }while(retString.length() == 0);

        return retString;
    }

    /**
     * A method that gets an integer from the user.
     *
     * @param pipe          Scanner to read the integer
     * @param prompt        Prompt for the user
     * @return              An integer value entered by the user
     */
    public static int getInt(Scanner pipe, String prompt)
    {
        boolean done = false;
        int val = 0;

        do{
            System.out.print(prompt + ": ");
            if(pipe.hasNextInt())
            {
                val = pipe.nextInt();
                pipe.nextLine();
                done = true;
            }
            else
            {
                String trash = pipe.nextLine();
                System.out.println("Enter a valid integer, not " + trash);
            }
        } while(!done);
        return val;
    }

    /**
     * A method that gets a double from the user.
     *
     * @param pipe          Scanner to read the double
     * @param prompt        Prompt for the user
     * @return              A double value entered by the user
     */
    public static double getDouble(Scanner pipe, String prompt)
    {
        boolean done = false;
        double val = 0;

        do{
            System.out.print(prompt + ": ");
            if(pipe.hasNextDouble())
            {
                val = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            }
            else
            {
                String trash = pipe.nextLine();
                System.out.println("Enter a valid double, not " + trash);
            }
        } while(!done);
        return val;
    }

    /**
     * A method that gets an integer from the user within a specified range.
     *
     * @param pipe          Scanner to read the integer
     * @param prompt        Prompt for the user
     * @param low           low bound of the range
     * @param high          high bound of the range
     * @return              An integer value entered by the user within specified range
     */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        boolean done = false;
        int val = 0;

        do{
            System.out.print(prompt + ": ");
            if(pipe.hasNextInt())
            {
                val = pipe.nextInt();
                pipe.nextLine();
                if(val>=low && val<=high)
                    done = true;
                else
                    System.out.println("Integer must be between " + low + " and " + high);
            }
            else
            {
                String trash = pipe.nextLine();
                System.out.println("Enter a valid integer, not " + trash);
            }
        } while(!done);
        return val;
    }

    /**
     * A method that gets a double from the user within a specified range.
     *
     * @param pipe          Scanner to read the integer
     * @param prompt        Prompt for the user
     * @param low           low bound of the range
     * @param high          high bound of the range
     * @return              A double value entered by the user within specified range
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        boolean done = false;
        double val = 0;

        do{
            System.out.print(prompt + ": ");
            if(pipe.hasNextDouble())
            {
                val = pipe.nextDouble();
                pipe.nextLine();
                if(val>=low && val<=high)
                    done = true;
                else
                    System.out.println("Double must be between " + low + " and " + high);
            }
            else
            {
                String trash = pipe.nextLine();
                System.out.println("Enter a valid double, not " + trash);
            }
        } while(!done);
        return val;
    }

    /**
     * A method that gets a Y or N from the user and returns a boolean
     *
     * @param pipe          Scanner to read the integer
     * @param prompt        Prompt for the user
     * @return              True for Y and False for N
     */
    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        boolean done = false;
        boolean retVal = false;
        String val = "";

        do{
            System.out.print(prompt + " [Y/N]: ");
            val = pipe.nextLine();
            if(val.equalsIgnoreCase("Y") || val.equalsIgnoreCase("N"))
                done = true;
            else
                System.out.println("Enter Y or N");
        } while(!done);
        return val.equalsIgnoreCase("Y");
    }

    /**
     * A method that gets a string that matches a regular expression
     *
     * @param pipe          Scanner to read the input
     * @param prompt        Prompt for the user
     * @param regEx         Regular expression to match
     * @return              A string that matches the regular expression
     */
    public static String getRegExString(Scanner pipe, String prompt, String regEx)
    {
        boolean done = false;

        String val = "";
        do {
            System.out.print(prompt + ": ");
            val = pipe.nextLine();
            if(val.matches(regEx))
                done = true;
            else
                System.out.println("Enter a value that matches the pattern: " + regEx + " Not: " + val);
        } while(!done);
        return val;
    }

    /**
     * A method that prints a header with message centered
     *
     * @param msg   String for message to print
     */
    public static void prettyHeader(String msg)
    {
        int totalWidth = 60;
        int sideStars = 3;
        int innerWidth = totalWidth - (sideStars * 2);

        // Print Top line
        for(int i = 0; i < totalWidth; i++)
            System.out.print("*");
        System.out.println();

        // Calculate spaces for centering
        int msgLength = msg.length();
        int totalSpaces = innerWidth - msgLength;
        int leftSpaces = totalSpaces / 2;
        int rightSpaces = totalSpaces - leftSpaces;

        // Middle line
        // Print left stars
        for(int i = 0; i < sideStars; i++)
            System.out.print("*");
        // Print left spaces
        for(int i = 0; i < leftSpaces; i++)
            System.out.print(" ");
        // Print message
        System.out.print(msg);
        // Print right spaces
        for(int i = 0; i < rightSpaces; i++)
            System.out.print(" ");
        // Print right stars
        for(int i = 0; i < sideStars; i++)
            System.out.print("*");
        System.out.println();
        // Print bottom line
        for(int i = 0; i < totalWidth; i++)
            System.out.print("*");
        System.out.println();
    }

}
