// Using constructor function pattern for object creation
function Task(title, priority){
    this.id = Date.now();
    this.title = title;
    this.priority = priority;
    this.completed = false;
    
}
// Enforce consistent priority values across all instances
Task.ALLOWED_PRIORITIES = ["Low", "Medium", "High"];

Task.prototype.getInfo = function(){
    return `Task ID: ${this.id}, Title: ${this.title}, Priority: ${this.priority}, Completed: ${this.completed}`;
}

// Prototype method to delegate markCompleted behaviour across instances
Task.prototype.markCompleted = function(){
    this.completed = true;
    return this;
}

Task.prototype.updatePriority = function(newPriority){
    
    if(!Task.ALLOWED_PRIORITIES.includes(newPriority)){
        throw new Error(`Invalid priority level "${newPriority}". Use: low, medium, high.`);    
    }
    this.priority = newPriority;
    return this; // Enable method chaining
}

// Demonstrates prototype delegation pattern
function PriorityTask(title, priority, dueDate){
    Task.call(this, title, priority);
    this.dueDate = dueDate;
}

// Object.create establishes proper prototype chain without calling Task constructor
PriorityTask.prototype = Object.create(Task.prototype);
// Restore constructor reference
PriorityTask.prototype.constructor = PriorityTask;

// Override to extend base behavior while preserving base implementation
PriorityTask.prototype.getInfo = function(){

    const baseInfo = Task.prototype.getInfo.call(this);

    if (this.dueDate === undefined) return baseInfo;

    return `${baseInfo}, Due Date: ${this.dueDate}`;
}

// Utility method on prototype to process multiple task instances
Task.prototype.getAllTasksInfo = function(tasks){

    if (!Array.isArray(tasks)) {
        throw new Error("getAllTasksInfo expects an array of tasks.");
    }

    return tasks.map((task) => {
        return task.getInfo();
    });

}

const t1 = new Task("Write notes", "Low");
const t2 = new PriorityTask("Submit assignment", "High", "2026-02-02");

t1.markCompleted().updatePriority("Medium");

console.log(t2.getInfo());                
console.log(t1.getAllTasksInfo([t1, t2]))

// Simulates async task creation using setTimeout
function createTaskAsync(title, priority){
    return new Promise((resolve, reject)=>{
        
        setTimeout(()=>{
            try{
                console.log("Task created.");
                const task = new Task(title, priority);
                resolve(task);
            }
            catch(error){
                reject(error);
            }
        }, 1000)
        console.log("Creating task...");
    })
}

// Using top-level await instead of promise chains for cleaner async code
const task = await createTaskAsync("Read book", "Medium");
console.log("Task creation promise resolved.");
  
// Demonstrates how setTimeout callbacks are queued based on delay times
function demonstrateEventLoop(){
    console.log("1");
    setTimeout(()=>{
        console.log("2");
    }, 6000)
    
    
    setTimeout(()=>{
        console.log("3");
    }, 4000)

    setTimeout(()=>{
        console.log("4");
    }, 2000)

}

demonstrateEventLoop();

// Demonstrates nested promise chains - using .then flat chaining
function createAndSaveTask(title, priority){
    createTaskAsync(title, priority)
    .then(()=>{
        console.log("Saved Task 1...");
        createTaskAsync(title, priority)
        .then((savedTask)=>{
            console.log("Task2 created and saved successfully", savedTask.getInfo());
        })
        .catch((error)=>{
            console.error("Error saving task:", error);
        }
        );
    })
    .catch((error)=>{
        console.error("Error creating task:", error);
    });
        
}

createAndSaveTask("Go shopping", "High");

// Uses Promise.all to run multiple async operations concurrently
function createMultipleTasksAsync(taskData){
    Promise.all(
        taskData.map(data => createTaskAsync(data.title, data.priority))
    ).then((results) => {
        console.log("All tasks created!", results);
    })
}
createMultipleTasksAsync([
    {title: "Task A", priority: "Low"},
    {title: "Task B", priority: "Medium"},
    {title: "Task C", priority: "High"},
]);