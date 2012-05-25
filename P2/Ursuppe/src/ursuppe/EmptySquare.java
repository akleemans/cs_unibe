package ursuppe;

/**
 * Extends the Square class for providing an empty Square.
 * 
 */
public class EmptySquare extends Square {
	
	public String Line(int line){
		if (line==0)
			return("+--------------");
		else
			return("|              ");
	}
	
	public boolean isIngameSquare(){
		return false;
	}

}
