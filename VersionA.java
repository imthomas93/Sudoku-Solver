package CS486AI.A1Q4;

import java.io.IOException;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;

public class VersionA {
	public static int counter;

	private static boolean isValid(int row, int column, int[][]sudoGrid, int target){
		counter++;
		// Check if target has already appeared, if false, backtrack
		for (int checker =0; checker <9; checker++){
			if (sudoGrid[row][checker] == target)
				return false;
		}
		
		// Check if target has already appeared, if false, backtrack
		for (int checker =0; checker <9; checker++){
			if (sudoGrid[checker][column] == target)
				return false;
		}
		
		/* check target is already in sub-grid of the SUDOKU board
		 * rowSection & columnSection is to define which section of the board
		 * i.e: For Row
		 * 	row 0 -2 belongs to section 0
		 * 	row 3-5 belongs to section 3
		 *  row 6-8 belongs to section 6
		 *  Note: Logic applies to column too.
		 */
		int rowSection = (row/3)*3;
		int columnSection = (column/3)*3;
	
		for (int x = 0; x < 3 ; x++){
			for (int y = 0; y < 3; y++){
				if (sudoGrid[x + rowSection][y + columnSection] == target){
					return false;
				}
			}
		}
		//Pass all tests for target, return true to proceed to next cell of board
		return true;
	}
	
	
	private static boolean sudoSolverV1(int row, int column, int[][] sudoGrid) {
		
		//TODO Backtracking with DFS

		// Check if SUDOKU is solved - recursion end case
		if (row == 9){
			row = 0;
			if (++column == 9){
				column = 0;
				//SUDOKU SOLVED
				return true;
			}
				
		}
		// Solver can't solve Problem, return
		else if (counter > 10000)
			return false;
		
		
		//Bypass if row and column value !=0
		if (sudoGrid[row][column] != 0)
			return (sudoSolverV1(row +1, column, sudoGrid));


		for (int target = 1; target <= 9; ++target){
			//counter++;
			if (isValid(row, column, sudoGrid, target)){
				// assigned target to cell of row and column
				sudoGrid[row][column] = target;
					if (sudoSolverV1(row +1, column, sudoGrid))
						return true;
			}
		}

		// failed assignment, backtrack here
		sudoGrid[row][column] = 0;
		return false;
		
	}
	
	@SuppressWarnings("unused")
	private static void printGrid(int [][] sudoGrid) {
		// TODO Auto-generated method stub
		
		System.out.println("\nSudoku board");
		for (int i =0;i<9;i++){
			String line = "";
			for (int j=0; j<9; j++){
				line += sudoGrid[i][j] + " ";
			}
			System.out.println(line + "\t");
		}
	}

	public static int [][] loadGrid(int file, int problem){
		
		int result[][] = new int[9][9];
	
		// TODO write codes to load sudo board
		try{

			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("CS486AI/A1Q4/problems/" + file + "/" + problem + ".sd"));
			String output;	
			for (int i = 0; i < 9; i++){
				output = reader.readLine();
				StringTokenizer defaultTokenizer = new StringTokenizer(output);
				for (int j = 0; j < 9; j++)
					result[i][j] = Integer.parseInt(defaultTokenizer.nextToken());
			}
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
	
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int avg = 0;
		
		// USE this to get individual result
		counter = 0;
		int [][] sudoGrid = loadGrid(1,3);

		boolean assignment = sudoSolverV1(0,0,sudoGrid);
		System.out.println("Solution for Problem 1, 3.sd");

		if (!assignment){
			System.out.println("SUDOKU cannot be solved.");
		}
		else{
			System.out.println("SUDOKU Solved. " + counter + " steps");
			printGrid(sudoGrid);
			System.out.println();
		}
		
		System.out.println("Avg varible assignment result per problem folder");
		// USE THIS FOR STATS RESULT FOR 71x10 files avg
		for (int i = 1; i <= 71; i++){
			counter = 0;
			for (int j =1; j <=10; j++){
				sudoGrid = loadGrid(i,j);
		
				assignment = sudoSolverV1(0,0,sudoGrid);
				
				if (!assignment){
					//System.out.println("SUDOKU cannot be solved.");
				}
				else{
					avg++;
					//printGrid(sudoGrid);
				}	
			}
			System.out.println("File" + i + " : " + counter/avg);
		}
	}

}
