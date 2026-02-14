import { IsBoolean, IsOptional, IsString, Length, MaxLength } from 'class-validator';

export class UpdateTodoDto {
    
    @IsOptional()
    @IsString()
    @Length(3, 100)
    title?: string;

    @IsOptional()
    @IsString()
    @MaxLength(500)
    description?: string;

    @IsOptional()
    @IsBoolean()
    completed?: boolean;
}
