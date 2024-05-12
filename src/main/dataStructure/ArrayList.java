package main.dataStructure;

import java.util.Arrays;
import java.util.Objects;

/**
 * The ArrayList class represents a resizable array implementation of a list.
 * It provides methods to add, remove, and access elements in the list.
 *
 * @param <T> the type of elements in the list
 */

public class ArrayList<T> {
    private T[] arr;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        arr = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        arr = (T[]) new Object[capacity];
        size = 0;
    }

    public ArrayList(T[] arr) {
        this.arr = arr;
        size = arr.length;
    }

    public boolean add(T element) {
        if (size >= arr.length) {
            try {
                arr = Arrays.copyOf(arr, arr.length * 2);
            } catch (OutOfMemoryError e) {
                return false;
            }
        }
        arr[size++] = element;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in the ArrayList.
     *
     * @param index   the index at which the element is to be inserted
     * @param element the element to be inserted
     * @return true if the element is successfully inserted, false otherwise
     */
    public boolean add(int index, T element) {
        if (index > size || index < 0) {
            return false;
        }

        if (index == size)
            return add(element);

        arr[index] = element;
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from the ArrayList.
     *
     * @param element the element to be removed
     * @return true if the element is successfully removed, false otherwise
     */
    public boolean remove(T element) {
        if (size == 0)
            return false;

        boolean removed = false;
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(element)) {
                removed = true;
            }
            if (removed && i < size - 1)
                arr[i] = arr[i + 1]; // Shift elements to the left
        }
        if (removed)
            size--;
        return removed;
    }

    /**
     * Removes and returns the element at the specified position in the ArrayList.
     *
     * @param index the index of the element to be removed
     * @return the removed element, or null if the index is out of range or the
     *         element is not found
     */
    public T pop(int index) {
        if (index > size || index < 0) {
            return null;
        }
        if (!remove(arr[index]))
            return null;
        return arr[index];
    }

    /**
     * Checks if the ArrayList contains the specified element.
     *
     * @param element the element to be checked
     * @return true if the element is found in the ArrayList, false otherwise
     */
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the
     * ArrayList.
     *
     * @param element the element to search for
     * @return the index of the element, or -1 if the element is not found
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the ArrayList contains all the elements in the specified array.
     *
     * @param elements the array of elements to be checked
     * @return true if all elements are found in the ArrayList, false otherwise
     */
    public boolean contains(T[] elements) {
        for (T element : elements) {
            if (!contains(element)) {
                return false; // Exit the method early if any element is not found
            }
        }
        return true; // All elements are found in the list
    }

    /**
     * Checks if the ArrayList contains all the elements in the specified ArrayList.
     *
     * @param elements the ArrayList of elements to be checked
     * @return true if all elements are found in the ArrayList, false otherwise
     */
    public boolean contains(ArrayList<T> elements) {
        if (isEmpty() || elements.isEmpty())
            return false;
        for (int i = 0; i < elements.size(); i++) {
            T element = elements.get(i);
            if (!contains(element)) {
                return false; // Exit the method early if any element is not found
            }
        }
        return true; // All elements are found in the list
    }

    /**
     * Returns the number of elements in the ArrayList.
     *
     * @return the number of elements in the ArrayList
     */
    public int size() {
        return size;
    }

    /**
     * Returns an array containing all the elements in the ArrayList.
     *
     * @return an array containing all the elements in the ArrayList
     */
    public T[] toArray() {
        return arr;
    }

    /**
     * Returns the element at the specified position in the ArrayList.
     *
     * @param index the index of the element to be returned
     * @return the element at the specified position
     */
    public T get(int index) {
        return arr[index];
    }

    /**
     * Replaces the element at the specified position in the ArrayList with the
     * specified element.
     *
     * @param index   the index of the element to be replaced
     * @param element the element to be stored at the specified position
     */
    public void set(int index, T element) {
        arr[index] = element;
    }

    /**
     * Removes all elements from the ArrayList.
     */
    public void clear() {
        if (size == 0)
            return;
        for (int i = 0; i < size; i++) {
            arr[i] = null;
        }
        size = 0;
    }

    /**
     * Fills the ArrayList with the specified element.
     *
     * @param element the element to be filled in the ArrayList
     */
    public void fill(T element) {
        for (int i = 0; i < size; i++) {
            arr[i] = element;
        }
    }

    /**
     * Displays the elements in the ArrayList.
     */
    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + 1 + ". " + arr[i]);
        }
    }

    /**
     * Checks if the ArrayList is empty.
     *
     * @return true if the ArrayList is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Compares this ArrayList to the specified object.
     *
     * @param o the object to compare to
     * @return true if the ArrayLists are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ArrayList<?> arrayList = (ArrayList<?>) o;
        return size == arrayList.size && Objects.deepEquals(arr, arrayList.arr);
    }

    /**
     * Returns the hash code value for the ArrayList.
     *
     * @return the hash code value for the ArrayList
     */
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(arr), size);
    }
}
