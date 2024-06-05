package server;


import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;


public class ServerEchoExample {
    public static void main( String[] args ) {
        new ServerEchoExample().run();
    }


    private static <T> T getBody( Request request, Class<T> clazz ) {
        var body = new Gson().fromJson( request.body(), clazz );
        if ( body == null ) {
            throw new RuntimeException( "missing required body" );
        }
        return body;
    }


    private void run() {
        Spark.port( 8080 );
        Spark.post( "/echo", this::echoBody );
    }


    private Object echoBody( Request req, Response res ) {
        var bodyObj = getBody( req, Map.class );

        res.type( "application/json" );
        return new Gson().toJson( bodyObj );
    }
}