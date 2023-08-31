/**
     * a fully-documented class named StoryTreeNode which represents a segment of the story. This class contains
     * a StoryTreeNode with three String member variables: position, which indicates the position of the node in the tree; 
     * option, which represents the text that should be displayed to the user to describe what 
     * choice this node represents (this will be read from the parent node), and the message, 
     * which is the message displayed when the user chooses this option (i.e: the consequence of taking the option).
 * @author 
 * 		Uday Turakhia, SBU ID #: 115102637
 * <dt><b>Assignment:</b><dd>
 *    Homework #5 for CSE 214, Spring 2023
 * 		Recitation #: R03
 * <dt><b>Date:</b><dd>
 *    April 5th, 2023
 */
public class StoryTreeNode 
{
    public final int MAX_CHILDREN = 10;//Maximum amount of child a node can have

    public static final String WIN_MESSAGE = "YOU WIN"; //Winning msg that should be in winning node
    public static final String LOSE_MESSAGE = "YOU LOSE";//Losing msg that should be in losing node

    private String position;//Position of the node
    private String option;//option of the node
    private String message;//message of the node
    private int numChild;//number of child the node has currently

    private StoryTreeNode[] childRef;//An array of reference to the children
    private StoryTreeNode parentRef;//A reference to the parent

    /**
     * Default constructor
     * @param pos
     *      position of the node
     * @param opt
     *      option of the node
     * @param mes
     *      message of the node
     */
    public StoryTreeNode(String pos, String opt, String mes)
    {
        position = pos;
        option = opt;
        message = mes;
        numChild = 0;

        childRef =  new StoryTreeNode[MAX_CHILDREN];
    }
    
    /**
     * Checks if the node is a leaf or not
     * @return
     *      true if the node has no child, false otherwise
     */
    public boolean isLeaf()
    {
        for(int i = 0; i<childRef.length;i++)
        {
            if(childRef[i] != null)
                return false;
        }
        return true;
    }

    /**
     * Checks if the node is a winning node
     * @return
     *      true if its a leaf and contains Win Message, false otherwise
     */
    public boolean isWinningNode()
    {
        return message.contains(WIN_MESSAGE) && isLeaf();
    }

    /**
     * Checks if the node is a lossing node
     * @return
     *      true if its a leaf and contains Lose Message, false otherwise
     */
    public boolean isLossingNode()
    {
        return message.contains(LOSE_MESSAGE) && isLeaf();
    }

    /**
     * Gets the ith child node of this node 
     * @param i
     *      which child to get
     * @return
     *      the child in i
     */
    public StoryTreeNode getChildRef(int i) 
    {
        return childRef[i];
    }

    /**
     * Set child node
     * @param n
     *      sets a child reference to node
     * @param i
     *      which position should the child be on
     */
    public void SetChildRef(StoryTreeNode n, int i)
    {
        childRef[i] = n;
    }

    /**
     * Get the parents reference
     * @return
     *      parent node
     */
    public StoryTreeNode getParentRef()
    {
        return parentRef;
    }

    /**
     * Setter of parent node
     * @param n
     *      the new parent node
     */
    public void SetParentRef(StoryTreeNode n)
    {
        parentRef = n;
    }

    /**
     * Getter of message
     * @return
     *      message
     */
    public String getMessage() 
    {
        return message;
    }

    /**
     * Getter of position
     * @return
     *      position string
     */
    public String getPosition() 
    {
        return position;
    }

    /**
     * Getter of option
     * @return
     *      option string
     */
    public String getOption() 
    {
        return option;
    }

    /**
     * Setter of message node
     * @param message
     *      new message
     */
    public void setMessage(String message) 
    {
        this.message = message;
    }

    /**
     * Setter of Option node
     * @param option
     *      new option
     */
    public void setOption(String option) 
    {
        this.option = option;
    }

    /**
     * Setter of position
     * @param position
     *      new position
     */
    public void setPosition(String position) 
    {
        this.position = position;
    }

