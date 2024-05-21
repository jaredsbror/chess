package server;

import spark.Spark;

public class Server {

    // Run the server
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    // Stop the server
    public void stop() {
        Spark.stop();
    }
}