package org.hamcrest.problemset04;

import java.awt.geom.Point2D;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/*DR Some very small fixes required for next week!
 * 
 * Please add a test class for the matcher like the CTO ordered you to do!
 * Also try to write more about the responsibilities of the classes. I think you read the paper
 * but I'd like to see not only what a class knows but what it does and provides (like I said only minor stuff^^)
 * 
 * You fully understand how to use mocks and implemented the hamcrest.closeTo for point2Ds very well!
 */

public class Point2DIsCloseTo extends TypeSafeMatcher<Point2D> {
	private final double distance = 0.001;
	private final double XCoordinate, YCoordinate;

	// DR you could also just save the point! instead of it's coordinates
	public Point2DIsCloseTo(Point2D refPoint) {
		this.XCoordinate = refPoint.getX();
		this.YCoordinate = refPoint.getY();
	}

	@Override
	protected boolean matchesSafely(Point2D item) {
		// DR delete this stuff
		// TODO Auto-generated method stub
		return Math.abs(item.getX() - XCoordinate) <= distance
				&& Math.abs(item.getY() - YCoordinate) <= distance;
	}

	// DR rad English but what exactly do you mean with shalt^^
	@Override
	public void describeTo(Description description) {
		description.appendText("target shalt be close to [" + XCoordinate
				+ ", " + YCoordinate + "]");

	}

	@Factory
	public static <T> Matcher<Point2D> closeTo(Point2D item) {
		return new Point2DIsCloseTo(item);
	}

}
