const { writeReport } = require("./reportGenerator");

async function generateDepartmentReport(employees, department, outputPath){
    
    
    let reportContent = `Department Report for: ${department}\n`;
    reportContent += "********************\n";

    const deptEmployees = employees.filter(emp => emp.department === department);
    reportContent += `Total Employees in ${department}: ${deptEmployees.length}\n\n`;

    deptEmployees.forEach((emp, index) => {
        reportContent += `${index + 1}. ${emp.name} - $${emp.salary}\n`;
    });


    let totalDeptSalary = 0;
    deptEmployees.forEach(emp => {
        totalDeptSalary += emp.salary;
    });
    
    reportContent += `Total Salary in ${department}: $${totalDeptSalary}\n`;

    const avgDeptSalary = (totalDeptSalary / deptEmployees.length);
    reportContent += `Average Salary in ${department}: $${avgDeptSalary.toFixed(2)}\n\n`;

    
    await writeReport(outputPath, reportContent);

}

module.exports = { generateDepartmentReport }; 