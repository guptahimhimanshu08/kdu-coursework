const { generateDepartmentReport } = require("./departmentReportGenerator");
const { readEmployeeData } = require("./fileReader");
const { generateSummaryReport } = require("./employeeReportGenerator");
const { generateTopEarnersReport } = require("./topEarnersReport");

const path = require("node:path");

async function run() {
  try {
    const command = process.argv[2]; 

    const dataFilePath = path.join(__dirname, "../data.json");
    const employees = await readEmployeeData(dataFilePath);

    const reportsDir = path.join(__dirname, "../reports");

    if (!command || command === "summary") {
      await generateSummaryReport();
    }

    if (!command || command === "department") {
      await generateDepartmentReport(employees, "Development", path.join(reportsDir, "development-report.txt"));
    }

    if (!command || command === "top") {
      await generateTopEarnersReport(employees, 5, path.join(reportsDir, "top-earners-report.txt"));
    }

    console.log("Report generation completed");
  } catch (error) {
    console.error("Error:", error.message);
  }
}

run();