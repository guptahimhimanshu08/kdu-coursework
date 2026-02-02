const fs = require('node:fs');
const path = require('node:path');

async function writeReport(filePath, content){
    try{
        const dirPath = path.dirname(filePath);

        await fs.promises.mkdir(dirPath, { recursive: true });
        
        await fs.promises.writeFile(filePath, content, 'utf-8');
    }
    catch(error){
        console.error('Error writing report to file:', error);
        throw error;
    }
}

module.exports = { writeReport };