import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileListMaker
{
    static List<String> list = new ArrayList<>();
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
        String itemToAdd;
        int indexToInsert;
        int indexToDelete;
        boolean done = false;
        String cmdRegEx = "[AaDdIiVvQqMmOoSsCc]";
        String cmd;

        do{
            showMenu();
            cmd = SafeInput.getRegExString(in, "Enter a command (A, D, I, V, Q, M, O, S, C)", cmdRegEx);

            try
            {
                switch (cmd.toUpperCase()) {
                    case "O":
                        if (newUnsavedFile || fileSaveFlag) {
                            String prompt = "Are you sure? You have an unsaved file!\n Opening a new file will replace your existing list.";
                            boolean burnFileYN = SafeInput.getYNConfirm(in, prompt);
                            if (burnFileYN) {
                                openFile();
                            }
                        } else {
                            openFile();
                        }
                        break;
                    case "A":
                        if (!hasAFile) {
                            newUnsavedFile = true;
                        }
                        itemToAdd = SafeInput.getNonZeroLenString(in, "Enter an item to add to the list");
                        list.add(itemToAdd);
                        showList(list);
                        fileSaveFlag = true;
                        break;
                    case "C":   // Clears the list in memory
                        if (!fileSaveFlag && !newUnsavedFile) {
                            list.clear();
                            hasAFile = newUnsavedFile = fileSaveFlag = false;
                        } else // Confirm user wants to clear unsaved list
                        {
                            boolean clearFileYN = SafeInput.getYNConfirm(in, "Are you sure you want to clear this list without saving? :");
                            if (clearFileYN) {
                                list.clear();
                                hasAFile = newUnsavedFile = fileSaveFlag = false;
                            }
                        }
                        break;
                    case "D":
                        if (list.size() > 0) {
                            indexToDelete = SafeInput.getRangedInt(in, "Enter the # of the item to delete", 1, list.size());
                            list.remove(indexToDelete - 1);
                            fileSaveFlag = true;
                            showList(list);
                        } else {
                            System.out.println("List is empty");
                            showList(list);
                        }
                        break;
                    case "I":
                        if (list.size() > 0) {
                            indexToInsert = SafeInput.getRangedInt(in, "Enter the index of item to insert", 1, list.size() + 1);
                            itemToAdd = SafeInput.getNonZeroLenString(in, "Enter an item to add");
                            list.add(indexToInsert - 1, itemToAdd);
                            showList(list);
                        } else {
                            System.out.println("List is empty");
                            showList(list);
                        }
                        break;
                    case "V":
                        showList(list);
                        break;
                    case "S":
                        saveFile();
                        fileSaveFlag = false;
                        newUnsavedFile = false;
                        hasAFile = true;
                        break;
                    case "M":
                        if (list.size() > 0)
                        {
                            indexToDelete = SafeInput.getRangedInt(in, "Enter the # of the item to move", 1, list.size());
                            itemToAdd = list.get(indexToDelete-1);
                            System.out.println(itemToAdd);
                            indexToInsert = SafeInput.getRangedInt(in, "Enter the index of item to insert", 1, list.size() + 1);
                            if(indexToDelete==1) {
                                list.add(indexToInsert - 1, itemToAdd);
                                list.remove(indexToDelete - 1);
                            }
                            else if (indexToDelete<indexToInsert)
                            {
                                list.add(indexToInsert - 1, itemToAdd);
                                list.remove(indexToDelete - 2);
                            }

                            showList(list);
                            fileSaveFlag = true;
                        } else {
                            System.out.println("List is empty");
                            showList(list);
                        }
                        break;
                    case "Q":
                        if (fileSaveFlag || newUnsavedFile)
                        {
                            if(SafeInput.getYNConfirm(in, "Save your list before quitting? You will lose it!"))
                            {
                                saveFile();
                                System.out.println("Thank you for running Listmaker!");
                                System.exit(0);
                            }
                        }
                        boolean confirmQuit = SafeInput.getYNConfirm(in, "Are you sure? ");
                        if (confirmQuit == true) {
                            done = true;
                            System.exit(0);
                            break;
                        }
                        else
                            System.out.println();
                }
            }
                catch(FileNotFoundException ex)
                {
                    System.out.println("There was an error and the file could not be found");
                    ex.printStackTrace();
                }
                catch(IOException ex)
                {
                    System.out.println("There was major IO error!\nExiting...");
                    ex.printStackTrace();
                    System.exit(1);
                }
        }while(!done);
    }

    private static void showList(List<String> lines)
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
        System.out.println("M - Move an item");
        System.out.println("O – Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C – Clear removes all the elements from the current list");
        System.out.println("V – View the list");
        System.out.println("Q – Quit the program");
        System.out.println("=====================================");
    }

    private static void saveFile() throws FileNotFoundException, IOException
    {
        if(!hasAFile)
        {
            curFileName = SafeInput.getNonZeroLenString(in, "Enter name of file (.txt extension will be added)");
            curFileName = curFileName + ".txt";
        }
        File workingDirectory = new File(".");
        Path path = Paths.get(workingDirectory.getPath() + "\\src\\" + curFileName);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            for (String rec : list) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            System.out.println("Data file written!");
        }
    }

    private static void openFile() throws FileNotFoundException, IOException
    {
        JFileChooser chooser = new JFileChooser();
        Path path = Paths.get(System.getProperty("user.dir"), "\\src");

        chooser.setCurrentDirectory(new File(path.toString()));

        // Create a simple parent to keep dialog in front
        JFrame parentFrame = new JFrame();
        parentFrame.setAlwaysOnTop(true);

        if(chooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION)
        {
            inFile = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            list.clear();
            while(inFile.ready())
            {
                list.add(inFile.readLine());
            }
            inFile.close();
            curFileName = chooser.getSelectedFile().getName();
            hasAFile = true;
            newUnsavedFile = false;
        }
    }
}

