import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


/*
 * AK The contract you handed in is well considered and if implemented
 * correctly will make a good design.
 * 
 * There are some short comings up to this point:
 * rotation does not yet guarantee that the
 *     result is still valid.
 * the method for validating a transform might fail
 *     for combinations
 * rotation is defined differently from our solution
 * 
 * If you fix these problems, your tests will run and you'll
 * pass this problemset
 * 
 */


/*
 * AK It'd probably be better, it you abstracted the 
 * BufferImage and the Dimension away by using Rectangle2d
 * for both and hand in these on the creation instead. 
 * This makes your code more flexible (it does not care
 * what's rotated, only, that the rectangle is in the
 * framedimension. It also is much shorter to write
 * 
 * EDIT: Your new design is really good. Well done!
 * 
 */
/*
 * AK Problemset04: You making good progress here. You did
 * accurately document the responsibilities of the objects in PS2
 * and wrote a first Mock-test. 
 * TODO Fix that (I posted an example on twitter) and
 * implement the TypeSafeMatcher CloseTo and you are done!
 */

public class Transform {
	
	private AffineTransform affTransform = new AffineTransform();
	
	private Rectangle2D panelRectangle;
	private Rectangle2D imageRectangle;
	private Rectangle2D imageTransformedRectangle;
	
	public Transform(Rectangle2D imageRectangle, Rectangle2D panelRectangle){
		
		this.imageRectangle = imageRectangle;
		this.panelRectangle = panelRectangle;
		this.imageTransformedRectangle = imageRectangle;
		
		assert invariant();
		
	}
	

	public void addTransformation(String description){
		String arguments[] = description.split(" ");
		String methodDescription = arguments[0];
		
		if (this.isValidTransformation(description)){
		
			if (methodDescription.equals("scale"))
				this.scale(Double.parseDouble(arguments[1]),Double.parseDouble(arguments[1])); 
			else if(methodDescription.equals("translate"))
				this.translate(Double.parseDouble(arguments[1]),Double.parseDouble(arguments[2]));
			else if(methodDescription.equals("rotate")){
				String direction = arguments[2];
				this.rotate(Double.parseDouble(arguments[1]),direction);
			}
		}
	}

	/*
	 * AK You might run into a trap here:
	 * a.scale means
	 *    * first scale, 
	 *    * then apply a.
	 * which is the wrong way around.
	 * 
	 * You can test the effect of this with
	 * rotate 1 clockwise
	 * translate 100 0
	 * 
	 * which makes the translation in the wrong direction.
	 * 
	 * take a look at the documentation of the #scale, #translate
	 * and #rotate methods and look at #preConcatenate to avoid this
	 * common mistake.
	 */
	
	/*
	 * AKL implemented #preConcatenate
	 */
	
	private void scale(Double x, Double y) {
		
		assert (x.equals(y));
		
		//create empty transformation affScale
		AffineTransform affScale = new AffineTransform();
		affScale.scale(x,y);
		
		AffineTransform affTempTransform = new AffineTransform();
		affTempTransform = (AffineTransform) affTransform.clone();
		
		affTempTransform.preConcatenate(affScale);
		
		if (inFrame(affTempTransform))
			affTransform.preConcatenate(affScale);
	}
	
	
	private void translate(Double x, Double y){
	
		AffineTransform affTranslate = new AffineTransform();
		affTranslate.translate(x,y);
		
		AffineTransform affTempTransform = new AffineTransform();
		affTempTransform = (AffineTransform) affTransform.clone();
		
		affTempTransform.preConcatenate(affTranslate);
		
		if (inFrame(affTempTransform))
			affTransform.preConcatenate(affTranslate);
	}

	/*
	 * AK your rotation revolves around (0/0), which is fine, but
	 * the tests imply that you should revolve around the centre of
	 * the image. Either you can change your implementation to
	 * make this happen, or you can reformulate the tests so that
	 * they imply YOUR solution instead.
	 */
	
	/*
	 * AKL Fixed this, rotation is now around center.
	 */
	
	private void rotate(Double angle, String direction) {

			if (direction.equals("clockwise")){
				AffineTransform affTempTransform = new AffineTransform();
				affTempTransform = (AffineTransform) affTransform.clone();
				
				affTempTransform.rotate(angle, imageRectangle.getCenterX(), imageRectangle.getCenterY());
				
				if (inFrame(affTempTransform))
					affTransform.rotate(angle, imageRectangle.getCenterX(), imageRectangle.getCenterY());
			}
			
			else if (direction.equals("anticlockwise")){
				AffineTransform affTempTransform = new AffineTransform();
				affTempTransform = (AffineTransform) affTransform.clone();
				
				affTempTransform.rotate(-angle, imageRectangle.getCenterX(), imageRectangle.getCenterY());
				
				if (inFrame(affTempTransform))
					affTransform.rotate(-angle, imageRectangle.getCenterX(), imageRectangle.getCenterY());
			}
	}
	
	private Rectangle2D transformImageRectangle(AffineTransform affTransform){
		return affTransform.createTransformedShape(imageRectangle).getBounds2D();
	}
		
	/*
	 * AK Very good!
	 */
	private boolean isValidTransformation(String transformationString) {
		return (this.isScale(transformationString) || this.isRotate(transformationString) || this.isTranslate(transformationString));
		}
	
	private boolean isScale(String stringToCheck) {
		String arguments[] = stringToCheck.split(" ");
		return (arguments.length==2 && arguments[0].contains("scale") && this.isNumerical(arguments[1]));
	}
	
	private boolean isTranslate(String stringToCheck) {
		String arguments[] = stringToCheck.split(" ");
		return (arguments.length==3 && arguments[0].contains("translate") && this.isNumerical(arguments[1])  && this.isNumerical(arguments[2]));
	}
	
	private boolean isRotate(String stringToCheck) {
		String arguments[] = stringToCheck.split(" ");
		return (arguments.length==3 && arguments[0].contains("rotate") && this.isNumerical(arguments[1]) && (arguments[2].equals("clockwise") || arguments[2].equals("anticlockwise")));
	}
	
	private boolean isNumerical(String stringToCheckIfNumerical)  
	{  
		try { 
			Double.parseDouble(stringToCheckIfNumerical);  
			return true;  
		}
		catch( Exception e) {  
			return false;  
		}  
	}
	
	public AffineTransform getAffineTransform() {
		
		imageTransformedRectangle = this.transformImageRectangle(affTransform);
		assert invariant();
		
		return affTransform;
		
	}

	/*
	 * AK if you update the coordinates after each transform, you can
	 * just call #invariant() to check if a transformation is valid.
	 */
	
	/*
	 * AKL I think the restructuring by RB obsoletes this comment
	 */
	
	/*
	 * AK it kind of does. Maybe you should call #inFrame here?
	 */
	protected boolean invariant(){
		return (panelRectangle.contains(imageTransformedRectangle));
	}
	
	/* 
	 * AK Well done!
	 */
	/*
	 * RB returns if the Rectangle2D "panelRectangle" still contains a hypothetic
	 * Rectangle2D after this latter Rectangle2D is transformed according to the
	 * AffineTransformation "affTempTransform"
	 * 
	 */
	protected boolean inFrame(AffineTransform affTempTransform){
		return (panelRectangle.contains(affTempTransform.createTransformedShape(imageRectangle).getBounds2D()));
	}
	
}
