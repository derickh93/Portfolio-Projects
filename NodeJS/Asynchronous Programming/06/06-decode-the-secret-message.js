/*
=======================
06-decode-the-secret-message.js
=======================
Student ID:15209569
Comment (Required):

=======================
*/
const fs = require("fs");
const zlib = require("zlib");
const files_dir = "./input/";
const files = fs.readdirSync(files_dir);
const output = "./output/secret.zip";

let reserved_space = Array(files.length);
let inflated = 0;
let n = files.length;

for(let i = 0; i < n;i++) {
	fs.readFile(`${files_dir}${files[i]}`,null,function after_read(err,compressed_data_buffer) {
		zlib.inflate(compressed_data_buffer,function after_decompressed(err,binary_data_buffer) {
			inflated++;
			reserved_space[i] = binary_data_buffer;
			if(inflated === files.length) {
				fs.writeFile(output,Buffer.concat(reserved_space),function after_write(err) {
					console.log(`${output} created`);
				});
			}
		});
	});
}