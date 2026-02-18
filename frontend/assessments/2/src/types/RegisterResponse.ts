import type { RegistrationFailedResponse } from "./RegistrationFailedResponse";
import type { RegistrationSuccessResponse } from "./RegistrationSuccessResponse";

export type RegisterResponse = RegistrationSuccessResponse | RegistrationFailedResponse;