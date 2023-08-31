/**
 * An exception thrown in the <CODE>StoryTree</CODE> class to indicate that 
 * there is no node present on which they have asked for. 
 * 
 * @author 
 * 		Uday Turakhia, SBU ID #: 115102637
 * <dt><b>Assignment:</b><dd>
 *    Homework #5 for CSE 214, Spring 2023
 * 		Recitation #: R03
 * <dt><b>Date:</b><dd>
 *    April 3th, 2023
 */
public class NodeNotPresentException extends Exception
{
    /**
     * Default constructor for an NodeNotPresentException that
     * passes a default string to the Exception class constructor.
     *
     * @custom.Postcondition:
     *    The object is created and contains the default message.
     */
    NodeNotPresentException()
    {
        //Default msg
        super("Error, no node present");
    }

    /**
     * Second constructor for the NodeNotPresentException that
     * passes a provided string to the Exception class constructor.
     *
     * @param errorMessage
     *    the message that the object is to contain
     *    
     * @custom.Postcondition:
     *    The object is created and contains the provided message.
     */
    NodeNotPresentException(String message)
    {
        //Passed message
        super(message);
    }
}
