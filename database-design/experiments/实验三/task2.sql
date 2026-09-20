USE Company;
#请在此处添加实现代码
########## Begin ##########
########## update the value ##########
UPDATE tb_emp
SET Name='Tracy',DeptId=302,Salary=4300.00
WHERE Name='Carly';
########## End ##########
SELECT * FROM tb_emp;
