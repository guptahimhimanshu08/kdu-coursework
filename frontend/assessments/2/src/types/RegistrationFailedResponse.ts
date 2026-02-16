import type { BaseResponse } from "./BaseResponse";

export interface RegistrationFailedResponse extends BaseResponse {
  status: "FAILED";
  message: string;
  errors: string[];
}