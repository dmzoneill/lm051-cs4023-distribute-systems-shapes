# CS4023 Distributed Systems Shapes

<p align="center">
  <img src=".github/logo.png" alt="lm051-cs4023-distribute-systems-shapes logo" width="128" />
</p>

Java assignments for the CS4023 module, from the LM051 Computer Systems degree
at the University of Limerick. They draw shapes and send them between programs
over sockets.

## What it does

- A Swing drawing canvas where you click to draw lines, rectangles, ovals and
  triangles, choose colours, draw random shapes or clear the canvas.
- A shapes server window that listens on a TCP socket and draws the shapes sent
  to it by the client.

## Tech

- Java with Swing and `java.net` sockets

## Running

Import the Eclipse project, start the server class first, then the client.

## License

Licensed under the Apache License 2.0. See [LICENSE](LICENSE).
