/*
=======================
01-n-files-asynchronously.js
=======================
Author: Derick Hansraj
Comment (Required): This following code will create 5 files asynchronously. Each file will have Data-n in 
accordance with the file number being written. There will be a console message indicating that each file has 
been successfully written.

=======================
*/
const fs = require("fs");//import necessary modules
const n = 5;	//input size 0 < n < 100
var count = 0; //count the numbers of files already written regardless of order.

//for loop that will loop through with respect to n
for(let i = 0; i < n;i++) {
	//name of current file being written
	var fileName = `output/${i}-output.txt`;
	//writing data to the file with respect to its count/name.
	fs.writeFile(fileName, `Data ${i}`, function (err) {
		//throw an error if file cannot be written to.
	if (err) throw err;
	//log the completion of the file being created and written to.
	console.log(`${i}-output.txt has been written`);
	count++;
	//once all n files have been written indicate so on the console.
	if(count == n) {
		console.log('Writing Complete');
	}
});
}

