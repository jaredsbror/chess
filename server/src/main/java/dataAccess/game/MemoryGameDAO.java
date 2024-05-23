package dataAccess.game;

import dataAccess.GameDAO;
import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    Map<String, GameData> list = new HashMap<>();

    public void clear() {
        list.clear();
    }
}
