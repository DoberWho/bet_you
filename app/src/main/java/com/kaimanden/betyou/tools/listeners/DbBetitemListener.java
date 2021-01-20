package com.kaimanden.betyou.tools.listeners;

import com.kaimanden.betyou.tools.models.BetItem;

import java.util.List;

public interface DbBetitemListener {
    void isOk(List<BetItem> items);
    void isKo(String error);
}
