import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Class RBTree.
 */
public class RedBlackTree {

	/** The root. */
	private TreeNode root;

	/** The nil. */
	public static TreeNode nil = new TreeNode();

	/**
	 * Instantiates a new RB tree.
	 */
	public RedBlackTree() {
		root = new TreeNode();
		nil.isBlack = true;
		root.parent = nil;
		root.left = nil;
		root.right = nil;
		root.ID = Integer.MIN_VALUE;

	}

	/**
	 * Insert : creates a given node with value as key and count and inserts it
	 * into red black tree.
	 *
	 * @param value
	 *            the value
	 * @param count
	 *            the count
	 */
	public void insert(int value, int count) {
		TreeNode node = root;
		TreeNode parent = nil;
		boolean isleft = false;
		while (!node.equals(nil)) {
			parent = node;
			if (node.ID >= value) {
				node = node.left;
				isleft = true;
			} else {
				if (node.ID == Integer.MIN_VALUE)
					parent = nil;
				node = node.right;
				isleft = false;
			}
		}
		node = new TreeNode();
		node.ID = value;
		node.parent = parent;
		node.left = nil;
		node.right = nil;
		node.isBlack = false;
		node.count = count;
		if (!parent.equals(nil)) {
			if (isleft)
				parent.left = node;
			else
				parent.right = node;

		} else {
			root = node;
		}
		insertInternal(node);

	}

	/**
	 * Insert fixup.
	 *
	 * @param node
	 *            the node
	 */
	private void insertInternal(TreeNode node) {
		// System.out.println(node.equals(root));
		while (!node.parent.isBlack) {
			TreeNode parent = node.parent;
			if (parent.equals(parent.parent.left)) {
				TreeNode pright = parent.parent.right;
				if (!pright.isBlack) {
					parent.isBlack = true;
					pright.isBlack = true;
					parent.parent.isBlack = false;
					node = parent.parent;
				} else {
					if (node.equals(parent.right)) {
						node = node.parent;
						leftRotate(node);
					}
					node.parent.isBlack = true;
					node.parent.parent.isBlack = false;
					rightRotate(node.parent.parent);
				}
			} else {
				TreeNode pleft = parent.parent.left;
				if (!pleft.isBlack) {
					parent.isBlack = true;
					pleft.isBlack = true;
					parent.parent.isBlack = false;
					node = parent.parent;
				} else {
					if (node.equals(parent.left)) {
						node = node.parent;
						rightRotate(node);
					}
					node.parent.isBlack = true;
					node.parent.parent.isBlack = false;
					leftRotate(node.parent.parent);
				}

			}

		}
		root.isBlack = true;
	}

	/**
	 * Min.
	 *
	 * @param root
	 *            the root
	 * @return the node
	 */
	public TreeNode min(TreeNode root) {
		TreeNode node = root;
		if (!node.equals(nil)) {
			while (!node.left.equals(nil))
				node = node.left;
		}
		return node;
	}



	/**
	 * Transplant.
	 *
	 * @param parent
	 *            the parent
	 * @param child
	 *            the child
	 */
	private void transplant(TreeNode parent, TreeNode child) {
		if (parent.parent.equals(nil))
			root = child;
		else if (parent.equals(parent.parent.left))
			parent.parent.left = child;
		else
			parent.parent.right = child;
		child.parent = parent.parent;
	}

	/**
	 * Delete.
	 *
	 * @param node
	 *            the node
	 */
	public void delete(TreeNode node) {
		if (!node.equals(nil)) {
			TreeNode n = node;
			boolean isblack = n.isBlack;
			TreeNode replace = new TreeNode();
			if (node.left.equals(nil)) {
				replace = node.right;
				transplant(node, node.right);
			} else if (node.right.equals(nil)) {
				replace = node.left;
				transplant(node, node.left);
			} else {
				n = min(node.right);
				isblack = n.isBlack;
				replace = n.right;
				if (n.parent.equals(node))
					replace.parent = n;
				else {
					transplant(n, n.right);
					n.right = node.right;
					n.right.parent = n;
				}
				transplant(node, n);
				n.left = node.left;
				n.left.parent = n;
				n.isBlack = node.isBlack;
			}
			if (isblack)
				deleteInternal(replace);

		}

	}

