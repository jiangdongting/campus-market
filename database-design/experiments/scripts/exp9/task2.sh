cat > /data/workspace/myshixun/src/Login.java <<'EOF'
import java.sql.*;
import java.util.Scanner;
public class Login {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Scanner input = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String loginName = input.nextLine();
        System.out.print("请输入密码：");
        String loginPass = input.nextLine();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/finance?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "123123";
            connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from client where c_mail = ? and c_password = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, loginName);
            pstmt.setString(2, loginPass);
            resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                System.out.println("登录成功。");
            }else{
                System.out.println("用户名或密码错误！");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the JDBC Driver!");
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if(resultSet != null) resultSet.close();
                if(pstmt != null) pstmt.close();
                if(connection != null) connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        input.close();
    }
}
EOF
echo "Login.java写入完成"
