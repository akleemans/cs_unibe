package ursuppe;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class SquareTest {
	Mockery context = new JUnit4Mockery();
	
	/*
	 * AK this test is broken on more than one level:
	 * 		1) It fails. Which is always a shock. If you are fully
	 * 		   aware of the problem, you should remove the test.
	 * 		   If you don't want to do that, you add an
	 * 		   @Ignore annotation to make sure, the test does not run.
	 * 		2) It does not test anything useful. To translate
	 * 		   JMock to English:
	 * 			You:   "Hey you, gimme a line(0) if I ask you! Just any line."
	 * 			Jmock: "Um, ok?"
	 * 			You:   "Gimme the line now."
	 * 			Jmock: "Um... I don't know, you didn't tell me which line, so I'll go with... null?"
	 * 			You:   "was that line '+----...'?"
	 * 			Jmock: "Actually..."
	 * 			You:   "Darn!" 
	 * 		   So you basically tested if Jmock would work. And it failed.
	 * 		   Darn indeed! Instead test the actual class `Square` and
	 * 		   Mock any dependencies - like the board, players, etc.
	 * 			
	 */
    @Test 
    public void returnCorrectLine() {
        final ISquare testSquare = context.mock(ISquare.class);
		
        context.checking(new Expectations() {{
        	oneOf(testSquare).Line(0); //will(returnValue("+--------------"));
        }});
        
        String t = testSquare.Line(0);
        
        assert(t.equals("+--------------"));
    }

}
