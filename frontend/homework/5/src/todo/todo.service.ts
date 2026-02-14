import { BadRequestException, Injectable, NotFoundException } from '@nestjs/common';
import { Task } from './interface/todo.interface';
import { UUID, randomUUID } from 'node:crypto';
import { CreateTodoDto } from './dto/create-todo.dto';
import { UpdateTodoDto } from './dto/update-todo.dto';

@Injectable()
export class TodoService {
    private tasks: Task[] = [];

   

    findAllTasks():Task[]{
        return this.tasks;
    }

    findOneTask(id: UUID): Task {
        const task = this.tasks.find(task => task.id === id);

        if (!task) {
            throw new NotFoundException(`Task with ID ${id} not found`);
        }
        return task;            
    }

    createTask(createTodoDto: CreateTodoDto): Task {

        const newTask: Task = {
            id: randomUUID(),
            ...createTodoDto,
            completed: false,
            createdAt: new Date(),
            updatedAt: new Date(),
        }

        this.tasks.push(newTask);
        return newTask;
    }

    updateTask(id: UUID, updateTodoDto: UpdateTodoDto): Task {
        const task = this.findOneTask(id);

        if (updateTodoDto.title === undefined && updateTodoDto.description === undefined && updateTodoDto.completed === undefined) {
            throw new BadRequestException('At least one field required');
        }

        if (updateTodoDto.title !== undefined) {
            task.title = updateTodoDto.title;
        }
        
        if (updateTodoDto.description !== undefined) {
            task.description = updateTodoDto.description;
        }

        if (updateTodoDto.completed !== undefined) {
            task.completed = updateTodoDto.completed;
        }
        
        task.updatedAt = new Date();
        
        return task;
    }

    deleteTask(id: UUID): string{
        
        const removedTask = this.findOneTask(id);

        this.tasks = this.tasks.filter(tasks => tasks.id !== removedTask.id);

        return `Task with ID ${id} has been removed.`;
    }

}
