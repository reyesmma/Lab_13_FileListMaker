import java.util.ArrayList;
import java.util.Scanner;

public class ListMaker
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<String>();
        String itemToAdd;
        int indexToInsert;
        boolean done = false;
        String cmdRegEx = "[AaDdIiPpQq]";

        do{
            showMenu();
            String cmd = SafeInput.getRegExString(in, "Enter a command (A, D, I, P, Q)", cmdRegEx);
            switch (cmd.toUpperCase())
            {
                case "A":
                    itemToAdd = SafeInput.getNonZeroLenString(in,"Enter an item to add");
                    list.add(itemToAdd);
                    showList(list);
                    break;
                case "D":
                    if(list.size()>0){
                        int indexToDelete = SafeInput.getRangedInt(in,"Enter the index of the item to delete", 1, list.size());
                        list.remove(indexToDelete-1);
                        showList(list);
                        break;
                    }
                    else{
                        System.out.println("List is empty");
                        showList(list);
                        break;
                    }
                case "I":
                    if(list.size()>0){
                        indexToInsert = SafeInput.getRangedInt(in,"Enter the index of item to insert", 1, list.size()+1);
                        itemToAdd = SafeInput.getNonZeroLenString(in,"Enter an item to add");
                        list.add(indexToInsert-1, itemToAdd);
                        showList(list);
                        break;
                    }
                    else{
                        System.out.println("List is empty");
                        showList(list);
                        break;
                    }
                case "P":
                    showList(list);
                    break;
                case "Q":
                    boolean confirmQuit = SafeInput.getYNConfirm(in, "Are you sure? ");
                    if(confirmQuit==true) {
                        done = true;
                        break;
                    }
                    else
                        System.out.println();
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
}

