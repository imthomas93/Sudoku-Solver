package CS486AI.A1Q4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;




public class versionB{
	public static int counter;

	public static costTable[][] cost = new costTable [9][9];

	private static boolean isValid(int row, int column, int[][]sudoGrid, int target){
		
		// increase counter as variable is assigned
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
		
		// check target is already in sub-grid of the SUDOKU board
		// rowSection & columnSection is to define which section of the board
		/*
		 * i.e: For Row
		 * 	row 0 -2 belongs to section 0
		 * 	row 3-5 belongs to section 1
		 *  row 6-8 belongs to section 2
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
			if (++column == 9)
				//SUDOKU SOLVED
				return true;
		}
		// Solver can't solve Problem, return
		else if (counter > 10000)
			return false;
		
		//Bypass if row and column value !=0
		if (sudoGrid[row][column] != 0)
			return (sudoSolverV1(row +1, column, sudoGrid));
		
		
		for (int target = 1; target <= 9; ++target){
			
			if(forwardChecking(row, column, target, sudoGrid)){
				if (isValid(row, column, sudoGrid, target)){

					// Update cost table
					for(int i = 0; i <9; i++){
						cost[row][i].deleteNum(target);
						cost[i][column].deleteNum(target);
					}
					int rowSection = (row/3)*3;
					int columnSection = (column/3)*3;
					for (int x = 0; x < 3 ; x++){
						for (int y = 0; y < 3; y++)
							cost[x + rowSection][y + columnSection].deleteNum(target);
					}
					
					
					// update SUDOKU Board solution
					sudoGrid[row][column] = target;
					if (sudoSolverV1(row +1, column, sudoGrid))
						return true;
				}
			}
		}
		// failed assignment, backtrack here
		sudoGrid[row][column] = 0;
		return false;
		
	}
	

	private static boolean forwardChecking(int row, int column, int target, int [][] sudoGrid) {
		// TODO Auto-generated method stub
		costTable[][] temp = new costTable[9][9];
		
		// duplicate temp to perform testing
		for (int i =0;i<9;i++){
			for(int j=0;j<9;j++){
				temp[i][j] = new costTable();
				if (sudoGrid[i][j] !=0){
					int k = sudoGrid[i][j];
					temp[i][j].deleteNum(k);
				}	
			}
		}
		
		// delete target off other rows and columns
		for(int i = 0; i <9; i++){
			temp[row][i].deleteNum(target);
			temp[i][column].deleteNum(target);
		}
	
		// delete target off specific grid of board
		int rowSection = (row/3)*3;
		int columnSection = (column/3)*3;
		for (int x = 0; x < 3 ; x++){
			for (int y = 0; y < 3; y++)
				temp[x + rowSection][y + columnSection].deleteNum(target);
		}
		
		
		for(int i = 0; i <9; i++){
			for(int j = 0; j < 9; j++){
				//backtrack as forward checking deem not possible
				if (sudoGrid[i][j] == 0 && temp[i][j].isEmpty())
					return false;
			}
		}

		// forward check deem safe to proceed
		return true;
		
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
	
	public static void loadCostTable(int [][] sudoGrid){
		
		for (int i =0;i<9;i++){
			for(int j=0;j<9;j++)
				cost[i][j] = new costTable();
		}
		
		for (int i =0;i<9;i++){
			for(int j=0;j<9;j++){
				if (sudoGrid[i][j] !=0){
					int target = sudoGrid[i][j];
					cost[i][j].deleteNum(target);
				
					// Update cost table
					for(int k = 0; k <9; k++){
						cost[i][k].deleteNum(target);
						cost[k][j].deleteNum(target);
					}
				}	
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int avg = 0;
	
		// USE this to get individual result
		counter = 0;
		int [][] sudoGrid = loadGrid(1,3);
		loadCostTable(sudoGrid);

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
				loadCostTable(sudoGrid);
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