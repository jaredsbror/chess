package datatypes;


import websocket.messages.ServerMessage;

// Observing messages received from the server
public interface ServerMessageObserver {

    void notify( ServerMessage serverMessage ) throws Exception;
}
