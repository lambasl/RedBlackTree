import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * The Class bbst. The driver class for this application
 */
public class bbst {
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); 
        String line; 
        String fileName = args[0];
        RedBlackTree tree = new RedBlackTree();
        
        try {
            tree.createTreeFromFile(fileName); // creating the tree
			while((line=stdin.readLine()) != null){
				String[] lineSplits = line.split("\\s+");
				int id, m,id1, id2;
				switch (lineSplits[0].trim()) {
				
				case "increase":
					id = Integer.valueOf(lineSplits[1]);
					m = Integer.valueOf(lineSplits[2]);
					tree.Increase(id, m);
					break;
					
				case "reduce":
					id = Integer.valueOf(lineSplits[1]);
					m = Integer.valueOf(lineSplits[2]);
					tree.Reduce(id, m);
					break;
				
				case "count":
					id = Integer.valueOf(lineSplits[1]);
					tree.count(id);
					break;
				
				case "inrange":
					id1 = Integer.valueOf(lineSplits[1]);
					id2 = Integer.valueOf(lineSplits[2]);
					tree.inRange(id1, id2);
					break;
					
				case "next":
					id = Integer.valueOf(lineSplits[1]);
					tree.Next(id);
					break;
				
				case "previous":
					id = Integer.valueOf(lineSplits[1]);
					tree.Previous(id);
					break;
					
				case "quit":
					System.exit(0);

				default:
					System.out.println("Invalid input");
					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
