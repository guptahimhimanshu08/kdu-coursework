const { writeReport } = require("./reportGenerator");

async function generateTopEarnersReport(employees, count, outputPath) {

    let reportContent = `Top ${count} Earners Report\n`;
    
    reportContent += "********************\n";

    
    let sortedEmployees = sortBySalary(employees);
    const topEarners = sortedEmployees.slice(0, count);

    topEarners.forEach((emp, index) => {
        reportContent += `${index + 1}. ${emp.name} - ${emp.department} - $${emp.salary}\n`;
    });

    await writeReport(outputPath, reportContent);
    
}

function sortBySalary(employees){
    let sortedEmployees = employees.sort((a, b) => b.salary - a.salary);
    return sortedEmployees;
}

module.exports = { generateTopEarnersReport };