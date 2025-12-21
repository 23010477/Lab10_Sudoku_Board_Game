package Backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Undo {
	private static final Path path_log = Path.of("src/main/java/Backend/Log.txt");

	public static void undo(int[][] board) throws IOException {

		if (!Files.exists(path_log)) {
			return;
		}

		List<String> lines = Files.readAllLines(path_log);
		if (lines.isEmpty()) {
			return;
		}

		String lastLine = lines.remove(lines.size() - 1);

		lastLine = lastLine.replace("(", "").replace(")", "");// delete ( ) to separate it into integer parts
		String[] parts = lastLine.split(",");// array of parts

		int x = Integer.parseInt(parts[0].trim());
		int y = Integer.parseInt(parts[1].trim());
		int val = Integer.parseInt(parts[2].trim());
		int previous = Integer.parseInt(parts[3].trim());

		board[x][y] = previous;

		Files.write(path_log, lines);
	}

}
