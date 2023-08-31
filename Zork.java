/**
 * An driver program used to test out the <CODE>StoryTree</CODE> 10-ary Tree
 * written as part of this assignment.  The user can use the commands below
 * to perform operations on <CODE>StoryTree</CODE> objects.
 * <dt><b>Commands:</b><dd> 
 * Edit:
 *  V: View the cursor's position, option and message.
    S: Select a child of this cursor (options are 1, 2, and 3).
    O: Set the option of the cursor.
    M: Set the message of the cursor.
    A: Add a child StoryNode to the cursor.
    D: Delete one of the cursor's children and all its descendants.
    R: Move the cursor to the root of the tree.
    Q: Quit editing and return to main menu.
 * Play and Quit. This also has the functionality to save and load StoryTrees. 
 * @author 
 * 		Uday Turakhia, SBU ID #: 115102637
 * <dt><b>Assignment:</b><dd>
 *    Homework #5 for CSE 214, Spring 2023
 * 		Recitation #: R03
 * <dt><b>Date:</b><dd>
 *    April 5th, 2023
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Zork 
{ 
    public static Scanner input = new Scanner(System.in);//Scanner

    /**
     * Provides a user interface allowing a user to edit the story followed by the game represented by tree. 
     * This function should continue to process user commands until the user enters Q to exit. 
     * The following commands should be supported for editing the tree:
     * @param tree
     *      The tree that is going to be edited
     */
    public static void editTree(StoryTree tree)
    {
        if(tree.getRoot().getNumChild() == 0)
            tree.SetCursor(tree.getRoot());

        while(true)
        {
            System.out.print("\nZork Editor:\n"
            +"V: View the cursor's position, option and message.\n"
            +"S: Select a child of this cursor (options are 1-10).\n"
            +"O: Set the option of the cursor.\n"
            +"M: Set the message of the cursor.\n"
            +"A: Add a child StoryNode to the cursor.\n"
            +"D: Delete one of the cursor's children and all its descendants.\n"
            +"R: Move the cursor to the root of the tree.\n"
            +"P: Go back to parent.\n"
            +"Q: Quit editing and return to main menu.\n"
            +"\nPlease select a option: ");

            char inp = input.nextLine().toUpperCase().charAt(0);
            System.out.println();

            if(inp == 'V')
            {
                System.out.println(tree.cursorEditToString());
            }
            else if(inp == 'S')
            {
                if(tree.getCursorOptions().length == 0)
                {
                    System.out.println("No child");
                }else
                {
                    System.out.print("Please select a child: ");
                    String i = input.nextLine();
                    try 
                    {
                        tree.selectChild(i);
                    }catch (Exception e) 
                    {
                        System.out.println("Error. No child "+i+" for the current node.");
                    }
                }
            }
            else if(inp == 'O')
            {
                System.out.print("Please enter a new option: ");
                String opt = input.nextLine();
                tree.setCursorOption(opt);
                System.out.println();
                System.out.println("Option set.");
            }
            else if(inp == 'M')
            {
                System.out.print("Please enter a new message: ");
                String msg = input.nextLine();
                tree.setCursorMessage(msg);
                System.out.println();
                System.out.println("Message set.");
            }
            else if(inp == 'A')
            {
                try 
                {
                    System.out.print("Enter an option: ");
                    String opt = input.nextLine();
                    System.out.print("Enter a message: ");
                    String msg = input.nextLine();
                    tree.addChild(opt, msg);
                    System.out.println();
                    System.out.println("Child added.");
                } catch (IllegalArgumentException e) 
                {
                    System.out.println("The option or message cannot be null or empty");
                }catch(FullTreeException e)
                {
                    System.out.println("Error, The node is already at its max capacity of children");
                }   
            }
            else if(inp == 'D')
            {

                if(tree.getCursorOptions().length == 0)
                {
                    System.out.println("No child");
                }else
                {
                    System.out.println();
                    System.out.print("Please select a child: ");
                    String child = input.nextLine();
                    try
                    {    
                        tree.removeChild(child);
                        System.out.println();
                        System.out.println("Subtree deleted.");
                    }catch(IllegalArgumentException e)
                    {
                        System.out.println("Invalid input, please try again");
                    }
                    catch(NodeNotPresentException e)
                    {
                        System.out.println("Error. No child "+child+" for the current node.");
                    }
                }
            }
            else if(inp == 'R')
            {
                if(tree.getRoot().getNumChild() == 0)
                {
                    System.out.println("you cannot reset to root if the story tree is empty");
                }
                else
                {
                    tree.resetCursor();
                    System.out.println("Cursor moved to root.");
                }
            }
            else if(inp == 'P')
            {
                try 
                {
                    tree.goBackToParent();
                    System.out.println("Cursor back to his parent");
                } catch (Exception e) 
                {
                    System.out.println(e.getMessage());
                }
            }
            else if(inp == 'Q')
            {
                break;
            }
            else
            {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    /**
     * Provides a user interface allowing a player to play the game represented by tree 
     * This method will allow a user to traverse the tree by continually displaying messages 
     * and allowing the user to select options until a leaf is reached.
     * @param tree
     *      the tree thats going to be played
     */
    public static void playTree(StoryTree tree)
    {
        tree.resetCursor();
        tree.setState(GameState.GAME_NOT_OVER);
        System.out.println(tree.getCursorOption()+"\n");
        while(tree.getGameState() == GameState.GAME_NOT_OVER)
        {   
            while(true)
            {
                System.out.println(tree.cursorPlayToString());
                System.out.print("Please make a choice: ");
                String inp = input.nextLine();
                System.out.println();
                try 
                {
                    if(inp.charAt(0) == 'B')
                    {
                        System.out.println("Cheat Code, going back to parent...");
                        tree.goBackToParent();
                        System.out.println("\n");
                        break;
                    }
                    else if(inp.charAt(0) == 'C')
                    {
                        double prob = Math.round(tree.winProbability()*10000.0)/100.0;
                        System.out.println("Probability of a win at this point: "+prob+"%\n\n");
                        break;
                    }

                    tree.selectChild(inp);
                    if(!(tree.getGameState() == GameState.GAME_NOT_OVER))
                    {
                        System.out.println(tree.getCursorMessage());
                    }
                    break;   
                } catch (Exception e) 
                {
                    System.out.println("Invalid input, please try again\n\n");
                }
            }
        }
        System.out.println("\nThanks for playing.\n");
    }


    /**
     * An input scanner to avoid bugs
     * @return
     *      the input
     */
    public static int nextInt()
    {
        int answer = input.nextInt();
        input.nextLine();
        return answer;
    }

    /**
     * Requests the user to enter a file name and builds a tree from the indicated file (if it exists, otherwise uses an empty tree) and 
     * asks the user whether they would like to edit this tree (E), play a game based on this tree (P), or exit the program (Q).
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("Hello and Welcome to Zork!\n");  
        System.out.print("Please enter the file name: ");  
        String filename = input.nextLine();
        StoryTree tree = new StoryTree();
        try
        {
            tree = StoryTree.readTree(filename);
            tree.resetCursor();
            System.out.println();
            System.out.println("Loading game from file...\n");
            System.out.println("File loaded!\n");
        }catch(IllegalArgumentException | IOException e)
        {
            System.out.println("Error. The current node has no children.");
        }catch(DataFormatException | FullTreeException e)
        {
            System.out.println("Error loading file, please try again\n");
        }

        while(true)
            {
            System.out.print("Would you like to edit (E), play (P) or quit (Q)? ");
            char inp = input.nextLine().toUpperCase().charAt(0);
            System.out.println();

            if(inp == 'P')
            {
                if(tree.getRoot().getNumChild() == 0)
                {
                    System.out.println("You cannot play with an empty tree\n");
                    continue;
                }

                playTree(tree);
            }
            else if(inp == 'E')
            {
                editTree(tree);
            }
            else if(inp == 'Q')
            {
                System.out.println("Game being saved to "+filename+"...");
                try 
                {
                    File file = new File(filename);
                    if(!file.exists())
                    {
                        file.createNewFile();
                    }
                    
                    StoryTree.saveTree(filename, tree);
                    System.out.println("\nSave successful!\n");  
                } catch (FileNotFoundException e) 
                {
                    System.out.println("File not found.");
                    System.out.println("\nSave unsuccessful!\n");
                }
                break;
            }
        }
        System.out.println("Program terminating normally.");
    }    
}
