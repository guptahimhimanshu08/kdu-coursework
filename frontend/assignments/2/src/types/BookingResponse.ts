export type BookingResponse = {
  bookingId: string;
  status: "CONFIRMED" | "PENDING";
  createdAt: string;
  pricing: { total: number };
  bookingDetails: {
    cleaningType: string | { id: string; name: string };
    frequency: string | { id: string; name: string };
    date: string;
    startTime: string;
    hours: number;
    bedrooms: number;
    bathrooms: number;
    extras: (string | { id: string; name: string; price?: number })[];
    addressLine: string;
    zip: string;
  };
};