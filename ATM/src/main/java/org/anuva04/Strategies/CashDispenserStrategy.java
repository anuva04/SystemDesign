package org.anuva04.Strategies;

import org.anuva04.Models.CashAvailabilityDataResponseModel;
import org.anuva04.Models.CashReserve;

import java.util.HashMap;

public interface CashDispenserStrategy {
    CashReserve dispenseCash(CashReserve cashReserve, CashReserve cashRequest);

    CashAvailabilityDataResponseModel getCashAvailability(CashReserve cashReserve, int amount);
}
