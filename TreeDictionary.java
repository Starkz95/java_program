package predictive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
/**
 * this class is to generate a tree by the dictionary.
 * @author Mingcheng Zhang
 * @version Feb 17, 2019
 */
public class TreeDictionary implements Dictionary{


	Tree4TreeDict tree=new Tree4TreeDict();
	
	/**
	 * this constructor is to read the dictionary and create the tree.
	 * @param path
	 */
	public TreeDictionary(String path){
		try {

			Scanner s=new Scanner(new File(path));
			while(s.hasNextLine()){
				String word=s.nextLine();
				word=word.toLowerCase();
				//Tree4TreeDict curTree=tree;
				if(PredictivePrototype.isValidWord(word)){
						createTree2(tree, word,0);
					}
		   }
			s.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found.");
		}
	}
	
	/**
	 * this method is to create a tree to store the matching words' prefix at the nodes of the tree.
	 * @param curTree
	 * @param word
	 */
	public void createTree(Tree4TreeDict curTree,String word){
		for(int i=0;i<word.length();i++){
			if("abc".contains(Character.toString(word.charAt(i)))){
				if(curTree.getBranch()[0]==null){
					curTree.getBranch()[0] = new Tree4TreeDict();
					curTree.getBranch()[0].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[0].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[0];
				continue;
			}
	    	if("def".contains(Character.toString(word.charAt(i)))){
	    		if(curTree.getBranch()[1]==null){
					curTree.getBranch()[1] = new Tree4TreeDict();
					curTree.getBranch()[1].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[1].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[1];
	    		continue;
	    	}
	    	if("ghi".contains(Character.toString(word.charAt(i)))){
	    		if(curTree.getBranch()[2]==null){
					curTree.getBranch()[2] = new Tree4TreeDict();
					curTree.getBranch()[2].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[2].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[2];
	    		continue;
	    	}
	    	if("jkl".contains(Character.toString(word.charAt(i)))){
	    		if(curTree.getBranch()[3]==null){
					curTree.getBranch()[3] = new Tree4TreeDict();
					curTree.getBranch()[3].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[3].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[3];
	    		continue;
	    	}
	    	if("mno".contains(Character.toString(word.charAt(i)))){
	    		if(curTree.getBranch()[4]==null){
					curTree.getBranch()[4] = new Tree4TreeDict();
					curTree.getBranch()[4].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[4].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[4];
	    		continue;
	    	}
	    	if("pqrs".contains(Character.toString(word.charAt(i)))){
	    		if(curTree.getBranch()[5]==null){
					curTree.getBranch()[5] = new Tree4TreeDict();
					curTree.getBranch()[5].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[5].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[5];
	    		continue;
	    	}
	    	if("tuv".contains(Character.toString(word.charAt(i)))){
	    		if(curTree.getBranch()[6]==null){
					curTree.getBranch()[6] = new Tree4TreeDict();
					curTree.getBranch()[6].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[6].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[6];
	    		continue;
	    	}
	    	if("wxyz".contains(Character.toString(word.charAt(i)))){
	    		if(curTree.getBranch()[7]==null){
					curTree.getBranch()[7] = new Tree4TreeDict();
					curTree.getBranch()[7].getNode().add(word.substring(0,i+1));
				}
				else{
					curTree.getBranch()[7].getNode().add(word.substring(0,i+1));
				}
				curTree=curTree.getBranch()[7];
	    		continue;
	    	}	
		}
	}
	
	public void createTree2(Tree4TreeDict tree,String word,int level){
		if(level==word.length()){
			return;
		}
		else{
			if("abc".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[0]==null){
					tree.getBranch()[0] = new Tree4TreeDict();
					tree.getBranch()[0].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[0], word, level+1);
				}
				else{
					tree.getBranch()[0].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[0], word, level+1);
				}
			}
			if("def".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[1]==null){
					tree.getBranch()[1] = new Tree4TreeDict();
					tree.getBranch()[1].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[1], word, level+1);
				}
				else{
					tree.getBranch()[1].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[1], word, level+1);
				}
			}
			if("ghi".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[2]==null){
					tree.getBranch()[2] = new Tree4TreeDict();
					tree.getBranch()[2].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[2], word, level+1);
				}
				else{
					tree.getBranch()[2].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[2], word, level+1);
				}
			}
			if("jkl".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[3]==null){
					tree.getBranch()[3] = new Tree4TreeDict();
					tree.getBranch()[3].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[3], word, level+1);
				}
				else{
					tree.getBranch()[3].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[3], word, level+1);
				}
			}
			if("mno".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[4]==null){
					tree.getBranch()[4] = new Tree4TreeDict();
					tree.getBranch()[4].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[4], word, level+1);
				}
				else{
					tree.getBranch()[4].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[4], word, level+1);
				}
			}
			if("pqrs".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[5]==null){
					tree.getBranch()[5] = new Tree4TreeDict();
					tree.getBranch()[5].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[5], word, level+1);
				}
				else{
					tree.getBranch()[5].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[5], word, level+1);
				}
			}
			if("tuv".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[6]==null){
					tree.getBranch()[6] = new Tree4TreeDict();
					tree.getBranch()[6].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[6], word, level+1);
				}
				else{
					tree.getBranch()[6].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[6], word, level+1);
				}
			}
			if("wxyz".contains(Character.toString(word.charAt(level)))){
				if(tree.getBranch()[7]==null){
					tree.getBranch()[7] = new Tree4TreeDict();
					tree.getBranch()[7].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[7], word, level+1);
				}
				else{
					tree.getBranch()[7].getNode().add(word.substring(0,level+1));
					createTree2(tree.getBranch()[7], word, level+1);
				}
			}
		}
	}
	
	
	/**
	 * this method is to find the node of the tree corresponding to the given signature.
	 * @param signature
	 * @return Set
	 */
	public  Set<String> signatureToWords(String signature){
		//Tree4TreeDict curTree=tree;
		for(int i=0;i<signature.length();i++){
			switch (signature.charAt(i)) {
			case '2':
				tree=tree.getBranch()[0];
				break;
			case '3':
				tree=tree.getBranch()[1];
				break;
			case '4':
				tree=tree.getBranch()[2];
				break;
			case '5':
				tree=tree.getBranch()[3];
				break;
			case '6':
				tree=tree.getBranch()[4];
				break;
			case '7':
				tree=tree.getBranch()[5];
				break;
			case '8':
				tree=tree.getBranch()[6];
				break;
			case '9':
				tree=tree.getBranch()[7];
				break;
			default:
				break;
			}
		}
		return tree.getNode();
	}
	
	
		
}
