package org.anuva04.Models;

import org.anuva04.Enums.Denomination;

import java.util.HashMap;

public class CashReserve {
    private HashMap<Denomination, Integer> cashReserveMap;

    public void modifyCashReserve(CashReserve newCashReserve) {
        newCashReserve.cashReserveMap.forEach((currency, count) -> {
            this.cashReserveMap.put(currency, this.cashReserveMap.get(currency) + count);
        });
    }

    public CashReserve() {
        cashReserveMap = new HashMap<>();
        for (Denomination denomination : Denomination.values()) {
            cashReserveMap.put(denomination, 0);
        }
    }

    public HashMap<Denomination, Integer> getCashReserveMap() {
        return cashReserveMap;
    }

    public void updateCash(Denomination denomination, Integer count) {
        cashReserveMap.put(denomination, count);
    }
}
