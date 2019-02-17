package predictive;

import java.util.Iterator;

public class Sigs2WordsTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeDictionary t=new TreeDictionary("words");
		//for(String a:args) {
			Iterator<String> it = t.signatureToWords("4663").iterator();
			System.out.print("4663"+" : ");
			while (it.hasNext()) {
				String s = it.next();
				System.out.print(s+" ");
			}
			System.out.print("\n");
		}
	//}

}
