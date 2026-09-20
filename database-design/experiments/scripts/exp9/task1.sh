cat > /data/workspace/myshixun/src/Client.java <<'EOF'
import java.sql.*;
public class Client {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/finance?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "123123";
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            String sql = "SELECT c_name,c_mail,c_phone FROM client WHERE c_mail IS NOT NULL";
            resultSet = statement.executeQuery(sql);
            System.out.println("姓名\t邮箱\t\t\t\t电话");
            while(resultSet.next()){
                String name = resultSet.getString("c_name");
                String mail = resultSet.getString("c_mail");
                String phone = resultSet.getString("c_phone");
                System.out.println(name+"\t"+mail+"\t\t"+phone);
            }
         } catch (ClassNotFoundException e) {
             System.out.println("Sorry,can`t find the JDBC Driver!");
             e.printStackTrace();
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         } finally {
             try {
                 if (resultSet != null) {
                     resultSet.close();
                 }
                 if (statement != null) {
                     statement.close();
                 }
                 if (connection != null) {
                     connection.close();
                 }
             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             }
         }
    }
}
EOF
echo "Client.java覆盖成功"
