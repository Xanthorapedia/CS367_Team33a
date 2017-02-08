import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.Scanner;

public class GradeEstimator {
	
	public static String [] letterGrades;
	public static Double [] miniThresholds; 
	public static String [] categoryNames;
	public static Double [] categoryWeights;
	
	public static void main(String[] args) {
     
		try{
			
			GradeEstimator thisGradeestimater = createGradeEstimatorFromFile(args[0]);
			System.out.println(getEstimateReport());
			
		}catch(FileNotFoundException e){
			throw new FileNotFoundException();

		}catch(GradeFileFormatException e){
			
			
			
		}
		

	}


	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) throws FileNotFoundException, GradeFileFormatException{


		try{
			FileInputStream file = new FileInputStream("gradeInfo");
			Scanner scan = new Scanner(file);
			while(scan.hasNext()){
				letterGrades = scan.nextLine().split(" "); 
				
			
				
				categoryNames = scan.nextLine().split(" ");
			
				      	
			}


		}catch(FileNotFoundException e){
			throw new FileNotFoundException();

		}
		catch(GradeFileFormatException e){
			

		}
	}


public String getEstimateReport(){
    
	return "";


}
}
