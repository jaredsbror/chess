package model.custom;

import java.util.ArrayList;

public record ListResult(Boolean success, String message, ArrayList<GameData> games) {
}
