import { IsNotEmpty, IsOptional, IsString, Length, MaxLength } from 'class-validator';
export class CreateTodoDto {
   
    @IsString()
    @IsNotEmpty()
    @Length(3, 100)
    title: string;

    @IsOptional()
    @IsString()
    @MaxLength(500)
    description?: string;
}