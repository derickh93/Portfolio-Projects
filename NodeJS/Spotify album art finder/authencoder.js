const client_id = "d7ef219f9f9643ad9ab7f8bf442887a2";
const client_secret = "03dde93b880d49f49404650a66a15754";
let base64data = Buffer.from(`${client_id}:${client_secret}`).toString('base64');
console.log(`Basic ${base64data}`);