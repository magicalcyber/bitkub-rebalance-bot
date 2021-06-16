# BitKub Re-balance trading bot
หลักการทำงานของการเทรดแบบ re-balance คือพยายามรักษาทรัพย์สินของเราให้อยู่ในปริมาณเท่าๆ กันตามที่กำหนด เช่น THB 50% และ Bitcoin 50% 

- หากราคา Bitcoin ขึ้นเกิน 50% ก็จะทำการขายออกเพื่อรักษา Balance ให้เป็น 50%
- หากราคา Bitcoin ลงต่ำกว่า 50% ก็จะทำการนำ THB ออกมาซื้อ Bitcoin เก็บไว้ในช่วงราคาถูก เพื่อนำไปขายตอนราคาขึ้น

# How to build
รันคำสั่ง
`mvn package`

# How to run
นำโฟลเดอร์ config และไฟล์ jar ที่ได้จากการ build มาเก็บไว้ที่โฟลเดอร์ที่ต้องการแล้วรันคำสั่ง
`java -jar bitkub-rebalance-bot.jar`