    /**
     * Counts the number of leafs that can be reached through this node
     * @return
     *      an int of # of leaves
     */
    public int countLeaf()
    {
        int count = 0;
        
        if(this.isLeaf())
        {
            return 1;
        }

        for(int i = 0; i<childRef.length;i++)
        {
            if(childRef[i] != null)
            {
                count += childRef[i].countLeaf();
            }
        }

        return count;
    }

    /**
     * Counts the number of win leafs that can be reached by this node 
     * @return
     *      an integer of number of winning leaf that could be reached by this node
     */
    public int countWinLeaf()
    {
        int count = 0;
        
        if(isWinningNode())
        {
            return 1;
        }

        for(int i = 0; i<childRef.length;i++)
        {
            if(childRef[i] != null)
            {
                count += childRef[i].countWinLeaf();
            }
        }

        return count;
    }
    
    /**
     * The win probabilty from this node
     * @return
     *      the win probability in a double
     */
    public double winProbability()
    {
        return ((double) countWinLeaf())/((double) countLeaf());
    }

    /**
     * A to string which will be used in edit
     * @return
     *      String which gives position, option and message in a tabular format
     */
    public String editToString()
    {
        String answer = "";

        answer += "Position: "+position;
        answer += "\nOption: "+option;
        answer += "\nMessage: "+message+"\n";

        return answer;
    }

    /**
     * A toString which will be used when the game is played
     * @return
     *      the message and all children's option
     */
    public String playToString()
    {
        String answer = "";

        answer += message + "\n";
        int counter = 1;
        for(int i = 0; i<childRef.length;i++)
        {
            if(childRef[i] != null)
            {
                answer += counter++ + ") "+childRef[i].getOption()+"\n";
            }
        }

        return answer;
    }

    /**
     * Getter of numChild
     * @return
     *      number of child the node has
     */
    public int getNumChild() 
    {
        return numChild;
    }

    /**
     * Adds a new child to the node
     * @param opt
     *      option of the new child
     * @param mes
     *      message of the new child
     * @throws FullTreeException
     *      if the tree already has maximum amount of children
     */
    public void addChild(String opt, String mes)  throws IllegalArgumentException, FullTreeException
    {
        if(numChild == MAX_CHILDREN)
        {
            throw new FullTreeException("There cannot be more than 10 childrens");
        }

        String pos;
        if(!position.equals("root"))
            pos = position + "-"+(numChild+1);
        else
            pos = Integer.toString(numChild+1);

        childRef[numChild] =  new StoryTreeNode(pos, opt, mes);
        childRef[numChild++].SetParentRef(this);
    }

    /**
     * Removes the child from the num
     * @param num
     *      the index where the child is
     * @return
     *      the child which was removed
     */
    public StoryTreeNode removeChild(int num)
    {
        for(int i = num; i<numChild-1;i++)
        {
            childRef[i] = childRef[i+1];
            String pos = childRef[i].getPosition();
            pos = pos.substring(0,pos.length()-1);
            pos += i;
            childRef[i].setPosition(pos);
        }

        StoryTreeNode pop = childRef[numChild-1];
        childRef[--numChild] = null;
        fixPosition();
        return pop;
    }

    /**
     * A To String which is used to save on a file
     * @return
     */
    public String printToString()
    {
        String answer = "";

        answer += position+" | ";
        answer += option + " | ";
        answer += message+"\n";

        for(int i = 0; i<MAX_CHILDREN ;i++)
        {
            if(getChildRef(i) != null)
                answer += getChildRef(i).printToString();
        }

        return answer;
    }

    /**
     * A getOptions method which would return every child's option and their position 
     * @return
     */
    public String[][] getOptions()
    {
        String[][] options = new String[numChild][2];

        for(int i = 0; i<numChild;i++)
        {
            options[i][0] = childRef[i].getPosition();
            options[i][1] = childRef[i].getOption();
        }

        return options;
    }

    /**
     * A helper method which fixes all position of node after this
     */
    public void fixPosition()
    {
        for(int i = 0; i<numChild;i++)
        {
            childRef[i].position = position + "-"+(i+1);
            childRef[i].fixPosition();
        }
    }
}