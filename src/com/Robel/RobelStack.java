package com.Robel;

import java.util.ArrayList;

//Custom Stack Class using ArrayLists
public class RobelStack<E>{

    private ArrayList<E> theStack;
    private int size;


    //Constructing an Empty Stack
    public RobelStack(){
        theStack = new ArrayList<>();
    }

    //Push an object onto stack
    public void push(E data){
        theStack.add(data);
    }

    //Returns object at the top of stack
    public E peek() throws RobelEmptyStackException {
        if(theStack.isEmpty())
            throw new RobelEmptyStackException();

        return theStack.get(theStack.size()-1);
    }

    //Removes and returns top object from stack
    public E pop() throws RobelEmptyStackException {
        if(theStack.isEmpty())
            throw new RobelEmptyStackException();

        return theStack.remove(theStack.size()-1);
    }

    //Returns true if Stack is Empty
    public boolean isEmpty() {
        return theStack.isEmpty();
    }
}
