package exercise1;

import java.util.ArrayList;
import java.util.Comparator;

/**This Binary Search Tree Implementation is using a LinkedBinaryTree...**/
public class TreeMap<K, V> extends AbstractSortedMap<K, V> {
	protected LinkedBinaryTree<Entry<K, V>> tree = new LinkedBinaryTree<>();


	/**recursive tree search algorithm, returns the position of the key, or the last position found if the key is not found.**/
//	private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
//		if(isExternal(p)) {
//			return p;
//		}
//		//using a comparator instead of traditional comparison checks
//		int comp = compare(key, p.getElement());
//		if(comp == 0) {
//			return p; //they are equal return the position
//		} else if (comp < 0) {
//			return treeSearch(left(p), key); // the key is less than the key at p so go left
//		} else {
//			return treeSearch(right(p), key); // the key is greater then the ket at p so go right
//		}
//	}

	/**Exercise 1 Question
		Tree Search Algorithm using a loop instead of recursion
	 **/
	private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
		int comp;
		while(!isExternal(p)) {
			comp = compare(key, p.getElement());
			if(comp == 0) { // we found the position, exit loop
				break; //exit loop and return p
			} else if(comp < 0) {// go left
				p = left(p);
			} else { // go right
				p = right(p);
			}
		}
		return p;
	}

	//make an empty map using natural ordering
	public TreeMap() {
		super();
		tree.addRoot(null);
	}

	//empty map using given comparator to order keys
	public TreeMap(Comparator<K> comp) {
		super(comp);
		tree.addRoot(null);
	}

	//return the number of entries in map
	public int size() {
		return (tree.size() -1) /2;
	}

	//used to make an external an internal so we can add nodes.
	private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
		tree.set(p, entry);
		tree.addLeft(p, null);
		tree.addRight(p, null);
	}

	protected Position<Entry<K, V>> root() { return tree.root(); }
	protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) { return tree.parent(p); }
	protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) { return tree.left(p); }
	protected Position<Entry<K, V>> right(Position<Entry<K, V>> p ) { return tree.right(p); }
	protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) { return tree.sibling(p); }
	protected boolean isRoot(Position<Entry<K, V>> p) { return tree.isRoot(p); }
	protected boolean isExternal(Position<Entry<K, V>> p) { return tree.isExternal(p); }
	protected boolean isInternal(Position<Entry<K, V>> p ) { return tree.isInternal(p); }
	protected void set(Position<Entry<K, V>> p, Entry<K, V> e) { tree.set(p, e); }
	protected Entry<K, V> remove(Position<Entry<K, V>> p) { return tree.remove(p); }

	//return the value of the key
	public V get(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
//		rebalanceAccess(p);
		if(isExternal(p)) {
			return null; // key is not in tree return null
		}
		return p.getElement().getValue(); // return the value of p.
	}

	//assign value to the key at position
	public V put(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K, V> entry = new MapEntry<>(key, value);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if(isExternal(p)) {
			expandExternal(p, entry);
//			rebalanceInsert(p);
			return null;
		} else {
			V old = p.getElement().getValue();
			set(p, entry);
//			rebalanceAccess(p);
			return old;
		}
	}

	//remove
	public V remove(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if(isExternal(p)) {
			return null;
		} else {
			V old = p.getElement().getValue();
			if(isInternal(left(p)) && isInternal(right(p))) { // has two children that are internal
				Position<Entry<K, V>> replacement = treeMax(left(p));
				set(p, replacement.getElement());
				p = replacement;
			}
			Position<Entry<K, V>> leaf = (isExternal(left(p))) ? left(p) : right(p);
			Position<Entry<K,V>> sib = sibling(leaf);
			remove(leaf);
			remove(p);
//			rebalanceDelete(sib);
			return old;
		}
	}
	//return the position with the smallest key in the subtree of p
	protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> walk = p;
		// go left until reach a leaf, that is the smallest value in the subtree..
		while(isInternal(p)){
			walk = left(walk);
		}
		return parent(walk);
	}

	//return position with highest key in subtree at p
	protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> walk = p;
		//go right until leaf
		while(isInternal(walk)) {
			walk = right(walk);
		}
		return parent(walk); // we want the parent of the leaf
	}

	//pass a key and find the highest key you can find that is less or = to the key passed
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isInternal(p)) {
			return p.getElement();
		}
		while(!isRoot(p)){
			if(p == right(parent(p))){
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	//return the first entry or just root....
	@Override
	public Entry<K, V> firstEntry() {
		if (isEmpty()) {
			return null;
		}
		return treeMin(root()).getElement();
	}
	//return the highest entry
	public Entry<K, V> lastEntry() {
		if(isEmpty()) {
			return null;
		}
		return treeMax(root()).getElement();
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if(isInternal(p)) {
			return p.getElement();
		}
		while(!isRoot(p)) {
			if(p == left(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	//return next highest key that is less than the key passed
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if(isInternal(p) && isInternal(left(p))) {
			//if both are internal then the next highest key is always one left then one right...
			return treeMax(left(p)).getElement();
		}
		//if position is right, then the next lowest entry is always the parent unless position is root..
		//will return null if is root..
		while(!isRoot(p)) {
			if(p == right(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	//return the next highest entry based on the key passed
	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if(isInternal(p) && isInternal(right(p))) {
			return treeMin(right(p)).getElement();
		}
		while(!isRoot(p)) {
			if(p == left(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}
	//return collection of key-value entries
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
		for(Position<Entry<K, V>> p : tree.inorder()) {
			if(isInternal(p)) {
				buffer.add(p.getElement());
			}
		}
		return buffer;
	}
	//return collection of keys in the range given
	public Iterable<Entry<K, V>> subMap(K from, K to) {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
		if(compare(from, to) < 0) { // make sure from < to
			subMapRecurse(from, to, root(), buffer);
		}
		return buffer;
	}

	public void subMapRecurse(K from, K to, Position<Entry<K, V>> p, ArrayList<Entry<K, V>> buffer) {
		if(isInternal(p)) {
			//if p is less than from we go right
			if(compare(p.getElement(), from) < 0) {
				subMapRecurse(from, to, right(p), buffer);
			}
			//otherwise go into the left side
			else {
				subMapRecurse(from, to, left(p), buffer);

				if(compare(p.getElement(), to) < 0) {
					buffer.add(p.getElement());
					subMapRecurse(from, to, right(p), buffer);
				}
			}
		}
	}

	/** Prints textual representation of tree structure (for debug purpose only). */
	protected void dump() {
		dumpRecurse(root(), 0);
	}

	/** This exists for debugging only */
	private void dumpRecurse(Position<Entry<K,V>> p, int depth) {
		String indent = (depth == 0 ? "" : String.format("%" + (2*depth) + "s", ""));
		if (isExternal(p))
			System.out.println(indent + "leaf");
		else {
			System.out.println(indent + p.getElement());
			dumpRecurse(left(p), depth+1);
			dumpRecurse(right(p), depth+1);
		}
	}
}
