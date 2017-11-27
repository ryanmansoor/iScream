package iScream;

import iScream.FlavoursLinkedList.Node;
    public class FlavoursLinkedList
   {
      private Node head;
      private int listCount;
   
       public FlavoursLinkedList()
      {
         head = new Node(null);
         listCount = 0;
      }
   
       public void add(Flavour data) //Function does not return anything. Calls an instance 'data' of type Flavour. 
//The code below creates a new node containing the data in the Flavour (Eg. Chocolate). 
      {
         Node temp = new Node(data);
         Node current = head;
         while(current.getNext() != null){
            current = current.getNext();
         }
//Code in the curly brackets above simply searches for the last node in the list.
         current.setNext(temp);//Self explanatory
         listCount++;//As a new flavour has been added, the list increases in size        
   }
   
       public void add(Flavour data, int index)//This function is the same as above, however
       //instead of adding to the end of the list, the flavour is added into the index that the function calls.
      {
         Node temp = new Node(data);
         Node current = head;
         for(int i = 1; i < index && current.getNext() != null; i++)
         {
            current = current.getNext();
         }
         temp.setNext(current.getNext());
         current.setNext(temp);
         listCount++;        }
   
       public Flavour get(int index)//returns the flavour at the index that is called.
      {
         if(index <= 0)
            return null;
      
         Node current = head.getNext();
         for(int i = 1; i < index; i++)
         {
            if(current.getNext() == null)
               return null;
         
            current = current.getNext();
         }
         return current.getData();
      }
   
       public boolean remove(int index)
       //purpose of function is to remove an entry of Flavour at the index called. 
       //However it also returns a true or false based on whether the function succeeded to remove the entry.
      {
         if(index < 1 || index > size())
            return false;
      
         Node current = head;
         for(int i = 1; i < index; i++)
         {
            if(current.getNext() == null)
               return false;
         
            current = current.getNext();
         }
         current.setNext(current.getNext().getNext());
         //The line above is where linked lists shine. Removing a node just requires the previous node to point to the next next node.
         listCount--;                 
         return true;
      }
   
       public int size()//returns an integer with the number of entries in the list.
      {
         return listCount;
      }
   
       public String toString()//This function outputs all the data in the list, using a loop.
      {
         Node current = head.getNext();
         String output = "";
         while(current != null)
         {
            output += "[" + current.getData().toString() + "]";
            current = current.getNext();
         }
         return output;
      }
   
       public class Node //definition of what a Node is
      {
         Node next;
//initialize a node called next, which will contain the address of the next node 
         Flavour data;
//Flavour is a class (in this case, like a datatype) containing multiple attributes. data is an instance of Flavour. What this data type contains is in Flavour.java
          public Node(Flavour _data)
//Constructor, creates a node, populated with the data that the function calls. 
         {
            next = null;//indicates there is nothing after this entry.
            data = _data;
         }
      
          public Node(Flavour _data, Node _next)
//Same as above, however creates an entry that points to the next node that the function calls.
//i.e adds a new node entry before the node that is called.
         {
            next = _next;
            data = _data;
         }
      
          public Flavour getData()//e.g. Chocolate.getData() would return the data in Chocolate
         {
            return data;
         }
      
          public void setData(Flavour _data)//e.g Chocolate.setData(temp) where temp has been populated by user entries
         {
            data = _data;
         }
      
          public Node getNext()//self explanatory
         {
            return next;
         }
      
          public void setNext(Node _next)//Self explanatory
         {
            next = _next;
         }
      }
   }
