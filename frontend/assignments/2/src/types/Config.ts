export type ConfigCleaningType = {
  id: string;
  name: string;
  description: string;
  basePrice: number;
};

export type ConfigFrequency = {
  id: string;
  name: string;
  multiplier: number;
};

export type ConfigRoom = {
  id: string;
  name: "Bedrooms" | "Bathrooms";
  price: number;
  icon?: string;
};

export type ConfigExtraOption = {
  id: string;
  name: string;
  price: number;
  icon?: string;
};

export type ConfigTimeslots = {
  start: string;
  name: string;
  available: boolean;
};

export type ConfigConstraints = {
  minBedrooms: number;
  maxBedrooms: number;
  minBathrooms: number;
  maxBathrooms: number;
  minHours: number;
  maxHours: number;
};

export type ConfigSummaryIcons = {
  iconType: string;
  iconDate: string;
  iconDuration: string;
  iconRepeats: string;
  iconLocation: string;
};

export type ConfigPaymentMethod = {
  id: string;
  name: string;
};

export type UIConfig = {
  cleaningTypes: ConfigCleaningType[];
  cleaningFrequency: ConfigFrequency[];
  rooms: ConfigRoom[];
  extraOptions: ConfigExtraOption[];
  timeSlots: ConfigTimeslots[];
  constraints: ConfigConstraints;
};
