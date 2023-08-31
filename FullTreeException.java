/**
 * An exception thrown in the <CODE>StoryTree</CODE> class to indicate that 
 * the node has maximum number of children already. 
 * 
 * @author 
 * 		Uday Turakhia, SBU ID #: 115102637
 * <dt><b>Assignment:</b><dd>
 *    Homework #5 for CSE 214, Spring 2023
 * 		Recitation #: R03
 * <dt><b>Date:</b><dd>
 *    April 3th, 2023
 */
public class FullTreeException extends Exception
{
    /**
     * Default constructor for an FullTreeExceotion that
     * passes a default string to the Exception class constructor.
     *
     * @custom.Postcondition:
     *    The object is created and contains the default message.
     */    
    FullTreeException()
    {
        //Default Message
        super("Error, the node is full");
    }
    
    /**
     * Second constructor for the FullTreeException that
     * passes a provided string to the Exception class constructor.
     *
     * @param errorMessage
     *    the message that the object is to contain
     *    
     * @custom.Postcondition:
     *    The object is created and contains the provided message.
     */
    FullTreeException(String msg)
    {
        //Passed Message.
        super(msg);
    }
}
