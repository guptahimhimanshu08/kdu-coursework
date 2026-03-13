

class Employee{

    constructor(id, name , age, salary, department, skills, experience){
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.skills = skills;
        this.experience = experience;
    }

    getFullInfo(){
        return `
        Employee ${this.name}, with id: ${this.id} and age: ${this.age} 
        is earning a salary of ${this.salary} and working in the department ${this.department}.
        The employee is skilled in: ${this.skills.toString()} 
        with professional experience of ${this.experience}`
    }
}

// Task 1.1
const emp1 = new Employee(1, "Alice Johnson", 30, 85000, "Engineering", ["JavaScript",  "React",  "Node.js"], 5);
const emp2 = new Employee(2, "Bob Smith", 45, 95000, "Marketing", ["SEO",  "Content Marketing",  "Social Media"], 20);
const emp3 = new Employee(3, "Charlie Brown", 28, 70000, "Design", ["Photoshop",  "Illustrator",  "Figma"], 3);
const emp4 = new Employee(4, "Diana Prince", 32, 88000, "Product Management", ["Agile",  "Scrum",  "Roadmapping"], 7);
const emp5 = new Employee(5, "Eve Davis", 35, 90000, "Engineering", ["Python",  "Django",  "Machine Learning", "React"], 10);

// Task 1.2
function getEmployeeInfo(employee) {
    return `Employee ${employee.name} works in the ${employee.department} department with a salary of $${employee.salary}.`;
}

function addSkill(employee, skill){
    employee.skills.push(skill);
    return employee.skills;
}

// Task 1.3
function hasMoreSkills(employee){
    return `employee ${employee.name} has more skills`;
}

function compareEmployees(empA, empB){
    if(empA.salary > empB.salary){
        return hasMoreSkills(empA);
    }
    else{
        return hasMoreSkills(empB);
    }
}

console.log(compareEmployees(emp1, emp2));

console.log(emp1.getFullInfo());

console.log(addSkill(emp3, "Sketch"));

//  Task 2.1
const employees = [emp1, emp2, emp3, emp4, emp5];

console.log(
    employees.map((key, value) => getEmployeeInfo(key))
)

// Task 2.2
function filterByExperience(employees, minExperience){
    return employees.filter((employee)=> employee.experience >= minExperience)
}

console.log(filterByExperience(employees, 5));

// Task 2.3
const summaries = [];

function getSummary(employees){
    employees.forEach((e) => {
        summaries.push(`${e.name} (${e.department}) – $${e.salary}`)
    })
}

getSummary(employees);
console.log(summaries);


// Task 2.4
function avgSalary(employees){
    let totalSalary = 0;
    totalSalary = employees.reduce((sum, employee) => sum + employee.salary, 0);
    return totalSalary / employees.length;
}

console.log(avgSalary(employees));

function departmentWiseEmployeeCount(){
    return employees.reduce((acc, curr) => {
        if(acc[curr.department]){
            acc[curr.department] += 1;
        }
        else{
            acc[curr.department] = 1;
        }
        return acc;
    }, {});
}

console.log(departmentWiseEmployeeCount());

// Task 2.5
function findHighestPaidEmployee(employees){
    let sortedEmployees = employees.sort((a, b) => b.salary - a.salary);
    return sortedEmployees[0];    
}

console.log(findHighestPaidEmployee(employees));

function sortByExperience(employees){
    let sortedEmployees = employees.sort((a, b) => b.experience - a.experience);
    return sortedEmployees;    
}
console.log(sortByExperience(employees));


// Task 3.1
function extractDetails(employee){
    const {id, name, department} = employee;
    return {id, name, department};
}
console.log(extractDetails(emp1));

// Task 3.2
function sortBySalary(employees){
    let sortedEmployees = employees.sort((a, b) => b.salary - a.salary);
    return sortedEmployees    ;
}

function getTopPaidEmployees(employees, topN){
    employees = sortBySalary(employees);
    return employees.slice(0, topN);
}
console.log(getTopPaidEmployees(employees, 3));

function getBottomPaidEmployees(employees, topN){
    employees = sortBySalary(employees);
    return employees.slice(employees.length - topN);
}
console.log(getBottomPaidEmployees(employees, 2));

// Task 3.3
function mergeSkills(empA, empB){
    const mergedSkills = [...empA.skills, ...empB.skills];
    return [...new Set(mergedSkills)];
}
console.log(mergeSkills(emp1, emp5));


// Task 3.4
function evaluate(...employee){
    const totalEmployees = employee.length;
    const avergaeAge = employee.reduce((sum, emp) => sum + emp.age, 0) / totalEmployees;

    return {totalEmployees, avergaeAge};
}
console.log(evaluate(emp1, emp2, emp3, emp4));

//  Task 4.1
 function getAnalytics(employees){
    return employees.reduce((skillFreqObj, emp) => {
        emp.skills.forEach(skill => {
            if(skillFreqObj[skill]){
                skillFreqObj[skill] += 1;
            }
            else{
                skillFreqObj[skill] = 1;
            }
        });
        return skillFreqObj;
    }, {});
 }
console.log(getAnalytics(employees));