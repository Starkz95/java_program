package predictive;

import java.util.HashSet;
import java.util.Set;

public class Tree4TreeDict {

	private HashSet<String> node;
	private Tree4TreeDict[] branch = new Tree4TreeDict[8];
	

	
	/**
	 * @param node
	 * @param branch
	 */
	public Tree4TreeDict(HashSet<String> node, Tree4TreeDict[] branch) {
		super();
		this.node = node;
		this.branch = branch;
	}

	public Tree4TreeDict() {
		super();
		this.node = new HashSet<>();
		this.branch = new Tree4TreeDict[8];
	}


	
	/**
	 * Getter for the node
	 * @return the node
	 */
	public HashSet<String> getNode() {
		return node;
	}
	

	/**
	 * Getter for the branch
	 * @return the branch
	 */
	public Tree4TreeDict[] getBranch() {
		return branch;
	}



	
}
