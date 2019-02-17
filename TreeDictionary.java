package predictive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TreeDictionary implements Dictionary{


	Tree4TreeDict tree=new Tree4TreeDict();
	
	public TreeDictionary(String path){
		try {

			Scanner s=new Scanner(new File(path));
			while(s.hasNextLine()){
				String word=s.nextLine();
				word=word.toLowerCase();
				Tree4TreeDict curTree=tree;
				if(PredictivePrototype.isValidWord(word)){
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
		   }
			s.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found.");
		}
	}
	
	
	
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
