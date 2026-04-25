import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker
{
    static List<String> list = new ArrayList();
    static Scanner in = new Scanner(System.in);
    static BufferedReader inFile;
    static PrintWriter outFile;
    static String curFileName = "";
    static boolean running = true;          // controls execution via Q command
    static boolean hasAFile = false;        // changes after a file is loaded
    static boolean newUnsavedFile = false;  // when the user begins creating a new list
    static boolean fileSaveFlag = false;    // tracks if file needs to be saved.

    public static void main(String[] args)
    {
        ArrayList<String> list = new ArrayList<String>();
        String itemToAdd;
        int indexToInsert;
        boolean done = false;
        String cmdRegEx = "[AaDdIiPpQq]";

        do{
            showMenu();
            String cmd = SafeInput.getRegExString(in, "Enter a command (A, D, I, P, Q)", cmdRegEx);

            try {
                switch (cmd.toUpperCase())
                {
                    case "O":
                        if(newUnsavedFile || fileSaveFlag)
                        {
                            String prompt = "Are you sure? You have an unsaved file!\n Opening a new file will replace it.";
                            boolean burnFileYN = SafeInput.getYNConfirm(in, prompt);

                            if(burnFileYN)
                            {
                                openFile();
                            }
                        }
                        else {
                            openFile();
                        }
                        break;
                    case "A":
                        if(!hasAFile)
                        {
                            newUnsavedFile = true;
                        }
                        itemToAdd = SafeInput.getNonZeroLenString(in, "Enter an item to add");
                        list.add(itemToAdd);
                        showList(list);
                        fileSaveFlag = true;
                        break;
                    case "C":   // Clears the list in memory
                    {
                        if(!fileSaveFlag && !newUnsavedFile)
                        {
                            list.clear();
                            hasAFile = newUnsavedFile = fileSaveFlag = false;
                        }
                        else // Conirm user wants to clear unsaved list
                        {
                            boolean clearFileYN = SafeInput.getYNConfirm(in, "Are you sure you want to clear this list without saving? :");
                            if(clearFileYN)
                            {
                                list.clear();
                                hasAFile = newUnsavedFile = fileSaveFlag = false;
                            }
                        }
                        break;


                    }
                    case "D":
                        if (list.size() > 0) {
                            int indexToDelete = SafeInput.getRangedInt(in, "Enter the index of the item to delete", 1, list.size());
                            list.remove(indexToDelete - 1);
                            showList(list);
                            break;
                        } else {
                            System.out.println("List is empty");
                            showList(list);
                            break;
                        }
                    case "I":
                        if (list.size() > 0) {
                            indexToInsert = SafeInput.getRangedInt(in, "Enter the index of item to insert", 1, list.size() + 1);
                            itemToAdd = SafeInput.getNonZeroLenString(in, "Enter an item to add");
                            list.add(indexToInsert - 1, itemToAdd);
                            showList(list);
                            break;
                        } else {
                            System.out.println("List is empty");
                            showList(list);
                            break;
                        }
                    case "P":
                        showList(list);
                        break;
                    case "Q":
                        boolean confirmQuit = SafeInput.getYNConfirm(in, "Are you sure? ");
                        if (confirmQuit == true) {
                            done = true;
                            break;
                        } else
                            System.out.println();
                }
            }
        }while(!done);
    }

    private static void showList(ArrayList<String> lines)
    {
        System.out.println("Current List");
        for(int i = 0; i < lines.size(); i++)
            System.out.println((i + 1) + ": " + lines.get(i));
    }

    private static void showMenu()
    {
        System.out.println("=====================================");
        System.out.println("\t\t\t\tMENU");
        System.out.println("=====================================");
        System.out.println("A – Add an item to the list");
        System.out.println("D – Delete an item from the list");
        System.out.println("I – Insert an item into the list");
        System.out.println("P – Print the list");
        System.out.println("Q – Quit the program");
        System.out.println("=====================================");
    }


    private static void saveFile() throws FileNotFoundException, IOException
    {
        if(!hasAFile)
        {
            curFileName = SafeInput.getNonZeroLenString(console, "Enter name of file (.txt extension will be added): ");
            curFileName = curFileName + ".txt";
        }

        Path path = Paths.get(System.getProperty("user.dir", "\\src\\", curFileName));

        outFile = new PrintWriter(new BufferedWriter(new FileWriter(curFileName)));
        for(String ln:list)
        {
            outFile.write(ln, 0, ln.length()); // write(String s, int off, int len)
            outFile.write('\n');
        }
    }




    private static void openFile() throws FileNotFoundException, IOException
    {
        // display the filechooser
        JFileChooser chooser = new JFileChooser();
        // File workingDirectory = new File(System.getProperty("user.dir;
        Path path = Paths.get(System.getProperty("user.dir"), "\\src");

        chooser.setCurrentDirectory(new File(path.toString()));

        if(chooser.showOpenDialog(null)) == JFileChooser.APPROVE_OPTION)
        {
            inFile = new BufferedReader(new FileReader(chooser.getSelectedFile()));

            list.clear();
            while(inFile.ready())
            {
                list.add(inFile.readLine());
            }
            inFile.close();

            curFilename = chooser.getSelectedFile().getname();

            hasAFile = true;
            newUnsavedFile = false;










        }

    }

}

