import { UUID } from 'node:crypto';

export class TodoResponseDto {
    id: UUID;
    title: string;
    description?: string;
    completed: boolean;
    createdAt: Date;

    constructor(todo: any){
        this.id = todo.id;
        this.title = todo.title;
        this.description = todo.description;
        this.completed = todo.completed;
        this.createdAt = todo.createdAt;
        
    }
}
