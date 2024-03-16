package gk.lab3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class SceneGraph extends JPanel {

	public static void main(String[] args) {
		JFrame window = new JFrame("Scene Graph 2D");
		window.setContentPane( new SceneGraph() );
		window.pack();
		window.setLocation(100,60);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private final static int WIDTH = 800;
	private final static int HEIGHT = 600;

	private final static double X_LEFT = -4; 
	private final static double X_RIGHT = 4;
	private final static double Y_BOTTOM = -3;
	private final static double Y_TOP = 3;

	private final static Color BACKGROUND = Color.WHITE;

	private float pixelSize;

	private int frameNumber = 0;

	private CompoundObject world;

	private TransformedObject Triangle_1, Triangle_2, Triangle_3;
	private TransformedObject Line_1, Line_2, Line_3;
	private TransformedObject Thirteen_1, Thirteen_2, Thirteen_3, Thirteen_4, Thirteen_5, Thirteen_6;

	private void createWorld() {

		world = new CompoundObject();

		Thirteen_1 = new TransformedObject(filledThirteen);
        Thirteen_2 = new TransformedObject(filledThirteen);
        Thirteen_3 = new TransformedObject(filledThirteen);
        Thirteen_4 = new TransformedObject(filledThirteen);
        Thirteen_5 = new TransformedObject(filledThirteen);
        Thirteen_6 = new TransformedObject(filledThirteen);
		Line_1 = new TransformedObject(filledRect);
		Line_2 = new TransformedObject(filledRect);
		Line_3 = new TransformedObject(filledRect);
		Triangle_1 = new TransformedObject(filledTriangle);
		Triangle_2 = new TransformedObject(filledTriangle);
		Triangle_3 = new TransformedObject(filledTriangle);

		Thirteen_1.setScale(0.3, 0.3).setTranslation(-0.889, -0.42);
        Thirteen_2.setScale(0.3, 0.3).setTranslation(0.899, -1.189);
        Thirteen_3.setScale(0.25, 0.25).setTranslation(-3, 1.825);
        Thirteen_4.setScale(0.25, 0.25).setTranslation(-1.4, 1.18);
        Thirteen_5.setScale(0.2, 0.2).setTranslation(0.83, 2.07);
        Thirteen_6.setScale(0.2, 0.2).setTranslation(2.16, 1.52);
		Line_1.setRotation(-22.5).setScale(2, 0.1).setTranslation(0, -0.8).setColor(Color.RED);
		Line_2.setRotation(-22.5).setScale(1.79, 0.1).setTranslation(-2.2, 1.50).setColor(Color.RED);
		Line_3.setRotation(-22.5).setScale(1.48, 0.08).setTranslation(1.5, 1.8).setColor(Color.RED);
		Triangle_1.setScale(0.5, 1.2).setTranslation(0, -2).setColor(Color.BLUE);
		Triangle_2.setScale(0.5, 1).setTranslation(-2.25, 0.5).setColor(new Color(200, 21, 132));
		Triangle_3.setScale(0.5, 0.8).setTranslation(1.5, 1).setColor(new Color(0, 128, 0));

		world.add(Thirteen_1);
        world.add(Thirteen_2);
        world.add(Thirteen_3);
        world.add(Thirteen_4);
        world.add(Thirteen_5);
        world.add(Thirteen_6);
		world.add(Line_1);
		world.add(Line_2);
		world.add(Line_3);
		world.add(Triangle_1);
		world.add(Triangle_2);
		world.add(Triangle_3);
	}

	public void updateFrame() {
		frameNumber++;

		double rotate = -2 * frameNumber;

		Thirteen_1.setRotation(rotate);
        Thirteen_2.setRotation(rotate);
        Thirteen_3.setRotation(rotate);
        Thirteen_4.setRotation(rotate);
        Thirteen_5.setRotation(rotate);
        Thirteen_6.setRotation(rotate);
	}

	private static abstract class SceneGraphNode {
		Color color;

		SceneGraphNode setColor(Color c) {
			this.color = c;
			return this;
		}
		final void draw(Graphics2D g) {
			Color saveColor = null;
			if (color != null) {
				saveColor = g.getColor();
				g.setColor(color);
			}
			doDraw(g);
			if (saveColor != null) {
				g.setColor(saveColor);
			}
		}

		abstract void doDraw(Graphics2D g);
	}
	
	private static class CompoundObject extends SceneGraphNode {
		ArrayList<SceneGraphNode> subobjects = new ArrayList<SceneGraphNode>();

		CompoundObject add(SceneGraphNode node) {
			subobjects.add(node);
			return this;
		}

		void doDraw(Graphics2D g) {
			for (SceneGraphNode node : subobjects)
				node.draw(g);
		}
	}

	private static class TransformedObject extends SceneGraphNode {
		SceneGraphNode object;
		double rotationInDegrees = 0;
		double scaleX = 1, scaleY = 1;
		double translateX = 0, translateY = 0;

		TransformedObject(SceneGraphNode object) {
			this.object = object;
		}

		TransformedObject setRotation(double degrees) {
			rotationInDegrees = degrees;
			return this;
		}

		TransformedObject setTranslation(double dx, double dy) {
			translateX = dx;
			translateY = dy;
			return this;
		}

		TransformedObject setScale(double sx, double sy) {
			scaleX = sx;
			scaleY = sy;
			return this;
		}

		void doDraw(Graphics2D g) {
			AffineTransform savedTransform = g.getTransform();
			if (translateX != 0 || translateY != 0)
				g.translate(translateX,translateY);
			if (rotationInDegrees != 0)
				g.rotate( rotationInDegrees/180.0 * Math.PI);
			if (scaleX != 1 || scaleY != 1)
				g.scale(scaleX,scaleY);
			object.draw(g);
			g.setTransform(savedTransform);
		}
	}

	private static SceneGraphNode filledRect = new SceneGraphNode() {
		void doDraw(Graphics2D g) {
			g.fill(new Rectangle2D.Double(-0.5, -0.5, 1, 1));
		}
	};

	private static SceneGraphNode filledTriangle = new SceneGraphNode() {
		void doDraw(Graphics2D g) {
			Path2D path = new Path2D.Double();
			path.moveTo(-0.5, 0);
			path.lineTo(0.5, 0);
			path.lineTo(0, 1);
			path.closePath();
			g.fill(path);
		}
	};

	private static SceneGraphNode filledThirteen = new SceneGraphNode() {
		void doDraw(Graphics2D g) {

			int n = 13;
			double t = 0, ang = (Math.PI * 2) / n;

			int[] x1 = new int[n];
			int[] y1 = new int[n];

			for (int i = 0; i < n; i++) {
				x1[i] = (int) (350 * Math.sin(t));
				y1[i] = (int) (350 * Math.cos(t));
				t += ang;
			}

			Polygon polygon = new Polygon(x1, y1, n);
			g.setStroke(new BasicStroke(4));
			g.scale(0.00475, 0.00475);

			for (int i = 0; i < n; i++) {
				g.drawLine(x1[i], y1[i], 0, 0);
			}

			g.draw(polygon);

		}
	};

	private JPanel display;

	public SceneGraph() {
		display = new JPanel() {

			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				applyLimits(g2, X_LEFT, X_RIGHT, Y_TOP, Y_BOTTOM, false);
				g2.setStroke( new BasicStroke(pixelSize) );
				world.draw(g2);
			}
		};

		display.setPreferredSize( new Dimension(WIDTH,HEIGHT));
		display.setBackground( BACKGROUND );
		final Timer timer = new Timer(17,new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateFrame();
				repaint();
			}
		});

		final JCheckBox animationCheck = new JCheckBox("Run Animation");
		animationCheck.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (animationCheck.isSelected()) {
					if ( ! timer.isRunning() )
						timer.start();
				}
				else {
					if ( timer.isRunning() )
						timer.stop();
				}
			}
		});

		JPanel top = new JPanel();
		top.add(animationCheck);
		setLayout(new BorderLayout(5,5));
		setBackground(Color.DARK_GRAY);
		setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY,4) );
		add(top,BorderLayout.NORTH);
		add(display,BorderLayout.CENTER);
		createWorld();
	}

	private void applyLimits(Graphics2D g2, double xleft, double xright, double ytop, double ybottom,
			boolean preserveAspect) {
		int width = display.getWidth();
		int height = display.getHeight();
		if (preserveAspect) {
			double displayAspect = Math.abs((double)height / width);
			double requestedAspect = Math.abs(( ybottom-ytop ) / ( xright-xleft ));
			if (displayAspect > requestedAspect) {
				double excess = (ybottom-ytop) * (displayAspect/requestedAspect - 1);
				ybottom += excess/2;
				ytop -= excess/2;
			}
			else if (displayAspect < requestedAspect) {
				double excess = (xright-xleft) * (requestedAspect/displayAspect - 1);
				xright += excess/2;
				xleft -= excess/2;
			}
		}

		double pixelWidth = Math.abs(( xright - xleft ) / width);
		double pixelHeight = Math.abs(( ybottom - ytop ) / height);
		pixelSize = (float)Math.min(pixelWidth,pixelHeight);
		g2.scale( width / (xright-xleft), height / (ybottom-ytop) );
		g2.translate( -xleft, -ytop );
	}
}