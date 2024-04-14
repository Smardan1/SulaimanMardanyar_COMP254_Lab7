package exercise1;

public interface SortedMap<K, V> extends Map<K, V> {
	Entry<K, V> firstEntry(); // return entry with smallest value
	Entry<K, V> lastEntry(); //return entry with highest value

	Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException; //return the entry with the smallest value <= the key passed

	Entry<K, V> floorEntry(K key) throws IllegalArgumentException; // return the entry with the highest value <= the key passed

	Entry<K, V> lowerEntry(K key) throws IllegalArgumentException; // return the entry with the highest value < key passed
	Entry<K, V> higherEntry(K key) throws IllegalArgumentException; // return the entry with the highest value > key passed
	Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException;// iterable collection of all the keys
}
