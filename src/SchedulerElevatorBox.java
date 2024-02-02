import java.util.ArrayList;

/**
 * A Class that acts as a universal dropBox, where the Agent and Barista's can either put, or take things from it
 * @author Zarif (modifed)
 * @version 2.0
 */
public class SchedulerElevatorBox extends Thread{

    /**
     * The object stored in this Box.
     */
    private Object contents = null;

    /**
     * State of the box.
     */
    private boolean empty = true;

    /**
     * Stores its argument in the Box if the Box is empty;
     * otherwise, the Box contents are not changed.
     *
     *
     */
    public synchronized void put(ArrayList<Object> dataList) {
        // Wait for the Box to be empty; ie it's full, therefore we cannot produce
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        // This Box is empty, so store obj.
        contents = dataList;
        empty = false; // Mark the box as full.
        notifyAll();
    }

    /**
     * Removes the object stored in this Box, leaving the Box empty.
     * @return the object stored in this Box, if there is one.
     *          If the Box is empty, returns null.
     */

    public synchronized Object get() {
        // Wait for the Box to full (not empty)
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        // Mark the box as empty.
        Object obj = contents;
        empty = true;
        notifyAll();

        return obj;
    }



}
