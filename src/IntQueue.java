// Queue är en kö med metoderna Put, Get, IsEmpty och Empty.

class ListNode
{
    int element;
    ListNode next = null;
}

class IntQueue
{
    private ListNode front = null, back = null;

    public void put(int element){ 
		if (isEmpty()) back = front = new ListNode();
		else back = back.next = new ListNode();
		
		back.element = element;
    }

    public int get() { 
		if (isEmpty()) return -1;
		int element = front.element;
		front = front.next;
		return element;
    }

    public boolean isEmpty(){
    	return front == null;
    }

    public void empty(){
    	front = back = null;
    }
}
