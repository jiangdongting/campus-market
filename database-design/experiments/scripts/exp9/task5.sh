cat > /data/workspace/myshixun/src/ChangePass.java <<'EOF'
import java.sql.*;
import java.util.Scanner;
public class ChangePass {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/finance?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    static final String USER = "root";
    static final String PASS = "123123";
    
    /**
     * 修改客户密码
     *
     * @param connection 数据库连接对象
     * @param mail 客户邮箱,也是登录名
     * @param password 客户登录密码
     * @param newPass  新密码
     * @return
     *   1 - 密码修改成功
     *   2 - 用户不存在
     *   3 - 密码不正确
     *  -1 - 程序异常(如没能连接到数据库等）
     */
    public static int passwd(Connection connection,
                             String mail,
                             String password, 
                             String newPass){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String querySql = "SELECT c_password FROM client WHERE c_mail = ?";
            pstmt = connection.prepareStatement(querySql);
            pstmt.setString(1,mail);
            rs = pstmt.executeQuery();
            if(!rs.next()){
                return 2;
            }
            String dbPwd = rs.getString("c_password");
            if(!dbPwd.equals(password)){
                return 3;
            }
            pstmt.close();
            rs.close();
            String updateSql = "UPDATE client SET c_password = ? WHERE c_mail = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setString(1,newPass);
            pstmt.setString(2,mail);
            pstmt.executeUpdate();
            return 1;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }finally {
            try{
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    // 不要修改main() 
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        while(sc.hasNext())
        {
            String input = sc.nextLine();
            if(input.equals(""))
                break;
            String[]commands = input.split(" ");
            if(commands.length ==0)
                break;
            String email = commands[0];
            String pass = commands[1];
            String pwd1 = commands[2];
            String pwd2 = commands[3];
            if (pwd1.equals(pwd2)) {
             int n = passwd(connection, email, pass, pwd1);  
             System.out.println("return: " + n);
            } else {
             System.out.println("两次输入的密码不一样!");
            }
        }
    }
}
EOF
echo "✅ ChangePass.java写入完成，可以提交评测"
