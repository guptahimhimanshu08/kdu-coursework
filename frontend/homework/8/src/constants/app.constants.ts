/**
 * Application-wide constants
 * Define all magic numbers and configuration values here
 */

// Price Calculation
export const PRICE_CONFIG = {
  DECIMAL_PLACES: 2,
  PERCENTAGE_DIVISOR: 100,
  MIN_DISCOUNT: 0,
} as const;

// Stock Configuration
export const STOCK_CONFIG = {
  DEFAULT_STOCK_DISPLAY: 9999,
  EMPTY_ARRAY_LENGTH: 0,
} as const;

// Navigation
export const NAVIGATION = {
  BACK_STEPS: -1,
  HOME: "/"
} as const;

// Array Indexing
export const ARRAY_OFFSET = {
  DISPLAY_INDEX: 1, 
} as const;
