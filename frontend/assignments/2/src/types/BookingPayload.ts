export type BookingPayload = {
  acceptedTerms: boolean;
  selection: {
    cleaningTypeId: string;
    frequencyId: string;
    bedrooms: number;
    bathrooms: number;
    hours: number;
    date: string;     
    startTime: string;
    extras?: string[];
    specialInstructions?: string;
  };
  customer: {
    email: string;
    phone: string;
    address: string;
    zip: string;
  };
  payment: {
    cardNumber: string;
    expiry: string;
    cvv: string;
    nameOnCard: string;
  };
};