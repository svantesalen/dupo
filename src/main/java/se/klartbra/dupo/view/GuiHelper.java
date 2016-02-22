package se.klartbra.dupo.view;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

public class GuiHelper {

	private GuiHelper() {}

	/**
	 * Put a component in the center of the screen.
	 * @param component The component
	 * @return A Point(x,y) object representing the point where the component was put.
	 */
	public static Point center(Component component) {
		Point screenCenter =  GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		double x = screenCenter.getX();
		double y = screenCenter.getY();
		double startPointX = x-component.getWidth()/2.0;
		double startPointY = y-component.getHeight()/2.0;
		component.setLocation((int)startPointX, (int)startPointY);
		return new Point((int)startPointX, (int)startPointY);
	}


}
