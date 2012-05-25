package ursuppe;

import static org.junit.Assert.*;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class EmptySquareTest {
		
		@Test
		public void newEmptySquareShouldBeCreated() {
			EmptySquare testEmptySquare = new EmptySquare();
			
			assertEquals(testEmptySquare.Line(0), "+--------------");
			assertEquals(testEmptySquare.Line(1), "|              ");
			assertEquals(testEmptySquare.Line(2), "|              ");
		}
		
		@Test
		public void secondEmptySquareShouldBeCreated() {
			EmptySquare testEmptySquare = new EmptySquare();
			EmptySquare testEmptySquare2 = new EmptySquare();
			
			assertFalse(testEmptySquare.equals(testEmptySquare2));
			assertEquals(testEmptySquare.Line(0),testEmptySquare2.Line(0));
		}
}
