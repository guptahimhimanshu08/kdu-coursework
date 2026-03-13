import { Body, Controller, Delete, Get, HttpCode, HttpStatus, Param, ParseUUIDPipe, Patch, Post } from '@nestjs/common';
import { TodoService } from './todo.service';
import { CreateTodoDto } from './dto/create-todo.dto';
import { UpdateTodoDto } from './dto/update-todo.dto';
import { TodoResponseDto } from './dto/todo-response.dto';
import type { UUID } from 'node:crypto';

@Controller('todos')
export class TodoController {
    constructor(private readonly todoService: TodoService){}
    
    @Get()
    @HttpCode(HttpStatus.OK)
    findAllTasks() {
        return this.todoService.findAllTasks();
    }
    
    @Post()
    @HttpCode(HttpStatus.CREATED)
    createTask(@Body() createTodoDto: CreateTodoDto): TodoResponseDto {
        const task = this.todoService.createTask(createTodoDto);
        return new TodoResponseDto(task);
    }

    
    @Get(':id')
    @HttpCode(HttpStatus.OK)
    findOneTask(@Param('id', ParseUUIDPipe) id: UUID) {
        const task = this.todoService.findOneTask(id);
        return new TodoResponseDto(task);
    }

    @Patch(':id')
    @HttpCode(HttpStatus.OK)
    updateTask(@Param('id', ParseUUIDPipe) id: UUID, @Body() updateTodoDto: UpdateTodoDto) {
        const task = this.todoService.updateTask(id, updateTodoDto);
            return new TodoResponseDto(task);
    }

    @Delete(':id')
    @HttpCode(HttpStatus.NO_CONTENT)
    deleteTask(@Param('id', ParseUUIDPipe) id: UUID) {
        return this.todoService.deleteTask(id);
    }

}