	/**
	 * Delete fixup.
	 *
	 * @param node
	 *            the node
	 */
	private void deleteInternal(TreeNode node) {
		while (!node.equals(root) && node.isBlack) {
			if (node.equals(node.parent.left)) {
				TreeNode pright = node.parent.right;
				if (!pright.isBlack) {
					pright.isBlack = true;
					node.parent.isBlack = false;
					leftRotate(node.parent);
					pright = node.parent.right;

				}
				if (pright.left.isBlack && pright.right.isBlack) {
					pright.isBlack = false;
					node = node.parent;
				} else {
					if (pright.right.isBlack) {
						pright.left.isBlack = true;
						pright.isBlack = false;
						rightRotate(pright);
						pright = node.parent.right;
					}
					pright.isBlack = node.parent.isBlack;
					node.parent.isBlack = true;
					pright.right.isBlack = true;
					leftRotate(node.parent);
					node = root;
				}
			} else {
				TreeNode pleft = node.parent.left;
				if (!pleft.isBlack) {
					pleft.isBlack = true;
					node.parent.isBlack = false;
					rightRotate(node.parent);
					pleft = node.parent.left;
				}
				if (pleft.right.isBlack && pleft.left.isBlack) {
					pleft.isBlack = false;
					node = node.parent;
				} else {
					if (pleft.left.isBlack) {
						pleft.left.isBlack = true;
						pleft.isBlack = false;
						leftRotate(pleft);
						pleft = node.parent.left;
					}
					pleft.isBlack = node.parent.isBlack;
					node.parent.isBlack = true;
					pleft.left.isBlack = true;
					rightRotate(node.parent);
					node = root;
				}
			}
		}
		node.isBlack = true;

	}

	/**
	 * Left rotate.
	 *
	 * @param node
	 *            the node
	 */
	private void leftRotate(TreeNode node) {
		TreeNode right = node.right;
		node.right = right.left;
		if (!right.left.equals(nil))
			right.left.parent = node;
		right.parent = node.parent;
		if (node.parent.equals(nil))
			root = right;
		else if (node.equals(node.parent.left))
			node.parent.left = right;
		else
			node.parent.right = right;
		right.left = node;
		node.parent = right;
	}

	/**
	 * Right rotate.
	 *
	 * @param node
	 *            the node
	 */
	private void rightRotate(TreeNode node) {
		TreeNode left = node.left;
		node.left = left.right;
		if (!left.right.equals(nil))
			left.right.parent = node;
		left.parent = node.parent;
		if (node.parent.equals(nil))
			root = left;
		else if (node.equals(node.parent.right))
			node.parent.right = left;
		else
			node.parent.left = left;
		left.right = node;
		node.parent = left;
	}

	/**
	 * Search.
	 *
	 * @param value
	 *            the value
	 * @return the node
	 */
	public TreeNode search(int value) {
		TreeNode node = root;
		if (node.ID > Integer.MIN_VALUE) {
			while (node.ID != value && !node.equals(nil)) {
				if (node.ID > value)
					node = node.left;
				else
					node = node.right;
			}

		}
		return node;
	}

	/**
	 * Delete.
	 *
	 * @param value
	 *            the value
	 */
	public void delete(int value) {
		delete(search(value));
	}


	/**
	 * Sorted array to bst.
	 *
	 * @param keys
	 *            the keys
	 * @param counts
	 *            the counts
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param parent
	 *            the parent
	 * @return the node
	 */
	private TreeNode sortedArrayToBST(int[] keys, int[] counts, int start, int end, TreeNode parent) {

		/* Base Case */
		if (start > end) {
			return nil;
		}

		/* Get the middle element and make it root */
		int mid = (start + end) / 2;
		TreeNode node = new TreeNode();
		node.ID = keys[mid];
		node.count = counts[mid];
		node.isBlack = true;
		node.left = nil;
		node.right = nil;
		//System.out.println(node.key);
		node.parent = parent;
		/*
		 * Recursively construct the left subtree and make it left child of root
		 */
		node.left = sortedArrayToBST(keys, counts, start, mid - 1, node);

		/*
		 * Recursively construct the right subtree and make it right child of
		 * root
		 */
		node.right = sortedArrayToBST(keys, counts, mid + 1, end, node);

		return node;
	}

	/**
	 * Creates the tree from file.
	 *
	 * @param filePath
	 *            the file path
	 * @throws NumberFormatException
	 *             the number format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void createTreeFromFile(String filePath) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));

		int size = Integer.valueOf(br.readLine());
		int[] keys = new int[size];
		int[] counts = new int[size];

		String line = null;
		int index = 0;
		while ((line = br.readLine()) != null) {
			String[] lineSplit = line.split("\\s");
			if(Integer.parseInt(lineSplit[1]) == 0){
				//the count is zero for thie node, we need not add it
				continue;
			}
			keys[index] = Integer.valueOf(lineSplit[0]);
			counts[index] = Integer.valueOf(lineSplit[1]);
			index++;
		}
		root = sortedArrayToBST(keys, counts, 0, counts.length - 1, nil);
		markLeavesAsRed();
		//if there is only one node in tree, set it to black
		if(root.left == nil && root.right == nil){
			root.isBlack = true;
		}

		br.close();

	}


	/**
	 * Increase the count of the event theID by m. If theID is not present,
	 * insert it. Print the count of theID after the addition.
	 *
	 * @param theID
	 *            the id
	 * @param m
	 *            the m
	 * @return the int
	 */
	public int Increase(int theID, int m) {
		TreeNode n = search(theID);
		if (n == nil) {
			insert(theID, m);
			System.out.println(m);
			return m;
		}
		n.count = n.count + m;
		System.out.println(n.count);
		return n.count;
	}

