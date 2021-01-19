package com.kaimanden.betyou.tools.interfaces;

import com.kaimanden.betyou.tools.models.Room;

import java.util.List;

public interface ChatListener {
    void list(List<Room> rooms);
    void ko(String error);
}
