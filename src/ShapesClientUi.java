import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;

/** Client window: draws random shapes and sends them to the shapes server. */
public final class ShapesClientUi extends JFrame {
  /** Serialisation id. */
  private static final long serialVersionUID = 1L;

  /** Canvas width and height in pixels. */
  private static final Dimension CANVAS_SIZE = new Dimension(750, 500);

  /** Initial window position (both axes). */
  private static final int ORIGIN = 10;

  /** Initial window width. */
  private static final int WIDTH = 800;

  /** Initial window height. */
  private static final int HEIGHT = 600;

  /** Grid columns spanned by the canvas. */
  private static final int CANVAS_COLUMNS = 7;

  /** Padding around every component. */
  private static final int PADDING = 5;

  /** Minimum number of random shapes. */
  private static final int MIN_SHAPES = 10;

  /** Extra random shapes on top of the minimum. */
  private static final int EXTRA_SHAPES = 5;

  /** Number of shape kinds (line, rectangle, oval). */
  private static final int SHAPE_KINDS = 3;

  /** Exclusive upper bound for a colour component. */
  private static final int COLOUR_RANGE = 255;

  /** Whether debug messages are printed. */
  private final boolean debug = true;

  /** The drawing canvas. */
  private Drawpanel drawArea;

  /** Layout constraints reused while building the window. */
  private GridBagConstraints constraint;

  /** Random source for shapes. */
  private final Random rand;

  /** Click listener shared by the canvas and buttons. */
  private final MouseAdapter clicks = new ClickHandler();

  /** Shapes drawn when the window opens. */
  private Shape[] shapes;

  /** Builds the window and draws an initial set of random shapes. */
  public ShapesClientUi() {
    this.setTitle("Client");
    this.rand = new Random();
    this.init();
    this.createRandomShapes();
  }

  /**
   * Random number in [0, max).
   *
   * @param max exclusive upper bound
   * @return the number
   */
  private int getRNum(final int max) {
    return this.rand.nextInt(max);
  }

  /**
   * A shape of the given kind with random corners and fill.
   *
   * @param kind 0 for a line, 1 for a rectangle, otherwise an oval
   * @param color the shape colour
   * @return the new shape
   */
  private Shape randomShape(final int kind, final Color color) {
    int w = this.drawArea.getWidth();
    int h = this.drawArea.getHeight();
    int x1 = this.getRNum(w);
    int y1 = this.getRNum(h);
    int x2 = this.getRNum(w);
    int y2 = this.getRNum(h);
    boolean fill = this.rand.nextBoolean();
    if (kind == 0) {
      return new Line(x1, y1, x2, y2, color, fill);
    } else if (kind == 1) {
      return new Rectangle(x1, y1, x2, y2, color, fill);
    }
    return new Oval(x1, y1, x2, y2, color, fill);
  }

  /** Draws lines, rectangles and ovals in turn with random colours. */
  private void createRandomShapes() {
    this.shapes = new Shape[MIN_SHAPES + this.getRNum(EXTRA_SHAPES)];
    for (int p = 0; p < this.shapes.length; p++) {
      int r = this.getRNum(COLOUR_RANGE);
      int g = this.getRNum(COLOUR_RANGE);
      int b = this.getRNum(COLOUR_RANGE);
      this.shapes[p] = this.randomShape(p % SHAPE_KINDS, new Color(r, g, b));
      this.shapes[p].draw(this.drawArea.getGraphics());
    }
    this.drawArea.repaint();
  }

  /**
   * Handles the buttons: clear, random shapes and send.
   *
   * @param event the click
   */
  private void buttonClicked(final MouseEvent event) {
    this.debug("Mouse clicked event");

    if (!(event.getSource() instanceof JButton)) {
      return;
    }
    String command = ((JButton) event.getSource()).getActionCommand();
    if ("canvasClear".equals(command)) {
      Graphics g = this.drawArea.getGraphics();
      g.setColor(Color.white);
      g.fillRect(0, 0, getWidth(), getHeight());
      this.debug("Clear canvas button clicked");
    } else if ("randomShapes".equals(command)) {
      this.drawArea.createRandomShapes();
      this.drawArea.repaint();
    } else if ("sendShapes".equals(command)) {
      this.sendShapesToServer();
    }
  }

  /** Sends the canvas shapes to the server. */
  public void sendShapesToServer() {
    ShapesClient client = new ShapesClient();
    client.send(this.drawArea.getSerializedShapes());
  }

  /** Forwards clicks on the canvas and buttons to the window. */
  private final class ClickHandler extends MouseAdapter {
    /**
     * Handles a click.
     *
     * @param event the click
     */
    @Override
    public void mouseClicked(final MouseEvent event) {
      buttonClicked(event);
    }
  }

  /**
   * Adds a component to the grid.
   *
   * @param c the component
   * @param x grid column
   * @param y grid row
   * @param n columns spanned
   */
  private void place(final Component c, final int x, final int y, final int n) {
    this.constraint.gridx = x;
    this.constraint.gridy = y;
    this.constraint.gridwidth = n;
    this.constraint.insets = new Insets(PADDING, PADDING, PADDING, PADDING);
    this.add(c, this.constraint);
  }

  /**
   * Adds a button to the bottom row.
   *
   * @param label button text
   * @param cmd action command
   * @param col grid column
   */
  private void addButton(final String label, final String cmd, final int col) {
    JButton button = new JButton();
    button.setText(label);
    button.addMouseListener(this.clicks);
    button.setActionCommand(cmd);
    this.place(button, col, 2, 1);
  }

  /** Lays out the canvas and the buttons. */
  private void init() {
    this.setBounds(ORIGIN, ORIGIN, WIDTH, HEIGHT);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new GridBagLayout());
    this.constraint = new GridBagConstraints();
    this.constraint.fill = GridBagConstraints.BOTH;

    this.drawArea = new Drawpanel(true);
    this.drawArea.setMinimumSize(CANVAS_SIZE);
    this.drawArea.setPreferredSize(CANVAS_SIZE);
    this.drawArea.setMaximumSize(CANVAS_SIZE);
    this.drawArea.setBackground(Color.WHITE);
    this.place(this.drawArea, 0, 1, CANVAS_COLUMNS);
    this.drawArea.addMouseListener(this.clicks);

    this.addButton("Clear Canvas", "canvasClear", 0);
    this.addButton("Random Shapes", "randomShapes", 1);
    this.addButton("Send shapes to server", "sendShapes", 2);

    this.pack();
  }

  /**
   * Prints a debug message when debugging is on.
   *
   * @param message the message
   */
  private void debug(final String message) {
    if (this.debug) {
      System.out.println(message);
    }
  }
}
