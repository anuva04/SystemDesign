package org.anuva04.Strategies;

import org.anuva04.Enums.Denomination;
import org.anuva04.Models.CashAvailabilityDataResponseModel;
import org.anuva04.Models.CashReserve;
import org.anuva04.Models.Currency;

import java.util.Map;

// Works for denomination [50, 100, 500, 1000, 2000]
public class SimpleCashDispenseStrategy implements CashDispenserStrategy {
    @Override
    public CashReserve dispenseCash(CashReserve cashReserve, CashReserve cashRequest) {
        cashRequest.getCashReserveMap().forEach((key, value) -> {
            cashReserve.getCashReserveMap().put(key, cashReserve.getCashReserveMap().get(key) - value);
        });
        return cashRequest;
    }

    @Override
    public CashAvailabilityDataResponseModel getCashAvailability(CashReserve cashReserve, int amount) {
        boolean isCashAvailable = false;
        CashReserve result = new CashReserve();

        for (Map.Entry<Denomination, Integer> entry : cashReserve.getCashReserveMap().entrySet()) {
            Denomination denomination = entry.getKey();
            Integer count = entry.getValue();

            while (count > 0 && amount > 0 && Currency.getValue(denomination) <= amount) {
                amount -= Currency.getValue(denomination);
                count -= 1;
                if (result.getCashReserveMap().containsKey(denomination)) {
                    result.getCashReserveMap().put(denomination, result.getCashReserveMap().get(denomination) + 1);
                } else {
                    result.getCashReserveMap().put(denomination, 1);
                }
            }
        }

        if (amount == 0) isCashAvailable = true;

        return new CashAvailabilityDataResponseModel(isCashAvailable, result);
    }
}
