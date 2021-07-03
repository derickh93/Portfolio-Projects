/*
=======================
05-batch-b.js
=======================
Student ID:
Comment (Required):

=======================
*/
const fs = require("fs");
const input_dir = "./input/";
const output_dir = "./output/";
const input_files = fs.readdirSync(input_dir);

let file_data = [];
const batch_files = 5;
let files_read = 0;

readFiles();

function readFiles() {
	for(let i = 0; i <input_files.length;i++){
		let path = `${input_dir}${input_files[i]}`;
		fs.readFile(path,"utf8",function after_read(err,data) {
			if(err){
				console.log(data);
			}
			else{
				file_data.push(data);
				file_read++;
				if(files_read % batch_files === 0 || files_read === input_files.length) {
					writeFile(Math.ceil(files_read / batch_files));
				}
			}
		});
	}
}

const writeFile = function(file_number){
	let file_name = `$(String(file_number).padStart(2,'0')}-output.txt;
	let data = file_data.slice((file_number - 1) * batch_files,file_number * batch_files + 1);
	fs.writeFile(`${output_dir}${file_name}`,data.join("\r\n"),"utf8",function after_write(err) {
		console.log(`File ${file_name} Written!`);
	});
}
