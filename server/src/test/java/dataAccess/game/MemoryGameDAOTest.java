package dataAccess.game;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoryGameDAOTest {

    @Test
    void clear() {
        Map<String, GameData> list = new HashMap<>();
        list.put("key", new GameData(0, "whiteUsername", "blackUsername", "game", new ChessGame()));
        list.clear();
        assertEquals(list, new HashMap<>());
        System.out.println(list);
    }

    @Test
    void notClear() {
        Map<String, GameData> list = new HashMap<>();
        list.clear();
        list.put("key", new GameData(0, "whiteUsername", "blackUsername", "game", new ChessGame()));
        assertNotEquals(list, new HashMap<>());
        System.out.println(list);
    }
}