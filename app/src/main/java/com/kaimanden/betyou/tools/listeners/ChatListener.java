package com.kaimanden.betyou.tools.listeners;

import com.kaimanden.betyou.tools.models.Room;

import java.util.List;

public interface ChatListener {
    void list(List<Room> rooms);
    void ko(String error);
}
