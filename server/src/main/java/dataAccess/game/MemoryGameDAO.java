package dataAccess.game;

import dataAccess.GameDAO;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    Map<String, UserData> list = new HashMap<>();

    public void clear() {
        list.clear();
    }
}
