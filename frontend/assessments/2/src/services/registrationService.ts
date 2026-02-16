import axios from "axios";
import type { RegistrationRequest } from "../types/RegistrationRequest";
import type { RegisterResponse } from "../types/RegisterResponse";
import type { StatusResponse } from "../types/StatusResponse";

export const postRegistration = async (eventData: RegistrationRequest): Promise<RegisterResponse> => {
    try{
        // console.log("in api call");
        
        const response = await axios.post('https://7ch6ieewz6.execute-api.ap-southeast-1.amazonaws.com/prod/register', eventData);
        return response.data;
    }catch(error){
        if(error instanceof Error){
            throw error;
        }
        throw new Error("Unknown error");
    }

}

interface ID{
    id: string
}

export const getEventById = async ({id}: ID): Promise<StatusResponse> => {
    try {
        const response = await axios.get(`https://7ch6ieewz6.execute-api.ap-southeast-1.amazonaws.com/prod/registration-status/${id}`);
        // API returns { data: { ... } }, so we need to unwrap it
        return response.data.data;
    } catch (error) {   
        if (error instanceof Error) {
            throw error;
        }
        throw new Error("Unknown error");
    }
}