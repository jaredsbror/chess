package model;

import java.util.ArrayList;

public record ListResult(boolean success, String message, ArrayList<GameData> gameDataArrayList) {
}
