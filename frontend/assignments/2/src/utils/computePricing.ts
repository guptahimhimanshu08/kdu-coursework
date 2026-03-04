import type { UIConfig } from "../types/Config";
import type { BookingPayload } from "../types/BookingPayload";

export function computePricing(config: UIConfig, payload: BookingPayload) {
  const sel = payload.selection;

  const ct = config.cleaningTypes.find((x) => x.id === sel.cleaningTypeId);
  if (!ct) throw new Error(`Invalid cleaningTypeId: ${sel.cleaningTypeId}`);

  const freq = config.cleaningFrequency.find((x) => x.id === sel.frequencyId);
  if (!freq) throw new Error(`Invalid frequencyId: ${sel.frequencyId}`);

  const extrasSelected = Array.isArray(sel.extras) ? sel.extras : [];
  const extrasResolved = extrasSelected.map((extraId: string) => {
    const ex = config.extraOptions.find((x) => x.id === extraId);
    if (!ex) throw new Error(`Invalid extra option id: ${extraId}`);
    return ex;
  });

  const basePrice = Number(ct.basePrice ?? 0);
  const extrasTotal = extrasResolved.reduce((sum: number, e) => sum + Number(e.price ?? 0), 0);
  const multiplier = Number(freq.multiplier ?? 1);
  const total = Math.round((basePrice + extrasTotal) * multiplier);

  return {
    basePrice,
    extrasTotal,
    multiplier,
    total,
    cleaningType: { id: ct.id, name: ct.name },
    frequency: { id: freq.id, name: freq.name },
    extras: extrasResolved.map((e) => ({ id: e.id, name: e.name, price: e.price })),
  };
}