package datatypes;


import websocket.messages.ServerMessage;

// Observing messages received from the server
public interface ServerMessageObserver {

    public void notify(ServerMessage serverMessage);
}
