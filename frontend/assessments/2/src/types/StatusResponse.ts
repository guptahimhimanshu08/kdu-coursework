export interface StatusResponse {
  registrationId: string;
  name: string;
  email: string;
  eventName: string;
  status: "SUCCESSFULL" | "FAILED" | "QUEUED";
}
