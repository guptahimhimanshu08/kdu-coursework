import type { BookingPayload } from "../types/BookingPayload";
import type { ConfigConstraints } from "../types/Config";

export interface ValidationResult {
  ok: boolean;
  missing?: string[];
  message?: string;
}

function validateSelection(payload: BookingPayload, missing: string[]): void {
  if (!payload?.selection?.cleaningTypeId) missing.push("Cleaning Type");
  if (!payload?.selection?.frequencyId) missing.push("Cleaning Frequency");
  if (typeof payload?.selection?.bedrooms !== "number") missing.push("Number of Bedrooms");
  if (typeof payload?.selection?.bathrooms !== "number") missing.push("Number of Bathrooms");
  if (typeof payload?.selection?.hours !== "number") missing.push("Hours");
  if (!payload?.selection?.date) missing.push("Date");
  if (!payload?.selection?.startTime) missing.push("Start Time");
}

function validateCustomer(payload: BookingPayload, missing: string[]): void {
  if (!payload?.customer?.email) missing.push("Email");
  if (!payload?.customer?.phone) missing.push("Phone");
  if (!payload?.customer?.address) missing.push("Address");
  if (!payload?.customer?.zip) missing.push("ZIP Code");
}

function validatePayment(payload: BookingPayload, missing: string[]): void {
  if (!payload?.payment?.cardNumber) missing.push("Card Number");
  if (!payload?.payment?.expiry) missing.push("Card Expiry");
  if (!payload?.payment?.cvv) missing.push("CVV");
  if (!payload?.payment?.nameOnCard) missing.push("Name on Card");
}

export function validateBooking(payload: BookingPayload, constraints?: ConfigConstraints): ValidationResult {
  const missing: string[] = [];

  validateSelection(payload, missing);
  validateCustomer(payload, missing);
  validatePayment(payload, missing);

  if (payload?.acceptedTerms !== true) missing.push("Terms and Conditions");

  if (missing.length) return { ok: false, missing };
  
  // Validate hours constraints
  const minHours = constraints?.minHours ?? 2;
  const maxHours = constraints?.maxHours ?? 12;
  if (payload.selection.hours < minHours) {
    return { ok: false, missing: [], message: `Hours must be at least ${minHours}` };
  }
  if (payload.selection.hours > maxHours) {
    return { ok: false, missing: [], message: `Hours cannot exceed ${maxHours}` };
  }
  
  // Validate bedrooms constraints
  const minBedrooms = constraints?.minBedrooms ?? 0;
  const maxBedrooms = constraints?.maxBedrooms ?? 10;
  if (payload.selection.bedrooms < minBedrooms) {
    return { ok: false, missing: [], message: `Bedrooms must be at least ${minBedrooms}` };
  }
  if (payload.selection.bedrooms > maxBedrooms) {
    return { ok: false, missing: [], message: `Bedrooms cannot exceed ${maxBedrooms}` };
  }
  
  // Validate bathrooms constraints
  const minBathrooms = constraints?.minBathrooms ?? 0;
  const maxBathrooms = constraints?.maxBathrooms ?? 10;
  if (payload.selection.bathrooms < minBathrooms) {
    return { ok: false, missing: [], message: `Bathrooms must be at least ${minBathrooms}` };
  }
  if (payload.selection.bathrooms > maxBathrooms) {
    return { ok: false, missing: [], message: `Bathrooms cannot exceed ${maxBathrooms}` };
  }

  return { ok: true };
}
