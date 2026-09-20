USE Company;
SELECT * FROM tb_emp WHERE DeptId=301 AND Salary>3000;
SELECT * FROM tb_emp WHERE DeptId IN(301,303);
