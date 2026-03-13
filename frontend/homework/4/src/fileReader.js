const fs = require('node:fs');

async function readEmployeeData(filePath) {
    try{
        const fileContents = await fs.promises.readFile(filePath, 'utf-8');

        const data = JSON.parse(fileContents);

        if(!Array.isArray(data)){
            throw new TypeError('Data is not an array');
        }

        return data;

    }catch(error){
        console.error('Error reading or parsing file:', error);
        throw error;
    }
}

module.exports = { readEmployeeData };

