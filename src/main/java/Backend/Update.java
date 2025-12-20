package Backend;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.nio.file.Files;
public class Update {
	private static final Path log_path=Path.of("log path");
	
	public static void log(int x, int y, int val, int [][]board) throws IOException {
		
		int prev=board[x][y];
		
		board[x][y]= val;//dosen't effect 
		
		String entry = "(" + x + "," + y + "," + val + "," + prev + ")\n";
		
		 if (!Files.exists(log_path)) {
	            Files.createFile(log_path);
	        }
		 
		 Files.write(log_path,entry.getBytes(),StandardOpenOption.APPEND);// to update in log file immediately 
		 																	//APPEND will help to write in the same file
		 																	// Bytes cause Files save as a Bytes not string 
	}

}
