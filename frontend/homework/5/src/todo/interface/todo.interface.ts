import { UUID } from "node:crypto";

export interface Task {

    id: UUID;

    title: string;

    description?: string;

    completed: boolean; 

    createdAt: Date;

    updatedAt: Date;    

}