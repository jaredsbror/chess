package model.custom;

import model.original.GameData;

import java.util.ArrayList;

public record ListResult(Boolean success, String message, ArrayList<GameData> games) {
}
