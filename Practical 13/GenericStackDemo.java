import java.util.Arrays;
import java.util.EmptyStackException;

class ArrayStack<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 4;

    public ArrayStack() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void push(T item) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[size++] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = (T) elements[--size];
        elements[size] = null;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[bottom -> ");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(" <- top]");
        return sb.toString();
    }
}

public class GenericStackDemo {
    public static void main(String[] args) {
        System.out.println("=== 1. ARRAYSTACK REUSED FOR TYPE 1: STRING ===");
        ArrayStack<String> historyStack = new ArrayStack<>();
        System.out.println("Initial stack isEmpty: " + historyStack.isEmpty() + ", size: " + historyStack.size());

        historyStack.push("home.page");
        historyStack.push("store.page");
        historyStack.push("product.page");
        historyStack.push("checkout.page");
        historyStack.push("confirmation.page");

        System.out.println("Stack after 5 pushes (auto-expanded): " + historyStack);
        System.out.println("Top element (peek): " + historyStack.peek());
        System.out.println("Stack size: " + historyStack.size());

        System.out.println("\nPopping elements:");
        while (!historyStack.isEmpty()) {
            System.out.println("Popped: " + historyStack.pop() + " | Remaining size: " + historyStack.size());
        }

        System.out.println("\nHandling empty case for String stack:");
        try {
            System.out.println("Attempting pop on empty stack...");
            historyStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Handled gracefully: EmptyStackException caught - cannot pop from empty stack.");
        }

        System.out.println("\n=== 2. ARRAYSTACK REUSED FOR TYPE 2: INTEGER ===");
        ArrayStack<Integer> numberStack = new ArrayStack<>();

        numberStack.push(10);
        numberStack.push(20);
        numberStack.push(30);
        numberStack.push(40);

        System.out.println("Integer stack contents: " + numberStack);
        System.out.println("Top value: " + numberStack.peek());
        System.out.println("Popped: " + numberStack.pop());
        System.out.println("Popped: " + numberStack.pop());
        System.out.println("Stack after 2 pops: " + numberStack);

        System.out.println("\nHandling empty case for Integer stack:");
        numberStack.pop();
        numberStack.pop();
        System.out.println("Current stack size: " + numberStack.size() + ", isEmpty: " + numberStack.isEmpty());
        try {
            System.out.println("Attempting peek on empty stack...");
            numberStack.peek();
        } catch (EmptyStackException e) {
            System.out.println("Handled gracefully: EmptyStackException caught - cannot peek into empty stack.");
        }
    }
}
