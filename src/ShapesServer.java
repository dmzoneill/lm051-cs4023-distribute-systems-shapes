import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputFilter.Config;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/** Receives serialized shapes from a client over a socket. */
public final class ShapesServer {
  /** TCP port the server listens on. */
  private static final int PORT = 56000;

  /** Maximum queued incoming connections. */
  private static final int BACKLOG = 10;

  /** Allow-list for network deserialization; all else is rejected. */
  private static final String SHAPES_FILTER =
      "Shape;Line;Oval;Rectangle;java.awt.Color;java.awt.color.*;"
          + "java.lang.*;java.util.*;maxdepth=20;!*";

  /** Listening socket. */
  private ServerSocket listenerSocket;

  /** Accepted client connection. */
  private Socket connection = null;

  /** Stream of objects from the client. */
  private ObjectInputStream in;

  /** Received shapes. */
  private ArrayList<ObjectOutputStream> shapesSerialized;

  /**
   * Accept one client and read shapes until it sends null.
   *
   * @throws IOException on socket errors
   * @throws ClassNotFoundException if a received class is unknown
   */
  public void listen() throws IOException, ClassNotFoundException {
    this.listenerSocket = new ServerSocket(PORT, BACKLOG);
    this.connection = this.listenerSocket.accept();
    String host = this.connection.getInetAddress().getHostName();
    System.out.println("Connection received from " + host);
    this.in = new ShapesInputStream(this.connection.getInputStream());
    // Reject anything but shapes to prevent deserialization gadget attacks.
    ObjectInputFilter filter = Config.createFilter(SHAPES_FILTER);
    this.in.setObjectInputFilter(filter);

    do {
      try {
        this.shapesSerialized.add((ObjectOutputStream) in.readObject());

      } catch (ClassNotFoundException classnot) {
        System.err.println("????");
      }
    } while (in.readObject() != null);
  }

  /** Object stream that only resolves allow-listed shape classes. */
  private static final class ShapesInputStream extends ObjectInputStream {
    /**
     * Wrap a raw stream.
     *
     * @param stream raw socket input
     * @throws IOException on stream header errors
     */
    ShapesInputStream(final InputStream stream) throws IOException {
      super(stream);
    }

    @Override
    protected Class<?> resolveClass(final ObjectStreamClass desc)
        throws IOException, ClassNotFoundException {
      String name = desc.getName();
      boolean allowed =
          name.equals("Shape")
              || name.equals("Line")
              || name.equals("Oval")
              || name.equals("Rectangle")
              || name.startsWith("java.awt.Color")
              || name.startsWith("java.awt.color.")
              || name.startsWith("java.lang.")
              || name.startsWith("java.util.");
      if (!allowed) {
        throw new InvalidClassException("Unauthorized class", name);
      }
      return super.resolveClass(desc);
    }
  }
}