	/**
	 * Decrease the count of theID by m. If theID’s count becomes less than or
	 * equal to 0, remove theID from the counter. Print the count of theID after
	 * the deletion, or 0 if theID is removed or not present.
	 *
	 * @param theID
	 *            the id
	 * @param m
	 *            the m
	 * @return the int
	 */
	public int Reduce(int theID, int m) {
		TreeNode n = search(theID);
		n.count = n.count - m;
		if (n.count <= 0) {
			delete(theID);
			System.out.println("0");
			return 0;
		}else{
			System.out.println(n.count);
			return n.count;
		}
	}

	/**
	 * Print the ID and the count of the event with the lowest ID that is
	 * greater that theID. Print “0 0”, if there is no next ID.
	 *
	 * @param theID
	 *            the id
	 * @return the int
	 */
	public int Next(int theID) {
		int diff = Integer.MAX_VALUE;
		TreeNode node = root;

		while (node != nil) {
			if (node.ID <= theID) {
				node = node.right;
			} else if (node.ID > theID) {

				int d = node.ID - theID;

				if (d < diff) {
					diff = d;
				}
				node = node.left;
			}
		}

		if (diff == Integer.MAX_VALUE) {
			// the max value of tree is less than ID
			System.out.println("0 0");
			return 0;
		} else {
			TreeNode result = search(theID + diff);
			System.out.println(result.ID + " " + result.count);
			return result.count;
		}

	}

	/**
	 * Print the ID and the count of the event with the greatest key that is
	 * less that theID. Print “0 0”, if there is no previous ID.
	 *
	 * @param theID
	 *            the id
	 * @return the int
	 */
	public int Previous(int theID) {
		int diff = Integer.MAX_VALUE;
		TreeNode node = root;

		while (node != nil) {
			if (node.ID >= theID) {
				node = node.left;
			} else if (node.ID < theID) {

				int d = theID - node.ID;

				if (d < diff) {
					diff = d;
				}
				node = node.right;
			}
		}

		if (diff == Integer.MAX_VALUE) {
			// the max value of tree is less than ID
			System.out.println("0 0");
			return 0;
		} else {
			TreeNode result = search(theID - diff);
			System.out.println(result.ID + " " + result.count);
			return result.count;
		}

	}

	/**
	 * Print the count of theID. If not present, print 0.
	 *
	 * @param theID
	 *            the id
	 * @return the int
	 */
	public int count(int theID) {
		TreeNode n = search(theID);
		if (n == nil) {
			System.out.println(0);
			return 0;
		} else {
			System.out.println(n.count);
			return n.count;
		}

	}

	/**
	 * In range internal.
	 *
	 * @param ID1
	 *            the i d1
	 * @param ID2
	 *            the i d2
	 * @param sumCount
	 *            the sum count
	 * @param node
	 *            the node
	 * @return the int
	 */
	private int inRangeInternal(int ID1, int ID2, int sumCount, TreeNode node) {
		if (node == nil) {
			return 0;
		}
		if (node.ID < ID1) {
			return inRangeInternal(ID1, ID2, sumCount, node.right);
		} else if (node.ID > ID2) {
			return inRangeInternal(ID1, ID2, sumCount, node.left);
		} else {
			return sumCount + node.count + inRangeInternal(ID1, ID2, sumCount, node.left)
					+ inRangeInternal(ID1, ID2, sumCount, node.right);
		}

	}

	/**
	 * Print the total count for IDs between ID1 and ID2 inclusively. Note, ID1
	 * ≤ ID2
	 *
	 * @param ID1
	 *            the i d1
	 * @param ID2
	 *            the i d2
	 * @return the int
	 */
	public int inRange(int ID1, int ID2) {
		int count = inRangeInternal(ID1, ID2, 0, root);
		System.out.println(count);
		return count;
	}

	/**
	 * Height.
	 *
	 * @param root
	 *            the root
	 * @return the int
	 */
	private int height(TreeNode root) {
		if (root == nil)
			return 0;
		else
			return 1 + Math.max(height(root.left), height(root.right));
	}

	/**
	 * Sets the last level red of the tree.
	 */
	public void markLeavesAsRed() {
		int ht = height(root);
		markLeavesAsRedInternal(root, ht);
	}

	/**
	 * Helper for making leaves node as Red.
	 *
	 * @param root the root
	 * @param level the level
	 */
	private void markLeavesAsRedInternal(TreeNode root, int level) {
		
		//level 0
		if (nil == root)
			return;
		//level 1
		if (level == 1) {
			root.isBlack = false;
			
		} else if (level > 1) {
			markLeavesAsRedInternal(root.left, level - 1);
			markLeavesAsRedInternal(root.right, level - 1);
		}
	}
}
