package exercise1;

public class TreeMapRunnable {
	/**exercise 1 treeSearch function can be found in TreeMap.java towards the top of the file**/
	public static void main(String[] args) {
		TreeMap<Integer, Character> map = new TreeMap<>();
		map.put(5, 'A');
		map.put(1, 'B');
		map.put(7, 'C');
		map.put(8, 'D');
		map.put(12, 'E');
		map.put(42, 'F');
		map.put(10, 'G');

		System.out.println("removing: " + map.remove(7)); // remove method uses treeSearch

		for(Entry<Integer, Character> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}
}
