package cs146F19.Garcia.project4;

public class RBTree<Key extends Comparable<Key>> {
	private RBTree.Node<String> root;									// Root Node
	static final int RED = 0;											// If int stored in color is 0, it  means RED
	static final int BLACK = 1;											// If int stored in color is 1, it means BLACK
	int size = 0;
	
	public static class Node<Key extends Comparable<Key>> {
		Key key;														// Value stored in the node
		Node<String> parent;											// Parent of the node
		Node<String> leftChild;											// Left child of the node
		Node<String> rightChild;										// Right child of the node
		int color;														// Node color (0 = RED; 1 = BLACK)
		
		public Node(Key data) {
			this.key = data;											// Initialize node key to data passed in constructor
			leftChild = null;											// Initialize left child to be null
			rightChild = null;											// Initialize right child to be null
		}
		
		public int compareTo(Node<Key> n) {								// This < That results in value <0
			return key.compareTo(n.key);								// This > That results in value >0
		}
		
		public boolean isLeaf() {
			if (leftChild == null && rightChild == null)				// Node is a leaf if both children are null
				return true;
			return false;
		}
	}
	
	public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n the visited node
		 */
		void visit(Node<Key> n);
	}

	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}
	
	// Default Constructor with no parameters
	public RBTree () {
		
	}
	
	// Constructor with a parameter to accept a value for the root
	public RBTree (String root) {
		insert(root);
	}
	
	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}
	
	private static void preOrderVisit(RBTree.Node<String> n, Visitor<String> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
	
	/**
	 * Print tree using helper method to go in preorder
	 */
	public void printTree() {
		if (root != null) {
			RBTree.Node<String> currentNode = root;
			printTree(currentNode);
		}
	}
	
	/**
	 * Recursive call for print tree (Visit, Go left, Go right)
	 * @param node
	 * 		Start position
	 */
	private void printTree(RBTree.Node<String> node) {
		System.out.print(node.key);
		if (node.isLeaf()) {
			return;
		}
		if (node.leftChild != null)
			printTree(node.leftChild);
		if (node.rightChild != null)
			printTree(node.rightChild);
	}
	
	/**
	 * Insert new data by calling addNode method
	 * @param data
	 * 		Data to be added
	 */
	public void insert(String data) {
		addNode(data);
		size++;
	}
	
	/**
	 * Place a new node in the RB tree with argument data and color it red
	 * @param data
	 * 		Data for node to be passed in
	 */
	private void addNode(String data) {
		Node<String> toAdd = new Node<String>(data);			// Create a node with the value being added
		if (root == null) {
			root = toAdd;										// If the RB Tree is empty (null root), set the root to the new node
			root.parent = new Node<String>("");					// Assign root's parent to a new node with data ""
		} else {												// If the RB Tree is not empty
			Node<String> position = root;						// To start at the root
			while (position != null) {							// While position is not null,
				if (toAdd.compareTo(position) < 0) {
					if (position.leftChild == null) {			// If value is less than position node and position node's left child is null, insert node here
						toAdd.parent = position;				// Assign new node's parent to current position node
						toAdd.color = RED;						// Assign new node color to RED
						position.leftChild = toAdd;				// Set current position left child to new node
						break;
					}
					position = position.leftChild;				// Set position to leftChild if value being added is less than the current position
				} else if (toAdd.compareTo(position) > 0) {
					if (position.rightChild == null) {			// If value is greater than position node and position node's right child is null, insert node here
						toAdd.parent = position;				// Assign new node's parent to current position node
						toAdd.color = RED;						// Assign new node color to RED
						position.rightChild = toAdd;			// Set current position right child to new node
						break;
					}
					position = position.rightChild;				// Set position to rightChild if value being added is greater than the current position
				}
			}
		}
		fixTree(toAdd);											// Run fixTree method from new node to verify RB properties
	}
	
	/**
	 * Search tree for value passed in parameter and return the Node if found; null if not found
	 * @param k
	 * 		Value being searched for
	 * @return
	 * 		Node of value if found
	 * 		<br>
	 * 		Null otherwise
	 */
	public RBTree.Node<String> lookup(String k) {
		if (root == null)
			return null;
		Node<String> position = root;							// Start position at the root
		while (position != null && position.key != k) {			// While the position is not null and the current position's key is not what we are looking for,
			if (position.key.equals(k))
				return position;
			if (k.compareTo(position.key) < 0)
				position = position.leftChild;					// Set position to leftChild if value we are looking for is less than the current position
			else if (k.compareTo(position.key) > 0)
				position = position.rightChild;					// Set position to rightChild if value we are looking for is greater than the current position
		}
		return null;
	}
	
	/**
	 * Rotate tree left around passed in node
	 * @param n
	 * 		Node to be rotated around
	 */
	private void rotateLeft(RBTree.Node<String> n) {
		Node<String> x = n;									// Assign x to passed in node
		Node<String> y = n.rightChild;						// Assign y to passed in node's right child (will be the new parent)
		Node<String> b = null;								// Initialize b as null
		if (y.leftChild != null)
			b = y.leftChild;								// If y has a left child, set b to that node
		if (n.equals(root)) {								// Case for if passed in node is root
			x.rightChild = b;								// Assign x's right child to be y's left child
			if (b != null)
				b.parent = x;								// If b is not null, b's parent is x
			y.leftChild = x;								// Set y's left child to x
			y.parent = x.parent;							// Set y's parent to x's parent
			x.parent = y;									// Change x's parent to y
			root = y;										// Set root to be y
		} else {
			x.rightChild = b;								// Case for if passed in node is not root is the same as previous case but assigns parent of subtree child and without assigning the root at the end
			if (b != null) {
				b.parent = x;
			}
			y.leftChild = x;
			if (isLeftChild(x.parent, x)) {					// If root of subtree is a left child
				x.parent.leftChild = y;						// Assign y to be the left child of the parent of the subtree
			} else {
				x.parent.rightChild = y;					// Otherwise assign y to be the right child of the parent of the subtree
			}
			y.parent = x.parent;
			x.parent = y;
		}
	}
	
	/**
	 * Rotate tree right around passed in node
	 * @param n
	 * 		Node to be rotated around
	 */
	private void rotateRight(RBTree.Node<String> n) {
		Node<String> x = n.leftChild;						// Assign x to passed in node's left child (will be the new parent)
		Node<String> y = n;									// Assign y to passed in node
		Node<String> b = null;								// Initialize b as null
		if (x.rightChild != null)
			b = x.rightChild;								// If x has a right child, set b to that node
		if (n.equals(root)) {								// Case for if passed in node is root
			y.leftChild = b;								// Assign y's left child to be x's right child
			if (b != null)
				b.parent = y;								// If b is not null, b's parent is y
			x.rightChild = y;								// Set x's right child to y
			x.parent = y.parent;							// Set x's parent to y's parent
			y.parent = x;									// Change y's parent to x
			root = x;										// Set root to be x
		} else {
			y.leftChild = b;								// Case for if passed in node is not root is the same as previous case but assigns parent of subtree child and without assigning the root at the end
			if (b != null)
				b.parent = y;
			if (isLeftChild(y.parent, y)) {					// If root of subtree is a left child
				y.parent.leftChild = x;						// Assign x to be the left child of the parent of the subtree
			} else {
				y.parent.rightChild = x;					// Otherwise assign x to be the right child of the parent of the subtree
			}
			x.rightChild = y;
			x.parent = y.parent;
			y.parent = x;
		}
	}
	
	/**
	 * Recursively traverse the tree to make it a Red Black tree
	 * @param current
	 * 		Starting Node to be fixed
	 */
	private void fixTree(RBTree.Node<String> current) {
		//  Case 1: If current is the root node. Make it black and quit.
		if (current.equals(root)) {
			current.color = BLACK;
			return;
			
		// Case 2: If parent is black, Quit. The tree is a Red Black Tree.
		} else if (current.parent.color == BLACK) {
			return;
			
		// Case 3: The current node is red and the parent node is red. The tree is unbalanced and has 2 ways to fix
		} else if (current.color == RED && current.parent.color == RED) {
			// Case 3a: If the aunt node is empty or black, then there are four sub cases to process.
			if (getAunt(current) == null || getAunt(current).color == BLACK || isEmpty(getAunt(current))) {
				// Case 3a 1): grandparent–parent(is left child)—current (is right child) case
				// Solution: Rotate the parent left and then continue recursively fixing the tree starting with the original parent.
				if (isLeftChild(getGrandparent(current), current.parent) && !isLeftChild(current.parent, current)) {
					Node<String> originalParent = current.parent;
					rotateLeft(current.parent);
					fixTree(originalParent);
				// Case 3a 2): grandparent–parent (is right child)—current (is left child) case
				// Solution: Rotate the parent right and then continue recursively fixing the tree starting with the original parent.
				} else if (!isLeftChild(getGrandparent(current), current.parent) && isLeftChild(current.parent, current)) {
					Node<String> originalParent = current.parent;
					rotateRight(current.parent);
					fixTree(originalParent);
				// Case 3a 3): grandparent–parent (is left child)—current (is left child) case
				// Solution: Make the parent black, make the grandparent red, rotate the grandparent to the right and quit. Tree is balanced.
				} else if (isLeftChild(getGrandparent(current), current.parent) && isLeftChild(current.parent, current)) {
					current.parent.color = BLACK;
					getGrandparent(current).color = RED;
					rotateRight(getGrandparent(current));
					return;
				// Case 3a 4): grandparent–parent (is right child)—current (is right child) case
				// Solution: Make the parent black, make the grandparent red, rotate the grandparent to the left and quit. Tree is balanced. 
				} else if (!isLeftChild(getGrandparent(current), current.parent) && !isLeftChild(current.parent, current)) {
					current.parent.color = BLACK;
					getGrandparent(current).color = RED;
					rotateLeft(getGrandparent(current));
					return;
				}
			// Case 3b: If the aunt is red, then make the parent and aunt black, make the grandparent red, and continue to recursively fix the tree starting with the grandparent
			} else if (getAunt(current).color == RED) {
				current.parent.color = BLACK;
				getAunt(current).color = BLACK;
				getGrandparent(current).color = RED;
				fixTree(getGrandparent(current));
			}
		}
	}
	
	/**
	 * Return node's aunt
	 * 
	 * @return Node's Aunt (parent's sibling)
	 */
	private RBTree.Node<String> getAunt(RBTree.Node<String> n) {
		if (n.parent != null)
			return getSibling(n.parent);							// Return node's aunt (parent's sibling)
		return null;
	}
	
	/**
	 * Return node's grandparent
	 * 
	 * @return Node's GrandParent (parent's parent)
	 */
	private RBTree.Node<String> getGrandparent(RBTree.Node<String> n) {
		if (n.parent != null && n.parent.parent != null)
			return n.parent.parent;									// Return node's GrandParent (parent's parent)
		return null;
	}
	
	/**
	 * Return node's sibling
	 * 
	 * @return	Right Child if current node is a Left Child <br> 
	 * 			Left Child if current node is a Right Child
	 */
	private RBTree.Node<String> getSibling(RBTree.Node<String> n) {
		if (n.parent != null && isLeftChild(n.parent, n) && n.parent.rightChild != null) {
			return n.parent.rightChild;								// Return right child if current node is a left child
		}
		else if (n.parent != null && !isLeftChild(n.parent, n) && n.parent.leftChild != null)
			return n.parent.leftChild;								// Return left child if current node is a right child
		return null;
	}
	
	/**
	 * Check if Node n is empty
	 * @param n
	 * 		Node to be checked
	 * @return
	 * 		TRUE if key in node is null
	 * 		<br>
	 * 		FALSE otherwise
	 */
	private boolean isEmpty(RBTree.Node<String> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if Node "child" is a left child to Node "parent"
	 * @param parent
	 * 		Parent node to check
	 * @param child
	 * 		Child node to check
	 * @return
	 * 		TRUE if child is less than parent
	 * 		<br>
	 * 		FALSE otherwise
	 */
	private boolean isLeftChild(RBTree.Node<String> parent, RBTree.Node<String> child) {
		if (child.compareTo(parent) < 0) {
			return true;								// Returns TRUE if child is less than parent
		}
		return false;
	}
}