import type { BaseResponse } from "./BaseResponse";

export interface RegistrationSuccessResponse extends BaseResponse {
  status: string,
  message: string
};