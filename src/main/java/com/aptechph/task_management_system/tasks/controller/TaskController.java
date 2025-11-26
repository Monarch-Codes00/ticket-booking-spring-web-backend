package com.aptechph.task_management_system.tasks.controller;

import com.aptechph.task_management_system.tasks.dto.TaskRequestDto;
import com.aptechph.task_management_system.tasks.dto.TaskResponseDto;
import com.aptechph.task_management_system.tasks.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }





    // Removed: Task management controller not needed for ticket booking system
// TaskController removed: task management code deprecated for ticket booking backend
// Placeholder comment to avoid compile issues
}
