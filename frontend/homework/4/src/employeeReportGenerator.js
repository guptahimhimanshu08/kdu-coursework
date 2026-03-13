const path = require("node:path");
const { readEmployeeData } = require("./fileReader");
const { writeReport } = require("./reportGenerator");

function departmentWiseEmployeeCount(employees) {
    return employees.reduce((count, currEmp) => {
        if(count[currEmp.department]){
            count[currEmp.department] += 1;
        }
        else{
            count[currEmp.department] = 1;
        }
        return count;
    }, {});
}

function departmentWiseTotalSalary(employees) {
    return employees.reduce((total, currEmp) => {
        if(total[currEmp.department]){
            total[currEmp.department] += currEmp.salary;
        }
        else{
            total[currEmp.department] = currEmp.salary;
        }
        return total;
    }, {});
}

async function generateSummaryReport() {
    const __dirname = path.resolve();
    try {
        const dataFilePath = path.join(__dirname, "./data.json");
        const reportFilePath = path.join(
        __dirname,
        "./reports/employee-report.txt"
        );

        const employees = await readEmployeeData(dataFilePath);

        let reportContent = "Employee Report\n";
        reportContent += "********************\n";
        reportContent += `Total Employees: ${employees.length}\n\n`;

        let totalCompanySalary = 0;

        employees.forEach(emp => {
            totalCompanySalary += emp.salary;
        })

        employees.forEach((emp, index) => {
        reportContent += `${index + 1}. ${emp.name} - ${emp.age} years\n`;
        });
        
        reportContent += `\nTotal Company Salary: $${totalCompanySalary}\n`;

        const avgSalary = (totalCompanySalary / employees.length);

        reportContent += `\nAverage Salary: $${avgSalary.toFixed(2)}\n`;

        const departmentCounts = departmentWiseEmployeeCount(employees);
        reportContent += `\nDepartment-wise Employee Count:\n`;
        for(const [dept, count] of Object.entries(departmentCounts)){
            reportContent += `- ${dept}: ${count}\n`;
        }

        const departmentSalaries = departmentWiseTotalSalary(employees);
        reportContent += `\nDepartment-wise Total Salary:\n`;
        for(const [dept, totalSalary] of Object.entries(departmentSalaries)){
            reportContent += `- ${dept}: $${totalSalary}\n`;
        }

        const departmentWiseAvgSalary = {};
        for(const [dept, totalSalary] of Object.entries(departmentSalaries)){
            departmentWiseAvgSalary[dept] = (totalSalary / departmentCounts[dept]).toFixed(2);
        }
        reportContent += `\nDepartment-wise Average Salary:\n`;
        for(const [dept, avgSalary] of Object.entries(departmentWiseAvgSalary)){
            reportContent += `- ${dept}: $${avgSalary}\n`;
        }

        await writeReport(reportFilePath, reportContent);

        console.log("Report generated successfully");
    } catch (error) {
        console.error("Error generating report:", error.message);
    }
}

module.exports = { generateSummaryReport };